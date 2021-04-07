package it.esedra.corso.journal.collections;

import it.esedra.corso.collections.AbstactCollections;
import it.esedra.corso.collections.Iterator;
import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.Chapter;

public class ChapterCollection extends JournalAbstactCollections<Chapter> implements Collection<Chapter> {

	public ChapterCollection() {
		super(new Chapter[AbstactCollections.DEFAULT_SIZE]);
	}

	@Override
	public Iterator<Chapter> createIterator() {
		return new Iterator<Chapter>(super.toArray());
	}

}
