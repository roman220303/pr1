package ua.edu.sumdu.j2se.Roman.tasks;

/**
 * The Task class implements the functionality
 *of the task manager with repetitive and non-repetitive tasks
 * @version 1.0
 * @author Roman
 */
public class Task {
    private String title;            //назва задачі
    private int time;               //час виконання задачі
    private int start;              //час, коли починається задача
    private int end;                //час, коли закінчується задача
    private int interval;           //інтервал виконання завдання
    private boolean active;         //чи активна задача
    private boolean isRepeated;         //чи повторюється задача

    /**
     *неактивна задачу, яка
     * виконується у заданий час без повторення із заданою назвою
     * @param title ert
     * @param time ert
     */
    public Task(String title, int time){
        this.title = title;
        this.time = time;
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
    public Task(String title, int start, int end, int interval){
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
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
    public int getTime(){
        if(isRepeated == true) return start;
        else return time;
    }

    /**
     * якщо задача повторювалась, вона має стати такою,
     * що не повторюється
     * @param time ert
     */
    public void setTime(int time){
        if(isRepeated == true) isRepeated = false;
        this.time = time;
    }

    /**
     * якщо задача повторювалась, вона має стати такою,
     * що не повторюється
     * @return ert
     */
    public int getStartTime(){
        if(isRepeated == false) return time;
        return start;
    }

    /**
     * якщо задача не повторюється метод має повертати час
     * виконання задачі;
     * @return ert
     */
    public int getEndTime(){
        if(isRepeated == false) return time;
        else return end;
    }

    /**
     * у разі, якщо задача не повторюється метод має
     * повертати 0
     * @return ert
     */
    public int getRepeatInterval(){
        if(isRepeated == false) return interval = 0;
        return interval;
    }

    /**
     * якщо задача не
     * повторювалася метод має стати такою, що повторюється
     * @param start ert
     * @param end ert
     * @param interval ert
     */
    public void setTime(int start, int end, int interval){
        if(isRepeated == false) isRepeated = true;
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
     * метод, що повертає час наступного
     * виконання задачі після вказаного часу current, якщо після вказаного часу задача не виконується, то
     * метод має повертати -1.
     * @param current
     * @return
     */
    public int nextTimeAfter(int current){
        if(active == false) { // якщо після вказаного часу задача не виконується, то метод має повертати -1
            return -1;
        }
        if(isRepeated == false) {
            if(current < time) {
                return time; // цей момент
            }
            else {
                return -1; // ніколи
            }

        }
        else{
            if(current < start) {
                return start; // початок
            }
            for(int i = start; i <= end; i+= interval){ //задача є активною і повторюється
                if(current < i) return i;
            }
        }

        return -1;
    }
}
