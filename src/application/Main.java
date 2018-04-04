package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import sun.awt.image.PixelConverter.ArgbPre;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;



public class Main extends Application {
	
	@Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/application/MySecene.fxml"));
            AnchorPane rootA = (AnchorPane) root;
            primaryStage.setTitle("漫画工具");
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("icon.jpg"));
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
