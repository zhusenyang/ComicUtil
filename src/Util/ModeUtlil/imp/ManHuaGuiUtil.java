package Util.ModeUtlil.imp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JOptionPane;

import com.sun.javafx.scene.control.skin.LabeledText;

import Util.ComicUtil;
import Util.GetHtml;
import Util.htmlUtil.UrlUtil;
import application.CConfig;
import application.ValidConfig;
import entity.Book;
import entity.Chapter;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import searchcomponent.LoadImage;
import searchcomponent.SearchComponent;
import searchcomponent.SearchData;
import searchcomponent.SearchResult;

/**
 * @author zhusenyang
 * @date   2018年3月1日
 * 站点-漫画柜  的解析工具类
 */
public class ManHuaGuiUtil extends ComicUtil {
	private static final String SEARCH_MODE_NAME = "漫画柜";
	public static final String SITE ="http://www.manhuagui.com";
	public static final  String FILE_SITE="http://i.hamreus.com:8080";
	public static String IMG_REX="class=\"bcover\" href=\"/comic/[\u4e00-\u9fa5_a-zA-Z0-9]*/\" title=\"[\u0020\u4e00-\u9fa5_a-zA-Z0-9]*\"><img src=\"https://cf.hamreus.com/cpic/b/[u4e00-u9fa5_a-zA-Z0-9]*.jpg\"";
	public static String TITLE_REX="title=\"[\u0020\u4e00-\u9fa5_a-zA-Z0-9]*\"";
	public static String IMGURL_REX="/comic/[\u4e00-\u9fa5_a-zA-Z0-9]*/";
	public static String IMAGE_REX="<img src=\"https://cf.hamreus.com/cpic/b/[u4e00-u9fa5_a-zA-Z0-9]*.jpg\"";
	
	/**
	 * 抽取漫画封面链接
	 */
	
