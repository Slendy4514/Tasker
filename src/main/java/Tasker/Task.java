/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public abstract class Task implements Runnable{
    protected volatile TimeTask executor;
    protected volatile Boolean on = false;
    Thread hilo;
    protected JSONObject args;
    
    public Task(JSONObject data){
        executor = TimeTask.fromJSON(data.optJSONObject("executor"));
        args = data.optJSONObject("args");
    }
    
    //Getters
    public TimeTask getExecutor(){
        return executor;
    }
    
    public String getArgs(){
        return args+"";
    }
    
    @Override
    public String toString(){
        return executor+" "+args;
    }
    
    //Funciones
    public void stop(){
        on = false;
    }
    
    public void start(){
        if(!on && executor.isTime()){
            on = true;
            hilo = new Thread(this);
            hilo.start();
        }
    }
    
    public abstract void forceStop();
    
    @Override
    public abstract void run();
    
}
