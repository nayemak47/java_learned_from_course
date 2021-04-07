package it.esedra.corso.journal.collections;

import it.esedra.corso.collections.AbstactCollections;
import it.esedra.corso.collections.Iterator;
import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.Image;

public class ImageCollection extends JournalAbstactCollections<Image> implements Collection<Image> {

	public ImageCollection() {
		super(new Image[AbstactCollections.DEFAULT_SIZE]);
	}

	@Override
	public Iterator<Image> createIterator() {
		return new Iterator<Image>(super.toArray());
	}

}
