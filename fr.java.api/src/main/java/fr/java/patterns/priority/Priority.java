package fr.java.patterns.priority;

public interface Priority /*extends Comparable<Object>*/ {

	public int getLevel();

	public boolean isLower(Priority _other);
	public boolean isHigher(Priority _other);

	public int compareTo(Priority _other);

}
