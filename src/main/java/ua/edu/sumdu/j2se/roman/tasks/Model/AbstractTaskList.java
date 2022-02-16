package ua.edu.sumdu.j2se.roman.tasks.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

abstract public class AbstractTaskList extends TaskListFactory implements Iterable<Task>, Serializable {
    public abstract Stream<Task> getStream();
    public abstract void add(Task task);
    public abstract Task getTask(int index)  throws Throwable;
    public abstract int size();
    public abstract boolean remove(Task task);
    private ListTypes.types type;


    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) throws Throwable{
        if(size() == 0) throw new Exception("Немає задач");
        AbstractTaskList list = new LinkedTaskList();
        getStream().filter(task ->task.getEndTime().isAfter(from) && task.getEndTime().isBefore(to)).forEach(list::add);
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


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
