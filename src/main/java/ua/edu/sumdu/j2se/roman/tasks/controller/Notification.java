package ua.edu.sumdu.j2se.roman.tasks.controller;

import ua.edu.sumdu.j2se.roman.tasks.model.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class Notification extends Thread {

    private AbstractTaskList list = new LinkedTaskList();

    private Thread thread;

    private File file;

    public Notification(AbstractTaskList model, File file) {
        list = model;
        this.file = file;
        thread = new Thread(this, "Task notification");
        thread.setDaemon(true);
        thread.start();
    }
    /**
     * Метод відповідає за показ сповіщень про завдання, які необхідно виконати
     */
    private void notifyN() {
        SortedMap<LocalDateTime, Set<Task>> repeatedTasks;

        LocalDateTime prev = LocalDateTime.now();
        LocalDateTime next = LocalDateTime.now().plusHours(1);

        repeatedTasks = Tasks.calendar(list, prev, next);
        for (Map.Entry<LocalDateTime, Set<Task>> item : repeatedTasks.entrySet()) {
            System.out.println("");
            System.out.println("Повідомлення на наступну годину" +
                    item.getKey().toString() + "\nЗадачі: ");
            for (Task j : item.getValue()) {
                System.out.print(" \'" +
                        j.getTitle().substring(0, 1).toUpperCase() +
                        j.getTitle().substring(1) +
                        "\' ");
            }
            System.out.println("");
        }

    }
    /**
     * Основний метод у класі, який запускає потік сповіщень
     */
    @Override
    public void run() {
        try {
            TaskIO.readText(list, file);
            notifyN();
            try {
                sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