	public String rexImgURL(String str){
		System.out.println("rexImgURL"+str);
		Pattern pattern = Pattern.compile(IMAGE_REX);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			String result = (matcher.group());
			String[] results = result.split("\"");
			if(results.length>0){
				return results[1];
			}
		}
		return "error";
	}
	/**
	 * 抽取漫画链接
	 * 如  /comic/7580/
	 */
	public String rexBookURL(String str){
		System.out.println("rexBookUrl"+str);
		Pattern pattern = Pattern.compile(IMGURL_REX);
		Matcher matcher = pattern.matcher(str);
		
		if(matcher.find()){
			String result = (matcher.group());
			return  result;
		}
		return "error";
	}
	/**
	 * 抽取漫画名
	 */
	public String rexTitle(String str){
		System.out.println("rexTitle"+str);
		Pattern pattern = Pattern.compile(TITLE_REX);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			String result = (matcher.group());
			String[] results = result.split("\"");
			if(results.length>0){
				return results[1];
			}
		}
		return "error";
	}
	/**
	 * 抽取漫画信息
	 */
	public ArrayList<Book> rexBooks(String htmlInfo){
		System.out.println(htmlInfo);
		Pattern pattern = Pattern.compile(IMG_REX);
		Matcher matcher = pattern.matcher(htmlInfo);
		ArrayList<Book> books = new ArrayList<>();
		System.out.println(htmlInfo);
		while(matcher.find()){
			String str = (matcher.group());
			String title = rexTitle(str);
			String URL = rexImgURL(str);
			String bookUrl = rexBookURL(str);
			System.out.println(URL+"\t"+title+"\t"+bookUrl);
			Book book = new Book();
			book.setImageUrl(URL);
			book.setTitle(title);
			book.setBookURL(bookUrl);
			books.add(book);
		}
		return books;
	}
	
	public String toString(){
		return SEARCH_MODE_NAME;
	}
	
	
	
	/**
	 * 漫画名查找漫画
	 */
	public  void searchComicName(){
		String keyWord = CConfig.getCConfig().getSearchText().getText();
		String realKeyWord = UrlUtil.getURLEncoderString(keyWord);
		ArrayList<Book> result = null;
		String resultPage = GetHtml.GetInfo(SITE+"/s/"+realKeyWord+".html");
		byte[] bytes = resultPage.getBytes();
		try {
			String resultPageUTF8 = new String(bytes, "UTF-8");
			result = rexBooks(resultPageUTF8);
			System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			CConfig.getCConfig().getLog().appendText("\n搜索失败");
			e.printStackTrace();
		}
		CConfig.getCConfig().getLog().appendText("\n正在为你生成检索结果");
		if(result!=null&&result.size()>1){
			CConfig.getCConfig().getLog().appendText("\n搜索完成，共有\""+result.size()+"\"个结果...");
			
			Pane resultContent = SearchResult.getinstanceSearchResult().getReusltContent();
			SearchResult.getinstanceSearchResult().getResultPane().setContent(resultContent);
			ArrayList<ImageView> imvList = new ArrayList<>();
			for (int i =0 ;i<result.size(); i++) {
				ImageView imv = new ImageView();
				imvList.add(imv);
			}
			long time = 0;
			for (int i = 0;i<result.size();i++) {
				String remoteUrl = result.get(i).getImageUrl();
				
				double imgHeight = 0;
				double imgWidth = 0;
				Image img = null;
				long time1 = System.currentTimeMillis();
				//TODO:多线程来加载图片 提高使用体验
				
				if(remoteUrl!=null){
					
					System.out.println(remoteUrl);
					img = new Image(remoteUrl,false);
					img = creatBookImage(remoteUrl);
					imgHeight=img.getHeight();
					imgWidth=img.getWidth();
					imvList.get(i).setImage(img);
					//将图片放入对应的book中
					result.get(i).setBookImg(imvList.get(i));
					System.out.println(img.isError());
				}
				long time2 = System.currentTimeMillis();
				time = time2-time1;
				System.out.println(result.get(i).getTitle()+"封面图片加载耗时：\t"+time);
				imvList.get(i).setLayoutY(i*200);
				imvList.get(i).setVisible(true);
				imvList.get(i).setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						// TODO Auto-generated method stub
						ArrayList<Book> currentComics = SearchResult.getinstanceSearchResult().getCurrentComics();
						if(currentComics!=null){
							ImageView clickImg = (ImageView) event.getTarget();
							for (Book book : currentComics) {
								if(book.getBookImg().equals(clickImg)){
									/**
									 * 与当前搜索结果进行匹配,一旦匹配到则结束匹配
									 * 将选中的漫画 设为当前打开的漫画
									 * 调用打开漫画的方法
									 */
									System.out.println(book);
									SearchResult.getinstanceSearchResult().setCurrentBook(book);
									ComicUtil comicUtil = CConfig.getCConfig().getComicUtil();
									comicUtil.GetChapter(book);
									break;
								}
							}
						}
					}
				});
				
				Label comicTitle = new Label(result.get(i).getTitle());
				comicTitle.setOnMouseClicked(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						// TODO Auto-generated method stub
						ArrayList<Book> currentComics = SearchResult.getinstanceSearchResult().getCurrentComics();
						if(currentComics!=null){
							LabeledText comicTitle = (LabeledText) event.getTarget();
							for (Book book : currentComics) {
								if(comicTitle.getText().equals(book.getTitle())){
									SearchResult.getinstanceSearchResult().setCurrentBook(book);
									ComicUtil comicUtil = CConfig.getCConfig().getComicUtil();
									comicUtil.GetChapter(book);
									break;
								}
							}
						}
					}
				});
				comicTitle.setLayoutX(imgWidth +10);
				comicTitle.setLayoutY(imvList.get(i).getLayoutY()+imgHeight/2);
				comicTitle.setFont(new Font(20));
				//记录搜索结果
				SearchResult.getinstanceSearchResult().setCurrentComics(result);//当前搜索结果
				SearchResult.getinstanceSearchResult().setLastComics(result);//覆盖上一次搜索结果
				resultContent.getChildren().add(imvList.get(i));
				resultContent.getChildren().add(comicTitle);
			}
			SearchResult.getinstanceSearchResult().getResultPane().setContent(resultContent);
			System.out.println(result);
		}else{
			CConfig.getCConfig().getLog().appendText("\n搜索完成，没有符合的结果...");
		}
	}
	
	
	@Override
	public void GetChapter(Book book) {
		// TODO Auto-generated method stub
		CConfig.getCConfig().getLog().appendText("\n正在为您打开漫画："+book.getTitle());
		String urlAdd = SITE+book.getBookURL()+"?"+ (new Date()).getTime();
		System.out.println(urlAdd);
		String HtmlInfo = GetHtml.GetInfo(urlAdd);
		System.out.println(HtmlInfo);
		String Title = null;
		String Html = null;
		if(null == HtmlInfo) return;
		String BookNameRex = "name: '(.+?)'";
		Pattern pattern = Pattern.compile(BookNameRex);
		Matcher matcher = pattern.matcher(HtmlInfo);
		//漫画柜在一部分网站上对于章节信息也采用了base64压缩，需要先进行解压缩
				if(HtmlInfo.indexOf("__VIEWSTATE") != -1)
				{
					String Rex = "VIEWSTATE\" value=\"(.+?)\"";
					String keyword = null;
					String tmpHtmlInfo = null;
					pattern = Pattern.compile(Rex);
					matcher = pattern.matcher(HtmlInfo);
					while(matcher.find())
					{
						
						keyword = matcher.group(1);
						System.out.println(keyword);
					}
					
					ScriptEngineManager enginemanager = new ScriptEngineManager();
					ScriptEngine engine = enginemanager.getEngineByName("js");
					try {
						engine.eval(ValidConfig.JSFile);
						Invocable inv = (Invocable)engine;
						tmpHtmlInfo = (String)inv.invokeFunction("getChapter", keyword);			
						System.out.println(tmpHtmlInfo);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(tmpHtmlInfo.length() > 0)
					{
						HtmlInfo = tmpHtmlInfo;
					}
				}
				
				Pane resultContent = SearchResult.getinstanceSearchResult().getReusltContent();	
				resultContent.getChildren().clear();//清理搜索结果面板
				//使用正则表达式进行匹配
				String Rex = "<li><a href=\"" + book.getBookURL() + "(.+?)\" title=\"(.+?)\"";
				System.out.println(Rex);
				resultContent.getChildren().add(SearchData.ALL_CHECK);
				pattern = Pattern.compile(Rex);
				matcher = pattern.matcher(HtmlInfo);
				while(matcher.find()){
					Title = matcher.group(2);
					Html = SITE + book.getBookURL()  + matcher.group(1);
					System.out.println("Title:\t"+Title);
					System.out.println("Html:\t"+Html);
					Chapter chapter = new Chapter(Title, Html);
					CheckBox checkBox = chapter.getCheckBox();
					resultContent.getChildren().add(checkBox);
					checkBox.setLayoutY(resultContent.getChildren().size()*SearchData.CHECK_BOX_HEIGHT);
					checkBox.setSelected(true);//默认全选
					book.getChapters().add(chapter);
				}
				CConfig.getCConfig().getLog().appendText("\n漫画："+book.getTitle()+",共"+book.getChapters().size()+"章节");
	}
	public void AnlyChapterUrl(String HtmlInfo,Book book){
		
	}
	private Image creatBookImage(String remoteUrl){
		URL url = null;
		try {
			url = new URL(remoteUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection urlcon = null;
		try {
			urlcon = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urlcon.setRequestProperty("accept", "*/*");
		urlcon.setRequestProperty("connection", "Keep-Alive");
		urlcon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
		urlcon.setRequestProperty(":scheme", "https");
		urlcon.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		urlcon.setRequestProperty("cookie", "cfduid=df01f76e3e637ecd321f73376015bcd4b1520350784; GUID=2bd751cf-3c30-449b-9083-0dbe07fea696; vct=1");
		urlcon.setConnectTimeout(3 * 1000);
		urlcon.setReadTimeout(3 * 1000);
		try {
			urlcon.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Image img = null;
		try {
			img = new Image(urlcon.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	
}
