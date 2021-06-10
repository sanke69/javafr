module javafr.sdk {
	requires java.logging;
	requires java.management;
	requires jdk.unsupported;

	requires transitive javafr;
	requires transitive javafr.beans;
	requires transitive javafr.maths;

	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires transitive java.desktop;

	exports fr.drawer.awt;

	exports fr.java.cli.impl;
	exports fr.java.cli.utils;

	exports fr.java.sdk.events;

	exports fr.java.sdk.file;

	exports fr.java.sdk.file.archive.simple;
	exports fr.java.sdk.file.archive.multi;

	exports fr.java.sdk.file.text;
	exports fr.java.sdk.file.text.cfg;
	exports fr.java.sdk.file.text.csv;
	exports fr.java.sdk.file.text.properties;

	exports fr.java.sdk.io.file.binary;
	exports fr.java.sdk.io.file.binary.collections;
	exports fr.java.sdk.io.stream.binary;
	exports fr.java.sdk.io.stream.binary.collections;

	exports fr.java.sdk.lang;
	exports fr.java.jvm.properties;
	exports fr.java.jvm.properties.id;
	exports fr.java.jvm.properties.version;
	exports fr.java.sdk.lang.tuples;

	exports fr.java.sdk.log;

	exports fr.java.sdk.nio;
	exports fr.java.sdk.nio.buffer;
	exports fr.java.sdk.nio.buffer.circular;
	exports fr.java.sdk.nio.file;
	exports fr.java.sdk.nio.file.filters;
	exports fr.java.sdk.nio.file.filters.samples;
	exports fr.java.sdk.nio.file.filters.samples.ops;
	exports fr.java.sdk.nio.stream;
	exports fr.java.sdk.nio.stream.std;
	exports fr.java.sdk.nio.stream.javax;
	exports fr.java.sdk.nio.stream.util;

	exports fr.java.sdk.player;
	exports fr.java.sdk.player.players;
	exports fr.java.sdk.player.playlists.defaults;
	exports fr.java.sdk.player.playlists.m3u;

	exports fr.media.players;

	exports fr.java.sdk.patterns.geometry;
	exports fr.java.sdk.patterns.mvc;
	exports fr.java.sdk.patterns.serializable.bin;
	exports fr.java.sdk.patterns.serializable.xml;
	exports fr.java.sdk.patterns.stateable;
	exports fr.java.sdk.patterns.tileable;
	exports fr.java.sdk.patterns.timeable;
	exports fr.java.sdk.patterns.timeable.timelines;
	exports fr.java.sdk.patterns.timeable.machine;
	exports fr.java.sdk.patterns.timeable.utils;
	exports fr.java.sdk.patterns.visitable;
	exports fr.java.sdk.patterns.visitable.specials;
	exports fr.java.sdk.patterns.visitable.utils;
	exports fr.java.sdk.patterns.service;

	exports fr.java.sdk.patterns.requester;

	exports fr.java.sdk.network.protocol.rtcp;
	exports fr.java.sdk.network.sockets;

	exports fr.java.sdk.security;
	exports fr.java.sdk.security.encryption;
	exports fr.java.sdk.security.files;
	exports fr.java.sdk.security.files.pem;
	exports fr.java.sdk.security.rsa;
	exports fr.java.sdk.security.utils;

	exports fr.java.multitasks;
	exports fr.java.multitasks.processes;
	exports fr.java.multitasks.threads;
	exports fr.java.multitasks.threads.lightweight;

	exports fr.java.hosts;
	exports fr.java.jvm;

	exports fr.java.sdk.file.archive.multi.tar;

//	exports fr.java.ui.layouts.cells;

	exports fr.java.sdk.draw.design;
	exports fr.java.sdk.draw.design.plots;

	exports fr.java.sdk.file.text.xml;

	exports fr.java.sdk.data.async;
	exports fr.java.sdk.data.async.caches;
	exports fr.java.sdk.data.async.caches.cleaners;

	exports fr.java.rasters.rasters;
	exports fr.java.rasters.tensors;

	exports fr.java.sdk.time;
	exports fr.java.sdk.time.tools;

	exports fr.java.util.collections;

	exports fr.java.utils;
	exports fr.java.utils.strings;
	exports fr.java.utils.primitives;

	exports fr.media.image;
	exports fr.media.image.utils;

	exports fr.media.sound;
	exports fr.media.sound.utils;

	exports fr.media.video;
	
}
