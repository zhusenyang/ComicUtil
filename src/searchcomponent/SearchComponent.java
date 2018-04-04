package searchcomponent;

import java.util.ArrayList;

import Util.ComicUtil;
import application.CConfig;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class SearchComponent {

	public void toSearch() {
		
	}

	public void showSearchResult() {
		testSearch();
	}

	public void clearResult(Pane resultPane, CheckBox cb) {
		System.out.println(resultPane.getChildren().remove(cb));
	}

	public static void refreshResult(Number oldValue, Number newValue) {
		ArrayList<CheckBox> checks = SearchResult.getinstanceSearchResult().getChecks();
		ScrollPane resultPane = SearchResult.getinstanceSearchResult().getResultPane();
	}
	
	public void testSearch() {
		System.out.println("testSearch");
		SearchResult search = SearchResult.getinstanceSearchResult();
		ArrayList<CheckBox> checks = search.getChecks();
		Pane resultContent = SearchResult.getinstanceSearchResult().getReusltContent();
		for (int i = 0; i < 100; i++) {
			final CheckBox cb = new CheckBox("checkBox" + i);
			final Tooltip tooltip = new Tooltip("$ tooltip");
			tooltip.setFont(new Font("Arial", 16));
			cb.setTooltip(tooltip);
			checks.add(cb);
			cb.setLayoutX(10);
			cb.setLayoutY(i * 18);
			resultContent.getChildren().add(cb);
		}
		
	}
	public void searchComicName(){
		ComicUtil comicUtil =  CConfig.getCConfig().getComicUtil();
		comicUtil.searchComicName();
	}
	public void searchComicChapters(){
		
	}
}
