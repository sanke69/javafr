/**
 * Copyright (C) 2004-2010 SK69
 *
 * @file     LinkedList.java
 * @author   <a href='mailto:steve.pechberti@gmail.com'> Steve PECHBERTI </a>
 * @version  1.0.0.0 [SPEC]
 * @date     2004/06/29
 * 
 *  License : 
 *    [EN] This file is the intellectual property of Steve PECHBERTI.
 *         Any use, partial or complete copy, modification of the file
 *         without my approval is forbidden
 *    [FR] Ce fichier est la propriete intellectuelle de Steve PECHBERTI.
 *         Toute utilisation, copie partielle ou totale, modification 
 *         du fichier sans mon autorisation est interdite
 *
 *  Disclaimer :
 *    [EN] This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *    [FR] Ce programme est distribu� dans l'espoir qu'il sera utile,
 *         mais SANS AUCUNE GARANTIE, sans m�me la garantie implicite de
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUT PARTICULIER.
 *
 */
package fr.java.util;

public class LinkedList<T> {
    private Linkable<T> head;
    
    public static interface Linkable<T> {
        public Linkable<T> 	next();
        public void 		setNext(Linkable<T> file);
    }

    public LinkedList() {
        super();
        head = null;
    }

    public void add(Linkable<T> _item) {
        if(_item == null) return ;
        _item.setNext(head);
        head = _item;
    }
    
    public void remove(Linkable<T> _item) {
        if(_item == null) return ;
        for(Linkable<T> cursor = head; cursor != null; cursor = cursor.next()) {
        	
        }
    }

	public Linkable<T> getHead() {
		return head;
	}

}
