package controllers;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameScene extends SceneController {
	private static String _MAIN_SCENE_PATH = "main_scene.fxml";
	private GameMap _map;

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

	public void initCanvas() {
		_map = new GameMap(mapCanvas.getGraphicsContext2D(), (int) mapCanvas.getWidth(), (int) mapCanvas.getHeight());
		_map.update();
	}

	public void updateCanvas() {
		_map.update();
	}
}