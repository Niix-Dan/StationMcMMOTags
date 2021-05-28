package com.csn_server.mcmmotags;

import com.gmail.nossr50.datatypes.skills.SkillType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

/**
 *
 * @author SrNox_
 */
public class Config {
    public static String newtop_chat_message, 
            newtop_title_title, 
            newtop_title_subtitle,
            newtop_sound_sound;
    public static boolean newtop_chat_allow, 
            newtop_title_allow, 
            newtop_sound_allow;
    
    
    public static ArrayList<String> swordstag = new ArrayList();
    public static ArrayList<String> unarmedtag = new ArrayList();
    public static ArrayList<String> repairtag = new ArrayList();
    public static ArrayList<String> herbalismtag = new ArrayList();
    public static ArrayList<String> excavationtag = new ArrayList();
    public static ArrayList<String> axestag = new ArrayList();
    public static ArrayList<String> acrobaticstag = new ArrayList();
    public static ArrayList<String> alchemytag = new ArrayList();
    public static ArrayList<String> archerytag = new ArrayList();
    public static ArrayList<String> fishingtag = new ArrayList();
    public static ArrayList<String> miningtag = new ArrayList();
    public static ArrayList<String> tamingtag = new ArrayList();
    public static ArrayList<String> woodcuttingtag = new ArrayList();
    
    
    private static ArrayList<String> noTop = new ArrayList<>();
    private static List<String> skills = new ArrayList<>();
    
    private static T_Config database = new T_Config(Main.getPlugin(Main.class), "database.yml");
    private static T_Config config = new T_Config(Main.getPlugin(Main.class), "config.yml");
    
    public static void carregar() {
        
        newtop_chat_message = config.getConfig().getString("NewTop.Chat.Message").replaceAll("&", "§");
        newtop_chat_allow = config.getConfig().getBoolean("NewTop.Chat.Allow");
        
        newtop_sound_allow = config.getConfig().getBoolean("NewTop.Sound.Allow");
        newtop_sound_sound = config.getConfig().getString("NewTop.Sound.Sound");
        
        newtop_title_allow = config.getConfig().getBoolean("NewTop.Title.Allow");
        newtop_title_title = config.getConfig().getString("NewTop.Title.Title").replaceAll("&", "§");
        newtop_title_subtitle = config.getConfig().getString("NewTop.Title.Subtitle").replaceAll("&", "§");
        
        
        swordstag = (ArrayList<String>) config.getConfig().getStringList("Skills.SWORDS.Tags");
        unarmedtag = (ArrayList<String>) config.getConfig().getStringList("Skills.UNARMED.Tags");
        acrobaticstag = (ArrayList<String>) config.getConfig().getStringList("Skills.ACROBATICS.Tags");
        axestag = (ArrayList<String>) config.getConfig().getStringList("Skills.AXES.Tags");
        alchemytag = (ArrayList<String>) config.getConfig().getStringList("Skills.ALCHEMY.Tags");
        archerytag = (ArrayList<String>) config.getConfig().getStringList("Skills.ARCHERY.Tags");
        fishingtag = (ArrayList<String>) config.getConfig().getStringList("Skills.FISHING.Tags");
        repairtag = (ArrayList<String>) config.getConfig().getStringList("Skills.REPAIR.Tags");
        miningtag = (ArrayList<String>) config.getConfig().getStringList("Skills.MINING.Tags");
        tamingtag = (ArrayList<String>) config.getConfig().getStringList("Skills.TAMING.Tags");
        woodcuttingtag = (ArrayList<String>) config.getConfig().getStringList("Skills.WOODCUTTING.Tags");
        herbalismtag = (ArrayList<String>) config.getConfig().getStringList("Skills.HERBALISM.Tags");
        excavationtag = (ArrayList<String>) config.getConfig().getStringList("Skills.EXCAVATION.Tags");
        
        skills = getListFromEnum(Arrays.asList(SkillType.values()));
        
        for(String str : getSkills()) {
            if(str.equalsIgnoreCase("smelting") || str.equalsIgnoreCase("salvage"))
                continue;
            if(database.getConfig().getString("Skills."+str+".Player") == null) {
                noTop.add(str);
                continue;
            }
            Util.setTop(str, database.getConfig().getString("Skills." + str + ".Player"), database.getConfig().getInt("Skills." + str + ".Level"));
        }
        
    }
  public static ArrayList<String> getSwordstag() {
    return swordstag;
  }
  
