package fr.javafx.stage.impl.splash;

import javafx.scene.control.Labeled;
import javafx.scene.control.ProgressIndicator;

public interface ProgressSplashScreen extends SplashScreen {

	public Labeled           getPhaseLabel();
	public Labeled           getActionLabel();
	public ProgressIndicator getProgressIndicator(); 

	public String 			 getPhase();
	public void 			 setPhase(String _text);
	public String			 getAction();
	public void 			 setAction(String _text);

	public double  			 getProgression();
	public void    			 setProgression(double _percent);
	public void    			 increaseProgression(double _step);
	public void    			 decreaseProgression(double _step);

}
