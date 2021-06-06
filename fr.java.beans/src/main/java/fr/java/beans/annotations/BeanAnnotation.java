/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * @file     BeanAnnotation.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target({ElementType.[], ...})
 * ==============================
ANNOTATION_TYPE				Types d'annotation
CONSTRUCTOR					Constructeurs
LOCAL_VARIABLE				Variables locales
FIELD						Champs
METHOD						Méthodes hors constructeurs
PACKAGE						Packages
PARAMETER					Paramètres d'une méthode ou d'un constructeur
TYPE						Classes, interfaces, énumérations, types d'annotation
**/

/**
 * @Retention(RetentionPolicy.[])
 * ==============================
Enumération					Rôle
RetentionPolicy.SOURCE		informations conservées dans le code source uniquement (fichier .java) : le compilateur les ignore
RetentionPolicy.CLASS		informations conservées dans le code source et le bytecode (fichiers .java et .class)
RetentionPolicy.RUNTIME		informations conservées dans le code source et le bytecode : elles sont disponibles à l'exécution par introspection
**/

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface BeanAnnotation {

}
