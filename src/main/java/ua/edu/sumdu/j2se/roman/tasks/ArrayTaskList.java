package ua.edu.sumdu.j2se.roman.tasks;


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayTaskList extends AbstractTaskList implements Iterable<Task>{

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

    public boolean remove(Task task) {
        for (int i = 0;i < size_now; i++){
            if (array[i].equals(task)){
                for (int j = i+1; j<size_now; j++) {
                    array[i] = array[j];
                    i++;
                }
                size_now--;
                return  true;
            }
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

    public Task getTask(int index) throws Throwable {
       try{
           return array[index];
       }
       catch(IndexOutOfBoundsException e){
           throw new IndexOutOfBoundsException();
        }
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

    @Override
    public Iterator<Task> iterator() {
        return new ArrayIterator(this);
    }

    static class ArrayIterator implements  Iterator<Task>{
        private ArrayTaskList array;
        private Task task;
        private int index = 0;
        public ArrayIterator(ArrayTaskList array){
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return index < array.size();
        }

        @Override
        public Task next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException("у списку не має наступного елемента");
            try {
                task = array.getTask(index++);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return task;
        }

        @Override
        public void remove() throws  IllegalStateException{
            if (task == null) throw new IllegalStateException("remove() без next() неможливо викликати");
            array.remove(task);
            index--;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayTaskList)) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        return size_all == that.size_all && Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size_all);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList arrayTaskList = (ArrayTaskList) super.clone();
        arrayTaskList.array = new Task[size_now];
        arrayTaskList.size_now = 0;
        for(Task a : array)
            arrayTaskList.add(a);
        return arrayTaskList;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "size_now=" + size_now +
                ", size_all=" + size_all +
                ", array=" + Arrays.toString(array) +
                '}';
    }

}





