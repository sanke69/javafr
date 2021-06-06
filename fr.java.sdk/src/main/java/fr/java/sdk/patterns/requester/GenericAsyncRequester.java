package fr.java.sdk.patterns.requester;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import fr.java.jvm.properties.id.IDs;
import fr.java.lang.properties.ID;
import fr.java.lang.tuples.Pair;
import fr.java.sdk.lang.Tuples;

public abstract class GenericAsyncRequester<REQUEST, RESPONSE, RESULT> implements AsyncRequester<REQUEST, RESPONSE, RESULT> {
	private static final int 					externalOutputPeriod = 40;
	private static final int 					workerIdlePeriod     = 40;

	private final int 							POOL_SIZE;

	private final Queue<Pair<ID, REQUEST>> 		userRequests   = new ConcurrentLinkedQueue<Pair<ID, REQUEST>>();
	private final Map<ID, Consumer<RESULT>> 	userHandlers   = new HashMap<ID, Consumer<RESULT>>();

	private final Map<ID, RequestFutureTask> 	tasks          = new HashMap<ID, RequestFutureTask>();

	private final ReentrantLock 				lock           = new ReentrantLock();	// TODO:: WARNING:: Maybe functional for only mono-thread...
	private final Condition 					notTerminated  = lock.newCondition();

	private final Thread						readerThread;
	private final ExecutorService 	  			workerPool;
	private final Thread						workerThread;

	public GenericAsyncRequester(int _nbSimultaneousTasks) {
		super();
		POOL_SIZE    = _nbSimultaneousTasks;

		readerThread = createExternalOutputReader();
		workerPool   = createWorkerExecutorService(POOL_SIZE);
		workerThread = createWorkerManagerThread();
	}

	@Override
	public final void 					newRequest(REQUEST _cmdLine) {
		ID requestId = IDs.random(16);
		userRequests.add(Tuples.of(requestId, _cmdLine));
	}
	@Override
	public final void 					newRequest(REQUEST _cmdLine, Consumer<RESULT> _afterRun) {
		ID requestId = IDs.random(16);
		userRequests.add(Tuples.of(requestId, _cmdLine));
		userHandlers.put(requestId, _afterRun);
	}
	@Override
	public final void 					newRequests(REQUEST[] _cmdLines) {
		for(REQUEST cmdLine : _cmdLines) {
			ID requestId = IDs.random(16);
			userRequests.add(Tuples.of(requestId, cmdLine));
		}
	}
	@Override
	public final void 					newRequests(REQUEST[]  _cmdLines, Consumer<RESULT> _afterRun) {
		for(int i = 0; i < _cmdLines.length; ++i) {
			ID      requestId = IDs.random(16);
			REQUEST cmdLine   = _cmdLines[i];
			userRequests.add(Tuples.of(requestId, cmdLine));
			
			if(i == _cmdLines.length - 1)
				userHandlers.put(requestId, _afterRun);
		}
	}

	// Internal Implementation to Communicate with External Process
	protected abstract void		 		writeRequest(OutputStream _os, REQUEST _request);
	protected abstract RESPONSE[] 		readResponses(InputStream _is);

	protected abstract Process	 		getExternalProcess();
	protected abstract REQUEST   		getRequestForInternalUsage(ID _id, REQUEST _externalRequest);
	protected abstract boolean 			isResponseForInternalUsage(RESPONSE _receivedResponse);
	protected abstract Pair<ID, RESULT> getResultForInternalUsage(RESPONSE _internalResponse);

