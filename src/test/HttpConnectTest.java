package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import javafx.scene.image.Image;

/**
 * @author zhusenyang
 * @date   2018年3月7日
 * 带cookie的 urlconnection操作
 */
public class HttpConnectTest {
	
	@Test
	public void testConnect(){
		URL url = null;
		try {
			url = new URL("http://www.manhuagui.com/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setRequestProperty("Referer", "http://www.manhuagui.com/");
			huc.setConnectTimeout(5 * 1000);
			huc.setReadTimeout(8 * 1000);
			CookieManager manager = new CookieManager();
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			// 保存这个定制的 CookieManager  
	        CookieHandler.setDefault(manager);  
	        String key = null;
	        for (int i = 1; (key = huc.getHeaderFieldKey(i)) != null; i++){
	      	  System.out.print(key+":");
	      	  System.out.println(huc.getHeaderField(key)); 
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void ttest(){
		String HttpInfo = null;
		String line;
		URL url;
		try {
			url = new URL("https://cf.hamreus.com/cpic/b/7580.jpg");
			URLConnection urlcon = url.openConnection();
			urlcon.setRequestProperty("accept", "*/*");
			urlcon.setRequestProperty("connection", "Keep-Alive");
			urlcon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
			urlcon.setRequestProperty(":scheme", "https");
			urlcon.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			urlcon.setRequestProperty("cookie", "cfduid=df01f76e3e637ecd321f73376015bcd4b1520350784; GUID=2bd751cf-3c30-449b-9083-0dbe07fea696; vct=1");
			urlcon.setConnectTimeout(3 * 1000);
			urlcon.setReadTimeout(3 * 1000);
			urlcon.connect();
			Image img = new Image(urlcon.getInputStream());
			System.out.println(img.isError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void tttest(){
		try {
			//http://i.hamreus.com/ps3/y/yiquanchaoren/00/000001.jpg.webp?cid=68731&md5=NQdewXeXQ-qHQ4XvAYZhBg
			URL url = new URL("http://i.hamreus.com/ps3/y/yiquanchaoren/00/000001.jpg.webp?cid=68731&md5=NQdewXeXQ-qHQ4XvAYZhBg");
			URLConnection urlcon = url.openConnection();
			urlcon.setRequestProperty("accept", "*/*");
			urlcon.setRequestProperty("connection", "Keep-Alive");
			urlcon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
			urlcon.setRequestProperty(":scheme", "https");
			urlcon.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			urlcon.setRequestProperty("cookie", "cfduid=df01f76e3e637ecd321f73376015bcd4b1520350784;"
					+ " GUID=2bd751cf-3c30-449b-9083-0dbe07fea696;"
					+ " vct=1;_gid=GA1.2.49538942.1521007085;"
					+ "_gat=1;"
					+ "_ga=GA1.2.304049923.1516935892;"
					+ "Hm_lpvt_38a1bab61660f620209480de377747ed=1521007085;"
					+ "Hm_lvt_38a1bab61660f620209480de377747ed=1519824362,1520348136,1521007085;");
			urlcon.setConnectTimeout(3 * 1000);
			urlcon.setReadTimeout(3 * 1000);
			urlcon.connect();
			Image img = new Image(urlcon.getInputStream());
			System.out.println(img.isError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
