package application.initialize;

import java.util.Date;

import application.CConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class ComicSite implements Runnable{
	
	private ChoiceBox<Object> dlsite;//漫画站点选项
	private TextArea Log;//日志展示
	private CConfig cfg;//配置
	




	public ComicSite(ChoiceBox<Object> dlsite, TextArea log, CConfig cfg) {
		super();
		this.dlsite = dlsite;
		Log = log;
		this.cfg = cfg;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		dlsite.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
	          public void changed(ObservableValue ov, Number value, Number new_value) {
	        	  if(!value.equals(new_value)){
	        		  Log.appendText("\n选择站点....");
		        	  if(new_value.toString().equals("0")){
		        		  cfg.setSite("站点1");
		        		  Log.appendText("\n切换至站点1....");
		        	  }else if(new_value.toString().equals("2")){
		        		  cfg.setSite("站点2");
		        		  Log.appendText("\n切换至站点2....");
		        	  }
	        	  }
	        	  
	          }
		});
	}
	
	public void start(){
		run();
	}
	
}
