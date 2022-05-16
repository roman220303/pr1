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
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Метод, який будує виводить назву задачі, якщо:
     * вона активна
     * повинна бути зроблена виконана протягом хвилини
     */
    private void notifyN() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime next = LocalDateTime.now().plusMinutes(1);
        SortedMap<LocalDateTime, Set<Task>> repeatedTasks = Tasks.calendar(list, now, next);

        try {
            view.printNotificator(repeatedTasks);
        } catch (NullPointerException e) {
            System.out.println();
        }
    }


    @Override
    public void run() {
        while (true) {
            file = new File("task.txt");
            TaskIO.readBinary(list,file);
            notifyN();
            try {
                // 60 * 1000
                thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
