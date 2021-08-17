import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	private static final String _LAYOUT_PATH = "layout.fxml";
	private static final String _TITLE = "Hello JavaFX from Scene Builder!";

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL path = getClass().getResource(_LAYOUT_PATH); // Caminho absoluto do arquivo.
		FXMLLoader fxmlLoader = new FXMLLoader(path); // Leitor FXML
		Parent root = fxmlLoader.load(); // Inicia a leitura
		Scene screen = new Scene(root); // Cria um canvas/ janela

		primaryStage.setTitle(_TITLE);
		primaryStage.setScene(screen);
		primaryStage.show(); // Apresenta a aplicação
	}
}
