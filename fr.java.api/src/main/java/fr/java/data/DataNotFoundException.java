package fr.java.data;

public class DataNotFoundException extends DataException {
	private static final long serialVersionUID = 6567157291903549562L;

	public DataNotFoundException() {
		super();
	}
	public DataNotFoundException(String _msg) {
		super(_msg);
	}
    public DataNotFoundException(String _msg, Throwable _err) {
        super(_msg, _err);
    }

}
