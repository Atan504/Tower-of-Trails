package ToT.ClassManagement;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Skill {

    Player p;
    String SkillName;
    String SkillStatus="Normal";
    List<String> SkillSubStatusList=new ArrayList<>();
    int SkillCooldown;
    int SkillManaCost;
    int SkillLevel;
    Runnable SkillCode;


    public Skill(String skillname, int skillLevel, int skillCooldown, int skillManaCost, Runnable skillCode, boolean active_inactive_switch){
        SkillName=skillname;
        SkillLevel = skillLevel;
        SkillCooldown=skillCooldown;
        SkillManaCost=skillManaCost;
        SkillCode=skillCode;
        if (active_inactive_switch){
            SkillSubStatusList.add("Inactive");
        }else{
            SkillSubStatusList.add("Normal");
        }
    }

    public String getSkillName() {
        return SkillName;
    }
    public String getSkillStatus() {
        return SkillStatus;
    }
    public List<String> getSkillSubStatus() {
        return SkillSubStatusList;
    }
    public int getSkillCooldown() {
        return SkillCooldown;
    }

    public int getSkillManaCost() {
        return SkillManaCost;
    }
    public int getSkillLevel() {
        return SkillLevel;
    }

    public Runnable getSkillCode() {
        return SkillCode;
    }

    public void setSkillLevel(int skilllevel) {
        SkillLevel = skilllevel;
    }

    public void setSkillCode(Runnable skillCode) {
        SkillCode = skillCode;
    }

    public void setSkillCooldown(int skillCooldown) {
        SkillCooldown = skillCooldown;
    }

    public void setSkillManaCost(int skillManaCost) {
        SkillManaCost = skillManaCost;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
    }

    public void setSkillStatus(String status) {
        SkillStatus=status;
    }

    public void setSkillSubStatus(List<String> status) {
        SkillSubStatusList=status;
    }

    public Player getPlayer() {
        return p;
    }

    public void setPlayer(Player p) {
        this.p = p;
    }
}
