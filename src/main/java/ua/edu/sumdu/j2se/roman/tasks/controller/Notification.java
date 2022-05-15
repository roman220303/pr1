package ua.edu.sumdu.j2se.roman.tasks.controller;

import ua.edu.sumdu.j2se.roman.tasks.model.*;
import ua.edu.sumdu.j2se.roman.tasks.view.View;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class Notification implements Runnable {

    private AbstractTaskList list = new LinkedTaskList();

    private Thread thread;

    private View view = new View();

    private File file;

    public Notification() {
        file = new File("task.txt");
        TaskIO.readBinary(list,file);
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    private void notifyN() {
        LocalDateTime prev = LocalDateTime.now();
        LocalDateTime next = LocalDateTime.now().plusMinutes(10);
        SortedMap<LocalDateTime, Set<Task>> repeatedTasks = Tasks.calendar(list, prev, next);

        try {
            System.out.println("Сповіщення про виконання задач");
            view.printCalendar(repeatedTasks);
            System.out.println("");
        } catch (NullPointerException e){
            System.out.println("Задач, які потрібно скоро виконати немає\n");
        }
    }


    @Override
    public void run() {
        while (true) {
            notifyN();
            try {
                // 10 * 1000
                thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
