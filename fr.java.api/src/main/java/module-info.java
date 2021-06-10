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
**/
module javafr {
	requires java.base;
	requires transitive java.desktop;
	requires transitive java.logging;
	requires java.naming;
	requires javafx.graphics;

	exports fr.java.lang;
	exports fr.java.lang.collections;
	exports fr.java.lang.enums;
	exports fr.java.lang.exceptions;
	exports fr.java.lang.functionals;
	exports fr.java.lang.properties;
	exports fr.java.lang.tuples;

	exports fr.java.raster;
	exports fr.java.draw;
	exports fr.java.draw.tools;

	exports fr.java.lang.enums.state;

	exports fr.java.patterns.adapter;
	exports fr.java.patterns.composite;
	exports fr.java.patterns.displayable;
	exports fr.java.patterns.drawable;
	exports fr.java.patterns.facilities;
	exports fr.java.patterns.identifiable;
	exports fr.java.patterns.measurable;
	exports fr.java.patterns.mvc;
	exports fr.java.player;
	exports fr.java.patterns.serializable;
	exports fr.java.patterns.service;
	exports fr.java.patterns.stateable;

	exports fr.java.patterns.valueable;
	
	exports fr.java.patterns.tileable;
	exports fr.java.patterns.timeable;
	exports fr.java.patterns.triggerable;
	exports fr.java.patterns.visitable;
	exports fr.java.patterns.priority;
	exports fr.java.patterns.geometry;

	exports fr.java.events;
	
	exports fr.java.io.binary;
	exports fr.java.io.binary.collection;

	exports fr.java.nio;
	exports fr.java.nio.buffer;
	exports fr.java.nio.buffer.direct;
	exports fr.java.nio.buffer.circular;
	exports fr.java.nio.data;
	exports fr.java.nio.data.objects;
	exports fr.java.nio.file;
	exports fr.java.nio.stream;
	exports fr.java.nio.stream.std;
	exports fr.java.nio.stream.pipes;
	exports fr.java.nio.stream.utils;

	exports fr.java.math;
	exports fr.java.math.numbers;

	exports fr.java.math.algebra;
	exports fr.java.math.algebra.tensor;
	exports fr.java.math.algebra.vector;
	exports fr.java.math.algebra.matrix;
	exports fr.java.math.algebra.matrix.specials;

	exports fr.java.math.geometry;
	exports fr.java.math.geometry.linear;
	exports fr.java.math.geometry.plane;
	exports fr.java.math.geometry.space;
	exports fr.java.math.geometry.space.shapes;

	exports fr.java.math.topology;
	exports fr.java.math.window;
	exports fr.java.math.polynoms;
	

	exports fr.java.media;
	exports fr.java.media.image;
	exports fr.java.media.sound;
	exports fr.java.media.video;
	exports fr.java.media.shape;
	exports fr.java.media.sound.buffer;
	exports fr.java.media.video.buffer;

	exports fr.java.log;

	exports fr.java.file.archive;
	exports fr.java.file.text;
	exports fr.java.file.text.config;

	exports fr.java.cli;

	exports fr.java.time;
	exports fr.java.time.api;
	exports fr.java.time.tools;

	exports fr.java.timeline;
	exports fr.java.timeline.resync;

	exports fr.java.network;
	exports fr.java.network.sockets;

	exports fr.java.security;
	exports fr.java.security.enums;
	exports fr.java.security.exceptions;
	exports fr.java.security.encryption;

	exports fr.java.data;
	exports fr.java.data.provider;
	exports fr.java.data.provider.caches;
	
	exports fr.java.math.functions;

	exports fr.java.draw.styles;
	
	exports fr.java.math.stats;
	

	exports fr.java.patterns.compatible;

	exports fr.java.tensor;

//	exports fr.java.user;
	exports fr.java.user.data;
	exports fr.java.user.data.properties;
	exports fr.java.user.session;
	exports fr.java.user.session.exceptions;
	exports fr.java.user.patterns; // TODO:: Remove or change
}
