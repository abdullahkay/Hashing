

import java.util.List;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author YOUR NAME HERE
 * @version 1.3
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code STARTING_SIZE}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(STARTING_SIZE);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity
     *            initial capacity of the backing array
     */

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;
    }

    @Override
    public V add(K key, V value) {
        // System.out.println();
        // System.out.println();
        System.out.println("adding " + key);
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            System.out.println("resizing");
            resizeBackingTable(table.length * 2 + 1);
        }
        int i = Math.abs(key.hashCode() % table.length);
        // System.out.println("i: "+i+ " length: "+table.length);
        // System.out.println("adding "+key);

        while ((table[i] != null && !table[i].getKey().equals(key))
                && (table[i] != null && !table[i].isRemoved())) {
            
            i = (i + 1) % table.length;
            
        }
        if (table[i] == null || table[i].isRemoved()) {
            table[i] = new MapEntry<K, V>(key, value);
            size++;
            return null;
        } else {
            V returnVal = table[i].getValue();
            table[i] = new MapEntry<K, V>(key, value);
            return returnVal;
        }

    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        int i = key.hashCode() % table.length;
        int counter = 0;

        while (table[i] != null
                && (!table[i].getKey().equals(key) && counter < table.length)) {
            i = (i + 1) % table.length;
            counter++;
        }
        if (counter == table.length || table[i] == null) {
            // key not in table
            throw new java.util.NoSuchElementException();
        } else {
            if (table[i].isRemoved()) {
                throw new java.util.NoSuchElementException();
            }
            V returnVal = table[i].getValue();
            size--;
            table[i].setRemoved(true);
            return returnVal;

        }

    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = key.hashCode() % table.length;
        int counter = 0;

        while (table[i] != null
                && ((!table[i].getKey().equals(key)
                        && counter < table.length) || (table[i]
                        .getKey().equals(key) && table[i].isRemoved()
                        && counter < table.length))) {
            i = (i + 1) % table.length;
            counter++;
        }
        if (counter == table.length || table[i] == null) {
            // key not in table
            throw new java.util.NoSuchElementException();
        } else {

            return table[i].getValue();

        }
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = key.hashCode() % table.length;
        int counter = 0;

        while (table[i] != null
                && ((!table[i].getKey().equals(key)
                        && counter < table.length) || (table[i]
                        .isRemoved() && counter < table.length))) {
            i = (i + 1) % table.length;
            counter++;
        }
        return !(counter == table.length || table[i] == null);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;

    }

    @Override
    public int size() {
        return size;

    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new java.util.HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                keySet.add(table[i].getKey());
            }
        }
        return keySet;

    }

    @Override
    public List<V> values() {
        List<V> listValues = new java.util.ArrayList<V>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                listValues.add(table[i].getValue());
            }
        }
        return listValues;

    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < 1 || length < size) {
            throw new IllegalArgumentException();
        }

        System.out.println("resize");
        for (int p = 0; p < table.length; p++) {
            MapEntry<K, V> item = table[p];
            if (item == null) {
                System.out.println(" ");
            } else {
                System.out.println(item.getKey());
            }
        }
        MapEntry<K, V>[] newArray = (MapEntry<K, V>[]) new MapEntry[length];

        for (int q = 0; q < table.length; q++) {
            int counter = 0;

            MapEntry<K, V> item = table[q];
            System.out.println("q is" + q + " item is " + item);
            if (item != null) {

                K key = item.getKey();
                int i = Math.abs(item.getKey().hashCode() % newArray.length);
                System.out.println("resize adding " + key);
                int start = i;

                while ((newArray[i] != null && !newArray[i].getKey()
                        .equals(key))
                        && (newArray[i] != null && !newArray[i].isRemoved())
                        && counter < newArray.length - 1) {
                    i = (i + 1) % newArray.length;
                    if (i == start) {
                        break;
                    }
                }
                if (newArray[i] == null || newArray[i].isRemoved()) {
                    newArray[i] = item;
                    counter++;

                } else {

                    newArray[i] = item;
                    counter++;
                }
            }

        }
        table = newArray;

    }

    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
