package fr.javafx.scene.properties;

import java.text.DecimalFormat;
import java.time.LocalDate;

import fr.javafx.scene.control.editor.BooleanEditor;
import fr.javafx.scene.control.editor.BooleanEditor.Style;
import fr.javafx.scene.control.editor.NumberEditor;
import fr.javafx.scene.control.editor.PasswordEditor;
import fr.javafx.scene.control.editor.TextEditor;
import fr.javafx.scene.control.slider.SliderAdv;
import fr.javafx.scene.control.slider.SliderDouble;
import fr.javafx.scene.control.slider.SliderWithDisplay;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class TestApplication extends Application {

    public static void main(String... args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    	setEditorTest(stage);
//    	setDoubleTest(stage);
//    	setDateTest(stage);
    }

    private void setEditorTest(Stage stage) {
    	Editor<Boolean> 	booleanA = new BooleanEditor(Style.CheckBox), 
    						booleanB = new BooleanEditor(Style.ToggleButton);
    	Editor<Number>		integerA = new NumberEditor(Integer.class, 0, 10, 3, 2, NumberEditor.Style.Simple),
							integerB = new NumberEditor(Integer.class, 0, 10, 3, 2, NumberEditor.Style.Slider),
							integerC = new NumberEditor(Integer.class, 0, 10, 3, 2, NumberEditor.Style.Spinner);
    	Editor<Number>		doubleA  = new NumberEditor(Double.class,  0, 10, 3, 0.05, NumberEditor.Style.Simple),
							doubleB  = new NumberEditor(Double.class,  0, 10, 3, 0.05, NumberEditor.Style.Slider),
							doubleC  = new NumberEditor(Double.class,  0, 10, 3, 0.05, NumberEditor.Style.Spinner);
    	Editor<String>		stringA  = new TextEditor(),
    						stringB  = new PasswordEditor();

        VBox root = new VBox(booleanA.getNode(), booleanB.getNode(), 
        					 integerA.getNode(), integerB.getNode(), integerC.getNode(),
        					 doubleA.getNode(),  doubleB.getNode(),  doubleC.getNode(),
        					 stringA.getNode(),  stringB.getNode());
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        Scene scene = new Scene(root, 300, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void setSliderDoubleTest(Stage stage) {
    	Editor<Boolean> 	booleanA = new BooleanEditor(Style.CheckBox), 
    						booleanB = new BooleanEditor(Style.ToggleButton);
        SliderAdv<Double> 	slider = new SliderDouble(0d, 1d, 0.75d, 0.05d);
        Label 				label = new Label();

        label.setStyle("-fx-font-size:30px");

        StringConverter<Double> toTicks = new StringConverter<Double>() {
        	DecimalFormat df2 = new DecimalFormat("#.##");

			@Override
			public String toString(Double object) {
				return df2.format(object);
			}

			@Override
			public Double fromString(String string) {
				return null;
			}
        	
        };


        slider.valueProperty().addListener((obs,old,val)->label.setText(toTicks.toString(val.doubleValue())+""));

        VBox root = new VBox(slider,label, booleanA.getNode(), booleanB.getNode());
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        Scene scene = new Scene(root,600,200);
        stage.setScene(scene);
        stage.show();
    }
    private void setSliderDateTest(Stage stage) {
        SliderAdv<LocalDate> slider = SliderWithDisplay.forLocalDate(LocalDate.of(2020, 12, 01), LocalDate.of(2021, 4, 27));
        Label 				 label = new Label();

        label.setStyle("-fx-font-size:30px");

        slider.valueProperty().addListener((obs,old,val)->label.setText(slider.objectProperty().get()+""));

        VBox root = new VBox(slider,label);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        Scene scene = new Scene(root,600,200);
        stage.setScene(scene);
        stage.show();
    }


}