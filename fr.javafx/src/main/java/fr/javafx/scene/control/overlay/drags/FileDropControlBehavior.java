package fr.javafx.scene.control.overlay.drags;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.Visual;

public class FileDropControlBehavior implements Behavior {
	FileDropControl control;

	public FileDropControlBehavior(FileDropControl _control, Visual<FileDropControl> _visual) {
		super();
		control = _control;

		initializeDragAndDrop(_visual.getNode());
	}

	public FileDropControl 	getControl() {
		return control;
	}

	@Override
	public void 				dispose() {
		
	}

	protected void 				initializeDragAndDrop(Node _node) {
		_node.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();

			if(db.hasFiles()) {
				List<File> candidates = new ArrayList<File>();

				if(getControl().isMultiFileAllowed())
					candidates.addAll(db.getFiles());
				else
					candidates.add(db.getFiles().get(0));
				
				boolean allIsOK = true, currentIsOK = false;
				Collection<FileDropControl.Action> actions = getControl().getRegisteredActions();
				for(File f : candidates) {
					currentIsOK = false;
					for(FileDropControl.Action a : actions)
						if(a.test(f.toPath()))
							currentIsOK = true;
					
					if(!currentIsOK) {
						allIsOK = false;
						break;
					}
				}

				e.acceptTransferModes(allIsOK ? TransferMode.COPY_OR_MOVE : TransferMode.NONE);
			} else
				e.consume();
		});
		_node.setOnDragDropped(e -> {
			Collection<FileDropControl.Action> actions = getControl().getRegisteredActions();

			boolean success = true;

			Dragboard db = e.getDragboard();
			
			if(e.getTransferMode() == TransferMode.MOVE || e.getTransferMode() == TransferMode.COPY)
				for(File f : db.getFiles())
					for(FileDropControl.Action a : actions)
						if(a.test(f.toPath()))
							a.accept(f.toPath());

			e.setDropCompleted(success);
			e.consume();
		});
	}

}
