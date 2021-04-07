package it.esedra.corso.journal.collections;

import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.User;
import it.esedra.corso.journal.UserBuilder;

public class UserCollectionTest {

	public UserCollectionTest() {

	}

	public static void main(String[] args) {

		Collection<User> userCollection = new UserCollection();

		User username1 = new UserBuilder().setName("Mario").setSurname("Rossi").setEmail("mario.rossi@boo.it")
				.setPassword("tttrrr").setRegistration("01/01/2016").build();
		User username2 = new UserBuilder().setName("Ruggero").setSurname("Bianchi").setEmail("ruggero.bianchi@boo.it")
				.setPassword("fffeee").setRegistration("21/11/2018").build();

		userCollection.add(username1);
		userCollection.add(username2);

		userCollection.test();

	}

}
