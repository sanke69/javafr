package fr.javafx.lang;

import fr.javafx.scene.layout.OptimizableContentPane;
import javafx.scene.transform.Transform;

public interface OptimizationRule {

	/**
	 * Determines whether the content pane shall be visible or not.
	 *
	 * @param p pane
	 * @param t transform
	 * @return {@code true} if the content shall be visible; {@code false}
	 * otherwise
	 */
	public boolean visible(OptimizableContentPane p, Transform t);

	/**
	 * Determines whether the content pane shall be attached or not.
	 *
	 * <p>
	 * <b>NOTE:</b> attaching/detaching nodes from the scene graph is an
	 * expensive operation.
	 * </p>
	 *
	 * @param p pane
	 * @param t transform
	 * @return {@code true} if the content shall be attached; {@code false}
	 * otherwise
	 */
	public boolean attached(OptimizableContentPane p, Transform t);

}
