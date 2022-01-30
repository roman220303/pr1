package ua.edu.sumdu.j2se.roman.tasks;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.LocalDateTime;
import java.util.Iterator;

public class Main {

	private static final int T00 = 8097;

	public static void main(String[] args) throws Throwable {

		//Task task1 = new Task("b",2022-01-13,2022-01-13,2022-01-14,3600,false);
		LocalDateTime date1 = LocalDateTime.of(2014, 9, 19, 14, 5);
		LocalDateTime date2 = LocalDateTime.of(2014, 9, 19, 14, 5);
		LocalDateTime date3 = LocalDateTime.of(2014, 9, 19, 14, 5);
		LocalDateTime date6 = LocalDateTime.of(2014, 9, 19, 14, 10);
		LocalDateTime date7 = LocalDateTime.of(2014, 9, 19, 14, 10);
		LocalDateTime date8 = LocalDateTime.of(2014, 9, 19, 14, 10);
		Task task1 = new Task("a",date1);
		Task task2 = new Task("b",date2);
		Task task3 = new Task("c",date3);
		Task task4 = new Task("d",date6);
		Task task5 = new Task("e",date7);
		Task task6 = new Task("f",date8);
		LinkedTaskList list1 = new LinkedTaskList();
		list1.add(task1);
		list1.add(task2);
		list1.add(task3);
		list1.add(task4);
		list1.add(task5);
		list1.add(task6);
		for (Task task : list1) {
			System.out.println(task);
		}
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream(in);
		TaskIO.write(list1,out);
		LinkedTaskList result = new LinkedTaskList();
		TaskIO.read(result, in);
		System.out.println("----------------1------------------");
		for (Task task : result) {
			System.out.println(task);
		}

		System.out.println("----------------2------------------");

		LinkedTaskList actual = new LinkedTaskList();
		TaskIO.write(list1, new FileWriter("test.json"));
		TaskIO.read(actual, new FileReader("test.json"));

		for (Task task : actual) {
			System.out.println(task);
		}


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
