package ua.edu.sumdu.j2se.roman.tasks;

import java.util.Iterator;
import java.util.Objects;

abstract public class AbstractTaskList extends TaskListFactory  implements Cloneable, Iterable<Task>{
    public abstract void add(Task task);
    public abstract Task getTask(int index)  throws Throwable;
    public abstract int size();
    public abstract boolean remove(Task task);
    private ListTypes.types type;


    public AbstractTaskList incoming(int from, int to) throws Throwable{
        if(size() == 0) throw new Exception("Немає задач");
        AbstractTaskList list = new LinkedTaskList();
        for(int i = 0; i < size(); i++){
            Task task = getTask(i);
            if (task.nextTimeAfter(from)!= -1 && task.nextTimeAfter(from) <= to){
                    list.add(task);
            }
        }
        return list;
    }


    public abstract Iterator<Task> iterator();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTaskList)) return false;
        AbstractTaskList that = (AbstractTaskList) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }


}
