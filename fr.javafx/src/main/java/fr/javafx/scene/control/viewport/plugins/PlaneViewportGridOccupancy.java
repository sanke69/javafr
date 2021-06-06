package fr.javafx.scene.control.viewport.plugins;

import java.util.function.Function;

import fr.java.math.topology.Coordinate;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PlaneViewportGridOccupancy<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD> {
	PlaneViewportControl<MODEL, COORD> control;
    boolean showHoverCursor = true;

    static class Cell extends StackPane {
        int column;
        int row;
        int value;

        public Cell(int _row, int _column, int _value) {
            row    = _row;
            column = _column;
            value  = _value;

            setOpacity(0.75);
        	updateStyle();
        }

        public void 	setValue(int _value) {
        	value = _value;
        	updateStyle();
        }
		public int 	getValue() {
			return value;
		}

        public void 	hoverHighlight() {
        	setStyle("-fx-background-color:derive(green,0.9);");
//      	System.out.println(toString());
        }
        public void 	hoverUnhighlight() {
        	updateStyle();
        }

        public String 	toString() {
            return row + "/" + column;
        }

        void 			updateStyle() {
        	String border = "-fx-border-color: derive(rgb(69, 169, 69), 0.75); -fx-border-width: 1px;";
        	if(value > 0d) {
        		setStyle(border + " -fx-background-color: derive(red, 0.9);");
//      		setStyle(border + " -fx-background-color: derive(red, " + (int)((1d - value)*100) + "%);");
//            		setStyle(border + " -fx-background-color: linear-gradient(to right, #90C7E0 " + (value*100d) + "%, white " + ((1d - value)*100d) + "%);");
        	} else
        		setStyle(border + " -fx-border-width: 1px; -fx-background-color: transparent;");
        }


    }

    int 	 rows,      columns;
    double 	 width = 1, height = 1;
    Pane     grid;
    Cell[][] cells;

    public PlaneViewportGridOccupancy(int _rows, int _columns) {
    	super();
        rows    = _rows;
        columns = _columns;

        grid    = new Pane();
        cells   = new Cell[columns][rows];
    	for(int row = 0; row < rows; row++)
    		for(int col = 0; col < columns; col++)
                createCell(row, col, 0);
    }
    public PlaneViewportGridOccupancy(int[][] _grid) {
    	super();
        columns = _grid.length;
        rows    = _grid[0].length;

        grid    = new Pane();
        cells   = new Cell[columns][rows];
        for(int col = 0; col < columns; col++)
        	for(int row = 0; row < rows; row++)
                createCell(row, col, _grid[col][row]);
    }

    public void 		reset() {
        for(int col = 0; col < columns; col++)
        	for(int row = 0; row < rows; row++)
                cells[col][row].setValue(0);
    }
    public void 		reset(int[][] _grid) {
    	columns = _grid.length;
    	rows    = _grid[0].length;
        cells   = new Cell[columns][rows];

        grid.getChildren().clear();
        for(int col = 0; col < columns; col++)
        	for(int row = 0; row < rows; row++)
                createCell(row, col, _grid[col][row]);
    }

    public void 		setGridOccupancy(int[][] _gridOccupancy, Function<Double, Integer> _color) {
    	columns = _gridOccupancy.length;
    	rows    = _gridOccupancy[0].length;
        cells   = new Cell[columns][rows];

        grid.getChildren().clear();
        for(int col = 0; col < columns; col++)
        	for(int row = 0; row < rows; row++)
                createCell(row, col, _gridOccupancy[col][row]);
    }
    public int[][] 		getGridOccupancy() {
    	int[][] grid = new int[columns][rows];

        for(int col = 0; col < columns; col++)
        	for(int row = 0; row < rows; row++)
            	grid[col][row] = getCellValue(row, col);

        return grid;
    }

    public void   		setCellValue(int _row, int _column, int _value) {
        cells[_column][_row].setValue(_value > 0 ? 65000 : 0);
    }
    public int 			getCellValue(int _row, int _column) {
        return cells[_column][_row].getValue();
    }

    Cell   				createCell(int _row, int _column, int _value) {
        EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
        	if(event.getSource() instanceof Cell) {
        		Cell cell = (Cell) event.getSource();
                if( event.isPrimaryButtonDown()) {
                    cell.setValue(65000);
                } else if( event.isSecondaryButtonDown()) {
                    cell.setValue(0);
                }
        	}
        };
        EventHandler<MouseEvent> onDragDetectedEventHandler = event -> {
        	if( event.getSource() instanceof Cell) {
        		Cell cell = (Cell) event.getSource();
        		cell.startFullDrag();
        	}
        };

        double w = width / columns;
        double h = height / rows;
        double x = w * _column;
        double y = h * _row;

    	Cell 
    	newCell = new Cell(_row, _column, _value);
        newCell . setLayoutX(x);
        newCell . setLayoutY(y);
        newCell . setPrefWidth(w);
        newCell . setPrefHeight(h);
        newCell . setOnMousePressed		(onMousePressedEventHandler);
        newCell . setOnMouseDragEntered	(onMousePressedEventHandler);
        newCell . setOnDragDetected		(onDragDetectedEventHandler);

        if(showHoverCursor)
        	newCell.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue) newCell.hoverHighlight();
                else 		 newCell.hoverUnhighlight();
            });

        grid.getChildren().add(newCell);
        
        cells[_column][_row] = newCell;
        return newCell;
    }

    void   				updateLayout() {
        double w = width / columns;
        double h = height / rows;

        for(int col = 0; col < columns; col++)
        	for(int row = 0; row < rows; row++) {
                double x = w * col;
                double y = h * row;
		    	Cell 
		    	cell = cells[col][row];
		        cell . setLayoutX(x);
		        cell . setLayoutY(y);
		        cell . setPrefWidth(w);
		        cell . setPrefHeight(h);
            }
    }

	@Override
	public void setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		control = _pvpControl;
		width   = control.getViewport().getModelBounds().getWidth();
		height  = control.getViewport().getModelBounds().getHeight();
		updateLayout();

		control.viewportProperty().addListener(evt -> {
			grid.setTranslateX(control.getViewport().getViewAnchor().getFirst());
			grid.setTranslateY(control.getViewport().getViewAnchor().getSecond());
			grid.setScaleX(control.getViewport().getViewScale());
			grid.setScaleY(control.getViewport().getViewScale());
		});
	}

	@Override
	public ObservableList<Node> getChildren() {
		return FXCollections.observableArrayList(grid);
	}

}