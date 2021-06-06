package fr.java.sdk.patterns.visitable.utils;

import fr.java.patterns.visitable.Treeable;
import fr.java.patterns.visitable.TreeableVisitor;
import fr.java.patterns.visitable.Visitable;
import fr.java.utils.Strings;

public class PrintIndentedVisitor implements TreeableVisitor<String> {
    private final String indent;

    public PrintIndentedVisitor(String _indent) {
    	super();
        indent = _indent;
    }
    public PrintIndentedVisitor(int _indent) {
    	super();
        indent = Strings.repeat(' ', _indent);
    }

    public void visit(Visitable<String> parent, String data) {
        System.out.println(indent + data);
    }
    @Override
	public void visit(Treeable<String, ?> tree) {
    	System.out.println(Strings.repeat(indent, tree.getDepth()) + tree.getData());
	}
 
}