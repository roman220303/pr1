package ua.edu.sumdu.j2se.roman.tasks;

public class ArrayTaskList {
    private int size_now; // кількість елементів, після додавання нової задачі
    private int size_all = 1; // вмістимість масиву
    private Task[] array = new Task[size_all];

    /**
     * метод, що додає до списку вказану задачу
     * @param task
     */

    public void add(Task task){
        if(size_all == size_now){ // якщо вмістимості масиву невистачає, доповнимо його на один елемент
            array = new_array();
        }
        array[size_now] = task;
        size_now++;
    }

    private Task []new_array(){
        size_all += 1;
        Task[] newArray = new Task[size_all];
        System.arraycopy(array, 0, newArray, 0, size_now);
        return newArray;
    }

    /**
     * метод, що видаляє задачу зі списку і повертає істину, якщо така задача була у списку
     * @param task
     * @return
     */

    public boolean remove(Task task){
        boolean isremove = false;
        int remove = 0;
        for(int i = 0; i < size_now; i++) {
            if(array[i].getTitle().equals(task.getTitle())){
                remove = i;
                isremove = true;
            }
        }

        if(isremove) {
            System.arraycopy(array, remove + 1, array, remove, size_now - 1 - remove);
            size_now--;
            return true;
        }
        return false;
    }

    /**
     * метод, що повертає кількість задач у списку
     * @return size_now
     */

    public int size(){
        return size_now;
    }

    /**
     * метод, що повертає задачу, яка знаходиться на вказаному місці у списку
     * @param index
     * @return array[index]
     */

    public Task getTask(int index) {
        return array[index];
    }

    /**
     * метод, що повертає
     * підмножину задач, які заплановані на виконання хоча б раз після часу from і не пізніше ніж to
     * @param from
     * @param to
     * @return arraytime
     */

    public ArrayTaskList incoming(int from, int to){
        ArrayTaskList arraytime = new ArrayTaskList();
        for(int i = 0; i < size_now; i++){
            if (array[i].nextTimeAfter(from)!= -1 && array[i].nextTimeAfter(from) <= to){
                arraytime.add(array[i]);
            }
        }
        return arraytime;
    }

}



