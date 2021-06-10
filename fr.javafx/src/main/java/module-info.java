module javafr.graphics {
	requires transitive java.desktop;
	requires transitive java.logging;
	requires transitive java.xml;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;

	requires transitive javafr;
	requires transitive javafr.beans;
	requires transitive javafr.maths;
	requires transitive javafr.graphs;
	requires transitive javafr.sdk;

	exports fr.filedropper;

	exports fr.javafx.beans.binding;
	exports fr.javafx.beans.property;
	exports fr.javafx.beans.property.math;
	exports fr.javafx.beans.propertysheet.items;

	exports fr.javafx.behavior;

	exports fr.javafx.cell;
	exports fr.javafx.cell.collections;

	exports fr.javafx.concurrent;

	exports fr.javafx.scene.chart;
	exports fr.javafx.scene.chart.axis;
	exports fr.javafx.scene.chart.types.xy;
	exports fr.javafx.scene.chart.plugins.behavior;
	exports fr.javafx.scene.chart.plugins.indicators;
	exports fr.javafx.scene.chart.plugins.overlays;

	exports fr.javafx.scene.control;
	exports fr.javafx.scene.control.collection;
	exports fr.javafx.scene.control.collection.styles;
	exports fr.javafx.scene.control.indicator;
	exports fr.javafx.scene.control.actionner;
	exports fr.javafx.scene.control.overlay;
	exports fr.javafx.scene.control.overlay.drags;
	exports fr.javafx.scene.control.titledpane;
	exports fr.javafx.scene.control.selection;

	exports fr.javafx.scene.control.checkcombobox;
	
	exports fr.javafx.scene.control.viewport.planar.utils;

	exports fr.javafx.scene.control.viewport.planar.implementations.group;
	exports fr.javafx.scene.control.viewport;
	exports fr.javafx.scene.control.viewport.planar.utils.info;
	exports fr.javafx.scene.control.viewport.planar.utils.minimap;
	exports fr.javafx.scene.control.viewport.utils.statusbar;

	exports fr.javafx.scene.control.editor;
	exports fr.javafx.scene.control.editor.skin;
	exports fr.javafx.scene.control.editor.plugin;
	exports fr.javafx.scene.control.viewport.planar;
	exports fr.javafx.scene.control.raster;
	exports fr.javafx.scene.control.viewport.plugins;
	exports fr.javafx.scene.control.viewport.planar.implementations.canvas;
	exports fr.javafx.scene.control.viewport.planar.implementations.tiles;
	exports fr.javafx.scene.control.viewport.planar.implementations.tiles.info;
	exports fr.javafx.scene.control.viewport.planar.implementations.tiles.minimap;

	exports fr.javafx.scene.control.list;
	exports fr.javafx.scene.control.slider;

	exports fr.javafx.scene.layout.overlay;
	exports fr.javafx.scene.layout.tabs;
	
	exports fr.javafx.xtra;

	exports fr.javafx.application;
	exports fr.javafx.scene.canvas;
	exports fr.javafx.scene.shape;
	exports fr.javafx.scene.layout;
	exports fr.javafx.scene.properties;

	exports fr.javafx.sdk.controls.service.skins;
	exports fr.javafx.sdk.controls.service.skins.contents;
	exports fr.javafx.scene.control.terminal.keys;
	exports fr.javafx.stage.impl.splash.impl;
	exports fr.javafx.stage.impl.splash.collection;
	exports fr.javafx.stage.animation;
	exports fr.javafx.sdk.controls;

	exports fr.javafx.scene.control.player;
	exports fr.javafx.temp.windows.behaviors;
	exports fr.javafx.sdk.controls.signal1D;
	exports fr.javafx.temp.windows.skins;
	exports fr.javafx.scene.control.player.playlist.utils;

	exports fr.javafx.graph;
	exports fr.javafx.graph.behaviors;

	exports fr.javafx.stage.impl.splash;
	exports fr.javafx.temp.polymorphic.behaviors;
	exports fr.javafx.utils;
	exports fr.javafx.temp.polymorphic.skins.node;
	exports fr.javafx.temp.graphics.fx.api.charts.addons;
	exports fr.javafx.temp.graphics.fx.api.charts.plots;
	exports fr.javafx.temp.graphics.fx.api.ribbon;
	exports fr.javafx.sdk.controls.service.animations;
	exports fr.javafx.stage.impl.dialogs;
	exports fr.javafx.temp.graphics.fx.api.charts.skins;
	exports fr.javafx.animation;
	exports fr.javafx.stage.animation.impl;
	exports fr.javafx.temp.graphics.fx.api.charts;
	exports fr.javafx.temp.graphics.fx.api.database;
	exports fr.javafx.xtra.capture.processes;
	exports fr.javafx.stage;
	exports fr.javafx.temp.graphics.fx.application;
	exports fr.javafx.temp.polymorphic;
	exports fr.javafx.event;
	exports fr.javafx.sdk.controls.transforms;
	exports fr.javafx.scene.control.terminal.io;
	exports fr.javafx.sdk.controls.composite;
	exports fr.javafx.temp.polymorphic.skins.pane;
	exports fr.javafx;
	exports fr.javafx.beans.propertysheet.editors;

	exports fr.javafx.sdk.controls.time;
	exports fr.javafx.sdk.controls.service;
	exports fr.javafx.scene.control.terminal;
	exports fr.javafx.temp.nodes;
	exports fr.javafx.temp.windows.components;
	exports fr.javafx.temp;
	exports fr.javafx.stage.impl.goodies;
	exports fr.javafx.temp.polymorphic.api;
	exports fr.javafx.io.mouse;
	exports fr.javafx.sdk.controls.media;
	exports fr.javafx.temp.windows.pane;
	exports fr.javafx.temp.graphics.fx.application.module;
	exports fr.javafx.temp.windows.events;
	exports fr.javafx.temp.graphics.fx.api.temp;
	exports fr.javafx.temp.windows;
	exports fr.javafx.temp.properties;
	exports fr.javafx.beans;
	exports fr.javafx.sdk.controls.gauge.skins;
	exports fr.javafx.temp.graphics.fx.api.charts.multiaxes;
	exports fr.javafx.lang.enums;
	exports fr.javafx.sdk.controls.service.actions;
	exports fr.javafx.stage.impl.notifications;
	exports fr.javafx.stage.impl;
	exports fr.javafx.sdk.controls.media.skins;
	exports fr.javafx.beans.propertysheet;
	exports fr.javafx.temp.editors.distribution;
	
	exports fr.javafx.io.screen;

	exports fr.javafx.xtra.capture;
	exports fr.javafx.sdk.controls.gauge;
	exports fr.javafx.beans.propertysheet.api;
	exports fr.javafx.scene.control.player.playlist;
	exports fr.javafx.sdk.controls.service.styles;

	exports fr.javafx.behavior.extended;
	exports fr.javafx.io.mouse.adapters;

	exports fr.javafx.windows;
	exports fr.javafx.windows.skins;
	exports fr.javafx.windows.behaviors;

	exports fr.javafx.sdk.controls.charts;

	exports fr.javafx.xtra.filedropper;
	

	exports fr.drawer.fx;
	exports fr.drawer.fx.raster;
	exports fr.drawer.fx.tiles;
	
//	opens fr.javafx.windows.skins to javafx.controls;
}
