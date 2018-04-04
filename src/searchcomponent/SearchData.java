package searchcomponent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class SearchData {
	public final static double CHECK_BOX_HEIGHT = 18.0;
	public final static CheckBox ALL_CHECK = new CheckBox("全选");
	static{
		ALL_CHECK.setSelected(true);
		ALL_CHECK.setOnMouseClicked(new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				if(SearchData.ALL_CHECK.isSelected()){
					System.out.println("进行全选");
				}else{
					System.out.println("进行全部取消");
				}
			}
		});
	}
}
