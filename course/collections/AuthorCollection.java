package it.esedra.corso.journal.collections;

import it.esedra.corso.collections.AbstactCollections;
import it.esedra.corso.collections.Iterator;
import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.Author;

public class AuthorCollection extends JournalAbstactCollections<Author> implements Collection<Author> {

	public AuthorCollection() {
		super(new Author[AbstactCollections.DEFAULT_SIZE]);
	}

	@Override
	public Iterator<Author> createIterator() {
		return new Iterator<Author>(super.toArray());
	}

}
