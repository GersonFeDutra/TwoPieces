package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class GameStart extends SceneController {
	private static String _GAME_SCENE_PATH = "game.fxml";

	@FXML
	private Button startButton;

	@FXML
	private TextField crewNameField;

	@FXML
	void startGame(ActionEvent event) throws IOException {
		crewName = crewNameField.getText();
		System.out.println(crewName);
		switchScene(event);
	}

	@FXML
	void toggleStartButton(KeyEvent event) {
		startButton.setDisable(crewNameField.getText().length() == 0);
	}

	@Override
	public void switchScene(ActionEvent event) throws IOException {
		_switchScene(event, _GAME_SCENE_PATH);
	}
}
