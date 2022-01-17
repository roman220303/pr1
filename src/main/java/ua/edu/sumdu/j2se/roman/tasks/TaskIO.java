package ua.edu.sumdu.j2se.roman.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream out2 = new ObjectOutputStream(out)) {
            out2.writeInt(tasks.size());
            for (Task task : tasks) {
                out2.writeInt(task.getTitle().length());
                out2.writeUTF(task.getTitle());
                out2.writeBoolean(task.isActive());
                out2.writeInt(task.getRepeatInterval());
                if(task.isRepeated()){
                    out2.writeInt(task.getStartTime().getNano());
                    out2.writeInt(task.getEndTime().getNano());
                }
                else{
                    out2.writeInt(task.getTime().getNano());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (ObjectInputStream in2 = new ObjectInputStream(in)) {
            int num = in2.readInt();
            for (int i = 0; i < num; i++) {
                int sizetitle = in2.readInt();
                String title = in2.readUTF();
                boolean isActive = in2.readBoolean();
                int interval = in2.readInt();
                if (interval != 0) {
                    LocalDateTime start = LocalDateTime.ofEpochSecond(in2.readInt(),0, ZoneOffset.UTC);
                    LocalDateTime end = LocalDateTime.ofEpochSecond(in2.readInt(),0, ZoneOffset.UTC);
                    Task task1 = new Task(title, start, end, interval);
                    task1.setActive(isActive);
                    tasks.add(task1);

                } else {
                    LocalDateTime time = LocalDateTime.ofEpochSecond(in2.readInt(),0,ZoneOffset.UTC);
                    Task task1 = new Task(title,time);
                    task1.setActive(isActive);
                    tasks.add(task1);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeBinary(AbstractTaskList tasks, File file) throws Throwable {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            write(tasks,bufferedWriter);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void readBinary(AbstractTaskList tasks, File file){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            read(tasks,bufferedReader);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) throws IOException{
        Gson gsonwrite = new Gson();
        gsonwrite.toJson(tasks,out);
        out.flush();

    }
    public static void read(AbstractTaskList tasks, Reader in) throws IOException{
        Gson gsonread = new Gson();
        AbstractTaskList taskList = gsonread.fromJson(in,tasks.getClass());
        for (Task task: taskList) {
            tasks.add(task);
        }

    }

    public static void writerText(AbstractTaskList tasks, File file){
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
            write(tasks,fileOutputStream);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void readText(AbstractTaskList tasks, File file){
        try(FileInputStream fileInputStream = new FileInputStream(file)){
            read(tasks,fileInputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
