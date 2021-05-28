package com.csn_server.mcmmotags;

import java.util.HashMap;
import org.bukkit.entity.Player;

/**
 *
 * @author SrNox_
 */
public class Util {
    
    private static HashMap<String, HashMap<String, Integer>> tops = new HashMap<>();
    
    
    public static String getTopPlayer(String skill) {
        return tops.get(skill).keySet().toArray()[0].toString();
    }
    
    public static int getTopLevel(String skill) {
        return tops.get(skill).get(getTopPlayer(skill)).intValue();
    }
    
    public static void setTop(String skill, String player, int level) {
        if(tops.containsKey(skill)) {
            tops.get(skill).clear();
            tops.get(skill).put(player, level);
        } else {
            HashMap<String, Integer> a = new HashMap();
            a.put(player, level);
            tops.put(skill, a);
        }
    }
    
}
