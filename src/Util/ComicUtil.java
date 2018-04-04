package Util;

import java.util.ArrayList;

import Util.ModeUtlil.imp.ManHuaGuiUtil;
import entity.Book;

public abstract class ComicUtil {
	private static final ComicUtil[] comicUtils = {new ManHuaGuiUtil()};
	private static final String SEARCH_MODE_NAME = "漫画柜";
	public static String SITE ="http://www.manhuagui.com";
	public static String IMG_REX="a class=\"bcover\" href=\"/comic/[\u4e00-\u9fa5_a-zA-Z0-9]*/\" title=\"[\u4e00-\u9fa5_a-zA-Z0-9]*\"><img src=\"http://cf.hamreus.com/cpic/b/[\u4e00-\u9fa5_a-zA-Z0-9]*.jpg\"";
	public static String TITLE_REX="title=\"[\u4e00-\u9fa5_a-zA-Z0-9]*\"";
	public static String IMGURL_REX="/comic/[\u4e00-\u9fa5_a-zA-Z0-9]*/";
	public static String IMAGE_REX="<img src=\"http://cf.hamreus.com/cpic/b/[\u4e00-\u9fa5_a-zA-Z0-9]*.jpg\"";
	
	public static ComicUtil[] getComicList(){
		return comicUtils;
	}
	public abstract  String rexImgURL(String str);
	public abstract  String rexBookURL(String str);
	public abstract  String rexTitle(String str);
	public abstract  ArrayList<Book> rexBooks(String htmlInfo);
	@Override
	public String toString(){
		return SEARCH_MODE_NAME;
	}
	public abstract void searchComicName();
	public abstract void GetChapter(Book book);
}
