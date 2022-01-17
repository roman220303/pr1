package ua.edu.sumdu.j2se.roman.tasks;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) throws Throwable {
		/*boolean a;
		boolean l;
		*//*Task task1 = new Task("greg",50);
		Task task2 = new Task("grgre",45);
		Task task3 = new Task("regre",32);*//*
		LinkedTaskList list1 = new LinkedTaskList();
		list1.add(task1);
		list1.add(task2);
		list1.add(task3);
		ArrayTaskList array1 = new ArrayTaskList();
		array1.add(task1);
		array1.add(task2);
		array1.add(task3);



		System.out.println("-----------array1-----------");

		for (Iterator <Task> iterator = array1.iterator(); iterator.hasNext();)
		{

			Task task = iterator.next();
			System.out.println(task);
		}

		System.out.println("-----------copy-----------");

		ArrayTaskList copy = (ArrayTaskList) array1.clone();

		for (Iterator <Task> iterator = copy.iterator(); iterator.hasNext();)
		{
			Task task = iterator.next();
			System.out.println(task);
		}

		System.out.println("---------remove------------");
		System.out.println(array1.getTask(0) + " before");
		copy.remove(array1.getTask(0));
		System.out.println(array1.getTask(0) + " after");
		System.out.println("---------remove------------");


		System.out.println("---------remove------------");
		System.out.println(copy.remove(copy.getTask(0)));
		System.out.println("---------remove------------");

		System.out.println("---------array1_after_remove------------");

		for (Task task : array1) {

			System.out.println(task);
		}

		System.out.println("---------removecopy------------");


		for (Iterator <Task> iterator = copy.iterator(); iterator.hasNext();)
		{
			Task task = iterator.next();
			System.out.println(task);
		}*/

	}

}
