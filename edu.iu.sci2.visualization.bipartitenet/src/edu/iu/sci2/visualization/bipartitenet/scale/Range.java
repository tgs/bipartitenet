package edu.iu.sci2.visualization.bipartitenet.scale;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;

public class Range<T> {
	private T min = null;
	private T max = null;
	private final Ordering<T> ordering;
	
	private Range(Ordering<T> ordering) {
		this.ordering = ordering;
	}
	
	public static <T extends Comparable<T>> Range<T> create() {
		return new Range<T>(Ordering.<T>natural());
	}
	
	public static <T> Range<T> createWithOrdering(Ordering<T> ordering) {
		return new Range<T>(ordering);
	}
	
	public void consider(T item) {
		min = ordering.nullsLast().min(min, item);
		max = ordering.nullsFirst().max(max, item);
	}
	
	public void considerAll(Iterable<T> items) {
		for (T item : items) {
			consider(item);
		}
	}
	
	public T getMin() {
		Preconditions.checkState(min != null, 
				"Range is invalid because no non-null items have been submitted");
		return min;
	}
	
	public T getMax() {
		Preconditions.checkState(min != null, 
				"Range is invalid because no non-null items have been submitted");
		return max;
	}
}
