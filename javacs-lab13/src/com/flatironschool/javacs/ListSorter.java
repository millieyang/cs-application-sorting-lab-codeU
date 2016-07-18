/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
       if (list.size() <= 1) {
    	   return list;
       }
       int sizeOfList = list.size();
       List<T> firstHalf = mergeSort(new LinkedList<T>(list.subList(0, sizeOfList/2)), comparator);
       List<T> secondHalf = mergeSort(new LinkedList<T>(list.subList(sizeOfList/2, sizeOfList)), comparator);
       
       return merge(firstHalf, secondHalf, comparator);
       
	}
	
	private List<T> merge (List<T> firstHalf, List<T> secondHalf, Comparator<T> comparator) {
		int size = firstHalf.size() + secondHalf.size();
		List<T> sortedList = new LinkedList<T>();
		for (int i=0; i < size ; i++) {
			while (secondHalf.size() > 0 && firstHalf.size() > 0) {
				int comparatorNumber = comparator.compare(firstHalf.get(0), secondHalf.get(0));
				if (comparatorNumber != 0 && comparatorNumber > 0 ) {
					sortedList.add(secondHalf.remove(0));
				}
				else {
					sortedList.add(firstHalf.remove(0));
				}
			}
			if (firstHalf.size() == 0 && secondHalf.size() == 0) {
				break;
			}
			else if (secondHalf.size() == 0) {
				sortedList.add(firstHalf.remove(0));
			}
			else if(firstHalf.size() == 0) {
				sortedList.add(secondHalf.remove(0));
			}
						
		}
		return sortedList;
		
	}
	

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> heap  = new PriorityQueue<T>(comparator);
        for (T element : list) {
        	heap.offer(element);
        }
        list.clear();
        while (!heap.isEmpty()) {
        	list.add(heap.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap  = new PriorityQueue(list.size(), comparator);
		for (T element : list) {
			if (heap.size() < k) {
				heap.offer(element);
			}
			else if (heap.size() == k) {
				T elementToCompare = heap.peek();
				int comp = comparator.compare(elementToCompare, element);
				if (comp < 0) {
					heap.poll();
					heap.offer(element);
				}
			}
		}
		list.clear();
		
		while (!heap.isEmpty()) {
			list.add(heap.poll());
		}
		
        return list;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
