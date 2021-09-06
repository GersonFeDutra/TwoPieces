package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

abstract class SceneController {
	private String _crewName = "Placeholder hats";

	private Stage _stage; // Contêiner primário da aplicação.
	private Scene _scene; // Cena atual - que está sendo apresentada na aplicação.
	private Parent _root; // Raíz da cena atual.
	static String _LAYOUTS_DIR_PATH = "../layouts/";

	@FXML
	private VBox window;

	public abstract void switchScene(ActionEvent event) throws IOException;

	protected void _switchScene(ActionEvent event, String to) throws IOException {
		// Carrega o arquivo do FXML com o layout da nova cena.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(_LAYOUTS_DIR_PATH + to));
		_root = fxmlLoader.load();
		// Controlador da cena instanciada.
		SceneController controller = fxmlLoader.<SceneController>getController();
		// Configurações da cena.
		controller.setCrewName(_crewName);
		_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		_centerStage(controller.window.getPrefWidth(), controller.window.getPrefHeight());
		_scene = new Scene(_root);
		_stage.setScene(_scene);
	}

	private void _centerStage(double width, double height) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		_stage.setX((screenBounds.getWidth() - width) / 2);
		_stage.setY((screenBounds.getHeight() - height) / 2);
	}

	public void setCrewName(String crewName) {
		this._crewName = crewName;
	}
}
