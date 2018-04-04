package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Util.ComicUtil;

public class CConfig {
	private String filepath;
	private String threadNum;
	private String site;
	private File config;
	private ComicUtil comicUtil;
	private TextField searchText;
	private TextArea log;
	public void setComicUtil(ComicUtil comicUtil){
		this.comicUtil=comicUtil;
	}
	public ComicUtil getComicUtil(){
		return comicUtil;
	}
	
	public TextArea getLog(){
		return log;
	}
	public void setLog(TextArea log){
		this.log=log;
	}
	private static CConfig cconfig = new CConfig();
	
	public void setSearchText(TextField searchText){
		this.searchText=searchText;
	}
	public TextField getSearchText(){
		return searchText;
	}
	public void initCConfig(){
		filepath="download";
		threadNum ="16";
		site="漫画柜";
	}
	private CConfig(){
		
	}
	public static CConfig getCConfig(){
		return cconfig;
	}
	
	
	public void parseConfig(File config) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(config));
		this.config=config;
		while(br.ready()) {
			String strcfg = br.readLine();
			if(strcfg==null){
				continue;
			}
			if (strcfg.contains("FilePath")) {
				String[] strs = strcfg.split("=");
				filepath=strs[1];
				File newfile = new File(filepath);
				if(!newfile.exists()||!newfile.isDirectory()){
					newfile = new File("/download");
					filepath=newfile.getAbsolutePath();
				}
			}
			if (strcfg.contains("threadNum")) {
				String[] strs = strcfg.split("=");
				threadNum = strs[1];
			}
			if (strcfg.contains("site")) {
				String[] strs = strcfg.split("=");
				site = strs[1];
				boolean flag = false;
				int siteNum = 0;
				for (ComicUtil comicUtil : comicUtil.getComicList()) {
					if(site.equals(comicUtil.toString())){
						flag=true;
						comicUtil = ComicUtil.getComicList()[siteNum];
					}
					siteNum++;
				}
				if(flag==false){
					site="漫画柜";
					comicUtil = ComicUtil.getComicList()[0];
				}
			}
			
		}
		br.close();
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
		try {
			BufferedReader br = new BufferedReader(new FileReader(config));
			
			ArrayList<String> properties = new ArrayList<>();
			while(br.ready()){
				
				StringBuilder str = new StringBuilder(br.readLine());
				properties.add(str.toString());
				if(properties.get(properties.size()-1).contains("FilePath")){
					str.delete(str.lastIndexOf("=")+1, str.length());
					str.append(filepath);
					properties.remove(properties.size()-1);
					properties.add(str.toString());
				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(config));
			for (String string : properties) {
				bw.write(string);
				bw.newLine();
			}
			
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setThreadNum(String threadNum) {
		this.threadNum = threadNum;
		try {
			BufferedReader br = new BufferedReader(new FileReader(config));
			
			ArrayList<String> properties = new ArrayList<>();
			while(br.ready()){
				
				StringBuilder str = new StringBuilder(br.readLine());
				properties.add(str.toString());
				if(properties.get(properties.size()-1).contains("threadNum")){
					str.delete(str.lastIndexOf("=")+1, str.length());
					str.append(threadNum);
					properties.remove(properties.size()-1);
					properties.add(str.toString());
				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(config));
			for (String string : properties) {
				bw.write(string);
				bw.newLine();
			}
			
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSite(String site) {
		this.site = site;
		try {
			BufferedReader br = new BufferedReader(new FileReader(config));
			
			ArrayList<String> properties = new ArrayList<>();
			while(br.ready()){
				
				StringBuilder str = new StringBuilder(br.readLine());
				properties.add(str.toString());
				if(properties.get(properties.size()-1).contains("site")){
					str.delete(str.lastIndexOf("=")+1, str.length());
					str.append(site);
					properties.remove(properties.size()-1);
					properties.add(str.toString());
				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(config));
			for (String string : properties) {
				bw.write(string);
				bw.newLine();
			}
			
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFilepath() {
		return filepath;
	}
	
	public String getThreadNum() {
		return threadNum;
	}

	public String getSite() {
		return site;
	}

}
