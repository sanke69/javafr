package fr.javafx.sdk.controls.time;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class TimeSelectionViewSkin extends SkinBase<TimeSelectionView> {

	private static final int MARGIN_LEFT = 30;
	private static final int MARGIN_RIGHT = MARGIN_LEFT;
	private static final int MARGIN_TOP = 5;

	BorderPane borderPane = new BorderPane();

	TimeTextFieldView timeTextFieldBegin;
	TimeTextFieldView timeTextFieldEnd;

	protected TimeSelectionViewSkin(TimeSelectionView control) {
		super(control);
		borderPane.setFocusTraversable(false);
		control.setFocusTraversable(false);
		timeTextFieldBegin = new TimeTextFieldView() {
			@Override
			public void setNewTime(int newTime) {
				System.out.println(newTime);
			}
		};
		timeTextFieldEnd = new TimeTextFieldView() {
			@Override
			public void setNewTime(int newTime) {
				System.out.println(newTime);
			}
		};

		BorderPane borderPaneLeft = getLeftPane();
		BorderPane borderPaneRight = getRightPane();

		borderPane.setLeft(borderPaneLeft);
		borderPane.setRight(borderPaneRight);

		this.getChildren().add(borderPane);

		timeTextFieldBegin.timeProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				control.forceLayout();
			}
		});
		timeTextFieldEnd.timeProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				control.forceLayout();
			}
		});

//		initBinding();

	}

	private BorderPane getLeftPane() {
		BorderPane borderPaneLeft = new BorderPane();
		borderPaneLeft.setCenter(timeTextFieldBegin);
		Polygon arrow = ArrowFactory.getArrow(0.3);
		arrow.setFill(Color.BLUE);
		borderPaneLeft.setLeft(arrow);
		BorderPane.setAlignment(arrow, Pos.CENTER);

		Line line = getUnderscore();
		BorderPane.setAlignment(line, Pos.TOP_RIGHT);
		borderPaneLeft.setBottom(line);

		return borderPaneLeft;
	}

	private BorderPane getRightPane() {
		BorderPane borderPaneRight = new BorderPane();
		borderPaneRight.setCenter(timeTextFieldEnd);
		Polygon arrow = ArrowFactory.getArrow(0.3);
		arrow.setScaleX(-1);
		arrow.setFill(Color.BLUE);
		borderPaneRight.setRight(arrow);
		BorderPane.setAlignment(arrow, Pos.CENTER);

		Line line = getUnderscore();
		BorderPane.setAlignment(line, Pos.TOP_LEFT);
		borderPaneRight.setBottom(line);

		return borderPaneRight;
	}

	private Line getUnderscore() {
		Line line = new Line(0, 0, 85, 0);
		line.setStroke(Color.BLUE);
		line.setStrokeWidth(0.5);

		return line;
	}

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		borderPane.resize(contentWidth, 25);
		borderPane.setPadding(new Insets(MARGIN_TOP, MARGIN_RIGHT, 0, MARGIN_LEFT));

	}
/*
	public void initBinding() {
		SoundLooperPlayer soundLooperPlayer = getSkinnable().getSoundLooperPlayer();
		Mark currentMark = soundLooperPlayer.getCurrentMark();
		if (currentMark != null) {
			timeTextFieldBegin.timeProperty().bind(currentMark.beginMillisecondProperty());
			timeTextFieldEnd.timeProperty().bind(currentMark.endMillisecondProperty());
			getSkinnable().forceLayout();
		}

		soundLooperPlayer.markProperty().addListener(new ChangeListener<Mark>() {
			@Override
			public void changed(ObservableValue<? extends Mark> observable, Mark oldMark, Mark newMark) {
				// Unbind old mark
				if (oldMark != null) {
					timeTextFieldBegin.timeProperty().unbind();
					timeTextFieldEnd.timeProperty().unbind();
				}

				// bind new mark
				if (newMark != null) {
					timeTextFieldBegin.timeProperty().bind(newMark.beginMillisecondProperty());
					timeTextFieldEnd.timeProperty().bind(newMark.endMillisecondProperty());
				}

			}
		});

	}
*/
}