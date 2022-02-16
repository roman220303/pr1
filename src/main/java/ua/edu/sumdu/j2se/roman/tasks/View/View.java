package ua.edu.sumdu.j2se.roman.tasks.View;

import ua.edu.sumdu.j2se.roman.tasks.Model.AbstractTaskList;
import ua.edu.sumdu.j2se.roman.tasks.Model.Task;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class View {
    public void outMenu(){

        String menu = "Меню\n" + "1 - Додати нову задачу\n" + "2 - Змінити параметри задачі\n" + "3 - Видалити задачу\n"
                + "4 - Переглянути всі наявні задачі\n" + "5 - Переглянути календар запланованих задач\n" + "6 - Вийти\n";

        System.out.println(menu);


    }
    public void displayList(AbstractTaskList list) {
        for (Task i : list)
            System.out.println(i);
    }
}
