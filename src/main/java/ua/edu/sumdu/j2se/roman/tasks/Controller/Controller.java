package ua.edu.sumdu.j2se.roman.tasks.Controller;

import ua.edu.sumdu.j2se.roman.tasks.Model.*;
import ua.edu.sumdu.j2se.roman.tasks.View.View;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Controller {
    private Task model;
    private AbstractTaskList list = new LinkedTaskList();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final File file = new File("task.txt");
    private LocalDateTime timech = LocalDateTime.of(2014, 9, 19, 14, 5);


    public void run(){
        try {
            TaskIO.readBinary(list,file);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        runStartMenu();
    }

    public void runStartMenu(){
        Scanner scan = new Scanner(System.in);
        View view = new View();
        view.outMenu();
        boolean isQuit = false;
        while (!isQuit) {
            System.out.print("Вибір -> ");
            String flag = scan.nextLine();

            switch (flag) {

                case "1":
                    createTask();
                    runStartMenu();
                    break;
                case "2":
                    changeTask();
                    runStartMenu();
                    break;

                case "3":
                    deleteTask();
                    runStartMenu();
                    break;

                case "4":
                    if(checkList(list)) view.displayList(list);
                    else System.out.println("Задач немає");
                    view.displayList(list);
                    runStartMenu();
                    break;

                case "5":
                    System.out.println("В разработке");
                    runStartMenu();
                    break;

                case "6":
                    isQuit = true;
                    break;

                default:
                    System.out.println("Введіть правильне значення");
                    break;
            }
        }

    }

    private boolean checkList(AbstractTaskList list) {
        return list.size() != 0;
    }

    private void createTask(){
        String str;
        String strTitle;
        String strDate;
        Task modelClone = new Task(" ", LocalDateTime.of(2014, 9, 19, 14, 5));

        System.out.println();
        System.out.print("\nВведіть назву задачі: ");

        strTitle = keyboardReadWholeLn();
        modelClone.setTitle(strTitle);

        System.out.print("\nВведіть час виконання задачі (yyyy-MM-dd HH:mm:ss): ");
        strDate = keyboardReadWholeLn();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timech = LocalDateTime.parse(strDate, formatter);
        modelClone.setTime(timech);
        System.out.print("\nВи хочете зробити завдання повторюваним (y/n) ? ");
        str = keyboardReadWholeLn();

        if (str.equals("y")) {
            modelClone.setActive(true);
            System.out.print("\nВведіть час завершення завдання (yyyy-MM-dd HH:mm:ss): ");
            strDate = keyboardReadWholeLn();
            timech = LocalDateTime.parse(strDate, formatter);
            modelClone.setTime(timech);
            makeInterval(modelClone);
            if(modelClone.getStartTime().isAfter(timech))
                modelClone.setTime(modelClone.getStartTime(), timech, modelClone.getRepeatInterval());
            else {
                System.out.println("Ви ввели час завершення завдання, який раніше, ніж час початку завдання!");
                createTask();
            }
        }
        model = modelClone;
        try {
            list.add(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        acceptChanges();
    }

    private void deleteTask(){
        String str;
        System.out.print("Яке завдання ви хочете видалити (введіть його назву)? ");
        str = keyboardReadWholeLn();


        for (Task i : list) {
            if (str.equals(i.getTitle().toLowerCase())) {
                System.out.println("Видалено");
                list.remove(i);
            }
            else {
                System.out.println("Задача не видалена. Виникла помилка!");
            }
        }
        try {
            TaskIO.writeBinary(list,file);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private String keyboardReadWholeLn() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private void changeTask(){
        String str;
        String strTitle;
        String strDate;

        System.out.println();
        System.out.println("Яке завдання ви хочете змінити (введіть його назву)? ");

        str = keyboardReadWholeLn();

        for (Task i : list) {
            if (str.equals(i.getTitle())) {
                System.out.println("\n1. Назва");
                System.out.println("2. Час");
                System.out.println("3. Час початку");
                System.out.println("4. Час закінчення");
                System.out.println("5. Інтервал");
                System.out.println("6. Зробіть його активним/неактивним");
                System.out.print("Введіть свій варіант -> ");
                str = keyboardReadWholeLn();

                switch (str) {
                    case "1":
                        System.out.print("Введіть назву завдання: ");
                        strTitle = keyboardReadWholeLn();
                        i.setTitle(strTitle);
                        break;
                    case "2":
                        System.out.print("\nВведіть дату виконання завдання (yyyy-MM-dd HH:mm:ss): ");
                        strDate = keyboardReadWholeLn();
                        timech = LocalDateTime.parse(strDate, formatter);
                        i.setTime(timech);
                        i.setActive(i.isActive());
                        break;
                    case "3":
                        System.out.print("\nВведіть час початку завдання (yyyy-MM-dd HH:mm:ss):");
                        strDate = keyboardReadWholeLn();
                        timech = LocalDateTime.parse(strDate, formatter);
                        i.setTime(timech);
                        break;
                    case "4":
                        System.out.print("\nВведіть час завершення завдання (yyyy-MM-dd HH:mm:ss): ");
                        strDate = keyboardReadWholeLn();
                        timech = LocalDateTime.parse(strDate, formatter);
                        if (i.getStartTime().isAfter(timech))
                            i.setTime(i.getStartTime(), timech, i.getRepeatInterval());
                        else {
                            System.out.println("Не вдалося проаналізувати дату! Введіть правильну дату, будь ласка.");
                            changeTask();
                        }
                        break;
                    case "5":
                        makeInterval(i);
                        break;
                    case "6":
                        System.out.println("Введіть 1/0 (активувати = 1, деактивувати = 0):");
                        str = keyboardReadWholeLn();
                        if (str.equals("1"))
                            i.setActive(true);
                        else
                            i.setActive(false);
                        break;
                }
                model = i;
            }
        }
        acceptChanges();
    }

    private void acceptChanges() {
        try {
            TaskIO.writeBinary(list, file);
        } catch (Exception e) {
            System.out.println("File error");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void makeInterval(Task task) {
        int days;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int time = 0;
        int interval = 0;
        Scanner sc = new Scanner(System.in);
        String str;
        System.out.print("\nЗ якою частотою(день - 'd', година - 'h', хвилина - 'm', секунда - 's'): ");
        str = keyboardReadWholeLn();
        switch (str){
            case "d":
                System.out.print("\nВведіть значення: ");
                interval = sc.nextInt();
                days = interval * 86400;
                time += days;
                break;
            case "h":
                System.out.print("\nВведіть значення: ");
                interval = sc.nextInt();
                hours = interval * 3600;
                time += hours;
                break;
            case "m":
                System.out.print("\nВведіть значення: ");
                interval = sc.nextInt();
                minutes = interval * 60;
                time += minutes;
                break;
            case "s":
                System.out.print("\nВведіть значення: ");
                interval = sc.nextInt();
                seconds = interval;
                time += seconds;
                break;

            default:
                System.out.println("Некоректно");
                runStartMenu();
                break;

        }
        task.setTime(task.getStartTime(), task.getEndTime(), time);
    }
}
