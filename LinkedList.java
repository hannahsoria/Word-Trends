/**
 * Author: Hannah Soria
 * Date: 3/7/2022
 * File: LinkedzList.java
 * Section lab C, Lecture A
 * Lab: 5 Linked Lists
 * CS231 Spring 2022
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;    // defines the Iterator interface
//import java.util.ArrayList;   
//import java.util.Collections; // contains a shuffle function

public class LinkedList <T> implements Iterable <T>{ //Meredith helped me with this

    private int size;
    private Node<T> head;

    private class Node <T>{
        private Node <T> next;
        private T data;

        public Node(T item){ // constructor that initializes next to null and the container field to item.
            data = item;
        }

        public T getThing(){ //public T getThing()
            return data;
        }

        public void setNext(Node<T> n){ //sets next to the given node.
            next = n;
        }

        public Node<T> getNext(){ //returns the next field.
            return next;
        }
    }

    public Iterator <T> iterator(){ //return a new LLIterator with head passed to the constructor, shown as the following code
        return new LLIterator(this.head);
    }

    private class LLIterator implements Iterator <T> {
        
        private Node<T> nextNode;

        public LLIterator(Node<T> head){ //the constructor for the LLIterator given the head of a list.
            nextNode = head;
        }

        public T next() { //returns the next item in the list, which is the item contained in the current node.
            if (hasNext()){
                T data = nextNode.getThing();
                nextNode = nextNode.getNext();
                return data;
            } else{
                return null;
            }
        }

        public boolean hasNext(){ //returns true if there are still values to traverse (if the current node reference is not null).
            if (nextNode != null){
                return true;
            } else {
                return false;
            }
        }

        public void remove(){ //does nothing. Implementing this function is optional for an Iterator.
        }
    }

    public LinkedList(){// constructor that initializes the fields so it is an empty list.
        size = 0;
        head = null;
    }

    public void clear(){ //resets the fields so it is an empty list
        size = 0;
        head = null;
    }

    public int size(){ //returns the size of the list
        return size;
    }

    public void addFirst(T item){ //inserts the item at the beginning of the list
        Node <T> newNode = new Node <T>(item);
        if (head == null){
            //newNode.setNext(head.getNext());
            head = newNode;
            size++;
        }else{
            newNode.setNext(head);
            head = newNode;
            size++;
        }
    }

    public void addLast(T item){ //appends the item at the end of the list
        Node<T> temp1 = head;
        Node <T> newNode = new Node <T> (item);
        if (head == null){
            head = newNode;
            size++;
        } else {
            while (temp1.getNext() != null ){
            temp1 = temp1.getNext();
            }
            temp1.setNext(newNode);
            size++;
        } 
    }

    public void add (int index, T item){ //inserts the item at the specified poistion in the list
        Node<T> temp2 = head;
        Node<T> newNode = new Node<T>(item);
        if (head == null){
            head = newNode;
            size++;
        }
        else if(index == 0){
            this.addFirst(item);
        } 

        else if (index == size){
            this.addLast(item);
        }
        else {
            for (int i = 0; i < size - 1; i++){
                if (index - 1 == i){
                    newNode.setNext(temp2.getNext());
                    temp2.setNext(newNode);
                }
                if (temp2 != null){
                    temp2 = temp2.getNext();
                }
                
            }
            size++;
        }
    }

    public T remove (int index){ //removes the item at the specified position in the list
        Node<T> current = head.getNext();
        Node<T> previous = head;
        int count = 0;
        if (index == 0){
            if(head.getNext()!= null){
                head = head.getNext();
            }
            else{
                head = null;
            }
        }
        while (current != null){
            if (count == index - 1){
                if (index == size){
                    current = null;
                }
                else{
                    previous.setNext(current.getNext());
                }
            }
            previous = current;
            current = current.getNext();
            count++;
        }
        size--;
        return null;
    }

    //bender helped me with this
    public ArrayList<T> toArrayList(){ //returns an ArrayList of the list contents in order.
        ArrayList<T> array = new ArrayList<T>();
        for (T item:this){
            array.add(item);
        }
        return array;
    }

    public ArrayList<T> toShuffledList(){ //returns an ArrayList of the list contents in shuffled order.
        ArrayList<T> array = toArrayList();
        Collections.shuffle(array);
        return array;
    }   
}
