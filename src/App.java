import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.GameScene;

public class App extends Application {
	// Caminho do arquivo FXML do layout principal da aplicação
	private static final String _LAYOUT_PATH = "layouts/main_scene.fxml";
	// Caminho do arquivo FXML da cena inicial alternativa.
	private static final String _TEST_MODE_SCENE_PATH = "layouts/game.fxml";
	// Determina se a aplicação está executando em modo de testes.
	private static boolean isTestMode = false;
	// Título da janela da aplicação.
	private static final String _TITLE = "Two Pieces";

	public static void main(String[] args) throws Exception {

		for (String arg : args) {
			// System.out.println(arg);

			if (arg.equals("-t"))
				isTestMode = true;
		}

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// Caminho absoluto do arquivo.
			URL path = getClass().getResource(isTestMode ? _TEST_MODE_SCENE_PATH : _LAYOUT_PATH);
			FXMLLoader fxmlLoader = new FXMLLoader(path); // Leitor FXML.
			Parent root = fxmlLoader.load(); // Raíz da cena extraída do FXML.
			Scene scene = new Scene(root); // Cena inicial.

			if (isTestMode)
				(fxmlLoader.<GameScene>getController()).initCanvas();

			primaryStage.setTitle(_TITLE);
			primaryStage.setScene(scene);
			primaryStage.show(); // Apresenta a aplicação
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
