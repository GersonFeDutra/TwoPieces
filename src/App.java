import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	// Caminho do arquivo FXML do layout principal da aplicação
	private static final String _LAYOUT_PATH = "layouts/main_scene.fxml";
	// Título da janela da aplicação.
	private static final String _TITLE = "Two Pieces";

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			URL path = getClass().getResource(_LAYOUT_PATH); // Caminho absoluto do arquivo.
			FXMLLoader fxmlLoader = new FXMLLoader(path); // Leitor FXML.
			Parent root = fxmlLoader.load(); // Raíz da cena extraída do FXML.
			Scene scene = new Scene(root); // Cena inicial.
			primaryStage.setTitle(_TITLE);
			primaryStage.setScene(scene);
			primaryStage.show(); // Apresenta a aplicação
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
