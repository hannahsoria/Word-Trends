/**
 * Author: Hannah Soria
 * Date: 4/5/2022
 * File: Hashmap.java
 * Section lab C, Lecture A
 * Lab 8: Hash Tables
 * CS231 Spring 2022
 */

import java.util.ArrayList;
import java.util.Comparator;

//Meredith helped me debug this 
public class Hashmap <K,V> implements MapSet <K,V> {
    private LinkedList[] hashmap;
    private int capacity;
    private Comparator<K> incomp;
    private int size;
    private int collisions;

    // Hashmap constructor that starts with default size hash table
    public Hashmap(Comparator<K> incomp) {
        this.capacity = 1000000;
        LinkedList[] hashmap = new LinkedList[this.capacity];
        for(int i = 0; i < this.capacity; i++){
            hashmap[i] = new LinkedList<>();
        }
        this.hashmap = hashmap;
    }

    // Hashmap constructor that starts with the suggecsted capacity hash table
    public Hashmap( Comparator<K> incomp, int capacity ) {
        this.capacity = capacity;
        this.incomp = incomp;
        LinkedList[] hashmap = new LinkedList[this.capacity];
        for(int i = 0; i < this.capacity; i++){
            hashmap[i] = new LinkedList<>();
        }
        this.hashmap = hashmap;
    }

    // look at the linked list in the hash location, see if the new_key is anywhere in this 
    // list. If it isn't, add in a new keyvaluepair into this list, otherwise just update the 
    // value associated with this new_key
    public V put(K new_key, V new_value) {
        KeyValuePair<K,V> kvpair = new KeyValuePair<K,V>(new_key, new_value);
        int hash = hashFunction(new_key);
        
        //for every keyvaluepair in the hashmap
        LinkedList<KeyValuePair<K,V>> entry = this.hashmap[hash];
        if (entry.size() > 0){
            collisions ++;
        }
        for(KeyValuePair<K,V> kvp : entry){
            //if there is kvp
            if (new_key.equals(kvp.getKey())) { 
                kvp.setValue(new_value);
                return new_value;
            }
        }
        this.hashmap[hash].addFirst(kvpair);
        size++;
        if (1.0 * entry.size() / capacity > .25){
            rehash();
        }
        return new_value;
    }

    // Returns true if the map contains a key-value pair with the given key
    public boolean containsKey(K key) {
        int hash = hashFunction(key);
        LinkedList<KeyValuePair<K,V>> entry = this.hashmap[hash];
        for(KeyValuePair<K,V> kvp : entry){
            if (kvp.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    // Returns the value associated with the given key.
    // If that key is not in the map, then it returns null.
    public V get(K key) {
        int hash = hashFunction(key);
        LinkedList<KeyValuePair<K,V>> entry = this.hashmap[hash];
        for(KeyValuePair<K,V> kvp : entry){
            if (kvp.getKey().equals(key)){
                return kvp.getValue();
            }
        }
        return null;
    }

    // Returns an ArrayList of all the keys in the map. There is no
    // defined order for the keys.
    public ArrayList<K> keySet() {
        ArrayList<K> keySetArr = new ArrayList<K>();
        for (int i = 0; i < capacity; i++) {
            LinkedList<KeyValuePair<K,V>> entry = this.hashmap[i];
            for (KeyValuePair<K,V> kvp : entry){
                keySetArr.add(kvp.getKey());
            }
        }
        return keySetArr;
    }

    // Returns an ArrayList of all the values in the map. These should
    // be in the same order as the keySet.
    public ArrayList<V> values() {
        ArrayList<V> valSetArr = new ArrayList<V>();
        for (int i = 0; i < capacity; i++) {
            LinkedList<KeyValuePair<K,V>> entry = this.hashmap[i];
            for (KeyValuePair<K,V> kvp : entry){
                valSetArr.add(kvp.getValue());
            }
        }
        return valSetArr;
    }

    // return an ArrayList of pairs.
    public ArrayList<KeyValuePair<K, V>> entrySet() { //
        ArrayList<KeyValuePair<K,V>> entrySetArr = new ArrayList<KeyValuePair<K,V>>();
        for (int i = 0; i < capacity; i++) {
            LinkedList<KeyValuePair<K,V>> entry = this.hashmap[i];
            for (KeyValuePair<K,V> kvp : entry){
                entrySetArr.add(kvp);
            }
        }
        return entrySetArr;
    }

    public int size() { //returns the size
        return size;
    }

    public void clear() { // clears the map
        hashmap = new LinkedList[this.capacity];
        for(int i = 0; i < this.capacity; i++){
            hashmap[i] = new LinkedList<>();
        }
        size = 0;
    }

    public int getCollisions(){ // returns the number of collisions
        return collisions;
    }

    //take in the key and uses hascode on it casted with absolute value then mod the hashmap length
    private int hashFunction(K key){
        return Math.abs(key.hashCode()) % this.capacity;
    }

    //doubles the capcity and puts the pairs into the new array with the new capacity
    //Bender helped me with this function
    public void rehash(){
        ArrayList<KeyValuePair<K, V>> entrySetArray = entrySet();
        hashmap = new LinkedList[this.capacity * 2];
        this.capacity = this.capacity * 2;
        size = 0;
        collisions = 0;
        for(int i = 0; i < this.capacity; i++){
            hashmap[i] = new LinkedList<>();
        }
        for (KeyValuePair<K, V> kvp : entrySetArray){
            put(kvp.getKey(), kvp.getValue());
        }
    }

    public static void main(String[]args){ //test the functions
        AscendingString string = new AscendingString();
        Hashmap hash = new Hashmap(string);
        hash.put("hi", 2);
        hash.put("hello", 5);
        hash.put("yo", 8);
        System.out.println("check of the values: " + hash.values());
        System.out.println("checl of the get: " + hash.get("hello"));
        System.out.println("check of the put: " + hash.put("yo", 6));
        System.out.println("check of the keySet: " + hash.keySet());
        System.out.println("values: " + hash.values());
        System.out.println("check of the entrySet: " + hash.entrySet());
        System.out.println("check of the size: " + hash.size());
    }  
}
