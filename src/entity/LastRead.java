package entity;

/**
 * @author yang  
 * @NGAID  vengeance_y
 * @date   2018年4月3日
 */
public class LastRead {
	/**
	 * 搜索关键字
	 */
	public String KeyWorld;
	
	/**
	 * 上一次最后阅读的漫画
	 */
	public  Book LastBook;
	
	/**
	 * 上一次最后阅读的章节
	 */
	public Chapter LastChapter;
	
	/**
	 * 上一次最后阅读的页数
	 */
	public Integer LastPage;
	public LastRead() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LastRead(String keyWorld, Book lastBook, Chapter lastChapter, Integer lastPage) {
		super();
		KeyWorld = keyWorld;
		LastBook = lastBook;
		LastChapter = lastChapter;
		LastPage = lastPage;
	}
	public String getKeyWorld() {
		return KeyWorld;
	}
	public void setKeyWorld(String keyWorld) {
		KeyWorld = keyWorld;
	}
	public Book getLastBook() {
		return LastBook;
	}
	public void setLastBook(Book lastBook) {
		LastBook = lastBook;
	}
	public Chapter getLastChapter() {
		return LastChapter;
	}
	public void setLastChapter(Chapter lastChapter) {
		LastChapter = lastChapter;
	}
	public Integer getLastPage() {
		return LastPage;
	}
	public void setLastPage(Integer lastPage) {
		LastPage = lastPage;
	}
	
	
}
