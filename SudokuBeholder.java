import java.util.*;

class SudokuBeholder implements Iterable<Brett> {
    int antallLoesninger;
    Node start;
    Node slutt;

    public void add(Brett nyttBrett) {
	Node nyNode = new Node(nyttBrett);
	if (antallLoesninger > 750) {
	    antallLoesninger++;
	    return;
	}

	if (start == null) {
	    start = nyNode;
	    slutt = nyNode;
	    antallLoesninger++;
	    return;
	}
	slutt.neste = nyNode;
	slutt = nyNode;
	antallLoesninger++;
    }

    public int getAntLoesninger() {
	return antallLoesninger;
    }

    public Iterator<Brett> iterator() {
	return new BeholderIterator();
    }
	    
	


    private class Node {
	
	Brett objekt;
	Node neste;

	Node(Brett obj) {
	    objekt = obj;
	}
    }

    private class BeholderIterator implements Iterator<Brett> {
	Node posisjon = start;

	public boolean hasNext() {
	    return posisjon != null;    
	}

	public Brett next() {
	    Brett retur = posisjon.objekt;
	    posisjon = posisjon.neste;
	    return retur;
	}

	public void remove() {}
    }
}