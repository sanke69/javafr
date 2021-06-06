package fr.java.data;

public class DataException extends Exception {
	private static final long serialVersionUID = 6567157291903549562L;

	public DataException() {
		super();
	}
	public DataException(String _msg) {
		super(_msg);
	}
    public DataException(String _msg, Throwable _err) {
        super(_msg, _err);
    }

}
