package Util.ModeUtlil.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import Util.DownLoadUtil;
import Util.download.DownLoadThread;
import application.CConfig;
import entity.Book;
import entity.Chapter;
import javafx.scene.control.CheckBox;
import searchcomponent.SearchResult;

public class ManHuaGuiDL extends DownLoadUtil {
	private static int ThreadNum = 16;
	private ExecutorService fixpool = Executors.newFixedThreadPool(32);;//线程池
	private String BookName = null;
	private String FilePath = null;
	
	//获取网页的章节
	public boolean AnalyChapter()
	{
		ArrayList<Chapter> chapters = SearchResult.getinstanceSearchResult().getCurrentBook().getChapters();
		BookName = SearchResult.getinstanceSearchResult().getCurrentBook().getTitle();
		if(chapters.isEmpty())
		{
			return false;
		}
		return true;
	}
	public void InterrputDL()
	{
		if(null != fixpool)
		{
			fixpool.shutdownNow();
		}
	}
	public void startDownLoad(){
		Book book = SearchResult.getinstanceSearchResult().getCurrentBook();
		ArrayList<Chapter> chapters = book.getChapters();
		if(chapters.isEmpty()){
			CConfig.getCConfig().getLog().appendText("\n该漫画没有任何内容..");
		}
		ArrayList<CheckBox> checkChapter = new ArrayList<>();
		ArrayList<Chapter> selectChapter = new ArrayList<>();
		for (Chapter chapter : chapters) {//遍历章节
			if(chapter.getCheckBox().isSelected()){//章节对应的复选框是否被选中
				checkChapter.add(chapter.getCheckBox());//加入到被选中章节的集合当中
				selectChapter.add(chapter);
			}
		}
		if(checkChapter.isEmpty()){//如果被选中章节为空  没有选中任何章节则程序执行结束
			CConfig.getCConfig().getLog().appendText("\n没有选中任何章节");
			return;
		}
		File HeadDir = new File(CConfig.getCConfig().getFilepath()+"/"+book.getTitle());
		if(!(HeadDir.exists() && HeadDir.isDirectory())){
			if(!HeadDir.mkdirs()){
				CConfig.getCConfig().getLog().appendText("\n创建初始文件夹失败，请检查磁盘是否已满/地址错误:"+HeadDir.getAbsolutePath());
			}
		}
		for (Chapter chapter : selectChapter) {
			DownLoadThread dl = new DownLoadThread(chapter,HeadDir.getAbsolutePath());
			fixpool.execute(dl);
		}
	}
}
