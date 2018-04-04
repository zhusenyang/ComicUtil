package Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import application.CConfig;
import application.ValidConfig;

//该类传入一个图片的地址，用于保存该图片
public class SaveImg {
	private String UrlAdd = null;
	private String Path = null;
	private String FileName = null;
	private ArrayList<String> PicturePath = new ArrayList<String>();
	
	public SaveImg(String UrlAdd, String Path, String FileName)
	{
		this.UrlAdd = UrlAdd;
		this.Path = Path;
		this.FileName = FileName;
	}
	
	public SaveImg(String UrlAdd){
		GetJsInfo(GetHtml.GetInfo(UrlAdd));
	}
	
	//将图片的二进制流写入文件，返回是否保存成功
	public int SavePicture()
	{
		if(null == Path || null == FileName) return 0;
		//提前判断文件是否存在，如果存在直接跳过下载，加快续传速度
		File file = new File(Path + FileName);
		if(file.exists())
		{
			return 1;
		}
		
		byte[] imgData = getImgData();
		if(null == imgData) return 0;
		
		FileOutputStream fop = null;
		
		try {
			try {
				fop = new FileOutputStream(file);
				fop.write(imgData);
				fop.flush();
			} catch (Exception e) {
				e.printStackTrace();
				CConfig.getCConfig().getLog().appendText("\n"+e.getMessage());
				return 0;
			}
			finally
			{
				fop.close();
			}
		}
		catch(Exception e)
		{
			CConfig.getCConfig().getLog().appendText("\n"+e.getMessage());
			e.printStackTrace();
		}
		
		return 1;
	}
	//获取图片的二进制流
	private byte[] getImgData()
	{
		byte[] ImgData = null;
		//如果连接失败，有3次重连机会
		int ConnectTime = 3;
		if(null == UrlAdd) return null;
		
		while(ConnectTime > 0)
		{
			try {
				URL url = new URL(UrlAdd);
				System.out.println(UrlAdd);
				HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
				urlcon.setRequestProperty("Referer", "http://www.manhuagui.com/");
				urlcon.setRequestMethod("GET");
				urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
				urlcon.setConnectTimeout(5 * 1000);
				urlcon.setReadTimeout(8 * 1000);
				ImgData = GetbyteFromStream(urlcon.getInputStream());
				return ImgData;
			} catch (Exception e) {
				e.printStackTrace();
				CConfig.getCConfig().getLog().appendText("\n"+e.getMessage());
				ConnectTime --;
				continue;
			}
		}
		
		return null;
	}
	
	private void GetJsInfo(String HtmlInfo){
		if(null == HtmlInfo) return;
		String Para1 = null,Para2 = null,Para3 = null,Para4 = null,Para5 = null;
		String Base64StringMatch = "\\(function\\(p,a,c,k,e,d\\)\\{(.+?)\\}\\('(.+?)',(.+?),(.+?),'(.+?)'(.+?),(.+?),";
		Pattern pattern = Pattern.compile(Base64StringMatch);
		Matcher match = pattern.matcher(HtmlInfo);
		while(match.find())
		{
			Para1 = match.group(2);
			Para2 = match.group(3);
			Para3 = match.group(4);
			Para4 = match.group(5);
			Para5 = match.group(7);
		}
		if(null == Para1)
		{
			return;
		}
		paraseInfo(Para1,Integer.parseInt(Para2), Integer.parseInt(Para3), Para4,Integer.parseInt(Para5));
		return;
	}
	
	//此处直接调用修改自源HTML的脚本来进行解码,另外，此处采用eval(String)而不用eval(FileReader)是因为打包成JAR后无法使用后者
		private void paraseInfo(String para1, int para2, int para3, String para4, int para5)
		{
			ScriptEngineManager engineManager = new ScriptEngineManager();
			ScriptEngine engine = engineManager.getEngineByName("js");
			try {
				engine.eval(ValidConfig.JSFile);
				Invocable inv = (Invocable)engine;
				String ParkerInfo = (String)inv.invokeFunction("parase", para1,para2,para3,para4,para5);
				getPath(ParkerInfo);
			} catch (Exception e) {
				CConfig.getCConfig().getLog().appendText("\n网站解析出错");
				e.printStackTrace();
			}	
		}
		
		//根据解码结果获取到图片的URL地址
		private void getPath(String ParkerInfo)
		{
			String path = null;
			String[] files = null;
			String cid = null;
			String md5 = null;
			String MatchString = "cid\":(.+?),\"cname(.+?)files\":\\[(.+?)\\],(.+?)path\":\"(.+?)\"(.+?)md5\":\"(.+?)\"";
			Pattern pattern = Pattern.compile(MatchString);
			Matcher match = pattern.matcher(ParkerInfo);
			
			while(match.find())
			{
				cid = match.group(1);
				path = match.group(5);
				files = match.group(3).replaceAll("\"","").replaceAll(".webp", "").split(",");
				md5 = match.group(7);
			}
			for(String file:files)
			{
				PicturePath.add("http://i.hamreus.com" + path + file + "?cid="+ cid + "&md5=" + md5);
			}
		}
	
	//将图片的Http流转化为二进制流
	private byte[] GetbyteFromStream(InputStream in) throws IOException
	{	
		
		int len;
		byte[] buffer = new byte[3 * 100 * 1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while((len = in.read(buffer)) != -1)
		{
			out.write(buffer, 0 ,len);
		}
	
		byte[] imgData = out.toByteArray();
		in.close();
		out.close();
		
		return imgData;
	}
}
