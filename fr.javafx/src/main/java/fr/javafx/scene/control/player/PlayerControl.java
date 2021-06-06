package fr.javafx.scene.control.player;

import fr.java.player.PlayList;
import fr.java.player.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class PlayerControl extends Control {
	private final ObjectProperty<Player<?>>   player;
	private final ObjectProperty<PlayList<?>> playlist;

	public PlayerControl() {
		this(null, null);
	}
	public PlayerControl(Player<?> _player) {
		this(_player, null);
	}
	public PlayerControl(Player<?> _player, PlayList<?> _playlist) {
		super();
		player   = new SimpleObjectProperty<Player<?>>(_player);
		playlist = new SimpleObjectProperty<PlayList<?>>(_playlist);

//		setPrefHeight(127);
	}

	@Override
	protected Skin<PlayerControl> 		createDefaultSkin() {
		return new PlayerControlVisualDefault(this);
	}

	public PlayerControlVisualDefault 	getPlayerSkin() {
		return (PlayerControlVisualDefault) getSkin();
	}

	public ObjectProperty<Player<?>> 	playerProperty() {
		return player;
	}
	public Player<?> 					getPlayer() {
		return player.get();
	}
	public void 						setPlayer(Player<?> _playable) {
		player.set(_playable);
	}

	public ObjectProperty<PlayList<?>> 	playlistProperty() {
		return playlist;
	}
	public PlayList<?> 					getPlaylist() {
		return playlist.get();
	}
	public void 						setPlaylist(PlayList<?> _playlist) {
		playlist.set(_playlist);
	}

}
