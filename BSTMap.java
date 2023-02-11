/**
 * Author: Hannah Soria
 * Date: 4/11/2022
 * File: BTSMap.java
 * Section lab C, Lecture A
 * Lab 7: Binary Search Trees and Sets
 * CS231 Spring 2022

Template for the BSTMap classes
Fall 2020
CS 231 Project 6
*/
import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {
	// fields here
    private TNode<K,V> root;
    private Comparator<K> comp;
    private int size;
    //private int total = 0;

	// constructor: takes in a Comparator object
	public BSTMap( Comparator<K> comp ) {
		// initialize fields heres
        this.root = null;
        this.size = 0;
        this.comp = comp;
	}

	// adds or updates a key-value pair
	// If there is already a pair with new_key in the map, then update
	// the pair's value to new_value.
	// If there is not already a pair with new_key, then
	// add pair with new_key and new_value.
	// returns the old value or null if no old value existed
	public V put( K key, V value ) {
        // check for and handle the special case
		// call the root node's put method
        if (this.root == null){
            this.root = new TNode<K, V>(key, value);
            return null;
        } else {
            return this.root.put(key, value, comp);
        }
    }

    // gets the value at the specified key or null
    public V get( K key ) {
        // check for and handle the special case
        // call the root node's get method
        if (root !=null){
            return root.get(key, comp);
        } else {
            return null;
        }
    }

    // Returns an ArrayList of all the keys in the map. There is no
    // defined order for the keys.
    public ArrayList<K> keySet(){
        ArrayList<K> keyArray = new ArrayList<K>();
        if (this.root != null){
            ArrayList<K> keyList = this.root.keySet(keyArray);
            return keyList;
        } else{
            return null;
        }
    }

    // Returns an ArrayList of all the values in the map. These should
    // be in the same order as the keySet.
    public ArrayList<V> values(){
        ArrayList<V> valueArray = new ArrayList<V>();
        if (this.root != null){
            for (K k: keySet()){
                V v = this.get(k);
                valueArray.add(v);
            }
            return valueArray;
        }
        return null;
    }

    // return an ArrayList of pairs.
    public ArrayList<KeyValuePair<K,V>> entrySet(){
        if (this.root == null){
            return null;
        }
        ArrayList<KeyValuePair<K,V>> keyValueArray = new ArrayList<KeyValuePair<K,V>>();
        return this.root.entrySet(keyValueArray);
    }

    // Returns the number of key-value pairs in the map.
    public int size(){
        return this.keySet().size();
    }
        
    // removes all mappings from this MapSet
    public void clear(){
        this.root = null;
    }

    //checks the root case for depth
    public int depth (){
        if (this.root == null){
            return 0;
        } else {
            return this.root.depth();
        }
    }

    //checks to see if the tree already has a key that is given in parameter
    public boolean containsKey(K key){
        if (this.root == null){
            return false;
        }
        return this.root.containsKey(key, comp);
        
    }

    //removes the key pair of the given key
    public V remove(K key){
        // if there is no root/ no tree
        if (this.root == null){
            return null;
        }
        if (this.comp.compare(this.root.data.getKey(), key) == 0){
            V rootVal = this.root.data.getValue();
            ArrayList<KeyValuePair<K,V>> deleteArray = new ArrayList<KeyValuePair<K,V>>();
            this.root.buildArray( deleteArray); //save children in the array
            this.root = null; //removes it and all children
            for (KeyValuePair<K,V> child: deleteArray){
                this.put(child.getKey(), child.getValue()); //replaces the children
            }
            return rootVal;
        }
        //calling the recursive
        return this.root.remove(key, comp);

        
    }

	// entrySet notes: For the sake of the word-counting project, the
    // pairs should be added to the list by a pre-order traversal.

    // You can make this a nested class
    private class TNode<K,V> {
         // need fields for the left and right children
        private TNode<K,V> left;
        private TNode<K,V> right;
        // need a KeyValuePair to hold the data at this node
        private KeyValuePair<K,V> data;
        // constructor, given a key and a value
        public TNode( K k, V v ) {
                // initialize all of the TNode fields
                this.left = null;
                this.right = null;
                this.data = new KeyValuePair<K,V>(k,v);
        }

        // Takes in a key, a value, and a comparator and inserts
        // the TNode in the subtree rooted at this node 

		// Returns the value associated with the key in the subtree
		// rooted at this node or null if the key does not already exist
        public V put( K key, V value, Comparator<K> comp ) {
            int compResult = comp.compare(data.getKey(), key);

            if (compResult ==0){
                V oldVal = this.data.getValue();
                this.data.setValue(value);
                return oldVal;
            }
            if (compResult < 0){
                if (this.left != null){
                    return this.left.put( key, value, comp );
                } else {
                    this.left = new TNode<K,V>(key, value);
                    size++;
                    return null;
                }
            } else {
                if (this.right != null){
                    return this.right.put(key, value, comp);
                } else {
                    this.right = new TNode<K,V>(key,value);
                    size++;
                    return null;
                }
            } 
        }
        
        // Takes in a key and a comparator
        // Returns the value associated with the key or null
        public V get( K key, Comparator<K> comp ) {
            if ( comp.compare(data.getKey(), key) == 0){
                return data.getValue();
            }
            if ( comp.compare(data.getKey(), key) < 0){
                if (this.left != null){
                    return left.get(key, comp);
                }
                return null;
            } else {
                if (this.right != null){
                    return right.get(key, comp);
                }
                return null;
            }
        } 

        // Any additional methods you want to add below, for
        // example, for building ArrayLists

        //checks to see if the tree already has a key that is given in parameter
        public boolean containsKey(K key, Comparator<K> comp){
            int compResult = comp.compare(data.getKey(), key);
            if(compResult == 0){
                return true;
            }if (compResult < 0){
                if(this.left == null){
                    return false;
                }
                return this.left.containsKey(key, comp);
            }
            if(compResult > 0){
                if(this.right == null){
                    return false;
                }
            }
            return this.right.containsKey(key, comp);
        }

        // Returns an ArrayList of all the keys in the map. 
        public ArrayList<K>keySet(ArrayList<K>keyArray){
            keyArray.add(this.data.getKey());
            if (this.left != null){
                this.left.keySet(keyArray);
            }
            if (this.right != null){
                this.right.keySet(keyArray);
            }
            return keyArray;
        }

        // return an ArrayList of pairs
        public ArrayList<KeyValuePair<K, V>> entrySet(ArrayList<KeyValuePair<K, V>> entryList){
            entryList.add(this.data);
            if (this.left != null){
                this.left.entrySet(entryList);
            }
            if (this.right != null){
                this.right.entrySet(entryList);
            }
            return entryList;
        }

        //removes the given keyvalue pair
        public V remove(K key, Comparator<K> comp){
            int compResult = comp.compare(data.getKey(), key);
            if (compResult == 0){ // if the key is found
                return null;
            }
            else if (compResult < 0){ //move to the left
                if (this.left == null){
                    return null; 
                } 
                if (comp.compare(this.left.data.getKey(), key) == 0){
                    V leftVal = this.left.data.getValue();
                    ArrayList<KeyValuePair<K,V>> deleteArray = new ArrayList<KeyValuePair<K,V>>();
                    this.left.buildArray( deleteArray); //save children in the array
                    this.left = null; //removes it and all children
                    for (KeyValuePair<K,V> child: deleteArray){
                        this.put(child.getKey(), child.getValue(), comp); //replaces the children
                    }
                    return leftVal;
                }
                //stops recursion takes value back up the tree
                return this.left.remove(key, comp);
            } else if (compResult > 0 ){ //move to right
                if (this.right == null){
                    return null;
                }
                if (comp.compare(this.right.data.getKey(), key) == 0){
                    V rightVal = this.right.data.getValue();
                    ArrayList<KeyValuePair<K,V>> deleteArray = new ArrayList<KeyValuePair<K,V>>();
                    this.right.buildArray( deleteArray); //save children in the array
                    this.right = null; //removes it and all children
                    for (KeyValuePair<K,V> child: deleteArray){
                        this.put(child.getKey(), child.getValue(), comp); //replaces the children
                    }
                    return rightVal;
                }
                //stops recursion takes valu back up tree
                return this.right.remove(key, comp);
            }
            return null;
        }
        
        //builds an array of the children below the removed keyvaluepair
        private void buildArray ( ArrayList<KeyValuePair<K,V>> deleteArray) {
            if( this.left != null){
                deleteArray.add(this.left.data);
                this.left.buildArray( deleteArray);

            }
            if (this.right != null){
                deleteArray.add(this.right.data);
                this.right.buildArray(deleteArray);
            }
        }

        //recurses down the tree and takes count of the depth
        //Elliot and Anna helped me with this function
        public int depth (){
            if (this.left == null){
                return 0;
            }
            if (this.right == null){
                return 0;
            }
            int lDepth = this.left.depth();
            int rDepth = this.right.depth();

            if (this.left == null){
                lDepth = 0;
            } else if (this.right == null) {
                rDepth = 0;
            }
            if (lDepth < rDepth){
                rDepth ++;
                return rDepth;
            } else {
                lDepth ++;
                return lDepth;
            }
        }
    // end of TNode class
}

    // test function
    public static void main( String[] argv ) { //test the methods
        // create a BSTMap
        BSTMap<String, Integer> bst = new BSTMap<String, Integer>( new AscendingString() );

        bst.put( "twenty ", 20 );
        bst.put( "ten ", 10 );
        bst.put( "eleven ", 11 );
        bst.put( "five ", 5 );
        bst.put( "six ", 6 );
        bst.put( "four ", 4 );

        System.out.println( "test get: " + bst.get( "six " ));
        System.out.println( "test keySet: " + bst.keySet());
        System.out.println( "test values: " + bst.values());
        System.out.println( "test entrySet: " + bst.entrySet());
        System.out.println( "test size: " + bst.size());
        System.out.println("contains; " + bst.containsKey("six "));
        System.out.println(bst.entrySet());

        //test of remove
        System.out.println("keySet before remove: " + bst.keySet());
        System.out.println("test remove of: " + bst.remove("five "));
        System.out.println("keySet after remove: " + bst.keySet());
        System.out.println("test remove of: " + bst.remove("twenty "));
        System.out.println("keySet after remove: " + bst.keySet());
    }
}