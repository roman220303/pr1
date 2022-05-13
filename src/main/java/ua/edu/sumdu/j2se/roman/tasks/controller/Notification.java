package ua.edu.sumdu.j2se.roman.tasks.controller;

import ua.edu.sumdu.j2se.roman.tasks.model.*;
import ua.edu.sumdu.j2se.roman.tasks.view.View;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class Notification extends Thread {

    private AbstractTaskList list = new LinkedTaskList();

    private final View view = new View();

    public Notification(AbstractTaskList model) {
        list = model;
        Thread thread = new Thread(this, "Task notification");
        thread.setDaemon(true);
    }

    /**
     * Метод відповідає за показ сповіщень про завдання, які необхідно виконати
     */
    private void notifyN() {
        LocalDateTime prev = LocalDateTime.now();
        LocalDateTime next = LocalDateTime.now().plusHours(2);

        SortedMap<LocalDateTime, Set<Task>> repeatedTasks = Tasks.calendar(list, prev, next);

        try {
            System.out.println("Сповіщення про виконання задач");
            view.printCalendar(repeatedTasks);
            System.out.println("");
        } catch (NullPointerException e){
            System.out.println("Задач, які потрібно скоро виконати немає\n");
        }

    }

    /**
     * Метод run() у класі, який запускає потік сповіщень
     */
    @Override
    public void run() {
        try {
            notifyN();
        } catch (Exception e) {
            System.out.println("Проблема з сповіщеннями");
        }
    }


}
