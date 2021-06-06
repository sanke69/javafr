package fr.java.sdk.patterns.visitable.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.java.patterns.visitable.Treeable;
import fr.java.patterns.visitable.TreeableVisitor;

public class PrintFullPathVisitor implements TreeableVisitor<String> {
	public static final String _DEFAULT_SEPARATOR = "/";
	private final       String separator;

    public PrintFullPathVisitor() {
        this(_DEFAULT_SEPARATOR);
    }
    public PrintFullPathVisitor(String _separator) {
    	separator = _separator;
    }

    @Override
	public void visit(Treeable<String, ?> tree) {
    	boolean lastSeparator = false;
    	if(tree.getParent() != null) {
	    	List<String>        ancestors = new ArrayList<String>();
	    	Treeable<String, ?> ancestor  = tree.getParent();

	        do {
	        	ancestors.add(ancestor.getData());
	        } while((ancestor = ancestor.getParent()) != null);
	        Collections.reverse(ancestors);

//	        System.out.print( ancestors.stream().reduce((t, u) -> t + SEPARATOR + u).get() );
	        System.out.print( String.join(separator, ancestors) );
	        lastSeparator = true;
    	}
    	System.out.println((lastSeparator ? separator : "") + tree.getData());
	}

}