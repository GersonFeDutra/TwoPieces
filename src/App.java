import java.net.URL;
// import java.util.Locale;
// import java.util.ResourceBundle;

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
	// TODO #2 -> Adicionar diretório de assets ao classpath, para habilitar as
	// locales.
	// // Caminho do arquivo de propriedades contendo as strings usadas na
	// aplicação.
	// private static final String _BUNDLE_PATH = "../assets/Bundle";
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
			// Leitor FXML.
			FXMLLoader fxmlLoader = new FXMLLoader(path/* , _getLocalizedResources() */);
			Parent root = fxmlLoader.load(); // Raíz da cena extraída do FXML.
			Scene scene = new Scene(root); // Cena inicial.

			if (isTestMode)
				fxmlLoader.<GameScene>getController();

			primaryStage.setTitle(_TITLE);
			primaryStage.setScene(scene);
			primaryStage.show(); // Apresenta a aplicação
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// // Retorna o bundle de localização.
	// private ResourceBundle _getLocalizedResources() {
	// // Locale padrão do usuário
	// Locale defaultLocale = Locale.getDefault();
	//
	// // if (defaultLocale.getLanguage().equals("pt"))
	// // defaultLocale = new Locale("pt");
	// // else
	// // defaultLocale = Locale.ENGLISH;
	//
	// // System.out.println(defaultLocale.getLanguage());
	// return ResourceBundle.getBundle(_BUNDLE_PATH, defaultLocale);
	// }
}
