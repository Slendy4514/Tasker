/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import static Tasker.TimeTask.getTime;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public class Monthly extends TimeTask{
    
    public Monthly(JSONObject data) {
        super(data);
        Predicate<LocalDateTime> on = onPred();
        Predicate<LocalDateTime> the = thePred();
        condition = on.or(the);
    }
    
    private Predicate<LocalDateTime> onPred(){
        JSONArray on = args.optJSONArray("on");
        if(on == null) return time -> false;
        ArrayList<Integer> days = new ArrayList();
        for (int i = 0; i<on.length(); i++){
            days.add(on.optInt(i));
        }
        return time -> days.contains(time.getDayOfMonth());
    }
    
    private Predicate<LocalDateTime> thePred(){
        Predicate<LocalDateTime> Pred = null;
        JSONArray the = args.optJSONArray("the");
        if(the == null) return time -> false;
        for (int i = 0; i<the.length(); i++){
            JSONArray pos_day = the.optJSONArray(i);
            int pos = pos_day.optInt(0);
            DayOfWeek day = pos_day.getEnum(DayOfWeek.class,1);
           Predicate<LocalDateTime> subPred = time -> time.getDayOfWeek() == day &&
                   pos == Math.ceil((double)time.getDayOfMonth()/7);
           Pred = (Pred != null) ? Pred.or(subPred):subPred;
        }
        return Pred;    
    }
    
}
