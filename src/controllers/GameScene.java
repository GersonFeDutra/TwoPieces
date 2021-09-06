package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.edu.ufca.actors.Player;
import br.edu.ufca.actors.Region;
import br.edu.ufca.gameObjects.Crew;
import br.edu.ufca.gameObjects.Island;
import controllers.GameMap.Cell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameScene extends SceneController implements Initializable {
	private static String _MAIN_SCENE_PATH = "main_scene.fxml";
	private GameMap _map;
	private Player _player;
	private Region _region = new Region();
	private Image _spriteNavigate = new Image("file:assets/compass.png", 64, 0, true, true);
	private Image _spriteNavigateColored = new Image("file:assets/compass-purple.png", 64, 0, true,
			true);

	@Override
	public void switchScene(ActionEvent event) throws IOException {
		_switchScene(event, _MAIN_SCENE_PATH);
	}

	@FXML
	private Canvas mapCanvas;

	@FXML
	private Label guideLabel;
	@FXML
	private Label crewLabel;
	@FXML
	private Label treasuresDisplay;
	@FXML
	private Label membersDisplay;
	@FXML
	private Label foodDisplay;
	@FXML
	private Label scoreDisplay;
	@FXML
	private Label ammoDisplay;

	@FXML
	private ImageView imgButtonSwitchRegion;

	@FXML
	private ScrollPane logScrollPane;

	@FXML
	private TextFlow logPane;

	@FXML
	private TitledPane battlePane;

	@FXML
	private ProgressBar attackBar;

	@FXML
	void onAttackButtonClicked(ActionEvent event) {
		// @laisdumont
	}

	@FXML
	void popupWarning(ActionEvent event) throws IOException {
		if (choose("Está certo disso?", "Retornar a tela principal.",
				"Retornar a tela principal fará com que você perca progresso."))
			// choose("Are you sure?", "Return to the main screen.", "Returning to the main
			// screen will cause you to lose progress.")
			// ... user chose OK
			switchScene(event);
		// else
		// ... user chose CANCEL or closed the dialog
	}

	void selectIsland() {
		log("Island selecionado.");
	}

	void selectCrew() {
		log("Crew selecionado.");
	}

	/*
	 * Muda o mapa para uma nova chunk na região.
	 */
	private void shuffleMap() {
		_region.generateChunk(_map.getGridWidth(), _map.getGridHeight());
		_region.iterate((xy, x, y) -> {
			Cell type = null;

			if (xy != null)

				if (xy instanceof Crew)
					type = Cell.CREW;
				else if (xy instanceof Island)
					type = Cell.ISLAND;

			_map.setCell(x, y, type);
		});
		_map.update();
	}

	/*
	 * Apresenta uma janela de mensagem na tela com uma escolha. Retorna booleano
	 * que indica se a escolha foi aceita.
	 */
	public boolean choose(String title, String header, String context) {
		Alert alertWindow = new Alert(Alert.AlertType.CONFIRMATION);
		alertWindow.setTitle(title);
		alertWindow.setHeaderText(header);
		alertWindow.setContentText(context);

		Optional<ButtonType> result = alertWindow.showAndWait();
		return result.get() == ButtonType.OK;
	}

	/*
	 * Adiciona texto no painel de log.
	 */
	public void log(Text text) {
		text.setStyle("-fx-font-family: \"Caladea\";-fx-font-size: 13.0;");
		logPane.getChildren().add(text);
		logScrollPane.setVvalue(1.0);
	}

	/*
	 * Apresenta uma mensagem no painel de Log.
	 */
	public void log(String logText) {
		Text text = new Text();
		text.setText(logText + "\n");
		// text.setStyle("-fx-font-family: \"Caladea\";-fx-font-size:
		// 13.0;-fx-font-style: italic;");
		log(text);
	}

	public void setTreasureScore(int points) {
		treasuresDisplay.setText(String.valueOf(points));
	}

	public void setMemberScore(int points) {
		membersDisplay.setText(String.valueOf(points));
	}

	public void setFoodScore(int points) {
		foodDisplay.setText(String.valueOf(points));
	}

	public void setAmmoScore(int points) {
		ammoDisplay.setText(String.valueOf(points));
	}

	public void setScore(int points) {
		scoreDisplay.setText(String.valueOf(points));
	}

	public void setAttackBarLevel(double to) {
		attackBar.setProgress(to);
	}

	public void showBattlePane() {
		battlePane.setVisible(true);
	}

	public void initialize(URL location, ResourceBundle resources) {
		guideLabel.setText("");
		battlePane.setVisible(false);
		_map = new GameMap(mapCanvas.getGraphicsContext2D(), mapCanvas.getWidth(),
				mapCanvas.getHeight());
		shuffleMap();

		Text welcomeText = (Text) logPane.getChildren().get(0);

		if (welcomeText != null)
			welcomeText.setText("Bem vindo ao mundo dos piratas!\n");

		// Por algum motivo os eventos de mouse do JavaFX tem de ser tratados com
		// filtros que recebem objetos sobrescrevendo a função de tratamento.
		// @laisdumont adicionar evento "Navegar"
		mapCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("mouse pressed");
				GameMap.Cell cell = _map.positionToCell((int) event.getX(), (int) event.getY());

				if (cell == null)
					return;

				switch (cell) {
				case ISLAND:
					selectIsland();
					break;
				case CREW:
					selectCrew();
					break;
				}

			}
		});
		mapCanvas.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				GameMap.IPoint2D coords = _map.mapToCoords(event.getX(), event.getY());
				Object current = _region.getObjectAt(coords.x, coords.y);
				_map.highlightCell(coords);

				if (current == null)
					guideLabel.setText("");
				else if (current instanceof Island)
					guideLabel.setText("Navegar para ilha.");
				else if (current instanceof Crew)
					guideLabel.setText("Pilhar bando.");
			}
		});
		mapCanvas.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				_map.disableHighlight();
				guideLabel.setText("");
			};
		});
		imgButtonSwitchRegion.addEventFilter(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						System.out.println("Navegar!"); // @laisdumont <- Tu vai fazer esse evento
						log("Navegar!");
					}
				});
		imgButtonSwitchRegion.addEventFilter(MouseEvent.MOUSE_MOVED,
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent arg0) {
						imgButtonSwitchRegion.setImage(_spriteNavigateColored);
					};
				});
		imgButtonSwitchRegion.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET,
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent arg0) {
						imgButtonSwitchRegion.setImage(_spriteNavigate);
					};
				});
	}

	public void updateCanvas() {
		_map.update();
	}

	@Override
	public void setCrewName(String crewName) {
		super.setCrewName(crewName);
		crewLabel.setText(crewName);
	}
}
