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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameScene extends SceneController implements Initializable {
	private static String _MAIN_SCENE_PATH = "main_scene.fxml";
	private GameMap _map;
	private Player _player;
	private Region _region = new Region();

	@Override
	public void switchScene(ActionEvent event) throws IOException {
		_switchScene(event, _MAIN_SCENE_PATH);
	}

	@FXML
	private Canvas mapCanvas;

	@FXML
	private Font x1;

	@FXML
	private Color x2;

	@FXML
	void popupWarning(ActionEvent event) throws IOException {
		Alert alertWindow = new Alert(Alert.AlertType.CONFIRMATION);
		alertWindow.setTitle("Are you sure?");
		alertWindow.setHeaderText("Return to the main screen.");
		alertWindow.setContentText("Returning to the main screen will cause you to lose progress.");

		Optional<ButtonType> result = alertWindow.showAndWait();
		System.out.println(crewName);

		if (result.get() == ButtonType.OK)
			// ... user chose OK
			switchScene(event);
		// else
		// ... user chose CANCEL or closed the dialog
	}

	void selectIsland() {

		System.out.println("Island selecionado.");
	}

	void selectCrew() {
		System.out.println("Crew selecionado.");
	}

	private void shuffleMap() {
		_region.generateChunk(_map.getWidth(), _map.getHeight());
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

	public void initialize(URL location, ResourceBundle resources) {
		_map = new GameMap(mapCanvas.getGraphicsContext2D(), (int) mapCanvas.getWidth(), (int) mapCanvas.getHeight());
		shuffleMap();

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
				_map.highlightCell((int) event.getX(), (int) event.getY());
			}
		});

		mapCanvas.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				_map.disableHighlight();
			};
		});
	}

	public void updateCanvas() {
		_map.update();
	}
}
