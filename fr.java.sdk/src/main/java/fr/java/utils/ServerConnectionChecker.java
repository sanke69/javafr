package fr.java.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class ServerConnectionChecker {
	private static final String	PROPERTY_NAME	= "status";

	private String	hostName;
	private int		portNumber;
	private long	checkPeriod;
	private Timer	timer;
	private boolean	started	= false;
	private boolean	online	= false;

	private PropertyChangeSupport propertyChangeSupport;

	public ServerConnectionChecker(final String _hostname, final int _port, final int _period) {
		this(_hostname, _port, _period, 0);
	}
	public ServerConnectionChecker(final String _hostname, final int _port, final int _period, final int _initialDelay) {
		hostName = _hostname;
		portNumber = _port;
		checkPeriod = _period;

		propertyChangeSupport = new PropertyChangeSupport(this);

		start(_initialDelay);
	}

	public void setCheckPeriod(long _period) {
		checkPeriod = _period;
	}
	public long getCheckPeriod() {
		return checkPeriod;
	}

	public void setHostName(String _hostName) {
		hostName = _hostName;
	}
	public String getHostName() {
		return hostName;
	}

	public void setPortNumber(int _portNumber) {
		portNumber = _portNumber;
	}
	public int getPortNumber() {
		return portNumber;
	}

	public boolean isOnline() {
//		if (!isOnlineFastCheck()) {
//			return false;
//		} else {
			return online;
//		}
	}

	public boolean isStarted() {
		return started;
	}

	public void start() {
		start(0);
	}
	public void start(int _delay) {
		if(!started) {
			timer = new Timer("onlineChecker", true);
			timer.schedule(createTimerTask(), _delay, checkPeriod);
			started = true;
		}
	}
	public void stop() {
		if(started) {
			timer.cancel();
			timer = null;
			started = false;
		}
	}
	public void restart() {
		stop();
		start();
	}

	private TimerTask createTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				doCheck();
			}
		};
	}

	private void doCheck() {
		boolean oldOnline = online;

		if(isOnlineFastCheck()) {
			try(Socket socket = new Socket(hostName, portNumber)) {
				online = (socket.getInputStream() != null);
			} catch(Throwable e) {
				online = false;
			}
		} else {
			online = false;
		}

		if(oldOnline != online)
			propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldOnline, online);
	}

	private boolean isOnlineFastCheck() {
		boolean check = false;
		try {
			InetAddress.getByName(hostName);
			check = true;
		} catch(UnknownHostException e) {
		}
		return check;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