	private final Thread				createExternalOutputReader() {
		return new Thread(() -> {
			RESPONSE[] externalOutputs;

			while(!Thread.currentThread().isInterrupted()) {

				if((externalOutputs = readResponses( getExternalProcess().getInputStream() )) != null) {

					for(RESPONSE str : externalOutputs) {
						if(!isResponseForInternalUsage(str)) {
							if(isDisplayEnabled())
								onNewResponse(str);
						} else {
							Pair<ID, RESULT> results = getResultForInternalUsage(str);
							onNewReturnValue(results.getFirst(), results.getSecond());
						}
					}

				}

				try {
					Thread.sleep(externalOutputPeriod);
				} catch (InterruptedException e) { Thread.currentThread().interrupt(); }

			}

		});
	}
	private final ExecutorService		createWorkerExecutorService(int _nbSimultaneousTasks) {
		if(_nbSimultaneousTasks == 1)
			return Executors.newSingleThreadExecutor(r -> {
				Thread t = new Thread(r, "processx-session");
				t.setPriority(Thread.MIN_PRIORITY);
				t.setDaemon(true);
				return t;
			});
		else
			return Executors.newFixedThreadPool(_nbSimultaneousTasks, new ThreadFactory() {
				private int count = 0;

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "requester-session-" + count++);
					t.setPriority(Thread.MIN_PRIORITY);
					t.setDaemon(true);
					return t;
				}

			});
			
	}
	private final Thread				createWorkerManagerThread() {
		return new Thread(() -> {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					if(!userRequests.isEmpty()) {
						while(tasks.size() < POOL_SIZE) {
							try {
								Pair<ID, REQUEST> userRequest = userRequests.remove();

								RequestFutureTask dataWorker = new RequestFutureTask( new RequestWorker( userRequest.getFirst(), userRequest.getSecond() ) );

								tasks.put(userRequest.getFirst(), dataWorker);
								workerPool.execute( dataWorker );

							} catch(NoSuchElementException e) {
								e.printStackTrace();
								break;
							}
						}
					}
					
					if(!tasks.isEmpty()) {
						for(ID taskId : tasks.keySet()) {
							RequestFutureTask task = tasks.get(taskId);
							if( task.isDone() && task.getWorker().isDone()) {

								Consumer<RESULT> resultHandler = userHandlers.get(taskId);
								if(resultHandler != null)
									resultHandler.accept(task.getWorker().returnCode);

								tasks.remove(taskId);
							} else if( task.isCancelled() ) {
								System.err.println("Task aborted");

								tasks.remove(taskId);
							}
						}
					} else
							Thread.sleep(workerIdlePeriod);

				} catch(InterruptedException e) { Thread.currentThread().interrupt(); }
			}
		});
	}

	public final void 					start() {
		startExternalOutputReader();
		startRequestWorkerManager();
	}
	public final void 					stop() {
		stopRequestWorkerManager();
		stopExternalOutputReader();

		userRequests.clear();
	}

	protected final void				startExternalOutputReader() {
		readerThread.start();
	}
	protected final void				stopExternalOutputReader() {
		readerThread.interrupt();
	}

	protected final void				startRequestWorkerManager() {
		workerThread.start();
	}
	protected final void				stopRequestWorkerManager() {
		workerPool.shutdown();
		workerThread.interrupt();
	}

	protected final void				clearTasksInProgress() {
		userRequests.clear();
		tasks.clear();
	}

	// Internal Implementation to Communicate with IHM
	protected boolean 					isDisplayEnabled() 					{ return false; }
	protected void 						onNewResponse(RESPONSE _response) 	{}

	protected void 						onNewReturnValue(ID _requestId, RESULT _returnCode) {
		tasks.get(_requestId).getWorker().setFinished(_returnCode);
	}

	class RequestWorker implements Callable<RESULT> {
		ID      requestId;
		REQUEST commandLine;
		RESULT  returnCode;

		public RequestWorker(ID _requestId, REQUEST _commandLine) {
			super();
			requestId   = _requestId;
			commandLine = _commandLine;
			returnCode  = null;
		}

		public boolean 				isDone() {
			return returnCode != null;
		}

		@Override
		public RESULT 				call() throws Exception {
			writeRequest(getExternalProcess().getOutputStream(), getRequestForInternalUsage(requestId, commandLine));
			notTerminated.await();

			return returnCode;
		}

		public void 				setFinished(RESULT _returnCode) {
			lock.lock();
			try {
				returnCode = _returnCode;
				notTerminated.signal();
			} finally {
				lock.unlock();
			}
		}

	}

	class RequestFutureTask extends FutureTask<RESULT> implements Future<RESULT> {
		RequestWorker worker;
	
		public RequestFutureTask(RequestWorker _callable) {
			super(_callable);
			worker = _callable;
		}

		public RequestWorker getWorker() {
			return worker;
		}

	}

}
