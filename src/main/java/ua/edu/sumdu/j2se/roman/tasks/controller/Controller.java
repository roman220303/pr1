package ua.edu.sumdu.j2se.roman.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.roman.tasks.model.*;
import ua.edu.sumdu.j2se.roman.tasks.view.View;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Controller implements Runnable {
    private Task model = new Task();
    private AbstractTaskList list = new LinkedTaskList();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private File file = new File("task.txt");
    private LocalDateTime timech;
    private View view = new View();
    private static final Logger logger = Logger.getLogger(Controller.class);

    /**
     *
     */
    public void run(){
        if(file.exists()){
            try {
                TaskIO.readBinary(list,file);
            } catch (NullPointerException e) {
                System.out.println("Проблеми з даними/NullPointerException");
            }
            runStartMenu();
        }
        else{
            System.out.println("tyt");
            file = new File("task.txt");
            try {
                if(file.createNewFile()) runStartMenu();
            } catch (IOException e) {
                System.out.println("Виникла проблема при створенні файлу даних");
                logger.info("Виникла проблема при створенні файлу даних");
            }
        }
    }

    /**
     *
     */
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
                    if(checkList(list)) changeTask();
                    else System.out.println("Задач немає");
                    runStartMenu();
                    break;

                case "3":
                    if(checkList(list)) deleteTask();
                    else System.out.println("Задач немає");
                    runStartMenu();
                    break;

                case "4":
                    if(checkList(list)) view.displayList(list);
                    else System.out.println("Задач немає");
                    runStartMenu();
                    break;

                case "5":
                    if(checkList(list)) makeCalendar();
                    else System.out.println("Задач немає");
                    runStartMenu();
                    break;

                case "ex":
                    isQuit = true;
                    System.exit(0);
                    break;

                default:
                    System.out.println("Введіть правильне значення");
                    break;
            }
        }
        try {
            TaskIO.writeBinary(list,file);
        } catch (Throwable e) {
            System.out.println("Проблеми з даними/Throwable");
            logger.info("Проблема з даними/Throwable");
        }

    }

    /**
     *
     * @param list
     * @return
     */
    private boolean checkList(AbstractTaskList list) {
        return list.size() != 0;
    }

    /**
     *
     */
    private void createTask(){
        String str;
        String strTitle;
        String strDate;

        System.out.println();
        System.out.print("Введіть назву задачі: ");

        strTitle = view.keyboardReadWholeLn();
        model.setTitle(strTitle);

        System.out.print("\nВведіть час виконання задачі (yyyy-MM-dd HH:mm:ss): ");
        strDate = view.keyboardReadWholeLn();

        try {
            timech = LocalDateTime.parse(strDate, formatter);
        } catch (DateTimeParseException e){
            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm:ss)");
            createTask();
        }
        model.setTime(timech);
        System.out.print("\nВи хочете зробити завдання повторюваним (y/n) ? ");
        str = view.keyboardReadWholeLn();

        if (str.equals("y")) {
            model.setActive(true);
            System.out.print("\nВведіть час завершення завдання (yyyy-MM-dd HH:mm:ss): ");
            strDate = view.keyboardReadWholeLn();
            try {
                timech = LocalDateTime.parse(strDate, formatter);
            } catch (DateTimeParseException e){
                System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm:ss)");
                createTask();
            }
            makeInterval(model);
            if(model.getStartTime().isBefore(timech))
                model.setTime(model.getStartTime(), timech, model.getRepeatInterval());
            else {
                System.out.println("Ви ввели час завершення завдання, який раніше, ніж час початку завдання!");
                createTask();
            }
        }
        try {
            list.add(model);
        } catch (Exception e) {
            System.out.println("Виникла помилка при додаванні нової задачі/Exception");
            logger.info("Виникла помилка при додаванні нової задачі/Exception");
            createTask();
        }
        acceptChanges();
        logger.info("Task \'" + model.getTitle() + "\' was created");
    }

    /**
     *
     */
    private void deleteTask(){
        String str;
        System.out.print("Яке завдання ви хочете видалити (введіть його назву)? ");
        str = view.keyboardReadWholeLn();
        boolean isDelete = false;

        for (Task i : list) {
            if (str.equals(i.getTitle().toLowerCase())) {
                list.remove(i);
                System.out.println("Видалено");
                logger.info("Task \'" + i.getTitle() + "\' was deleted");
                isDelete = true;
            }
        }
        if(!isDelete) System.out.println("Задачі не знайдено. Виникла помилка!");
        else {
            try {
                TaskIO.writeBinary(list,file);
            } catch (Throwable e) {
                System.out.println("Проблема з даними/Throwable");
                logger.info("Проблема з даними/Throwable");
            }
        }
    }

    /**
     *
     */
    private void changeTask(){
        String str;
        String strTitle;
        String strDate;

        System.out.println();
        System.out.println("Яке завдання ви хочете змінити (введіть його назву)? ");

        String strn = view.keyboardReadWholeLn();

        for (Task i : list) {
            if (strn.equals(i.getTitle())) {
                System.out.println("\n1. Назва");
                System.out.println("2. Час");
                System.out.println("3. Час початку");
                System.out.println("4. Час закінчення");
                System.out.println("5. Інтервал");
                System.out.println("6. Зробіть його активним/неактивним");
                System.out.print("Введіть свій варіант -> ");
                str = view.keyboardReadWholeLn();

                switch (str) {
                    case "1":
                        System.out.print("Введіть назву завдання: ");
                        strTitle = view.keyboardReadWholeLn();
                        i.setTitle(strTitle);
                        logger.info("Task \'" + strn + "\' was name changed -> " + strTitle);
                        break;
                    case "2":
                        System.out.print("\nВведіть дату виконання завдання (yyyy-MM-dd HH:mm:ss): ");
                        strDate = view.keyboardReadWholeLn();
                        try {
                            timech = LocalDateTime.parse(strDate, formatter);
                        } catch (DateTimeParseException e){
                            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm:ss)");
                            changeTask();
                        }
                        i.setTime(timech);
                        i.setActive(i.isActive());
                        logger.info("Task \'" + strn + "\' was date changed -> " + i.getTime());
                        break;
                    case "3":
                        System.out.print("\nВведіть час початку завдання (yyyy-MM-dd HH:mm:ss):");
                        strDate = view.keyboardReadWholeLn();
                        try {
                            timech = LocalDateTime.parse(strDate, formatter);
                        } catch (DateTimeParseException e){
                            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm:ss)");
                            changeTask();
                        }
                        if (i.getStartTime().isAfter(timech))
                            i.setTime(timech, i.getEndTime(), i.getRepeatInterval());
                        else {
                            System.out.println("Не вдалося проаналізувати дату! Введіть правильну дату, будь ласка.");
                            changeTask();
                        }
                        logger.info("Task \'" + strn + "\' was start date changed -> " + i.getStartTime());
                        break;
                    case "4":
                        System.out.print("\nВведіть час завершення завдання (yyyy-MM-dd HH:mm:ss): ");
                        strDate = view.keyboardReadWholeLn();
                        try {
                            timech = LocalDateTime.parse(strDate, formatter);
                        } catch (DateTimeParseException e){
                            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm:ss)");
                            changeTask();
                        }
                        if (i.getStartTime().isAfter(timech))
                            i.setTime(i.getStartTime(), timech, i.getRepeatInterval());
                        else {
                            System.out.println("Не вдалося проаналізувати дату! Введіть правильну дату, будь ласка.");
                            changeTask();
                        }
                        logger.info("Task \'" + strn + "\' was end date changed -> " + i.getEndTime());
                        break;
                    case "5":
                        makeInterval(i);
                        logger.info("Task \'" + strn + "\' new interval");
                        break;
                    case "6":
                        System.out.println("Введіть 1/0 (активувати = 1, деактивувати = 0):");
                        str = view.keyboardReadWholeLn();
                        if (str.equals("1")) i.setActive(true);
                        else i.setActive(false);
                        logger.info("Task \'" + strn + "\' active = " + str);
                        break;
                    default:
                        System.out.println("Такої задачі не знайдено");
                        runStartMenu();
                }
                model = i;
            }
        }
        acceptChanges();
        logger.info("Task \'" + model.getTitle() + "\' was changed in datafile");
    }

    /**
     *
     */
    private void acceptChanges() {
        try {
            TaskIO.writeBinary(list, file);
        } catch (Throwable e) {
            System.out.println("Помилка з записом у файл/Throwable");
            logger.info("Помилка з записом у файл/Throwable");
        }
    }

    /**
     *
     * @param task
     */
    private void makeInterval(Task task) {
        String strDate;
        int days;
        int hours;
        int minutes;
        int seconds;
        int time = 0;
        int interval;
        Scanner sc = new Scanner(System.in);
        String str;
        System.out.print("\nВведіть час завершення завдання (yyyy-MM-dd HH:mm:ss): ");
        strDate = view.keyboardReadWholeLn();
        LocalDateTime end = LocalDateTime.now();
        try {
            end = LocalDateTime.parse(strDate,formatter);
        } catch (DateTimeParseException e){
            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm:ss)");
            createTask();
        }
        System.out.print("\nЗ якою частотою(день - 'd', година - 'h', хвилина - 'm', секунда - 's'): ");
        str = view.keyboardReadWholeLn();
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
        task.setTime(task.getStartTime(), end, time);
    }

    /**
     *
     */
    private void makeCalendar() {
        view.makeCalendarView();
        Scanner scan = new Scanner(System.in);
        String scanchar = scan.nextLine();
        switch (scanchar){
            case "2":
                System.out.print("\nВивести задачі, починаючи з (yyyy-MM-dd HH:mm:ss): ");
                String start = view.keyboardReadWholeLn();
                System.out.print("\nВивести задачі, по дату (yyyy-MM-dd HH:mm:ss): ");
                String end = view.keyboardReadWholeLn();
                SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(list, LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter));
                try{
                    view.printCalendar(calendar);
                } catch (NullPointerException e){
                    System.out.println("Активних задач на цей проміжок часу немає");
                }
                break;
            case "1":
                SortedMap<LocalDateTime, Set<Task>> calendar7 = Tasks.calendar(list, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
                try{
                    view.printCalendar(calendar7);
                } catch (NullPointerException e){
                    System.out.println("Активних задач на цей проміжок часу немає");
                }
                break;
        }
    }

}
