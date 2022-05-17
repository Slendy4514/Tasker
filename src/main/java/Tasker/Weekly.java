/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public class Weekly extends TimeTask {
    
    public Weekly(JSONObject data) {
        super(data);
        timeUnit = TimeType.Weekly;
        condition = daysPred();
    }
    
    private Predicate<LocalDateTime> daysPred(){
        JSONArray days = args.getJSONArray("days");
        ArrayList<DayOfWeek> daysList = new ArrayList();
        for (int i = 0; i < days.length(); i++) {
            daysList.add(days.optEnum(DayOfWeek.class, i));
        }
        return time -> daysList.contains(time.getDayOfWeek());
    }
    
}
