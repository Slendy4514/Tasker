/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Predicate;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public abstract class TimeTask {
    
    public enum TimeType{
        Dated,Yearly,Monthly,Weekly,Daily;
    }
    
    TimeType timeUnit;
    TimeTask subTime;
    Predicate<LocalDateTime> condition;
    JSONObject args;
    
    public static TimeTask fromJSON(JSONObject data){
        TimeType type = data.optEnum(TimeType.class, "type");
        switch (type){
            case Dated:
                return new Dated(data);
            case Daily:
                return new Daily(data);
            case Weekly:
                return new Weekly(data);
            case Yearly:
                return new Yearly(data);
        }
        return null;
    }
    
    public TimeTask(JSONObject data){
        timeUnit = data.optEnum(TimeType.class, "type", TimeType.Dated);
        subTime = (data.isNull("subTime")) ? null : fromJSON(data.optJSONObject("subTime"));
        args = data;
        
    }
    
    public static TimeTask load(){
        return null;
    }
    
    public boolean isTime(){
        return isTime(LocalDateTime.now());
    }
    
    public boolean isTime(LocalDateTime dateTime){
        return (subTime != null) ? condition.test(dateTime) && subTime.isTime(dateTime):condition.test(dateTime);
    }
    
    public boolean isToday(){
        LocalDateTime DT = LocalDateTime.now().withHour(23).withMinute(59);
        return (subTime != null && subTime.timeUnit != TimeType.Daily) ? 
                condition.test(DT) && subTime.isToday():(this.timeUnit == TimeType.Daily) ?
                    true:condition.test(DT);
    }
    
    public static LocalDateTime getTime(long miliseconds){
        return LocalDateTime.ofEpochSecond(miliseconds/1000, 0, ZoneOffset.UTC);
    }
    
    @Override
    public String toString(){
        return this.args.toString();
    }
}
