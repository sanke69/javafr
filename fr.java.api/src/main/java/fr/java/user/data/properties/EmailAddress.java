package fr.java.user.data.properties;

import java.io.Serializable;

public interface EmailAddress extends Serializable {

	public default String 	getCategory() { return "default"; }

	public String 			getValue();

}
