package Util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Util.ModeUtlil.imp.ManHuaGuiUtil;
import Util.htmlUtil.UrlUtil;
import application.CConfig;
import entity.Book;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import searchcomponent.SearchResult;

public class TestSearchComic extends Application{
	
	ScrollPane scrollPane;
	
	public void test(){
		String keyWord = "一拳超人";
		String realKeyWord = UrlUtil.getURLEncoderString(keyWord);
		ArrayList<Book> result = null;
		String resultPage = GetHtml.GetInfo("http://www.manhuagui.com"+"/s/"+realKeyWord+".html");
		ManHuaGuiUtil mhg = new ManHuaGuiUtil();
		System.out.println("scrollPane:\t"+scrollPane);
		byte[] bytes = resultPage.getBytes();
		try {
			String resultPageUTF8 = new String(bytes, "UTF-8");
			result = mhg.rexBooks(resultPageUTF8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.size()>1){
			Pane resultContent = new Pane();
			System.out.println("content:\t"+scrollPane.getContent());
			System.out.println(resultContent.getChildren());
			for (Book book : result) {
				ImageView imv = new ImageView();
				String remoteUrl = book.getImageUrl();
				Image img = new Image(remoteUrl);
				imv.setImage(img);
				imv.setVisible(true);
				System.out.println("生成图片");
				System.out.println(resultContent);
				System.out.println("--------------------");
				resultContent.getChildren().add(imv);
			}
			ImageView ii = new ImageView();
			Image iii = new Image("http://cf.hamreus.com/cpic/b/7580.jpg");
			ImageView imv2 = new ImageView();
			Image img2 = new Image("http://cf.hamreus.com/cpic/b/9637.jpg");
			ArrayList<ImageView> list = new ArrayList<>();
			imv2.setImage(img2);
			ii.setImage(iii);
			
			list.add(imv2);
			list.add(ii);
			resultContent.getChildren().add(imv2);
			resultContent.getChildren().add(ii);
			ii.setLayoutY(200);
			scrollPane.setContent(resultContent);
			System.out.println("content:\t"+scrollPane.getContent());
			System.out.println(resultContent.getChildren());
			System.out.println("scrollPane:\t"+scrollPane);
		}else{
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 500, 200);
        stage.setScene(scene);// =>  w w W .Y I I B  A I .c O  M
        
        ScrollPane s1 = new ScrollPane();
        s1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        System.out.println("scrollPane:\t"+s1);
        scrollPane = s1;
        s1.setPannable(true);
        s1.setPrefWidth(180);
        s1.setPrefHeight(200);
        test();
        root.getChildren().add(s1);
        stage.show();
        
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
