/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public class Daily extends TimeTask{
    
    public Daily(JSONObject data) {
        super(data);
        timeUnit = TimeType.Daily;
        Predicate<LocalDateTime> at = atPred();
        Predicate<LocalDateTime> from = fromPred();
        condition = at.or(from);
    }
    
    private Predicate<LocalDateTime> atPred(){
        Predicate<LocalDateTime> Pred = null;
        JSONArray at = args.optJSONArray("at");
        if(at == null) return time -> false;
        for (int i = 0; i<at.length(); i++){
            LocalDateTime ldt = getTime(at.optLong(i));
            Predicate<LocalDateTime> subPred = time -> time.toLocalTime().getHour() == ldt.getHour() && ldt.getMinute() == time.toLocalTime().getMinute();
            Pred = (Pred != null) ? Pred.or(subPred):subPred;
        }
        return Pred;
    }
    
    private Predicate<LocalDateTime> fromPred(){
        Predicate<LocalDateTime> Pred = null;
        JSONArray from = args.optJSONArray("from");
        if(from == null) return time -> false;
        for (int i = 0; i<from.length(); i++){
            JSONArray from_to = from.optJSONArray(i);
            LocalTime begin = getTime(from_to.optLong(0)).toLocalTime();
            LocalTime end = getTime(from_to.optLong(1)).toLocalTime();
           Predicate<LocalDateTime> subPred = time -> time.toLocalTime().isAfter(begin) 
                   && time.toLocalTime().isBefore(end);
            Pred = (Pred != null) ? Pred.or(subPred):subPred;
        }
        return Pred;
    }
    
}
