module javafr.session {
	requires javafr;
	requires javafr.beans;
	requires javafr.sdk;
	requires javafx.controls;
	
	uses fr.java.user.session.SessionAuthenticator;

	exports fr.session;
	
	exports fr.session.test;
}
