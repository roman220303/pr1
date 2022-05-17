package ua.edu.sumdu.j2se.roman.tasks.model;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {

    /**
     * Метод, який записує задачі із списку у
     * потік у бінарному форматі, описаному нижче.
     * @param tasks
     * @param out
     */
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

    /**
     * Метод, який зчитує задачі із потоку у даний
     * список задач.
     * @param tasks
     * @param in
     */
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

    /**
     * Метод, який записує задачі із списку у файл.
     * @param tasks
     * @param file
     * @throws Throwable
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws Throwable {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            write(tasks,bufferedWriter);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Метод, який зчитує задачі із файлу у список задач.
     * @param tasks
     * @param file
     */
    public static void readBinary(AbstractTaskList tasks, File file){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            read(tasks,bufferedReader);
        } catch (IOException e){
            System.out.println("Проблеми з зчитуванням файлу");
            e.printStackTrace();
        }
    }

    /**
     * Метод, який  записує задачі зі списку у потік в
     * форматі JSON.
     * @param tasks
     * @param out
     * @throws IOException
     */
    public static void write(AbstractTaskList tasks, Writer out) throws IOException{
        Gson gsonwrite = new Gson();
        gsonwrite.toJson(tasks,out);
        out.flush();

    }

    /**
     * Метод, який зчитує задачі із потоку у список.
     * @param tasks
     * @param in
     * @throws IOException
     */
    public static void read(AbstractTaskList tasks, Reader in) throws IOException{
        Gson gsonread = new Gson();
        AbstractTaskList taskList = gsonread.fromJson(in,tasks.getClass());
        for (Task task: taskList) {
            tasks.add(task);
        }

    }

    /**
     * Метод, який записує задачі у файл у форматі
     * JSON
     * @param tasks
     * @param file
     */
    public static void writerText(AbstractTaskList tasks, File file){
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
            write(tasks,fileOutputStream);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Метод, який зчитує задачі із файлу.
     * @param tasks
     * @param file
     */
    public static void readText(AbstractTaskList tasks, File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        try {
            read(tasks, bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferedReader.close();
    }

}
