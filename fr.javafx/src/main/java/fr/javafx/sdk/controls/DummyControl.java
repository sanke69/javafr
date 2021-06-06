package fr.javafx.sdk.controls;

import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;

import fr.javafx.scene.control.AbstractFxControl;

public class DummyControl extends AbstractFxControl {

	class DefaultDummySkin implements Skin<DummyControl> {
		private DummyControl control;
		private Pane         node;

		public DefaultDummySkin(DummyControl _control) {
			super();
			control = _control;
		}

		@Override
		public DummyControl getSkinnable() {
			return control;
		}

		@Override
		public Node 		getNode() {
			if(node == null)
				node = new Pane();
			return node;
		}

		@Override
		public void 		dispose() {
			
		}
		
	}

	public DummyControl() {
		super();
	}
	
	public Skin<DummyControl> createDefaultSkin() {
		return new DefaultDummySkin(this);
	}

	@Override
	public void refresh() {
		new Thread(() -> {
			try { Thread.sleep(500);
			} catch (InterruptedException e) { }

			requestRefresh();
		}).start();
	}

}
