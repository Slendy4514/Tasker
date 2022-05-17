/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.synchronizedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public class Tasker implements Runnable {
    
    volatile boolean on;
    volatile boolean shutdown;
    //LocalDate start;
    Collection<Task> tasks = new ArrayList();
    
    
    public Tasker(){
        on = true;
    }
    
    public void load(Task T){
        tasks.add(T);
    }
    
    public void shutdown(){
        on = false;
    }
    
    private void startTasks(){
        for(Task task : tasks){
            task.start();
        }
    }
    
    private void shutdownTasks(){
        for(Task task : tasks){
            task.stop();
        }
    }
    
    public void start(){
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        shutdown = false;
        while (on){
            try {
                Thread.sleep(1000);
                startTasks();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        shutdownTasks();
        shutdown = true;
    }
    
    
}
