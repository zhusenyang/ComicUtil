package test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestImage extends Application {
//	@Override
//	  public void start(Stage primaryStage) {
//	    try {
//	      File file = new File("D:\\Java\\JavaFxText\\src\\icon.jpg");
//	      String localUrl = file.toURI().toURL().toString();
//	      // don"t load in the background
//	      Image localImage = new Image(localUrl, false);
//
//	      String remoteUrl = "http://cf.hamreus.com/cpic/b/7580.jpg";
//	      // load in the background
//	      Image remoteImage = new Image(remoteUrl, true);
//
//	      System.out.println(localUrl);
//	      System.out.println(remoteUrl);
//
//	    } catch (MalformedURLException ex) {
//	      // error
//	    }
//	  }
	
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title");
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 600, 330, Color.WHITE);
        
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        final ImageView imv = new ImageView();
        File file = new File("D:\\Java\\JavaFxText\\src\\icon.jpg");
	    String localUrl = null;
		try {
			localUrl = file.toURI().toURL().toString();
			System.out.println(localUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      // don"t load in the background
	      Image localImage = new Image(localUrl, false);
	      
	      ArrayList<ImageView> imageViews = new ArrayList<>();
	      for(int i = 0 ;i<30; i++){
	    	  Image localImage2 = new Image(localUrl, false);
	    	  ImageView imv22 = new ImageView();
	    	  imv22.setImage(localImage2);
	    	  imageViews.add(imv22);
	      }
	      
	      String remoteUrl = "http://cf.hamreus.com/cpic/b/7580.jpg";
	      // load in the background
	      Image remoteImage = null;
	      remoteImage = new Image(remoteUrl, true);
	      final CreateRemoteImage cri = new CreateRemoteImage();
	      cri.setRemoteUrl(remoteUrl);
	      final Timer imgTimer = new Timer();
	      
	      imgTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(cri.isAlive()){
					cri.interrupt();
					System.out.println("网络图片获取超时");
				}else{
					System.out.println("网络图片获取成功");
				}
				imgTimer.cancel();
				
			}
	    	  
	      }, 1000, 15000);
	      
	      cri.start();
	      System.out.println("网络图片获取线程开启");
	      
	      
	      
        imv.setImage(localImage);
        final ImageView imv2 = new ImageView();
        imv2.setImage(remoteImage);
        final HBox pictureRegion = new HBox();
        imv.setFitWidth(50);
        imv.setFitHeight(80);
        pictureRegion.getChildren().add(imv);
        pictureRegion.getChildren().add(imv2);
        for (ImageView imageView : imageViews) {
            pictureRegion.getChildren().add(imageView);
		}
        gridpane.setGridLinesVisible(false);
        pictureRegion.setManaged(true);
        pictureRegion.autosize();
        pictureRegion.setFillHeight(true);
        
        gridpane.addColumn(1, pictureRegion);
        root.getChildren().add(gridpane);        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	  public static void main(String[] args) {
	    launch(args);
	  }
	  
}

class CreateRemoteImage extends Thread{
	
	public String remoteUrl;
	
	@Override
	public void  run(){
		Image remoteImage = null;
	      remoteImage = new Image(remoteUrl, true);
	      System.out.println("线程结束");
	}
	public void setRemoteUrl(String remoteUrl){
		this.remoteUrl=remoteUrl;
	}
}
