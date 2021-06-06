package fr.java.user.data;

public interface User {

	public Identity 			getIdentity();
	public default Organisation getOrganisation() { return null; }

}
