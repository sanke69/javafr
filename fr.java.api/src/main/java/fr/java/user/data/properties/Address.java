package fr.java.user.data.properties;

import java.io.Serializable;

public interface Address extends Serializable {

	public default String 	getCategory() { return "default"; }

	public String 			getStreetAddress();
	public String 			getLocation();
	public String 			getZipCode();
	public String 			getState();
	public String 			getCountry();

}
