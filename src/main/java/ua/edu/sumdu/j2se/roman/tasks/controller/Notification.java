package ua.edu.sumdu.j2se.roman.tasks.controller;

import ua.edu.sumdu.j2se.roman.tasks.model.*;
import ua.edu.sumdu.j2se.roman.tasks.view.View;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class Notification extends Thread {

    private AbstractTaskList list = new LinkedTaskList();

    private Thread thread;

    private View view = new View();


    public Notification(AbstractTaskList model) {
        list = model;
        thread = new Thread(this, "Task notification");
        thread.setDaemon(true);
        thread.start();
    }
    /**
     * Метод відповідає за показ сповіщень про завдання, які необхідно виконати
     */
    private void notifyN() {
        LocalDateTime prev = LocalDateTime.now();
        LocalDateTime next = LocalDateTime.now().plusHours(2);

        System.out.println(list.size());

        SortedMap<LocalDateTime, Set<Task>> repeatedTasks = Tasks.calendar(list, prev, next);

        try {
            view.printCalendar(repeatedTasks);
        } catch (NullPointerException e){
            System.out.println("Задач від " + prev + " до " + next + " немає");
        }


    }
    /**
     * Основний метод у класі, який запускає потік сповіщень
     */
    @Override
    public void run() {
        try {
            notifyN();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
