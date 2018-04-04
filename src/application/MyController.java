package application;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


import Alert.AlertDialog;
import Util.ComicUtil;
import Util.ModeUtlil.DownLoadSiteEnum;
import Util.ModeUtlil.imp.ManHuaGuiDL;
import application.initialize.ComicSite;
import application.initialize.ConfigInit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import searchcomponent.SearchComponent;
import searchcomponent.SearchResult;

public class MyController implements Initializable {
	
	private StringBuilder logStr = new StringBuilder("");//日志
	
	@FXML
	private ChoiceBox<Object> dlmodel;//下载模式选项
	
	@FXML
	private ChoiceBox<Object> dlsite;//漫画站点选项
	
	@FXML
	private Button download;//下载按钮
	
	@FXML
	private Button toSearch;//检索按钮
	
	@FXML
	private ScrollPane resultPane;//搜索结果模块
	
	@FXML
	private AnchorPane myAnchorPane;
	
	@FXML
	private TextField searchText;//搜索框
	
	@FXML
	private TextArea Log;//日志展示
	
	@FXML
	private Button filepath;//路径选择
	
	@FXML
	private Label dlpath;//下载路径
	private Stage primaryStage = new Stage();
	private String defualtPath;//默认路径
	private CConfig cfg;//配置
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {//初始化
		//TODO 将部分初始化动作投放在其他线程中，提高程序界面展开速度
		ConfigInit configInit = new ConfigInit(logStr, dlmodel, dlsite, download, toSearch, resultPane, myAnchorPane, searchText, Log, filepath, dlpath, primaryStage, defualtPath, cfg);
		configInit.start();
		cfg = CConfig.getCConfig();
		ComicSite comicsite = new ComicSite(dlsite, Log, cfg);
		comicsite.start();
		
		SearchResult.getinstanceSearchResult().setResultPane(resultPane);//初始化搜索结果面板
		Log.appendText("\n初始化下载路径....");
		logStr.append("\n初始化下载路径"+new Date());
		File dlpathfile = new File(cfg.getFilepath());
		dlpath.setText(dlpathfile.getAbsolutePath());
		defualtPath=dlpathfile.getAbsolutePath();
		Log.appendText("\n初始化完成..");
		logStr.append("\n初始化完成.."+new Date());
		
	}

	// When user click on myButton
	// this method will be called.
	public void filepathchoose(ActionEvent event){//路径选择
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(defualtPath));
		File path = directoryChooser.showDialog(primaryStage);
		if(path!=null){
			dlpath.setText(path.getAbsolutePath());
			try {
				cfg.setFilepath(path.getCanonicalPath());
				defualtPath=path.getCanonicalPath();
			} catch (IOException e) {
				Log.appendText("\n设置下载路径失败....");
				logStr.append("\n设置下载路径失败"+new Date());
				e.printStackTrace();
			}
			Log.appendText("\n设置下载路径成功....");
			logStr.append("\n设置下载路径成功"+new Date());
			cfg.setFilepath(defualtPath);
		}
	}
	
	
	
	public void startDowdLoad(){
		ManHuaGuiDL mhGuiDL = new ManHuaGuiDL();
		mhGuiDL.startDownLoad();
	}
	
	public void toSearch(){
		ComicUtil comicUtil =  CConfig.getCConfig().getComicUtil();
		comicUtil.searchComicName();
		Log.appendText("\n点击图标/标题即可进入漫画");
	}
	
	
	public void search(){
		if(searchText.getText().length()==0||searchText.getText().equals("请输入漫画名")||searchText.getText()==null){
			AlertDialog searchWrong = new AlertDialog();
			try {
				searchWrong.init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			toSearch();
		}
		
	}
	
	public void enterSearch(ActionEvent event){
		
		System.out.println("enterSearch");
		search();
	}
	
	public void download(ActionEvent event){
		System.out.println("下载");
	}
	
}
