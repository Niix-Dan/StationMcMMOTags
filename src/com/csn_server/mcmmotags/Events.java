package com.csn_server.mcmmotags;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.util.player.UserManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author SrNox_
 */
public class Events implements Listener {
    private static HashMap<String, String> tags = new HashMap<>();
    
    private boolean filterSkill(String skill) {
        return (skill.equalsIgnoreCase("SALVAGE") && skill.equalsIgnoreCase("SMELTING"));
    }
    
    private static T_Config database = new T_Config(Main.getPlugin(Main.class), "database.yml");
    private static T_Config config = new T_Config(Main.getPlugin(Main.class), "config.yml");
    
    @EventHandler
    public void onLvlUp(McMMOPlayerLevelUpEvent e) {
        Player p = e.getPlayer();
        
        if(filterSkill(e.getSkill().getName())) return;
        
        if(e.getSkillLevel() > Util.getTopLevel(e.getSkill().name())) {
            if(Util.getTopPlayer(e.getSkill().name()).equalsIgnoreCase(p.getName())) return;
            
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(Config.newtop_chat_allow) {
                    player.sendMessage(Config.newtop_chat_message.replaceAll("%skill%", e.getSkill().getName()).replaceAll("%player%", e.getPlayer().getName()));
                }
                if(Config.newtop_sound_allow) {
                    player.playSound(p.getLocation(), Config.newtop_sound_sound, 1, 1);
                }
                if(Config.newtop_title_allow) {
                    player.sendTitle(Config.newtop_title_title.replaceAll("%skill%", e.getSkill().getName()).replaceAll("%player%", e.getPlayer().getName()), Config.newtop_title_subtitle.replaceAll("%skill%", e.getSkill().getName()).replaceAll("%player%", e.getPlayer().getName()));
                }
            }
            
            String oldTop = Util.getTopPlayer(e.getSkill().name()); // Pegando antigo player
            Util.setTop(e.getSkill().name(), p.getName(), e.getSkillLevel()); // Setando novo player
            
            checkTags(oldTop);
            checkTags(p.getName());
            
            database.getConfig().set("Skills." + e.getSkill().name() + ".Player", e.getPlayer().getName());
            database.getConfig().set("Skills." + e.getSkill().name() + ".Level", e.getSkillLevel());
            
            database.saveConfig();
        }
    }
    
    @EventHandler
    public void tag(ChatMessageEvent e) {
        Player p = e.getSender().getPlayer();
        
        if(e.getTags().contains("topskiller") && tags.containsKey(e.getSender().getName())) {
            e.setTagValue("topskiller", tags.get(e.getSender().getName()));
        }
    }
    
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        
        if(!Config.getNoTop().isEmpty()) {
            for(String str : Config.getSkills()) {
                if(!str.equalsIgnoreCase("SMELTING") && !str.equalsIgnoreCase("SALVAGE") && Config.getNoTop().contains(str)) {
                    if(UserManager.hasPlayerDataKey(e.getPlayer())) {
                        Util.setTop(str, p.getName(), ExperienceAPI.getLevel(p, str));
                        database.getConfig().set("Skills." + str + ".Level", ExperienceAPI.getLevel(e.getPlayer(), str));
                    } else {
                        Util.setTop(str, p.getName(), 0);
                        database.getConfig().set("Skills." + str + ".Level", 0);
                    }
                    
                    database.getConfig().set("Skills." + str + ".Player", e.getPlayer().getName());
                    database.saveConfig();
                
                    Config.getNoTop().remove(str);
                }
            }
        }
        checkTags(e.getPlayer().getName());
    }
    
    private static void checkTags(String p) {
        if(getPlayerTags(p) == null || getPlayerTags(p).isEmpty()) {
            if(tags.containsKey(p))
                tags.remove(p);
        } else {
            tags.put(p, getPlayerMasterTag(p, getPlayerTags(p)));
        }
    }
    
    
    private static String getPlayerMasterTag(String p, ArrayList<String> tag) {
        
        int num = 0;
        int maior = 0;
        
        for(String tag_name : tag) {
            if(Util.getTopLevel(tag_name) >= maior) {
                maior = Util.getTopLevel(tag_name);
                num = num+1;
            }
        }
        
        if(num >= tag.size()) num = tag.size()-1;
        
        return Config.getTag(tag.get(num), num);
    }
    
    
    private static ArrayList getPlayerTags(String playerName) {
        ArrayList<String> tags = new ArrayList<>();
        
        for(String skill : Config.getSkills()) {
            if(!skill.equalsIgnoreCase("smelting") && !skill.equalsIgnoreCase("salvage") && Util.getTopPlayer(skill).equalsIgnoreCase(playerName)) {
                tags.add(skill);
            }
        }
        
        if(tags.isEmpty())
            return null;
        
        return tags;
    }
    
}
