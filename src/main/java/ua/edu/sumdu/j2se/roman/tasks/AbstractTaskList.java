package ua.edu.sumdu.j2se.roman.tasks;

abstract public class AbstractTaskList extends TaskListFactory {
    public abstract void add(Task task);
    public abstract Task getTask(int index)  throws Throwable;
    public abstract int size();
    public abstract boolean remove(Task task);
    private ListTypes.types type;

    public AbstractTaskList incoming(int from, int to) throws Throwable{
        if(size() == 0) throw new Exception("Немає задач");
        AbstractTaskList list = AbstractTaskList.createTaskList(type);
        for(int i = 0; i < size(); i++){
            Task task = getTask(i);
            if (task.nextTimeAfter(from)!= -1 && task.nextTimeAfter(from) <= to){
                    list.add(task);
            }
        }
        return list;
    }

}
