package fr.javafx.utils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.layout.Region;

public class FxTableUtils {
	/*
    private static Method columnToFitMethod;

    static {
        try {
            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
            columnToFitMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void autoFitTableWidth(TableView<?> tableView) {
        tableView.getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(Change<?> c) {
                for (Object column : tableView.getColumns()) {
                    try {
                        columnToFitMethod.invoke(tableView.getSkin(), column, -1);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    */

	public static <T> void autoFitTableHeight(TableView<T> table, double _rowHeight, int _rowThreshold) {
		InvalidationListener autoFitter = new InvalidationListener() {
			final double rowHeight    = _rowHeight;
			final int    rowThreshold = _rowThreshold;

			@Override
			public void invalidated(Observable observable) {
			    int rowCount = table.getItems().size();

				if(rowCount < rowThreshold) {
					table.setFixedCellSize(rowHeight);

				    TableHeaderRow headerRow = (TableHeaderRow) table.lookup("TableHeaderRow");
				    double tableHeight = (rowCount * table.getFixedCellSize())
									      // add the insets or we'll be short by a few pixels
									      + table.getInsets().getTop() + table.getInsets().getBottom()
									      // header row has its own (different) height
									      + (headerRow == null ? 0 : headerRow.getHeight());

				    table.setMinHeight(tableHeight);
				    table.setMaxHeight(tableHeight);
				    table.setPrefHeight(tableHeight);
				} else {
					table.setFixedCellSize(-1);

				    table.setMinHeight(Region.USE_COMPUTED_SIZE);
				    table.setMaxHeight(Region.USE_COMPUTED_SIZE);
				    table.setPrefHeight(Region.USE_COMPUTED_SIZE);
				}
			}
			
		};

		table.itemsProperty().addListener(autoFitter);
	}
	
    
}