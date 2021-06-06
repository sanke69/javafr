package fr.javafx.scene.control.collection.styles;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import fr.java.lang.functionals.Constructor;
import fr.java.nio.file.FileX;
import fr.java.patterns.visitable.Treeable;
import fr.java.sdk.nio.FilesX;

import fr.javafx.scene.control.collection.FxTreeableStyle;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FxTreeableStyleFileSystem extends FxTreeableStyle {

	public FxTreeableStyleFileSystem() {
		super();
	}

	@Override public Constructor.OneArg<TreeableItem, Treeable<?, ?>> 			itemGenerator() { return (_bean) -> new TreeableItem(_bean, new ImageView(getIconFor(_bean))); }
	@Override public Constructor.OneArg<TreeableCell, TreeView<Treeable<?, ?>>> cellGenerator() { return null; }
	@Override public Constructor.OneArg<ContextMenu,  Treeable<?, ?>> 			menuGenerator() { return null; }

	protected static final int 		iconSize    	= 24;
	public    static final Image	fileDftIcon		= new Image(FxTreeableStyleFileSystem.class.getResourceAsStream("/default/file-system/file-default.png"),	iconSize, iconSize, true, true);
	public    static final Image	folderOpenIcon	= new Image(FxTreeableStyleFileSystem.class.getResourceAsStream("/default/file-system/folder-open.png"), 	iconSize, iconSize, true, true);
	public    static final Image	folderCloseIcon	= new Image(FxTreeableStyleFileSystem.class.getResourceAsStream("/default/file-system/folder-close.png"), iconSize, iconSize, true, true);
	protected Map<String, Image>  	loadedIcons 	= new HashMap<String, Image>();

	protected Image getIconFor(Treeable<?, ?> _item) {
		if(_item instanceof FileX)
			return getIconForFileX((FileX) _item);

		if(_item.getData() instanceof Path)
			return getIconForFileX(FilesX.of((Path) _item.getData()));
		if(_item.getData() instanceof File)
			return getIconForFileX(FilesX.of((File) _item.getData()));

		return fileDftIcon;
	}
	protected Image getIconForFileX(FileX _fo) {
		if(!_fo.isLeaf())
			return folderCloseIcon;

		String fileName  = _fo.getName();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		if(loadedIcons.get(extension) != null)
			return loadedIcons.get(extension);

		InputStream iconIS = FxTreeableStyleFileSystem.class.getResourceAsStream("/default/file-system/file-" + extension + ".png");
		if(iconIS != null) {
			Image image = new Image(iconIS,	iconSize, iconSize, true, true);
			loadedIcons.put(extension, image);
			return image;
		}

		return fileDftIcon;
	}

}
