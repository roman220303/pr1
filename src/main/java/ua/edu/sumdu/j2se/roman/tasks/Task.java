package ua.edu.sumdu.j2se.roman.tasks;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDateTime;

/**
 * The Task class implements the functionality
 *of the task manager with repetitive and non-repetitive tasks
 * @version 1.0
 * @author roman
 */
public class Task implements Cloneable, Serializable {
    private String title;           //назва задачі
    private LocalDateTime time;     //час виконання задачі
    private LocalDateTime start;    //час, коли починається задача
    private LocalDateTime end;      //час, коли закінчується задача
    private int interval;           //інтервал виконання завдання
    private boolean active;         //чи активна задача
    private boolean isRepeated;     //чи повторюється задача

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return time == task.time && start == task.start && end == task.end && interval == task.interval && active == task.active && isRepeated == task.isRepeated && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active, isRepeated);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                ", isRepeated=" + isRepeated +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     *неактивна задачу, яка
     * виконується у заданий час без повторення із заданою назвою
     * @param title ert
     * @param time ert
     */
    public Task(String title, LocalDateTime time){
        this.title = title;
        if(time == null) throw new IllegalArgumentException(time + " < 0");
        else this.time = time;
        active = false;
        isRepeated = false;
    }

    /**
     *неактивна задача, яка виконується у заданому проміжку часу (і початок і кінець включно) із
     * заданим інтервалом і має задану назву.
     * @param title ert
     * @param start ert
     * @param end ert
     * @param interval ert
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval){
        this.title = title;
        if(start == null || end == null || interval < 0) throw new IllegalArgumentException(time + " < 0");
        else{
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
        active = false;
        isRepeated = true;
    }

    /**
     * гетер для виведення назви задачі
     * @return ert
     */
    public String getTitle(){
        return title;
    }

    /**
     * сетер для введення назви задачі
     * @param title ert
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * гетер для виведення стану задачі
     * @return ert
     */
    public boolean isActive() {
        return active;
    }

    /**
     * сетер для встановлення стану задачі
     * @param active ert
     */
    public void setActive(boolean active){
        this.active = active;
    }

    /**
     * якщо задача повторюється метод має повертати час початку
     * повторення
     * @return ert
     */
    public LocalDateTime getTime(){
        if(isRepeated) return start;
        else return time;
    }

    /**
     * якщо задача повторювалась, вона має стати такою,
     * що не повторюється
     * @param time ert
     */
    public void setTime(LocalDateTime time){
        if(isRepeated) isRepeated = false;
        this.time = time;
    }

    /**
     * якщо задача повторювалась, вона має стати такою,
     * що не повторюється
     * @return ert
     */
    public LocalDateTime getStartTime(){
        if(!isRepeated) return time;
        return start;
    }

    /**
     * якщо задача не повторюється метод має повертати час
     * виконання задачі;
     * @return ert
     */
    public LocalDateTime getEndTime(){
        if(!isRepeated) return time;
        else return end;
    }

    /**
     * у разі, якщо задача не повторюється метод має
     * повертати 0
     * @return ert
     */
    public int getRepeatInterval(){
        if(!isRepeated) return interval = 0;
        return interval;
    }

    /**
     * якщо задача не
     * повторювалася метод має стати такою, що повторюється
     * @param start ert
     * @param end ert
     * @param interval ert
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval){
        if (start == null) {
            throw new IllegalArgumentException("Вказаний початковий час менше 0");
        }
        else if (end == null) {
            throw new IllegalArgumentException("Вказаний кінцевий час менше 0");
        }
        else if (start.isAfter(end)){
            throw new IllegalArgumentException("Початковий час пізніше кінцевого часу");
        }
        if (!isRepeated){
            isRepeated = true;
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     * Метод для перевірки повторюваності задачі
     * @return ert
     */
    public boolean isRepeated(){
        return isRepeated;

    }

    /**
     * метод, що повертає час наступного виконання задачі
     * після вказаного часу current, якщо після вказаного часу задача не виконується, то
     * метод має повертати -1.
     * @param current
     * @return
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current){
        if (current == null) return  null;
        if (!active){ // якщо після вказаного часу задача не виконується, то метод має повертати -1
            return null;
        }
        if (!isRepeated){
            if (current.isBefore(time)) {
                return time; // ще не виконалась
            }
            else {
                return null; // виконалась
            }
        }
        else{ // якщо задача є активною і повторюється
            if (current.isBefore(start)) {
                return start; // початок
            }
            for (LocalDateTime i = start; !i.isAfter(end);)  //задача є активною і повторюється
            {
                if (current.isBefore(i)) return  i;
                i = i.plusSeconds(interval);
            }
        }
        return  null;
    }
}