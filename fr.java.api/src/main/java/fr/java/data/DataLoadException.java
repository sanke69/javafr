package fr.java.data;

public class DataLoadException extends DataException {
	private static final long serialVersionUID = 6567157291903549562L;

	public DataLoadException() {
		super();
	}
	public DataLoadException(String _msg) {
		super(_msg);
	}
    public DataLoadException(String _msg, Throwable _err) {
        super(_msg, _err);
    }

}
