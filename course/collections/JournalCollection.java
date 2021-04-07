package it.esedra.corso.journal.collections;

import it.esedra.corso.collections.AbstactCollections;
import it.esedra.corso.collections.Iterator;
import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.Journal;

public class JournalCollection extends JournalAbstactCollections<Journal> implements Collection<Journal> {

	public JournalCollection() {
		super(new Journal[AbstactCollections.DEFAULT_SIZE]);
	}

	@Override
	public Iterator<Journal> createIterator() {
		return new Iterator<Journal>(super.toArray());
	}

	
	
}
