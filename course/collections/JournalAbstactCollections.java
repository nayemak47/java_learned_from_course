package it.esedra.corso.journal.collections;

import java.util.Arrays;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.collections.interfaces.DataObjectInterface;
import it.esedra.corso.collections.interfaces.Iterator;
@Deprecated
public abstract class JournalAbstactCollections<T extends DataObjectInterface> implements Collection<T> {

	public final static int DEFAULT_SIZE = 10;

	public JournalAbstactCollections() {

	}

	public JournalAbstactCollections(T[] array) {
		this.array = array;
	}

	private T[] array;

	@Override
	public Iterator<T> createIterator() {
		return null;
	}

	@Override
	public void add(T item) {

		boolean flag = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				array[i] = item;
				flag = true;
				break;
			}
		}
		if (flag == false) {
			array = Arrays.copyOf(array, array.length + 1);
			array[array.length - 1] = item;
		}

	}

	@Override
	public void test() {

	}

	@Override
	public T[] toArray() {
		return this.array;

	}
	
	@Override
	public JsonArray toJson() {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		Iterator<T> iterator = this.createIterator();
		
		while (iterator.hasNext()) {
			arrayBuilder.add(iterator.next().toJson());
		}
		
		return arrayBuilder.build();

	}
	
	

}
