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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private File file = new File("task.txt");
    private LocalDateTime timech;
    private View view = new View();
    private static final Logger logger = Logger.getLogger(Controller.class);
    Notification notification;

    /**
     * Метод, який зчитує дані з файлу. Якщо такого не існує, то створюється новий файл з іменем "task.txt".
     */
    public void run(){
        if(file.exists()){
            try {
                TaskIO.readBinary(list,file);

            } catch (NullPointerException e) {
                System.out.println("Проблеми з даними/NullPointerException");
            }
            Notification notification = new Notification(list);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runStartMenu();
        }
        else{
            file = new File("task.txt");
            try {
                if(file.createNewFile()) {
                    runStartMenu();
                }
            } catch (IOException e) {
                System.out.println("Виникла проблема при створенні файлу даних");
                logger.info("Виникла проблема при створенні файлу даних");
            }
        }
    }

    /**
     * Метод, який дає функціональність додати/змінити/видалити/переглянути задачі.
     */
    public void runStartMenu(){
        Scanner scan = new Scanner(System.in);
        View view = new View();
        view.outMenu();
        boolean isQuit = false;
        checkTask();
        while (!isQuit) {
            System.out.print("\nВибір -> ");
            String flag = scan.nextLine();

            switch (flag) {
                case "1":
                    createTask();
                    System.out.println();
                    runStartMenu();
                    break;
                case "2":
                    view.displayList(list);
                    if(checkList(list)) changeTask();
                    else System.out.println("Задач немає");
                    System.out.println();
                    runStartMenu();
                    break;

                case "3":
                    view.displayList(list);
                    if(checkList(list)) deleteTask();
                    else System.out.println("Задач немає");
                    System.out.println();
                    runStartMenu();
                    break;

                case "4":
                    if(checkList(list)) view.displayList(list);
                    else System.out.println("Задач немає");
                    System.out.println();
                    runStartMenu();
                    break;

                case "5":
                    if(checkList(list)) makeCalendar();
                    else System.out.println("Задач немає");
                    System.out.println();
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
     * Метод, який перевіряє на наявність задач в списку задач.
     * @param list
     * @return
     */
    private boolean checkList(AbstractTaskList list) {
        return list.size() != 0;
    }

    /**
     * Метод, який робить не активними задачі, які вже виконані за часом.
     */
    private void checkTask(){
        for (Task task: list) {
            if(!task.isRepeated() && task.getTime().isBefore(LocalDateTime.now())){
                task.setActive(false);
            }
            if(task.isRepeated() && task.getEndTime().isBefore(LocalDateTime.now())){
                task.setActive(false);
            }
        }
    }


    /**
     * Метод, який додає задачу до списку задач.
     */
    private void createTask(){
        Task model1 = new Task();
        String str;
        String strTitle;
        String strDate;

        System.out.println();
        System.out.print("Введіть назву задачі: ");

        strTitle = view.keyboardReadWholeLn();
        model1.setTitle(strTitle);

        System.out.print("\nВведіть час виконання задачі (yyyy-MM-dd HH:mm): ");
        strDate = view.keyboardReadWholeLn();

        try {
            timech = LocalDateTime.parse(strDate, formatter);
        } catch (DateTimeParseException e){
            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm)");
            createTask();
        }
        model1.setTime(timech);
        System.out.print("\nВи хочете зробити завдання повторюваним (y/n) ? ");
        str = view.keyboardReadWholeLn();

        while (!str.equals("y") && !str.equals("n")){
            System.out.println("Неправильний ввід");
            System.out.print("\nВи хочете зробити завдання повторюваним (y/n) ? ");
            str = view.keyboardReadWholeLn();
        }

        if (str.equals("y")) {
            model1.setActive(true);
            System.out.print("\nВведіть час завершення завдання (yyyy-MM-dd HH:mm): ");
            strDate = view.keyboardReadWholeLn();
            try {
                timech = LocalDateTime.parse(strDate, formatter);
            } catch (DateTimeParseException e){
                System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm)");
                createTask();
            }
            makeInterval(model1);
            if(model1.getStartTime().isBefore(timech))
                model1.setTime(model1.getStartTime(), timech, model1.getRepeatInterval());
            else {
                System.out.println("Ви ввели час завершення завдання, який раніше, ніж час початку завдання!");
                createTask();
            }
        }
        try {
            list.add(model1);
        } catch (Exception e) {
            System.out.println("Виникла помилка при додаванні нової задачі/Exception");
            logger.info("Виникла помилка при додаванні нової задачі/Exception");
            createTask();
        }
        acceptChanges();
        logger.info("Task \'" + model1.getTitle() + "\' was created");
    }

    /**
     * Метод, який видаляє задачу до списку задач.
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
     * Метод, який дає змогу зміни параметри задачі.
     */
    private void changeTask(){
        String str;
        String strTitle;
        String strDate;
        Boolean isChanged = false;

        Map <Task, Integer> ch = new HashMap<>();

        int m = 1;

        for (Task i : list){
            ch.put(i,m);
            m = m + 1;
        }

        System.out.println();
        System.out.print("Яке завдання ви хочете змінити (введіть номер завдання)? : ");

        int strn = view.keyboardReadWholeInt();

        for (Map.Entry<Task, Integer> entry : ch.entrySet()) {
            //int k = 0;
            if (strn == entry.getValue()) {
                view.makeMenuChanged();
                str = view.keyboardReadWholeLn();

                switch (str) {
                    case "1":
                        System.out.print("Введіть назву завдання: ");
                        strTitle = view.keyboardReadWholeLn();
                        entry.getKey().setTitle(strTitle);
                        if(entry.getKey().isRepeated()) entry.getKey().setActive(true);
                        logger.info("Task number\'" + strn + "\' was name changed -> " + strTitle);
                        break;
                    case "2":
                        System.out.print("\nВведіть дату виконання завдання (yyyy-MM-dd HH:mm): ");
                        strDate = view.keyboardReadWholeLn();
                        try {
                            timech = LocalDateTime.parse(strDate, formatter);
                        } catch (DateTimeParseException e){
                            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm)");
                            changeTask();
                        }
                        entry.getKey().setTime(timech);
                        entry.getKey().setActive(entry.getKey().isActive());
                        if(entry.getKey().isRepeated()) entry.getKey().setActive(true);
                        logger.info("Task number\'" + strn + "\' was date changed -> " + entry.getKey().getTime());
                        break;
                    case "3":
                        System.out.print("\nВведіть час початку завдання (yyyy-MM-dd HH:mm):");
                        strDate = view.keyboardReadWholeLn();
                        try {
                            timech = LocalDateTime.parse(strDate, formatter);
                        } catch (DateTimeParseException e){
                            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm)");
                            changeTask();
                        }
                        if (entry.getKey().getStartTime().isAfter(timech)) {
                            entry.getKey().setTime(timech, entry.getKey().getEndTime(), entry.getKey().getRepeatInterval());
                            if(entry.getKey().isRepeated()) entry.getKey().setActive(true);
                        }
                        else {
                            System.out.println("Не вдалося проаналізувати дату! Введіть правильну дату, будь ласка.");
                            changeTask();
                        }
                        logger.info("Task number\'" + strn + "\' was start date changed -> " + entry.getKey().getStartTime());
                        break;
                    case "4":
                        System.out.print("\nВведіть час завершення завдання (yyyy-MM-dd HH:mm): ");
                        strDate = view.keyboardReadWholeLn();
                        try {
                            timech = LocalDateTime.parse(strDate, formatter);
                        } catch (DateTimeParseException e){
                            System.out.println("Неправильний формат дати. Увага! (yyyy-MM-dd HH:mm)");
                            changeTask();
                        }
                        if (entry.getKey().getStartTime().isBefore(timech)){
                            entry.getKey().setTime(entry.getKey().getStartTime(), timech, entry.getKey().getRepeatInterval());
                            if(entry.getKey().isRepeated()) entry.getKey().setActive(true);
                        }
                        else {
                            System.out.println("Не вдалося проаналізувати дату! Введіть правильну дату, будь ласка.");
                            changeTask();
                        }
                        logger.info("Task number\'" + strn + "\' was end date changed -> " + entry.getKey().getEndTime());
                        break;
                    case "5":
                        makeInterval(entry.getKey());
                        if(entry.getKey().isRepeated()) entry.getKey().setActive(true);
                        logger.info("Task number\'" + strn + "\' new interval");
                        break;
                    case "6":
                        System.out.println("Введіть 1/0 (активувати = 1, деактивувати = 0):");
                        str = view.keyboardReadWholeLn();
                        if (str.equals("1")) entry.getKey().setActive(true);
                        else entry.getKey().setActive(false);
                        logger.info("Task \'" + strn + "\' active = " + str);
                        break;
                    default:
                        System.out.println("Невірно вказаний пункт");
                        runStartMenu();
                }
                model = entry.getKey();
                acceptChanges();
                logger.info("Task \'" + model.getTitle() + "\' was changed in datafile");
                isChanged = true;
            }
        }
        if(!isChanged) {
            System.out.println("Задачу з такою назвою не знайдено!");
            changeTask();
        }
    }

    /**
     * Метод, який викликається для збереження всіх змін у файл.
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
     * Допоміжний метод до createTask(), який викликається у разі, якщо задача буде повторюватись.
     * @param task
     */
    private void makeInterval(Task task) {
        int days;
        int hours;
        int minutes;
        int seconds;
        int time = 0;
        String str;
        System.out.print("\nЗ якою частотою(день - 'd', година - 'h', хвилина - 'm', секунда - 's'): ");
        str = view.keyboardReadWholeLn();
        switch (str){
            case "d":
                days = 86400;
                time += days;
                break;
            case "h":
                hours = 3600;
                time += hours;
                break;
            case "m":
                minutes = 60;
                time += minutes;
                break;
            case "s":
                seconds = 1;
                time += seconds;
                break;

            default:
                System.out.println("Некоректно");
                runStartMenu();
                break;

        }
        task.setTime(task.getStartTime(), task.getEndTime(), time);
        task.setActive(true);
    }

    /**
     * Метод, який будує календар, відповідно до функціоналу:
     * 1 - На тиждень від сьогоднішнього дня
     * 2 - Задати власний проміжок часу
     */
    private void makeCalendar() {
        view.makeCalendarView();
        Scanner scan = new Scanner(System.in);
        System.out.print("Вибір -> ");
        String scanchar = scan.nextLine();
        switch (scanchar){
            case "1":
                SortedMap<LocalDateTime, Set<Task>> calendar7 = Tasks.calendar(list, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
                try{
                    view.printCalendar(calendar7);
                } catch (NullPointerException e){
                    System.out.println("Активних задач на цей проміжок часу немає");
                }
                break;
            case "2":
                System.out.print("\nВивести задачі, починаючи з (yyyy-MM-dd HH:mm): ");
                String start = view.keyboardReadWholeLn();
                System.out.print("\nВивести задачі, по дату (yyyy-MM-dd HH:mm): ");
                String end = view.keyboardReadWholeLn();
                try{
                    SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(list, LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter));
                    try{
                        view.printCalendar(calendar);
                    } catch (NullPointerException e){
                        System.out.println("Активних задач на цей проміжок часу немає");
                    }
                } catch (DateTimeParseException e){
                    System.out.println("Неправильний формат дати!");
                    makeCalendar();
                }
                break;
            case "3":
                SortedMap<LocalDateTime, Set<Task>> calendarm = Tasks.calendar(list, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10));
                try{
                    view.printCalendar(calendarm);
                } catch (NullPointerException e){
                    System.out.println("Активних задач на цей проміжок часу немає");
                }
                break;
            default:
                System.out.println("Невірно вказаний пункт");
        }
    }

}
