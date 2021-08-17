import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class GameStartController {

    @FXML
    private Button startButton;

    @FXML
    private TextField crewNameField;

    @FXML
    void startGame(ActionEvent event) {
		String crewName = crewNameField.getText();
		System.out.println(crewName);
    }

    @FXML
	void toggleStartButton(KeyEvent event) {
		startButton.setDisable(crewNameField.getText().length() == 0);
    }

}
