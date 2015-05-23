package com.trendginx.nlp.util;


public class ComparableObj implements Comparable<ComparableObj> {
	public String input;
	public int occurrences;

	public ComparableObj(String input, int occurrences) {
		super();
		this.input = input;
		this.occurrences = occurrences;
	}

	@Override
	public int compareTo(ComparableObj arg0) {
		int compare = Integer.compare(arg0.occurrences, this.occurrences);
		return compare != 0 ? compare : input.compareTo(arg0.input);
	}

	@Override
	public int hashCode() {
		final int x = 19;
		int result = 9;
		result = x * result + occurrences;
		result = x * result + ((input == null) ? 0 : input.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object compareObj) {
		if (this == compareObj)
			return true;
		if (compareObj == null)
			return false;
		if (getClass() != compareObj.getClass())
			return false;
		ComparableObj other = (ComparableObj) compareObj;
		if (occurrences != other.occurrences)
			return false;
		if (input == null) {
			if (other.input != null)
				return false;
		} else if (!input.equals(other.input))
			return false;
		return true;
	}
}
