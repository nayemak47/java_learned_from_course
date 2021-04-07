package it.esedra.corso.journal.collections;

import it.esedra.corso.collections.AbstactCollections;
import it.esedra.corso.collections.Iterator;
import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.User;

public class UserCollection extends JournalAbstactCollections<User> implements Collection<User> {

	public UserCollection() {
		super(new User[AbstactCollections.DEFAULT_SIZE]);
	}

	@Override
	public Iterator<User> createIterator() {
		return new Iterator<User>(super.toArray());
	}

}
