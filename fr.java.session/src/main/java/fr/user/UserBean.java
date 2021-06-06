package fr.user;

import fr.java.user.data.Identity;
import fr.java.user.data.User;
import fr.user.data.IdentityBean;

public class UserBean implements User {
	Identity identity;

	public UserBean(Identity _identity) {
		super();

		if(_identity != null)
			identity = new IdentityBean(_identity);
	}

	public void     setIdentity(Identity _identity) {
		identity = new IdentityBean(_identity);
	}
	@Override
	public Identity getIdentity() {
		return identity;
	}

}
