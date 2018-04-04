package searchcomponent;

import java.util.ArrayList;

import entity.Book;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class SearchResult {
	private ArrayList<CheckBox> checks = new ArrayList<>();
	private ScrollPane resultPane;
	private CheckBox searchCheck;
	private Pane reusltContent;
	/**
	 * 当前搜索结果
	 */
	private ArrayList<Book> currentcomics;
	/**
	 * 当前搜索结果可能为空，为使部分功能可用 当前搜索结果为空时可调用上次的搜索结果
	 */
	private ArrayList<Book> lastComics;
	
	private Book currentBook;
	
	
	public Book getCurrentBook() {
		return currentBook;
	}
	public void setCurrentBook(Book currentBook) {
		this.currentBook = currentBook;
	}
	public ArrayList<Book> getLastComics() {
		return lastComics;
	}
	public void setLastComics(ArrayList<Book> lastComics) {
		this.lastComics = lastComics;
	}
	public ArrayList<Book> getCurrentComics() {
		return currentcomics;
	}
	public void setCurrentComics(ArrayList<Book> comics) {
		this.currentcomics = comics;
	}
	public Pane getReusltContent() {
		return reusltContent;
	}
	public void setReusltContent(Pane resultContent) {
		this.reusltContent = resultContent;
	}
	public static SearchResult getSearchResult() {
		return searchResult;
	}
	public static void setSearchResult(SearchResult searchResult) {
		SearchResult.searchResult = searchResult;
	}
	private static SearchResult searchResult = new SearchResult();
	private SearchResult(){
		
	}
	public static SearchResult getinstanceSearchResult(){
		return searchResult;
	}
	public ScrollPane getResultPane() {
		return resultPane;
	}
	public void setResultPane(ScrollPane resultPane) {
		this.resultPane = resultPane;
	}
	public CheckBox getSearchCheck() {
		return searchCheck;
	}
	public void setSearchCheck(CheckBox searchCheck) {
		this.searchCheck = searchCheck;
	}
	public ArrayList<CheckBox> getChecks() {
		return checks;
	}
	public void setChecks(ArrayList<CheckBox> checks) {
		this.checks = checks;
	}
	
}
 