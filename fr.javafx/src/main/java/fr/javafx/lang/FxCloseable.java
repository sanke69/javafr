package fr.javafx.lang;

import fr.java.beans.impl.ReadOnlyObjectBeanProperty;
import fr.java.patterns.displayable.Closeable;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface FxCloseable extends Closeable {

	/**
	 * Returns the "on-close-action" property.
	 *
	 * @return the "on-close-action" property.
	 *
	 * @see #setOnCloseAction(javafx.event.EventHandler)
	 */
	public ReadOnlyObjectBeanProperty<EventHandler<ActionEvent>> 	onCloseActionProperty();
	/**
	 * Defines the action that shall be performed before the window will be
	 * closed.
	 *
	 * @param onClosedAction the action to set
	 */
//	public void 													setOnCloseAction(EventHandler<ActionEvent> onClosedAction);
	/**
	 * Returns the action that shall be performed before the window will be
	 * closed.
	 *
	 * @return the action that shall be performed before the window will be
	 * closed or <code>null</code> if no such action has been defined
	 */
	public EventHandler<ActionEvent> 								getOnCloseAction();

	/**
	 * Returns the "on-closed-action" property.
	 *
	 * @return the "on-closed-action" property.
	 *
	 * @see #setOnClosedAction(javafx.event.EventHandler)
	 */
	public ReadOnlyObjectBeanProperty<EventHandler<ActionEvent>> 	onClosedActionProperty();
	/**
	 * Defines the action that shall be performed after the window has been
	 * closed.
	 *
	 * @param onClosedAction the action to set
	 */
//	public void 													setOnClosedAction(EventHandler<ActionEvent> onClosedAction);
	/**
	 * Returns the action that shall be performed after the window has been
	 * closed.
	 *
	 * @return the action that shall be performed after the window has been
	 * closed or <code>null</code> if no such action has been defined
	 */
	public EventHandler<ActionEvent> 								getOnClosedAction();

	/**
	 * Returns the "close-transition" property.
	 *
	 * @return the "close-transition" property.
	 *
	 * @see #setCloseTransition(javafx.animation.Transition)
	 */
	public ReadOnlyObjectBeanProperty<Transition> 					closeTransitionProperty();
	/**
	 * Defines the transition that shall be used to indicate window closing.
	 *
	 * @param t the transition that shall be used to indicate window closing or
	 * <code>null</code> if no transition shall be used.
	 */
//	public void 													setCloseTransition(Transition t);
	/**
	 * Returns the transition that shall be used to indicate window closing.
	 *
	 * @return the transition that shall be used to indicate window closing or
	 * <code>null</code> if no such transition has been defined
	 */
	public Transition 												getCloseTransition();

}
