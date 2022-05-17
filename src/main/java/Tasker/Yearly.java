/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public class Yearly extends TimeTask{
    
    public Yearly(JSONObject data) {
        super(data);
        timeUnit = TimeType.Yearly;
        condition = monthsPred();
    }
    
    private Predicate<LocalDateTime> monthsPred(){
        JSONArray days = args.getJSONArray("months");
        ArrayList<Month> monthsList = new ArrayList();
        for (int i = 0; i < days.length(); i++) {
            monthsList.add(days.optEnum(Month.class, i));
        }
        return time -> monthsList.contains(time.getMonth());
    }
    
}
