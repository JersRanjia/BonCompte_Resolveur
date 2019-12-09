import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader() ;
        Parent parent = loader.load(this.getClass().getResource("ui/Index.fxml")) ;
        Scene scene = new Scene(parent) ;
        primaryStage.setTitle("Bon Compte Resolveur");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
