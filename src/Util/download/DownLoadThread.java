package Util.download;

import java.io.File;
import java.util.ArrayList;

import Util.SaveImg;
import Util.ModeUtlil.imp.ManHuaGuiGetPic;
import application.CConfig;
import application.ValidConfig;
import entity.Chapter;

public class DownLoadThread implements Runnable {
	private String html = null;
	private Chapter chapter = null;
	private String path = null;
	private String title = null;
	private int threadNum = 32;
	private int len = 0;	
	private SaveImg currimg = null;

	public DownLoadThread(Chapter chapter,String path){
		this.chapter=chapter;
		title=chapter.getTitle();
		html=chapter.getHtml();
		this.path = path;
		processTitle();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		File dir = new File(path + "/" + title);
		System.out.println("开始下载:\t"+dir.getAbsolutePath());
		if(!(dir.exists() && dir.isDirectory())){
			if(!dir.mkdir()){
				CConfig.getCConfig().getLog().appendText("\n创建章节文件夹失败，请检查磁盘是否已满/地址错误:"+ dir.getAbsolutePath());
				return;
			}
		}
		ArrayList<String> PicPath = new ManHuaGuiGetPic(html).getPicturePath();
		if(PicPath.isEmpty()){
			CConfig.getCConfig().getLog().appendText("\t解析图片地址失败:" + html);
			return;
		}
		len = PicPath.size();
		int numlen = getNumLen(len);
		int index = 1;
		int result = 0;
		for(String path : PicPath){				
			currimg = new SaveImg(path, dir.getAbsolutePath() + "/", String.format("%0" + numlen + "d", index) + ".jpg");
			result = currimg.SavePicture();
			if(0 == result){
				CConfig.getCConfig().getLog().appendText("\n下载图片地址失败:" + path);
				return;
			}
			
			index ++;
		}
	}
	//一些带奇怪符号的名称可能会导致创建文件夹失败，所以需要进行预处理
	private void processTitle(){
		title =	chapter.getTitle().replaceAll("[\\^%&',.;=?$]+", "");
	}
	private int getNumLen(int num)
	{
		int len = 0;
		
		do
		{
			num = num /10;
			len ++;
		}
		while(num != 0);
		
		return len;
	}
}
