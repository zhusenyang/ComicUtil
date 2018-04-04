package entity;

import javafx.scene.control.CheckBox;

/**
 * @author yang  
 * @NGAID  vengeance_y
 * @date   2018年4月3日
 */
public class Chapter implements Comparable{
	private String Title = null;
	private String Html = null;
	private CheckBox checkBox = new CheckBox();
	public CheckBox getCheckBox(){
		return checkBox;
	}
	public Chapter(String Title, String Html)
	{
		this.Title = Title;
		this.Html = Html;
		checkBox.setText(Title);
	}

	public String getTitle() {
		return Title;
	}

	public String getHtml() {
		return Html;
	}
	
	@Override
	public int compareTo(Object o) {
		//TODO 根据不同优化排序功能
		return Title.compareToIgnoreCase(o.toString());
	}
	@Override
	public String toString(){
		return Title;
	}
}
