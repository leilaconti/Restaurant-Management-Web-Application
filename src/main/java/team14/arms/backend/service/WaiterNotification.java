package team14.arms.backend.service;

import com.vaadin.flow.component.html.Label;

public class WaiterNotification {
    Label content;
    Boolean show = false;

	public WaiterNotification(){
		this.content = new Label("<font size = \\\"5\\\" color=\\\"red\\\"> A customer would like your attention!");
	}
	
	public Label getLabel() {
		return content;
	}
	
	public void setVisable() {
		show = true;
	}
	
	public boolean upset() {
		return show;
	}
	
	
}
