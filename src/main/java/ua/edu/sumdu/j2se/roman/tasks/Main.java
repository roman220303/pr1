package ua.edu.sumdu.j2se.roman.tasks;

import ua.edu.sumdu.j2se.roman.tasks.Controller.Controller;

public class Main {

	public static void main(String[] args){
		Controller controller = new Controller();
		controller.run();
		/*TreeMap<Integer, String> states = new TreeMap<Integer, String>();
		states.put(10, "Germany");
		states.put(2, "Spain");
		states.put(14, "France");
		states.put(3, "Italy");

		// получим объект по ключу 2
		String first = states.get(2);
		// перебор элементов
		for(Map.Entry<Integer, String> item : states.entrySet()){

			System.out.printf("Key: %d  Value: %s \n", item.getKey(), item.getValue());
		}
		LocalDateTime date1 = LocalDateTime.of(2014, 9, 19, 14, 5, 0);
		LocalDateTime date2 = LocalDateTime.of(2014, 9, 20, 14, 5, 0);
		LocalDateTime date3 = LocalDateTime.of(2014, 9, 21, 14, 5, 0);
		LocalDateTime date6 = LocalDateTime.of(2014, 9, 22, 14, 10, 0);
		LocalDateTime date7 = LocalDateTime.of(2014, 9, 25, 14, 10, 0);
		LocalDateTime date8 = LocalDateTime.of(2014, 9, 26, 14, 10, 0);
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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();
		for (Task task: list1) {
			LocalDateTime holder = task.nextTimeAfter(LocalDateTime.parse("1999-01-01 10:00:00",formatter)); //start time
			System.out.println(holder);
			while (holder != null && !holder.isAfter(LocalDateTime.parse("2022-01-01 10:00:00",formatter))){
				System.out.println("while");
				if(calendar.containsKey(holder)){
					calendar.get(holder).add(task); //якщо дата вже є, додаємо задачу
					System.out.println("if");
				}
				else{ // якщо дати ще немає, створюємо її та додаєм в неї задачу
					Set<Task> taskSet = new HashSet<>();
					taskSet.add(task);
					calendar.put(holder, taskSet);
					System.out.println("else");
				}
				holder = task.nextTimeAfter(holder);
			}
		}

		for(Map.Entry<LocalDateTime, Set<Task>> item : calendar.entrySet()){

			System.out.printf("Key: %d  Value: %s \n", item.getKey(), item.getValue());
		}*/

		//Task task1 = new Task("b",2022-01-13,2022-01-13,2022-01-14,3600,false);
		/*
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
		}*/


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
