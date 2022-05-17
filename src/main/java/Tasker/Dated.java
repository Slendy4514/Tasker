/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tasker;

import java.time.LocalDateTime;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public class Dated extends TimeTask {
    
    
    
    public Dated(JSONObject data) {
        super(data);
        timeUnit = TimeType.Dated;
        LocalDateTime at = getTime(args.optLong("at"));
        condition = time -> time.isAfter(at) || time.isEqual(at);
    }
    
}
