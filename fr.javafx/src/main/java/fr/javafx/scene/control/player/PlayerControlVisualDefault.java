package fr.javafx.scene.control.player;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.END;
import static javafx.scene.input.KeyCode.F4;
import static javafx.scene.input.KeyCode.HOME;
import static javafx.scene.input.KeyCode.KP_DOWN;
import static javafx.scene.input.KeyCode.KP_LEFT;
import static javafx.scene.input.KeyCode.KP_RIGHT;
import static javafx.scene.input.KeyCode.KP_UP;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.lang.properties.Timestamp;
import fr.java.media.Media;
import fr.java.media.MediaPlayer;
import fr.java.media.sound.Sound;
import fr.java.player.PlayList;
import fr.java.player.Player;
import fr.java.player.PlayerEvent;
import fr.java.player.PlayerListener;
import fr.java.player.Recorder;
import fr.java.state.StateEvent;
import fr.java.state.StateListener;
import fr.java.utils.LocalFiles;
import fr.javafx.behavior.extended.BehaviorBase;
import fr.javafx.behavior.extended.bindings.KeyBinding;
import fr.javafx.behavior.extended.bindings.impl.OrientedKeyBinding;
import fr.javafx.behavior.extended.bindings.impl.SimpleKeyBinding;
import fr.javafx.scene.control.ClampableSlider;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerControlVisualDefault implements Skin<PlayerControl>, StateListener, PlayerListener {
	private static final int big = 40, small = 32;

	final private PlayerControl control;

	boolean useDuration = false;
	boolean useDHMS     = true;
	Function<Double, String> valueDisplay = d -> {
		if(!useDuration)
			return String.format("%d", d.intValue()); // Assume d is frame index

		// Assume d is timestamp
		if(!useDHMS)
			return String.format("%04d ms", d.intValue());

		int t_ms    = d.intValue();
		int millis  = (int) ( t_ms) % 1000;
		int seconds = (int) ( t_ms / 1000) % 60;
		int minutes = (int) ((t_ms / (1000*60)) % 60);
		int hours   = (int) ((t_ms / (1000*60*60)) % 24);
		int days    = (int) ((t_ms / (1000*60*60*24)));

		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	};

	/* ********************************************************** */
	private AnchorPane		mainPane;

	private ClampableSlider recordSlider;

	private VBox			playerInfosPane;
	private HBox			playerControlsPane;
	private HBox			recorderControlsPane;
	private HBox			recordControlsPane;
	private HBox			volumeControlsPane;

	private Label 			playRate;
	private Label 			currentFrame, totalFrame;

	private GridPane		playerControlsGroup;
	private ButtonBase		play_pause, stop, step_forward, step_backward, slower, faster, record;

	private HBox			volumeControlsGroup;
	private ButtonBase		mute, volumeUp, volumeDn;

	private HBox			recordControlsGroup;
	private ButtonBase		refresh, verse, markA, markB, loopAB, repeat;

	private GridPane		playlistControls;
	private ButtonBase		playlist, previous, next;

	public PlayerControlVisualDefault(PlayerControl _control) {
		super();
		control = _control;

		_control.playerProperty().addListener((_obs, _old, _new) -> build());
		_control.playerProperty().get().registerListener((StateListener) this::onStateEvent);
		_control.playerProperty().get().registerListener((PlayerListener) this::onPlayerEvent);
		
		build();
	}
	
	@Override
	public PlayerControl 		getSkinnable() {
		return control;
	}
	@Override
	public final Node 			getNode() {
		if(mainPane == null) {
			mainPane = new AnchorPane();
			mainPane . setStyle("-fx-background-color : orange;");
		}
		return mainPane;
	}
	@Override
	public void 				dispose() {
		;
	}

	public ObservableList<Node> getChildren() {
		return ((AnchorPane) getNode()).getChildren();
	}

	@Override
	public void 				onStateEvent(StateEvent _event) {
		State     state  = _event.getState();
		Player<?> player = control.playerProperty().get();

		if(state instanceof ServiceState)
			switch((ServiceState) state) {
			case setting     :
			case starting    :
			case pausing     :
			case resuming    :
			case stopping    : break ;
			case ready       : build(); break ;
			case started     : break ;
			case paused      : break ;
			case stopped     : onStop(player); break ;
			case faulted     :
			case unavailable :
			default          : break;
			}
	}
	@Override
	public void 				onPlayerEvent(PlayerEvent _event) {
		Timestamp timestamp = _event.getTimestamp();
		
		if(timestamp == null)
			return ;

		long fid    = _event.getFrameIndex();

		long t_ms   = _event.getTimestamp().toEpochMilli();

		int millis  = (int) ( t_ms) % 1000;
		int seconds = (int) ( t_ms / 1000) % 60;
		int minutes = (int) ((t_ms / (1000*60)) % 60);
		int hours   = (int) ((t_ms / (1000*60*60)) % 24);
		int days    = (int) ((t_ms / (1000*60*60*24)));

		String position_ms = String.format("%07d ms", t_ms);
		String position    = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
		String frame       = String.format("%04d \t %04d", (int) (t_ms / 25d), fid);

		Platform.runLater(() -> {
			if(currentFrame != null)
				currentFrame . setText( valueDisplay.apply((double) (useDuration ? t_ms : fid)));
			if(recordSlider != null)
				recordSlider       . setValue(fid);
//			totalFrame.setText(frame);
		});
		

//		System.out.println(position_ms + "\t" + position + "\t" + frame);
//		currentFrame	. textProperty().set(_manipulable.frameIndex().get().toString());
	}

	void build() {
		if(mainPane != null)
			mainPane.getChildren().clear();

		// PLAYER CONTROLS
		// ---------------
		if(control.playerProperty().get() != null) {
			Player<?> player = control.playerProperty().get();
	
			createPlayerControlsPane();
			addPlayerControls		(player);
			addPlayerInfos			(player);
			addPlayerHandlers		(player);

			// MEDIA CONTROLS
			// --------------
			if(player instanceof MediaPlayer<?>) {
				MediaPlayer<?> mediaplayer = (MediaPlayer<?>) player;

				if(mediaplayer.media() instanceof Media.Record<?>) {
					Media.Record<?> record = (Media.Record<?>) mediaplayer.media();

					createRecordControlsPane();
					addRecordSlider		(record);
					addRecordControls	(record);
					addRecordInfos		(record);
					addRecordHandlers	(record);
				}
		
				if(mediaplayer.media() instanceof Media) {
					if(mediaplayer.media() instanceof Sound) {
						Sound<?> sound = (Sound<?>) mediaplayer.media();
						addVolumeControls(sound);
					}
				}
		
			}

			// RECORDER CONTROLS
			// -----------------
			if(player instanceof Recorder<?>) {
				Recorder<?> recorder = (Recorder<?>) player;

				addRecorderControls(recorder);
				addRecorderHandlers(recorder);
			}

		}

		// PLAYLIST CONTROLS
		// -----------------
		if(control.playlistProperty().get() == null) {
			PlayList<?> playlist = control.playlistProperty().get();

			if(playlist != null)
				addPlaylistControls();
		}

	}

	private void createPlayerControlsPane() {
		playerControlsPane = new HBox();
//		playerControlsPane.setStyle("-fx-background-color : red;");
		playerControlsPane.setAlignment(Pos.CENTER);

		AnchorPane.setLeftAnchor  	(playerControlsPane, 0.0);
		AnchorPane.setRightAnchor 	(playerControlsPane, 0.0);
		AnchorPane.setTopAnchor   	(playerControlsPane, recordSlider != null ? recordSlider.getHeight() + 5.0 : 5.0);

		getChildren().add(playerControlsPane);
	}
	private void createRecordControlsPane() {
		recordControlsPane = new HBox();
//		recordControlsPane.setStyle("-fx-background-color : yellow;");
		recordControlsPane.setAlignment(Pos.CENTER_LEFT);

		AnchorPane.setLeftAnchor	(recordControlsPane, 5.0);
		AnchorPane.setBottomAnchor	(recordControlsPane, 5.0);

		getChildren().add(recordControlsPane);
	}
	private void createRecorderControlsPane() {
		recorderControlsPane = new HBox();
//		recorderControlsPane.setStyle("-fx-background-color : magenta;");
		recorderControlsPane.setAlignment(Pos.CENTER_LEFT);

		AnchorPane.setLeftAnchor	(recorderControlsPane, 5.0);
		AnchorPane.setBottomAnchor	(recorderControlsPane, 5.0);

		getChildren().add(recorderControlsPane);
	}

	void addPlayerControls(Player<?> _player) {
		Command ppCommand = _player.getState() == ServiceState.started ? Command.PAUSE : Command.PLAY;

		play_pause = buildBtn(false, ppCommand, 			  big,   big, Command.PLAY);
		stop 	   = buildBtn(false, Command.STOP, 			small,    20, Command.STOP);
		faster 	   = buildBtn(false, Command.FASTER, 		small, small, Command.FASTER);
		slower 	   = buildBtn(false, Command.SLOWER, 		small, small, Command.SLOWER);

		playerControlsGroup = new GridPane();
		playerControlsGroup . setPadding(new Insets(8, 0, 8, 0));
		playerControlsGroup . add(slower,     2, 0);
		playerControlsGroup . add(play_pause, 3, 0);
		playerControlsGroup . add(stop,       3, 1);
		playerControlsGroup . add(faster,     4, 0);
		GridPane.setHalignment(stop, HPos.CENTER);

		playerControlsPane.getChildren().add(playerControlsGroup);
	}
	void addPlayerInfos(Player<?> _player) {
		playRate = new Label("x" + _player.getPlayRate());

		playerInfosPane = new VBox();
		playerInfosPane . getChildren().addAll(playRate);

		AnchorPane.setTopAnchor		(playerInfosPane, 27.0);
		AnchorPane.setRightAnchor	(playerInfosPane, 5.0 + playerInfosPane.getWidth());

		getChildren().add(playerInfosPane);
	}
	void addPlayerHandlers(Player<?> _player) {
		play_pause.setOnAction((event) -> {
			try {
				if(_player.isStarted()) {
					_player.pause();
					play_pause.setGraphic(Command.PLAY.getIconView());

				} else {
					if(_player.isPaused())
						_player.resume();
					else
						_player.play();
					play_pause.setGraphic(Command.PAUSE.getIconView());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		});

		stop.setOnAction((event) -> {
			play_pause.setGraphic(Command.PLAY.getIconView());

			if(_player.isStarted() || _player.isPaused())
				try { _player.stop();
				} catch(Exception e) { e.printStackTrace(); }
		});

		DecimalFormat df = new DecimalFormat("#.00");
		faster.setOnAction((event) -> {
			_player.setPlayRate(getSkinnable().getPlayer().getPlayRate() * 1.25);
			playRate.setText("x" + df.format( _player.getPlayRate() ));
		} );
		slower.setOnAction((event) -> {
			_player.setPlayRate(getSkinnable().getPlayer().getPlayRate() / 1.25);
			playRate.setText("x" + df.format( _player.getPlayRate() ));
		});
	}

	void addRecordSlider(Media.Record<?> _record) {
		recordSlider = new ClampableSlider(0, _record.frameCount().get() - 1, 0);
		recordSlider.setBlockIncrement(250);

		AnchorPane.setTopAnchor		(recordSlider,        0.0);
		AnchorPane.setLeftAnchor	(recordSlider,       15.0);
		AnchorPane.setRightAnchor	(recordSlider,       15.0);

		getChildren().add(recordSlider);

		// Update other pane positions
		AnchorPane.setTopAnchor		(playerInfosPane,    12.0);
		AnchorPane.setTopAnchor   	(playerControlsPane, 12.0);
	}
	void addRecordControls(Media.Infos _record) {
		recordControlsGroup = new HBox();

		refresh	= buildBtn(false, Command.REFRESH,			small, small, Command.REFRESH);
		repeat 	= buildBtn( true, Command.LOOPOREPEATMODE, 	small, small, Command.LOOPOREPEATMODE);
		verse 	= buildBtn( true, Command.VERSE,			small, small, Command.VERSE);
		markA 	= buildBtn( true, Command.MARK_A, 			small, small, Command.MARK_A);
		markB 	= buildBtn( true, Command.MARK_B, 			small, small, Command.MARK_B);
		loopAB 	= buildBtn( true, Command.LOOP, 			small, small, Command.LOOP);

		recordControlsGroup.getChildren().addAll(refresh, repeat, verse, markA, markB, loopAB);

//		if( _record.hasMarks() ) {
			step_backward 	= buildBtn(false, Command.STEPBACKWARD,	small, small, Command.STEPBACKWARD);
			step_forward 	= buildBtn(false, Command.STEPFORWARD, 	small, small, Command.STEPFORWARD);

			playerControlsGroup.add(step_backward, 1, 0);
			playerControlsGroup.add(step_forward,  5, 0);
//		}

		recordControlsPane.getChildren().add(recordControlsGroup);

		if(recordSlider != null) {
			recordSlider.startClampableProperty().bind(((ToggleButton) markA).selectedProperty());
			recordSlider.stopClampableProperty().bind(((ToggleButton) markB).selectedProperty());
	
			if(_record.frameStart().isPresent()) {
				((ToggleButton) markA).setSelected(true);
				recordSlider.setStart(_record.frameStart().get());
			}
			if(_record.frameStop().isPresent()) {
				((ToggleButton) markB).setSelected(true);
				recordSlider.setStop(_record.frameStop().get());
			}
		}
	}
	void addRecordInfos(Media.Record<?> _record) {
		HBox boxText = new HBox(currentFrame = new Label("???"), new Label("/"), totalFrame   = new Label("???"));
		playerInfosPane.getChildren().add(boxText);
	}
	void addRecordHandlers(Media.Record<?> _record) {
		currentFrame	. setText( _record.frameIndex().get().toString() );
		totalFrame		. setText( _record.frameCount().get().toString() );

		if(step_backward != null)
			step_backward.setOnAction((event) -> _record.setStart(_record.frameIndex().get() - 24) );
		if(step_forward != null)
			step_forward.setOnAction((event) -> _record.setStop(_record.frameIndex().get() + 24) );

		ChangeListener<? super Number> setCursor = (_obs, _old, _new) -> _record.setIndex(_new.longValue());
		recordSlider.setPopUpFormatter(d -> "" + d.intValue());
//		slider.setOnMousePressed((_evt) -> { slider.valueProperty().addListener(setCursor); } );
//		slider.setOnMouseReleased((_evt) -> { slider.valueProperty().removeListener(setCursor); });
		recordSlider.setOnKeyPressed((event) -> {
			switch(event.getCode()) {
			case RIGHT : 	{
							double newVal = recordSlider.getValue() + 1;
							if(newVal <= recordSlider.getMax())
								recordSlider.setValue(newVal);
							event.consume();
							}
							break;
			case LEFT : 	{
							double sVal = recordSlider.getValue();
							long newVal = (long) (recordSlider.getValue() - 1);
							while((sVal == newVal))
								newVal--;
							if(newVal >= recordSlider.getMin())
								recordSlider.setValue(newVal);
							event.consume();
							}
							break;
			default :		break;
			}
		});
		recordSlider.setOnMouseDragged((evt) -> {
			Node node = (Node) evt.getTarget();
			if(node.getStyleClass().contains("thumb-start") ) {
				_record.setStart((int) recordSlider.getStart());
			} else if(node.getStyleClass().contains("thumb-stop") ) {
				_record.setStop((int) recordSlider.getStop());
			} else if(node.getStyleClass().contains("thumb") ) {
				int index = recordSlider.getValue() >= 1 ? (int) recordSlider.getValue() - 1 : 0;
				_record.setIndex(index);
			}
		});

		recordSlider.startClampableProperty() . addListener((_obs, _old, _new) -> { if(_new == false) _record.setStart(0); });
		recordSlider.stopClampableProperty()  . addListener((_obs, _old, _new) -> { if(_new == false) _record.setStop(_record.frameCount().get() - 1); });
		recordSlider.startProperty()          . addListener((_obs, _old, _new) -> { _record.setStart(_new.longValue()); });
		recordSlider.stopProperty()           . addListener((_obs, _old, _new) -> { _record.setStop(_new.longValue()); });
	}

	void addVolumeControls(Sound<?> _sound) {
		GridPane volume = new GridPane();

		mute 		= buildBtn(true, Command.MUTE, 			32, 32, Command.MUTE);
		volumeUp 	= buildBtn(false, Command.VOLUME_UP, 	32, 32, Command.VOLUME_UP);
		volumeDn 	= buildBtn(false, Command.VOLUME_DOWN, 	32, 32, Command.VOLUME_DOWN);

		volumeUp.setOnAction((e) -> {
//			int prevVolume = ((Sound) getSkinnable().getPlayer()).getVolume();
//			((Sound) getSkinnable().getPlayer()).setVolume(prevVolume + 10);
		});
		volumeDn.setOnAction((e) -> {
//			int prevVolume = ((Sound) getSkinnable().getPlayer()).getVolume();
//			((Sound) getSkinnable().getPlayer()).setVolume(prevVolume - 10);
		});

		GridPane.setMargin(volume, new Insets(8, 8, 8, 16));

//		volume.getChildren().addAll(mute, volumeUp, volumeDn);
		volume.add(mute, 0, 0);
		volume.add(volumeUp, 2, 0);
		volume.add(volumeDn, 3, 0);

		AnchorPane.setLeftAnchor	(volume, 5.0);
		mainPane.getChildren().add(volume);
	}

	void addRecorderControls(Recorder<?> _recorder) {
		record = buildBtn(false, Command.RECORD_ON,	small, small, Command.RECORD_ON);

		playerControlsGroup.add(record, 6, 0);
	}
	void addRecorderHandlers(Recorder<?> _recorder) {
		record.setOnAction((event) -> {
			System.err.println("TBD");
			/*
			if(_recorder.isStopped()) {
				record.setGraphic(Command.RECORD_ON.getIconView());
				record.setTooltip(new Tooltip(Command.RECORD_ON.getText()));
			} else {
				record.setGraphic(Command.RECORD_OFF.getIconView());
				record.setTooltip(new Tooltip(Command.RECORD_OFF.getText()));
			}
			*/
		});
	}

	void addPlaylistControls() {
		playlistControls = new GridPane();

		playlist		= buildBtn(false, Command.PLAYLIST,		small, small, Command.PLAYLIST);

		previous 		= buildBtn(false, Command.PREVIOUS, 	small, small, Command.PREVIOUS);
		next 			= buildBtn(false, Command.NEXT, 		small, small, Command.NEXT);

		GridPane.setMargin(previous, 	new Insets(0, 4, 0, 0));
		GridPane.setMargin(next, 		new Insets(0, 0, 0, 4));

//		previous.setOnAction((event) -> {});
//		next.setOnAction((event) -> {});

		playlist.setOnMouseClicked((event) -> {});

		playerControlsGroup.add(previous, 	1, 0);
		playerControlsGroup.add(next, 		5, 0);

		GridPane.setMargin(playlist, new Insets(0, 8, 0, 0));

//		playbox.setPadding(new Insets(8, 0, 8, 8));
		
		playlistControls.add(playlist, 0, 0);
		playlistControls.add(step_backward, 1, 0);
		playlistControls.add(step_forward, 2, 0);

		AnchorPane.setBottomAnchor	(playlistControls, 5.0);
		mainPane.getChildren().add(playlistControls);
	}

	void onStop(Player<?> _player) { 
		Platform.runLater(() -> play_pause.setGraphic(Command.PLAY.getIconView()));

		if(repeat != null && ((ToggleButton) repeat).isSelected())
			try {
				_player.play();
			} catch(Exception e) { e.printStackTrace(); }
	}

	public static enum Command {
		// Playable Control
		STOP			("Stop", 				null), 
		PAUSE			("Pause", 				null), 
		PLAY			("Play", 				null), 
		SLOWER			("Slower", 				null), 
		FASTER			("Faster", 				null), 
		// Volume
		MUTE			("Mute", 				null), 
		UNMUTE			("Unmute", 				null), 
		VOLUME_UP		("VolumeUp", 			null), 
		VOLUME_DOWN		("VolumeDown", 			null),
		// Advanced Playable Control
		REFRESH			("Refresh", 			null), 
		VERSE			("Verse", 				null), 
		REVERSE			("Reverse", 			null), 
		STEPBACKWARD	("StepBackward", 		null), 
		STEPFORWARD		("StepForward", 		null), 
		LOOPOREPEATMODE	("Loop_or_Repeat_mode", null), 
		LOOP			("Loop_A_B", 			null),
		MARK_A			("Mark_A", 				null),
		MARK_B			("Mark_B", 				null),
		// Recordable Control
		RECORD_ON		("RecordOn", 			null), 
		RECORD_OFF		("RecordOff", 			null),
		// PlayList Control
		PLAYLIST		("PlayList", 			null), 
		PREVIOUS		("Previous", 			null), 
		NEXT			("Next", 				null);

		String	prefix;
		String	tooltip;

		Image 		icon;
		ImageView 	view;

		private Command(String _prefix, String _tooltip) {
			prefix  = _prefix;
			tooltip = _tooltip;
		}

		public String getText() {
			return tooltip == null ? prefix.replace("_or_", "/").replace("_", " ") : tooltip;
		}
		public String getPrefix() {
			return prefix;
		}
		public InputStream getIconStream() {
			return LocalFiles.getContent(PlayerControlVisualDefault.class, "/default/playerfx/" + prefix + ".png");
		}
		public Image getIconImage() {
			return new Image(LocalFiles.getContent(PlayerControlVisualDefault.class, "/default/playerfx/" + prefix + ".png"));
		}
		public ImageView getIconView() {
			return new ImageView(new Image(LocalFiles.getContent(PlayerControlVisualDefault.class, "/default/playerfx/" + prefix + ".png")));
		}

	}

	private ButtonBase buildBtn(boolean _toggle, Command _icon, int _w, int _h, Command _tooltip) {
		ButtonBase btn = _toggle ?  new ToggleButton(null, _icon.getIconView()) : new Button(null, _icon.getIconView());

		btn.setMaxSize(_w, _h);
		btn.setMinSize(_w, _h);
		btn.setTooltip(new Tooltip(_tooltip.getText()));

		return btn;
	}

    public static class PlayerKeyBinding<B extends BehaviorBase<?, ?, B>> extends OrientedKeyBinding<B> {
        public PlayerKeyBinding(KeyCode code, String action) {
            super(code, action);
        }

        public PlayerKeyBinding(KeyCode code, EventType<KeyEvent> type, String action) {
            super(code, type, action);
        }

        @Override 
        public boolean getVertical(Control control) {
            return false; //((Slider)control).getOrientation() == Orientation.VERTICAL;
        }
    }

	private static final <B extends BehaviorBase<?, ?, B>> List<KeyBinding<B>> sliderBindings() {
		List<KeyBinding<B>> PLAYER_BINDINGS = new ArrayList<KeyBinding<B>>();

		PLAYER_BINDINGS.add(new SimpleKeyBinding<B>(F4, "TraverseDebug").alt().ctrl().shift());

		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(LEFT, "DecrementValue"));
		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(KP_LEFT, "DecrementValue"));
		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(UP, "IncrementValue").vertical());
		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(KP_UP, "IncrementValue").vertical());
		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(RIGHT, "IncrementValue"));
		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(KP_RIGHT, "IncrementValue"));
		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(DOWN, "DecrementValue").vertical());
		PLAYER_BINDINGS.add(new PlayerKeyBinding<B>(KP_DOWN, "DecrementValue").vertical());

		PLAYER_BINDINGS.add(new SimpleKeyBinding<B>(HOME, KeyEvent.KEY_RELEASED, "Home"));
		PLAYER_BINDINGS.add(new SimpleKeyBinding<B>(END, KeyEvent.KEY_RELEASED, "End"));
		
		return PLAYER_BINDINGS;
	}

}
