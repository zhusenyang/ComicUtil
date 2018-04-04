package Util;

import javafx.scene.image.Image;

public class CreateRemoteImage implements Runnable {
	public String remoteUrl;
	public String title;
	public Image remoteImage;
	public Image getImage(){
		return remoteImage;
	}
	@Override
	synchronized public void  run(){
		System.out.println("正在生成封面");
		remoteImage = new Image(remoteUrl, true);
	}
	public void setRemoteUrl(String remoteUrl){
		this.remoteUrl=remoteUrl;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return title;
	}
}
