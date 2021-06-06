package fr.java.patterns.loadable;

//@Deprecated
public interface Loadable {

	public boolean 	isLoading();
	public boolean 	isLoaded();
	public boolean 	hasError();

	public void 	load() throws Exception;

}
