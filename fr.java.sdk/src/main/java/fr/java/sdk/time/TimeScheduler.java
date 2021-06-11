package fr.java.sdk.time;

import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fr.java.time.Time;
import fr.java.time.TimeOperation;
import fr.java.time.TimeOperator;

public class TimeScheduler<OP extends TimeOperation> {
	public static final Period 		DEFAULT_PERIOD     = Period.ofMonths(1);


	protected final Instant 				 	 start;
	protected final Collection<TimeOperator<OP>> operators;

	public TimeScheduler() {
		this(Instant.now());
	}
	public TimeScheduler(Instant _start) {
		super();
		start     = _start;
		operators = new ArrayList<TimeOperator<OP>>();
	}

	public Instant 									getStart() {
		return start;
	}
	public Collection<? extends TimeOperator<OP>> 	getOperators() {
		return operators;
	}

	public void 									addOperator(TimeOperator<OP> _operator) {
		operators.add(_operator);
	}
	public void 									addOperators(Collection<? extends TimeOperator<OP>> _operators) {
		operators.addAll(_operators);
	}

	public Iterable<OP>								computeSince(Instant _from) {
		return new Iterable<OP>() {
			@Override
			public Iterator<OP> iterator() {
				return new Iterator<OP>() {
					private Instant   last  = _from;
					private Stack<OP> stack = new Stack<OP>();
		
					@Override
					public boolean hasNext() {
						if(!stack.isEmpty())
							return true;

						Collection<OP> cops = TimeOperator.Utils.getNextOperations(operators, last);

						if(cops.isEmpty())
							return false;

						stack.addAll(cops);

						last = Time.addNanos(stack.peek().getTerm(), 1);

						return true;
					}
		
					@Override
					public OP next() {
						if(stack.isEmpty())
							throw new IllegalAccessError();
						return stack.pop();
					}
		
				};
			}
		};
	}
	public Iterable<OP>								computeBetween(Instant _from, Instant _to) {
		return new Iterable<OP>() {
			@Override
			public Iterator<OP> iterator() {
				return new Iterator<OP>() {
					private Instant   last  = _from;
					private Stack<OP> stack = new Stack<OP>();

					@Override
					public boolean hasNext() {
						if(!stack.isEmpty())
							return true;

						Collection<OP> cops = TimeOperator.Utils.getNextOperations(operators, last);

						if(cops.isEmpty())
							return false;

						for(OP co : cops)
							if(co.getTerm().isBefore(_to))
								stack.add(co);

						if(stack.isEmpty())
							return false;

						last = Time.addNanos(stack.peek().getTerm(), 1);

						return true;
					}

					@Override
					public OP next() {
						if(stack.isEmpty())
							throw new IllegalAccessError();
						return stack.pop();
					}

				};
			}
		};
	}
	public Iterable<Collection<OP>>					computeBetween(Instant _from, Instant _to, Period _step) {
		return new Iterable<Collection<OP>>() {
			@Override
			public Iterator<Collection<OP>> iterator() {
				return new Iterator<Collection<OP>>() {
					private boolean  nexted;
					private Instant  last = _from;
					private List<OP> next;

					@Override
					public boolean hasNext() {
						if(last.equals(_to) || last.isAfter(_to))
							return false;

						if(nexted)
							return true;

						Collection<OP> cops;
						Instant t1 = Time.add(last, _step);
						if(t1.isAfter(_to)) t1 = _to;

						next = new ArrayList<OP>();
						while(last.isBefore(t1)) {
							cops = TimeOperator.Utils.getNextOperations(operators, last);

							if(!cops.isEmpty())
								next.addAll(cops);
//								for(OP op : cops)
//									next.add(op);

//							last = Time.addNanos(next.get(next.size() - 1).getTerm(), 1);
//							last = Time.addMillis(next.get(next.size() - 1).getTerm(), 1);
							last = next.get(next.size() - 1).getTerm();
						}

						if(next.isEmpty())
							return false;

//						last = Time.addNanos(stack.peek().getTerm(), 1);

						nexted = true;
						return true;
					}
		
					@Override
					public Collection<OP> next() {
						if(next == null  || next.isEmpty())
							throw new IllegalAccessError();
						nexted = false;
						return next;
					}
		
				};
			}
		};
	}

}
