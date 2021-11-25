package ua.edu.sumdu.j2se.roman.tasks;

public class LinkedTaskList extends AbstractTaskList {
    private int size;   //розмір
    private Node head;

    private class Node {
        private Task data;  //вміст вузла
        private Node pNext; //посилання на наступний вузол

        public Node(Task data) {
            this.data = data;
            pNext = null;
        }
    }

    public void add(Task task){
        Node currentNode = head;    // поточний вузол
        if(head == null) { // якщо елементів не було, додай на верх списку
            head = new Node(task);
        }
        else { // якщо елементи були
            while (currentNode.pNext != null) {
                currentNode = currentNode.pNext;// посилання на наступний вузол
            }
            currentNode.pNext = new Node(task); // створюємо останній елемент
        }
        size++;
    }

    public Task getTask(int index) {
        int ind = 0;
        Node current = head;
        while (current != null) {
            if(ind == index){
                return current.data;
            }
            current = current.pNext;
            ind++;
        }
        return null;
    }

    public boolean remove(Task task){
        Node current = head;
        Node previous = null;
        boolean isremove = false;

        while (!current.data.getTitle().equals(task.getTitle())) {
            previous = current;
            current = current.pNext;
        }
        if (current == head) { // якщо потрібно видалити голову
            head = head.pNext; //тепер головою став наступний елемент
            isremove = true;
        }
        else {
            previous.pNext = current.pNext; // посилання попереднього тепер вказує на посилання цього об'єкту на наступний елемент
            isremove = true;
        }

        if(isremove){
            size--;
            return true;
        }

        return false;
    }

    public int size() {
        return size;
    }


    public LinkedTaskList incoming(int from, int to){
        LinkedTaskList listtime = new LinkedTaskList();
        Node listall = head;
        for (int i = 0; i < size; i++){
            if (listall.data.nextTimeAfter(from)!= -1 && listall.data.nextTimeAfter(from) <= to){
                listtime.add(listall.data);
            }
            listall = listall.pNext;
        }
        return listtime;
    }


}