  public static ArrayList<String> getUnarmedtag() {
    return unarmedtag;
  }
  
  public static ArrayList<String> getRepairtag() {
    return repairtag;
  }
  
  public static ArrayList<String> getHerbalismtag() {
    return herbalismtag;
  }
  
  public static ArrayList<String> getExcavationtag() {
    return excavationtag;
  }
  
  public static ArrayList<String> getAxestag() {
    return axestag;
  }
  
  public static ArrayList<String> getNoTop() {
    return noTop;
  }
  
  public static String getTag(String str, int tops) {
      if(tops < 0) tops = 0;
    
    switch (str.toLowerCase()) {
      case "acrobatics":
        return getAcrobaticstag().get(tops >= getAcrobaticstag().size() ? getAcrobaticstag().size()-1 : tops);
      case "swords":
        return getSwordstag().get(tops >= getSwordstag().size() ? getSwordstag().size()-1 : tops);
      case "unarmed":
        return getUnarmedtag().get(tops >= getUnarmedtag().size() ? getUnarmedtag().size()-1 : tops);
      case "repair":
        return getRepairtag().get(tops >= getRepairtag().size() ? getRepairtag().size()-1 : tops);
      case "herbalism":
        return getHerbalismtag().get(tops >= getHerbalismtag().size() ? getHerbalismtag().size()-1 : tops);
      case "excavation":
        return getExcavationtag().get(tops >= getExcavationtag().size() ? getExcavationtag().size()-1 : tops);
      case "axes":
        return getAxestag().get(tops >= getAxestag().size() ? getAxestag().size()-1 : tops);
      case "alchemy":
        return getAlchemytag().get(tops >= getAlchemytag().size() ? getAlchemytag().size()-1 : tops);
      case "archery":
        return getArcherytag().get(tops >= getArcherytag().size() ? getArcherytag().size()-1 : tops);
      case "fishing":
        return getFishingtag().get(tops >= getFishingtag().size() ? getFishingtag().size()-1 : tops);
      case "mining":
        return getMiningtag().get(tops >= getMiningtag().size() ? getMiningtag().size()-1 : tops);
      case "taming":
        return getTamingtag().get(tops >= getTamingtag().size() ? getTamingtag().size()-1 : tops);
      case "woodcutting":
        return getWoodcuttingtag().get(tops >= getWoodcuttingtag().size() ? getWoodcuttingtag().size()-1 : tops);
    } 
    return null;
  }
  public static ArrayList<String> getAcrobaticstag() {
    return acrobaticstag;
  }
  
  public static ArrayList<String> getAlchemytag() {
    return alchemytag;
  }
  
  public static ArrayList<String> getArcherytag() {
    return archerytag;
  }
  
  public static ArrayList<String> getFishingtag() {
    return fishingtag;
  }
  
  public static ArrayList<String> getMiningtag() {
    return miningtag;
  }
  
  public static ArrayList<String> getTamingtag() {
    return tamingtag;
  }
  
  public static ArrayList<String> getWoodcuttingtag() {
    return woodcuttingtag;
  }
  
  public static List<String> getSkills() {
    return skills;
  }
  private static List<String> getListFromEnum(List<SkillType> skillsTypes) {
    List<String> skills = new ArrayList<>();
    for (SkillType skillType : skillsTypes)
      skills.add(skillType.name()); 
    return skills;
  }
}

