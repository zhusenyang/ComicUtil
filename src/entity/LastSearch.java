package entity;

import java.util.ArrayList;

import Util.ComicUtil;



/**
 * @author yang  
 * @NGAID  vengeance_y
 * @date   2018年4月3日
 */
public class LastSearch {
	public String KeyWord;
	public ArrayList<Book> LastBooks;
	public ComicUtil ComicUtil;
	public LastSearch(String keyWord, ArrayList<Book> lastBook, Util.ComicUtil comicUtil) {
		super();
		KeyWord = keyWord;
		LastBooks = lastBook;
		ComicUtil = comicUtil;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}
	public ArrayList<Book> getLastBooks() {
		return LastBooks;
	}
	public void setLastBooks(ArrayList<Book> lastBooks) {
		LastBooks = lastBooks;
	}
	public ComicUtil getComicUtil() {
		return ComicUtil;
	}
	public void setComicUtil(ComicUtil comicUtil) {
		ComicUtil = comicUtil;
	}
	
}
