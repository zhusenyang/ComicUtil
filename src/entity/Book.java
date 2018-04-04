package entity;

import java.util.ArrayList;


import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

/**
 * @author yang  
 * @NGAID  vengeance_y
 * @date   2018年4月3日
 * 书本的实体类
 */
public class Book {
	private String title;
	private ArrayList<Chapter> chapters = new ArrayList<>();
	private ArrayList<CheckBox> chapterChoice = new ArrayList<>();
	private String ImageUrl;
	private ImageView bookImg;
	private String bookURL;
	public void setBookImg(ImageView bookImg){
		this.bookImg = bookImg;
	}
	public ImageView getBookImg(){
		return bookImg;
	}
	public void setBookURL(String bookURL){
		this.bookURL=bookURL;
	}
	public String getBookURL(){
		return bookURL;
	}
	
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	
	public void setTitle(String titile) {
		this.title = titile;
	}

	public String getTitle() {
		return title;
	}
	public ArrayList<Chapter> getChapters() {
		return chapters;
	}
	public ArrayList<CheckBox> getChapterChoice() {
		return chapterChoice;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	@Override
	public String toString() {
		return "Book [title=" + title + "\r, chapters=" + chapters + "\r, chapterChoice=" + chapterChoice + "\r, ImageUrl="
				+ ImageUrl + "\r, bookImg=" + bookImg + "\r, bookURL=" + bookURL + "]";
	}
}
