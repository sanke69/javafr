package fr.java.utils;

public class Processes {

	public static void main(String[] args) {
		System.out.println( ProcessHandle.current() );
		System.out.println( ProcessHandle.current().info() );
		
		ProcessHandle.current().children().forEach(c -> System.out.println( c.info()) );
		

		ProcessHandle.allProcesses().forEach(c -> System.out.println( c.info()) );
	}
}
