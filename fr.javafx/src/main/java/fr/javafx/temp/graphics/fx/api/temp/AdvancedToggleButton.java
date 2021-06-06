package fr.javafx.temp.graphics.fx.api.temp;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdvancedToggleButton extends Button {
	public enum STATUS {
		active("active", "-fx-base: lightgreen;"),
		inactive("inactive", "-fx-base: darkred;"),
		waiting("waiting", "-fx-base: yellow;"),
		warning("warning", "-fx-base: orange;"),
		error("error", "-fx-base: black;");

		String name, style;
		
		private STATUS(String _name, String _style) {
			name = _name;
			style = _style;
		}
	}

	private Image        active, inactive;
	private ImageView    toggleImage;

	private boolean isActive;

	public AdvancedToggleButton() {
		super();
		toggleImage = new ImageView();
		setGraphic(toggleImage);

		disable();
	}
	public AdvancedToggleButton(Image _sel, Image _unsel) {
		this();

		active   = _sel;
		inactive = _unsel;
		
		toggleImage.setImage(inactive);

		disable();
//		update();
	}

	public void enable() {
		isActive = true;
//		update(); 
	}
	public void disable() {
		isActive = false;
	}
	
	public void setStatus(STATUS _status) {
		setStyle(_status.style);
	}
	public void toogle(boolean _true) {
		if(_true)
			Platform.runLater(() -> toggleImage.setImage(inactive));
		else
			Platform.runLater(() -> toggleImage.setImage(active));
	}

	public void setSize(double _w, double _h) {
		toggleImage.setFitWidth(_w);
		toggleImage.setFitHeight(_h);
	}
/*
	public void update() {
		if(isActive) {
			if(!service.isServiceExist()) {
				setStyle(errorColor);
	        	Platform.runLater(() -> toggleImage.setImage(inactiveSVG));
	        	setDisable(true);
			}

			if(service.isServiceAlive())
	        	Platform.runLater(() -> toggleImage.setImage(activeSVG));
			else
	        	Platform.runLater(() -> toggleImage.setImage(inactiveSVG));

			pressedProperty().addListener((_value, _old, _new) -> {
				if(!_new)
					return;

	    		if(service.isServiceAlive()) {
	    			new Thread( () -> {
	    	        	setDisable(true);
		    			System.out.println("Service is alive, try to kill");
		            	setStyle(waitingColor);
			            service.stop();
			            	
		            	if(service.isServiceAlive()) {
		        			System.out.println("Failed to kill");
		                	setStyle(warningColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(inactiveColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(warningColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(inactiveColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(warningColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(inactiveColor);
		                	Platform.runLater(() -> toggleImage.setImage(activeSVG));            		
		            	} else {
		        			System.out.println("Kill Success");
		                	setStyle(activeColor); // TODO:: change for okColor
		                	Platform.runLater(() -> toggleImage.setImage(inactiveSVG)); 
		            	}
		            	setDisable(false);
	                	Platform.runLater(() -> label.update());
	    			}).start();
	    		} else {
	    			new Thread( () -> {
	    	        	setDisable(true);
		    			System.out.println("Service is not alive, try to start");
		            	setStyle(waitingColor);
		            	service.start();
		
		            	if(!service.isServiceAlive()) {
		        			System.out.println("Failed to start");
		                	setStyle(warningColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(inactiveColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(warningColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(inactiveColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(warningColor);
		            		try { Thread.sleep(125); } catch(Exception e) { e.printStackTrace(); }
		                	setStyle(inactiveColor);
		                	Platform.runLater(() -> toggleImage.setImage(inactiveSVG));            		
		            	} else {
		        			System.out.println("Start Success");
		                	setStyle(activeColor); // TODO:: change for okColor
		                	Platform.runLater(() -> toggleImage.setImage(activeSVG)); 
		            	}
		            	setDisable(false);
	                	Platform.runLater(() -> label.update());
	    			}).start();
	    		}	
			});
		}
	}
*/

}