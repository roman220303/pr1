package ua.edu.sumdu.j2se.roman.tasks.view;

import ua.edu.sumdu.j2se.roman.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.roman.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.*;

public class View {
    public void outMenu(){

        String menu = "Меню\n" + "1 - Додати нову задачу\n" + "2 - Змінити параметри задачі\n" + "3 - Видалити задачу\n"
                + "4 - Переглянути всі наявні задачі\n" + "5 - Переглянути календар запланованих задач\n" + "ex - Вийти\n";

        System.out.println(menu);

    }

    public void displayList(AbstractTaskList list) {
        for (Task i : list)
            System.out.println(i);
    }

    public void printCalendar(SortedMap<LocalDateTime, Set<Task>> calendar) {
        if(!calendar.isEmpty()){
            for (Map.Entry<LocalDateTime, Set<Task>> item : calendar.entrySet()){
                for (Task i : item.getValue()) {
                    System.out.println(item.getKey() +
                            " \'" +
                            i.getTitle().substring(0, 1).toUpperCase() +
                            i.getTitle().substring(1) +
                            "\'");
                }
            }
        }
        else{
            System.out.println("Активних задач на цей проміжок часу немає");
        }

    }

    public void makeCalendarView(){
        String menu = "Меню\n" + "1 - На тиждень від сьогоднішнього дня\n" + "2 - Задати власний проміжок часу\n";

        System.out.println(menu);
    }

    public void makeMenuChanged(){
        System.out.println("\n1. Назва");
        System.out.println("2. Час");
        System.out.println("3. Час початку");
        System.out.println("4. Час закінчення");
        System.out.println("5. Інтервал");
        System.out.println("6. Зробіть його активним/неактивним");
        System.out.print("Введіть свій варіант -> ");
    }

    public String keyboardReadWholeLn() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

}