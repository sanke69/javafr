package fr.java.security.enums;

public enum KeyEncryption { 
	NONE		(""), 
	AES128		("-aes128"), 
	AES192		("-aes192"), 
	AES256		("-aes256"), 
	CAMELLIA128	("-camellia128"), 
	CAMELLIA192	("-camellia192"), 
	CAMELLIA256	("-camellia256"), 
	DES			("-des"), 
	DES3		("-des3"), 
	IDEA		("-idea");
	
	String openSslOption;
	
	private KeyEncryption(String _oslOption) {
		openSslOption = _oslOption;
	}

	public String getOpenSSlOption() {
		return openSslOption;
	}

}