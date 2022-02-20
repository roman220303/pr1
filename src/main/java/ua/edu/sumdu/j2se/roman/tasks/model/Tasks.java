package ua.edu.sumdu.j2se.roman.tasks.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

public class Tasks {

    /**
     * метод, що повертає
     * підмножину задач із колекції tasks, які заплановані на виконання хоча б раз після часу start і не пізніше ніж end.
     * @param tasks
     * @param start
     * @param end
     * @return
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        return new Iterable<Task>(){
            @Override
            public Iterator<Task> iterator() {
                return StreamSupport.stream(tasks.spliterator(), false).
                        filter(task -> task.nextTimeAfter(start) != null
                        && (end.isAfter(task.nextTimeAfter(start)) || end.isEqual(task.nextTimeAfter(start)))).iterator();

            }
        };
    }

    /**
     * метод, який буде будувати календар задач на заданий період.
     * @param tasks
     * @param start
     * @param end
     * @return
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();
        for (Task task: tasks) {
                LocalDateTime holder = task.nextTimeAfter(start);
                while (holder != null && !holder.isAfter(end)){
                    if(calendar.containsKey(holder)){
                        calendar.get(holder).add(task); //якщо дата вже є, додаємо задачу
                    }
                    else{ // якщо дати ще немає, створюємо її та додаєм в неї задачу
                        Set<Task> taskSet = new HashSet<>();
                        taskSet.add(task);
                        calendar.put(holder, taskSet);
                    }
                    holder = task.nextTimeAfter(holder);
                }
        }
        return calendar;
    }


}
