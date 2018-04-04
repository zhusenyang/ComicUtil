package application.initialize;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import Util.ComicUtil;
import Util.ModeUtlil.DownLoadSiteEnum;
import application.CConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import searchcomponent.SearchResult;

/**
 * @author yang  
 * @NGAID  vengeance_y
 * @date   2018年4月3日
 * 读取配置文件进行初始化
 */
public class ConfigInit implements Runnable{
	private StringBuilder logStr;
	private ChoiceBox<Object> dlmodel;
	private ChoiceBox<Object> dlsite;
	private Button download;//下载按钮
	private Button toSearch;//检索按钮
	private ScrollPane resultPane;//搜索结果模块
	private AnchorPane myAnchorPane;
	private TextField searchText;//搜索框
	private TextArea Log;//日志展示
	private Button filepath;//路径选择
	private Label dlpath;//下载路径
	private Stage primaryStage ;
	private String defualtPath;//默认路径
	private CConfig cfg;//配置
	
	
	
	public ConfigInit(StringBuilder logStr, ChoiceBox<Object> dlmodel, ChoiceBox<Object> dlsite, Button download,
			Button toSearch, ScrollPane resultPane, AnchorPane myAnchorPane, TextField searchText, TextArea log,
			Button filepath, Label dlpath, Stage primaryStage, String defualtPath, CConfig cfg) {
		super();
		this.logStr = logStr;
		this.dlmodel = dlmodel;
		this.dlsite = dlsite;
		this.download = download;
		this.toSearch = toSearch;
		this.resultPane = resultPane;
		this.myAnchorPane = myAnchorPane;
		this.searchText = searchText;
		Log = log;
		this.filepath = filepath;
		this.dlpath = dlpath;
		this.primaryStage = primaryStage;
		this.defualtPath = defualtPath;
		this.cfg = cfg;
	}


	public void start(){
		run();
	}
	
	@Override
	public void run() {
		File config = new File("src/config.cfg");
		cfg = CConfig.getCConfig();
		cfg.setLog(Log);
		resultPane.setPannable(true);
		Log.setText("初始化开始....");
		Log.appendText("\n初始化搜索框....");
		logStr.append("\n初始化搜索框"+new Date());
		cfg.setSearchText(searchText);
		logStr.append("初始化开始"+new Date());
		Log.appendText("\n读取配置文件....");
		logStr.append("\n读取配置文件"+new Date());
		Pane resultContent = new Pane();
		resultPane.setContent(resultContent);
		SearchResult.getinstanceSearchResult().setReusltContent(resultContent);
		if(!config.exists()){
			try {
				config.createNewFile();
				cfg.initCConfig();
				Log.appendText("\n创建配置文件....");
				logStr.append("\n创建配置文件"+new Date());
			} catch (IOException e) {
				e.printStackTrace();
				Log.appendText("\n创建配置文件异常....");
				logStr.append("\n创建读取配置异常"+new Date());
			}
		}
		else{
			try {
				cfg.parseConfig(config);
				Log.appendText("\n解析配置文件....");
				logStr.append("\n解析配置文件"+new Date());
			} catch (IOException e) {
				Log.appendText("\n解析配置文件失败....");
				logStr.append("\n解析配置文件失败"+new Date());
				e.printStackTrace();
			}
		}
		Log.appendText("\n初始化下载线程....");
		logStr.append("\n初始化下载线程"+new Date());
		dlmodel.setItems(FXCollections.observableArrayList("16线程",new Separator(),"8线程"));
		
		if(cfg.getThreadNum().contains("16")){
			dlmodel.getSelectionModel().select(0);
		}else{
			dlmodel.getSelectionModel().select(2);
		}
		dlmodel.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
	          public void changed(ObservableValue ov, Number value, Number new_value) {
	        	  if(!value.equals(new_value)){
	        		  Log.appendText("\n选择线程....");
		        	  logStr.append("\n选择线程"+new Date());
		        	  if(new_value.toString().equals("0")){
		        		  cfg.setThreadNum("16");
		        		  Log.appendText("\n切换至16线程....");
			        	  logStr.append("\n切换至16线程"+new Date());
		        	  }else if(new_value.toString().equals("2")){
		        		  cfg.setThreadNum("8");
		        		  Log.appendText("\n切换至8线程....");
			        	  logStr.append("\n切换至8线程"+new Date());
		        	  }
	        	  }
	          }
		});
		Log.appendText("\n初始化下载站点....");
		logStr.append("\n初始化下载站点"+new Date());
		dlsite.setItems(FXCollections.observableArrayList(new Separator(),DownLoadSiteEnum.漫画柜,DownLoadSiteEnum.站点2));
		boolean siteFlag = false;
		int siteNum = 1;
		for (ComicUtil site : ComicUtil.getComicList()) {
			if(cfg.getSite().equals(site.toString())){
				cfg.setComicUtil(site);
				siteFlag = true;
				dlsite.getSelectionModel().select(siteNum);
			}
			siteNum++;
		}
		if(!siteFlag){
			dlsite.getSelectionModel().select(1);
		}
	}
	
}
