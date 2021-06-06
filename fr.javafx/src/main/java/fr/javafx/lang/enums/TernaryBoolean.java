package fr.javafx.lang.enums;

import javafx.scene.input.KeyCombination.ModifierValue;

public enum TernaryBoolean {
	TRUE, FALSE, ANY;

	public final ModifierValue asModifier() {
		switch(this) {
		case TRUE  : return ModifierValue.DOWN;
		case FALSE : return ModifierValue.UP;
		default    :
		case ANY   : return ModifierValue.ANY;
		}
	}

	public boolean equals(boolean _true) {
		if(this == ANY)
			return true;
		else if((_true) && (this == TRUE))
			return true;
		else if((!_true) && (this == FALSE))
			return true;
		else
			return false;
	}

}