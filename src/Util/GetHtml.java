package Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JOptionPane;


import org.junit.Test;

import Util.ModeUtlil.imp.ManHuaGuiUtil;
import Util.htmlUtil.UrlUtil;
import entity.Chapter;
import javafx.scene.image.Image;
import searchcomponent.SearchResult;
import test.ValidConfig;

public class GetHtml {
	
	public static void main(String[] args) {
		
//		String imageInfo = GetInfo("http://www.manhuagui.com/comic/20224/233848.html");
//		System.out.println(imageInfo);
		
	}
	@Test
	public void test(){
		
		String keyWord ="一拳超人";
		SearchResult.getinstanceSearchResult().getReusltContent();
		String realKeyWord = UrlUtil.getURLEncoderString(keyWord);
		
		ManHuaGuiUtil comicUtil = new ManHuaGuiUtil();
		byte[] bytes = searchNameModel(realKeyWord).getBytes();
		try {
			String resultPageUTF8 = new String(bytes, "UTF-8");
			comicUtil.rexBooks(resultPageUTF8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String searchNameModel(String keyWord){
		String html = "http://www.manhuagui.com/s/"+keyWord+".html";
		System.out.println(html);
		String resultPage = GetHtml.GetInfo(html);
		return resultPage;
	}
	
	public static String GetInfo(String UrlAdd)
	{
		String HttpInfo = null;
		if(null == UrlAdd) return null;
		String line;
		URL url;
		try {
			url = new URL(UrlAdd);
			URLConnection urlcon = url.openConnection();
			urlcon.setRequestProperty("accept", "*/*");
			urlcon.setRequestProperty("connection", "Keep-Alive");
			urlcon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
			urlcon.setConnectTimeout(3 * 1000);
			urlcon.setReadTimeout(3 * 1000);
			urlcon.connect();
			BufferedReader urlRead = new BufferedReader(new InputStreamReader(urlcon.getInputStream(), "utf-8"));
			while((line = urlRead.readLine()) != null)
			{
				HttpInfo += line;
			}
			urlRead.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return HttpInfo;
	}
	
	private ArrayList<Chapter> Chapter = new ArrayList<Chapter>();
	private String UrlAdd = null;
	private String ComicNum = null;
	private String BookName = null;
	//分析网页内容，提取章节标题和URL以及书名
		private  void AnalyHtml(String HtmlInfo)
		{
			String Title = null;
			String Html = null;
			
			if(null == HtmlInfo) return;
			
			String BookNameRex = "name: '(.+?)'";
			Pattern pattern = Pattern.compile(BookNameRex);
			Matcher matcher = pattern.matcher(HtmlInfo);
			while(matcher.find())
			{
				BookName = matcher.group(1);
				System.out.println("boobname");
				System.out.println(BookName);
			}
			
			//漫画柜在一部分网站上对于章节信息也采用了base64压缩，需要先进行解压缩
			if(HtmlInfo.indexOf("__VIEWSTATE") != -1)
			{
				System.out.println("base64解压");
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
//					LOG.log(e.getMessage(), LOG.NormalType);
					e.printStackTrace();
				}
				
				if(tmpHtmlInfo.length() > 0)
				{
					JOptionPane.showMessageDialog(null, "当前漫画为网站标定为版权受限的漫画，请不要下载过多，可能被封IP,详细见下面的说明", "提示", JOptionPane.CLOSED_OPTION);
					HtmlInfo = tmpHtmlInfo;
				}
			}
			
			//使用正则表达式进行匹配
			String Rex = "<li><a href=\"/comic/" + ComicNum + "/(.+?)\" title=\"(.+?)\"";
			pattern = Pattern.compile(Rex);
			matcher = pattern.matcher(HtmlInfo);
			while(matcher.find())
			{
				Title = matcher.group(2);
				Html = "http://www.manhuagui.com/comic/" + ComicNum + "/" + matcher.group(1);
				System.out.println(Html);
				Chapter.add(new Chapter(Title, Html));
			}
			
			return;
		}
		
		
	
}
