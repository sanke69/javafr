package fr.java.trial;

import java.util.function.Function;

/**
 * A function throwing an exception.
 */
@FunctionalInterface
public interface ThrowingFunction<I, O> /*extends Function<I, O>*/ {
/*
    public default O apply(I i) {
    	try {
    		return throwingApply(i);
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }
*/
    /**
     * Applies this function to the given argument.
     *
     * @param i the function argument
     * @return the function result
     * @throws Exception
     */
    O apply(I i) throws Exception;
//    O throwingApply(I i) throws Exception;

}
