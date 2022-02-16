package ua.edu.sumdu.j2se.roman.tasks.Model;

import ua.edu.sumdu.j2se.roman.tasks.Model.AbstractTaskList;
import ua.edu.sumdu.j2se.roman.tasks.Model.ArrayTaskList;
import ua.edu.sumdu.j2se.roman.tasks.Model.LinkedTaskList;
import ua.edu.sumdu.j2se.roman.tasks.Model.ListTypes;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type){
        if(type == ListTypes.types.ARRAY){
            return new ArrayTaskList();
        }
        if(type == ListTypes.types.LINKED){
            return new LinkedTaskList();
        }
        return null;
    }
}
