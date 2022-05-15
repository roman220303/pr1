package ua.edu.sumdu.j2se.roman.tasks;

import ua.edu.sumdu.j2se.roman.tasks.controller.Controller;
import ua.edu.sumdu.j2se.roman.tasks.controller.Notification;

public class Main {

	public static void main(String[] args){
		Notification notification = new Notification();
		Controller controller = new Controller();
		controller.run();
	}

}
