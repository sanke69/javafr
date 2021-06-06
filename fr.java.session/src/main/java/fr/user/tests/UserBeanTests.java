package fr.user.tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.java.user.data.Identity;
import fr.user.data.IdentityBean;
import fr.user.data.properties.AddressBean;
import fr.user.data.properties.EmailAddressBean;
import fr.user.data.properties.PhoneNumberBean;

public class UserBeanTests {

	public static void main(String[] args) {
		String file = "/tmp/bean_test.bin";

		saveBean(defaultIdentity(), file);
		Identity id = loadBean(file);
		
		System.out.println(id);
	}

	public static Identity loadBean(String _filename) {
		ObjectInputStream ois = null;

		Identity identity = null;

		try (FileInputStream fichier = new FileInputStream(_filename)) {
			ois = new ObjectInputStream(fichier);
			identity = (Identity) ois.readObject();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		    System.out.println("done");
		}

		return identity;
	}

	public static void saveBean(Identity _identity, String _filename) {
		ObjectOutputStream oos = null;

		try (FileOutputStream fichier = new FileOutputStream(_filename)) {
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(_identity);
			oos.flush();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("done");
		}
	}

	public static Identity defaultIdentity() {
		return new IdentityBean.Builder()
					.setFirstName("Steve")
					.setLastName("PECHBERTI")
					.setSurname("sanke")
					.addAddress(
							new AddressBean(
									"98", "Boulevard de la Reine",
									"VERSAILLES", "78000",
									"Idf", "France"), true)
					.addEmailAddress(new EmailAddressBean("steve.pechberti@laposte.net"), true)
					.addEmailAddress(new EmailAddressBean("steve.pechberti@gmail.com"), false)
					.addEmailAddress(new EmailAddressBean("spechberti@yahoo.fr"), false)
					.addPhoneNumber(new PhoneNumberBean("0622429183"), false)
					.build();
	}
}
