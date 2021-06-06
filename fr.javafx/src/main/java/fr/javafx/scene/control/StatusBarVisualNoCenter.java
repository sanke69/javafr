package fr.javafx.scene.control;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class StatusBarVisualNoCenter extends SkinBase<StatusBar> {

    private HBox        leftBox;
    private HBox        rightBox;

    // "Center" group
    private Label       label;
    private ProgressBar progressBar;

    public StatusBarVisualNoCenter(StatusBar statusBar) {
        super(statusBar);

        leftBox = new HBox();
        leftBox.getStyleClass().add("left-items");

        rightBox = new HBox();
        rightBox.getStyleClass().add("right-items");

        progressBar = new ProgressBar();
        progressBar.progressProperty().bind(statusBar.progressProperty());
        progressBar.visibleProperty().bind(
                Bindings.notEqual(0, statusBar.progressProperty()));

        label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.textProperty().bind(statusBar.textProperty());
        label.graphicProperty().bind(statusBar.graphicProperty());
        label.getStyleClass().add("status-label");

        leftBox.getChildren().setAll(getSkinnable().getLeftItems());

        rightBox.getChildren().setAll(getSkinnable().getRightItems());

        statusBar.getLeftItems().addListener(
                (Observable evt) -> leftBox.getChildren().setAll(
                        getSkinnable().getLeftItems()));

        statusBar.getRightItems().addListener(
                (Observable evt) -> rightBox.getChildren().setAll(
                        getSkinnable().getRightItems()));

        GridPane gridPane = new GridPane();

        GridPane.setFillHeight(leftBox, true);
        GridPane.setFillHeight(rightBox, true);
        GridPane.setFillHeight(label, true);
        GridPane.setFillHeight(progressBar, true);

        GridPane.setVgrow(leftBox, Priority.ALWAYS);
        GridPane.setVgrow(rightBox, Priority.ALWAYS);
        GridPane.setVgrow(label, Priority.ALWAYS);
        GridPane.setVgrow(progressBar, Priority.ALWAYS);

        GridPane.setHgrow(label, Priority.ALWAYS);

        gridPane.add(leftBox, 0, 0);
        gridPane.add(label, 1, 0);
        gridPane.add(progressBar, 2, 0);
        gridPane.add(rightBox, 4, 0);

        getChildren().add(gridPane);
        
        getSkinnable().layout();
    }

}