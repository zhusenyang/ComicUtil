package Alert;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertDialog extends Application {

	@Override
	public void start(Stage searchWrong) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass()
	            .getResource("/searchwrong/AlertDialog.fxml"));
	    
		searchWrong.setTitle("搜索有误");
		searchWrong.setScene(new Scene(root));
		searchWrong.getIcons().add(new Image("icon.jpg"));
		searchWrong.show();
	}
	
}
