package controllers;

import java.io.IOException;
import java.net.URL;
// import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class StartScene extends SceneController implements Initializable {
	private static String _GAME_SCENE_PATH = "game.fxml";

	@FXML
	private Button startButton;

	@FXML
	private TextField crewNameField;

	// @FXML
	// private ComboBox<String> languageComboBox;

	@FXML
	void startGame(ActionEvent event) throws IOException {
		setCrewName(crewNameField.getText());
		switchScene(event);
	}

	@FXML
	void toggleStartButton(KeyEvent event) {
		startButton.setDisable(crewNameField.getText().length() == 0);
	}

	// @FXML
	// void changeLanguage(ActionEvent event) {
	// }

	@Override
	public void switchScene(ActionEvent event) throws IOException {
		_switchScene(event, _GAME_SCENE_PATH);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// languageComboBox.getItems().removeAll(languageComboBox.getItems());
		// languageComboBox.getItems().addAll(resources.getString("languageOption.en"),
		// resources.getString("languageOption.pt"));
		// languageComboBox.getSelectionModel().select("English");
	}
}
