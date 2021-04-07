package it.esedra.corso.journal.collections;

import it.esedra.corso.collections.AbstactCollections;
import it.esedra.corso.collections.Iterator;
import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.Video;

public class VideoCollection extends JournalAbstactCollections<Video> implements Collection<Video> {

	public VideoCollection() {
		super(new Video[AbstactCollections.DEFAULT_SIZE]);
	}

	@Override
	public Iterator<Video> createIterator() {
		return new Iterator<Video>(super.toArray());
	}

}
