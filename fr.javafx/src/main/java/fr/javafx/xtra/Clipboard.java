package fr.javafx.xtra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.java.patterns.displayable.Selectable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;

public interface Clipboard {

	public static class System {
		private static final javafx.scene.input.Clipboard instance = javafx.scene.input.Clipboard.getSystemClipboard();
	
		public static void setTextContent(String _text) {
			final ClipboardContent content = new ClipboardContent();
	 
			content.putString(_text);
			instance.setContent(content);
		}
	 
		public static void setImageContent(Image _image) {
			final ClipboardContent content = new ClipboardContent();
	 
			content.putImage(_image);
			instance.setContent(content);
	 	}
	
		public static void setFileContent(File _file) {
			final ClipboardContent content = new ClipboardContent();
	 
			content.putFiles(java.util.Collections.singletonList(_file));
			instance.setContent(content);
	 
		}
		public static void setFilesContent(List<File> _files) {
			final ClipboardContent content = new ClipboardContent();
	 
			content.putFiles(_files);
			instance.setContent(content);
	 	}
	 
	}

	public static class SelectableItem {
		private static final ObservableList<Selectable> items = FXCollections.observableArrayList();
	
		public static boolean select(Selectable n, boolean selected) {
			if(n.requestSelection(selected)) {
				if(selected) {
					if(!items.contains(n))
						items.add(n);
				} else {
					items.remove(n);
				}
	
				return true;
			} else {
				return false;
			}
		}
	
		public static ObservableList<Selectable> getSelectedItems() {
			return items;
		}
	
		public static void deselectAll() {
			List<Selectable> unselectList = new ArrayList<>();
			unselectList.addAll(items);
	
			for(Selectable sN : unselectList)
				select(sN, false);
		}
	
	}

}
