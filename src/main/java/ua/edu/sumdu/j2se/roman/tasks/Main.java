package ua.edu.sumdu.j2se.roman.tasks;

import ua.edu.sumdu.j2se.roman.tasks.controller.Controller;
import ua.edu.sumdu.j2se.roman.tasks.controller.Notification;

import java.io.File;

public class Main {

	public static void main(String[] args){
		File file = new File ("task.txt");
		Controller controller = new Controller();
		Notification notify = new Notification(controller.getModelList(), file);
		controller.run();
		notify.start();
	}

}
