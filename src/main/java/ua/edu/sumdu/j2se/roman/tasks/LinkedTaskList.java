package ua.edu.sumdu.j2se.roman.tasks;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LinkedTaskList extends AbstractTaskList implements Iterable<Task> {

    private int size;   //розмір
    private Node head;

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "size=" + size +
                ", head=" + head +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;

        LinkedTaskList that = (LinkedTaskList) o;
        if (that.head == null && head == null) return true;
        while (head.pNext != null) {
            if (!(that.head.data.equals(head.data))) {
                return false;
            }
            head = head.pNext;
        }
        return head.data.equals(that.head.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size, head.data);
    }



    @Override
    public Iterator<Task> iterator() {
        return new LinkedIterator(this);
    }
    static class LinkedIterator implements  Iterator<Task>{
        private LinkedTaskList list;
        private Task task;
        private int index = 0;
        public LinkedIterator(LinkedTaskList list){
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public Task next() throws NoSuchElementException {
            if(hasNext()){
                task = list.getTask(index++);
                return task;
            }
            else throw new NoSuchElementException("у списку не має наступного елемента");
        }

        @Override
        public void remove() throws  IllegalStateException{
            if (task == null) throw new IllegalStateException("remove() без next() неможливо викликати");
            list.remove(task);
            index--;
        }
    }


    private class Node {
        private Task data;  //вміст вузла
        private Node pNext; //посилання на наступний вузол

        public Node(Task data) {
            this.data = data;
            pNext = null;
        }
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList list = (LinkedTaskList) super.clone();
        list.head = null;
        list.size = 0;
        for(Task a : this)
            list.add(a);
        return list;
    }

    @Override
    public Stream<Task> getStream() {
        Iterator<Task> taskIterator = this.iterator();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        taskIterator,
                        Spliterator.ORDERED),
                false
        );
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


    /*
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
    *
     */

}
