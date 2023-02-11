/**
 * Author: Hannah Soria
 * Date: 4/19/2022
 * File: PQHeap.java
 * Section lab C, Lecture A
 * Lab 9: Heaps
 * CS231 Spring 2022
 */

import java.util.Comparator;

public class PQHeap<T> {
    private Comparator<T> incomp;
    private Object[] heap;
    private int size;

    // a constructor that initializes the empty heap, sets the size to zero, and stores the comparator.
    public PQHeap(Comparator<T> comparator){ 
        this.size = 0;
        Object[] heap = new Object[100000000];
        this.heap = heap;
        incomp = comparator;
    }

    //returns the number of elements in the heap.
    public int size() {
        return size;
    }

    //adds the value to the heap and increments the size. Be sure to use the Comparator to reshape/reheap the heap. You may want to add private methods to handle the reheap process.
    //i recieved help from tess for this function
    public void add(T obj) {
        if (heap[0]!=null){ //if it is not empty
            int currentIndex = size; //index is the end of the heap
            heap[currentIndex] = obj; //the obj is placed at the end
            while (currentIndex > 0 && incomp.compare(obj, (T)parent(currentIndex)) > 0){ //reheap
                T data = (T)heap[((currentIndex)-1)/2];
                heap[((currentIndex)-1)/2] = obj;
                heap[currentIndex] = data;
                currentIndex = (currentIndex - 1)/2;
            }
            size++;
        } else {
            heap[0] = obj; //if the heap is empty
            size++;
        }
    }

    //removes and returns the highest priority element from the heap. Be sure to use the Comparator to reshape/reheap the heap. You may want to add private methods to handle the reheap process.
    //tess and Meredith Green helped me with the reheap part of this function
    public T remove() {
        T first = null; 
        boolean finished = false;
        if (size!= 0) { //of not empty
            first = (T)heap[0]; //first item in heap set to null
            int cur_index = size-1; 
            heap[0] = heap[cur_index];
        }
        else {
            return null;
        }
        int current = 0; 
        while (finished == false) {
            if (((current*2)+1) >= size) { //left child larger or equals than size
                finished = true; 
                continue; //statement breaks one iteration (in the loop), if a specified condition occurs, and continues with the next iteration in the loop. I learned about this via: https://www.w3schools.com/java/java_break.asp
            }
            T value = (T)heap[((current*2)+1)]; //left child 
            int max = (current*2)+1; //left child set to max
            if (((current*2)+2) < size) { //right child small than size
                if (incomp.compare(value, (T)heap[((current*2)+2)]) <= 0) { //compare right to value to right, right iis bigger, have to reheap
                    value = (T)heap[((current*2)+2)]; //right swaps with value
                    max = ((current*2)+2);//value swaps with right
                }
            }
            if (incomp.compare(value, (T)heap[current]) >= 0) { //left child to current, if left is bigger, need to reheap
                heap[max] = heap[current]; 
                heap[current] = value; //swap
                current = max; //max becomes current
            }
            else {
                finished = true; //reaches true once reheaped
            }
        }
        size--;
        return first;
    }

    //return the parent
    public T parent(int i){ 
        return (T)heap[(i-1)/2];
    }

    //returns the leftchild
    public T leftChild(int i){ 
        return (T)heap[(2 * i) + 1];
    }

    //returns the leftchild
    public T rightChild(int i){ 
        return (T)heap[(2 * i) + 2];
    }

    //looks through the heap to see if the key is in it returns true or false
    public boolean containsKey(T obj){
        for (int i = 0; i < heap.length; i ++){
            int compResult = incomp.compare(obj, (T)heap[i]);
            if (compResult == 0){
                return true;
            }
        }
        return false;
    }
}

//functions tested through test file
