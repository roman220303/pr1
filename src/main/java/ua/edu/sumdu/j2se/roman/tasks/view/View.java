package ua.edu.sumdu.j2se.roman.tasks.view;

import ua.edu.sumdu.j2se.roman.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.roman.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.*;

public class View {

    /**
     * Пустий метод, який виводе головне меню функціональності
     */
    public void outMenu(){

        String menu = "Меню\n" + "1 - Додати нову задачу\n" + "2 - Змінити параметри задачі\n" + "3 - Видалити задачу\n"
                + "4 - Переглянути всі наявні задачі\n" + "5 - Сформувати календар задач(повідомлення) на певний період\n" + "ex - Вийти\n";

        System.out.println(menu);

    }

    /**
     * Пустий метод, який виводе всі задачі зі списку
     * @param list
     */
    public void displayList(AbstractTaskList list) {
        int k = 1;
        for (Task i : list){
            System.out.println(k + " - " + i);
            k++;
        }
    }

    /**
     * Пустий метод, який виводе всі задачі за певний період часу
     * @param calendar
     */
    public void printCalendar(SortedMap<LocalDateTime, Set<Task>> calendar) {
        if(!calendar.isEmpty()){
            for (Map.Entry<LocalDateTime, Set<Task>> item : calendar.entrySet()){
                for (Task i : item.getValue()) {
                    System.out.println("Час задачі - " + item.getKey() + " \' Назва задачі " + i.getTitle().substring(0, 1).toUpperCase() + i.getTitle().substring(1) + "\'");
                }
            }
        }
        else{
            System.out.println("Активних задач на цей проміжок часу немає");
        }

    }

    /**
     * Пустий метод, який виводе меню вибору потрібного нам календаря.
     * 1 - На тиждень від сьогоднішнього дня
     * 2 - Задати власний проміжок часу
     */
    public void makeCalendarView(){
        String menu = "Меню\n" + "1 - На тиждень від сьогоднішнього дня\n" + "2 - Задати власний проміжок часу\n" + "3 - Через 10 хвилин\n";

        System.out.println(menu);
    }

    /**
     * Пустий метод, який виводе меню вибору зміни параметрів задачі
     */
    public void makeMenuChanged(){
        System.out.println("\n1. Назва");
        System.out.println("2. Час");
        System.out.println("3. Час початку");
        System.out.println("4. Час закінчення");
        System.out.println("5. Інтервал");
        System.out.println("6. Зробіть його активним/неактивним");
        System.out.print("Введіть свій варіант -> ");
    }

    /**
     * Метод для введення значень типу String
     * @return
     */
    public String keyboardReadWholeLn() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int keyboardReadWholeInt() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

}
