package ToT;

import ToT.Config.ConfigUtills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    Player p;
    public boolean isMoving = false;
    public String status=null;
    public String substatus=null;
    public boolean set=false;
    public String cla =null;
    public int smn=0;
    public List<String> sks =new ArrayList<>();
    public List<List<String>> sksusl =new ArrayList<>();
    public List<Integer> skc =new ArrayList<>();
    public List<Integer> skmana =new ArrayList<>();
    public int stp=0;
    public int mana =0;
    public int maxmana =0;
    public int lvl =0;
    public int floor =0;
    public double xp =0.0D;
    public double mxp =0.0D;
    public int str =0;
    public int hp =0;
    public int maxhp =0;
    public int def =0;
    public int spe =0;
    public int magic =0;
    public PlayerData(UUID id){
        p = Bukkit.getPlayer(id);
        update();
    }
    public void update(){
        status = (String) ConfigUtills.PlayersDataGet(p, "Info", "Status");
        isMoving = (boolean) ConfigUtills.PlayersDataGet(p, "Info", "IsMoving");
        substatus = (String) ConfigUtills.PlayersDataGet(p, "Info", "SubStatus");
        set= (boolean) ConfigUtills.PlayersDataGet(p, "Info", "Setuped");
        cla = (String) ConfigUtills.PlayersDataGet(p, "Info", "Class");
        smn= (int) ConfigUtills.PlayersDataGet(p, "Info", "Selected Move Number");
        floor =(int) ConfigUtills.PlayersDataGet(p, "Info", "Floor");
        sks = (List<String>) ConfigUtills.PlayersDataGetList(p,"Skills","Status");
        sksusl = (List<List<String>>) ConfigUtills.PlayersDataGetList(p,"Skills","SubStatusList");
        skc =(List<Integer>) ConfigUtills.PlayersDataGetList(p, "Skills", "Cooldown");
        skmana =(List<Integer>) ConfigUtills.PlayersDataGetList(p, "Skills", "Mana Cost");
        stp= (int) ConfigUtills.PlayersDataGet(p, "Points", "Stat");
        mana =(int) ConfigUtills.PlayersDataGet(p, "Stats", "Mana");
        maxmana = (int) ConfigUtills.PlayersDataGet(p, "Stats", "Max Mana");
        lvl = (int) ConfigUtills.PlayersDataGet(p, "Stats", "Level");
        xp =(double) ConfigUtills.PlayersDataGet(p, "Stats", "Xp");
        mxp =(double) ConfigUtills.PlayersDataGet(p, "Stats", "MaxXp");
        str =(int) ConfigUtills.PlayersDataGet(p, "Stats", "Strength");
        hp =(int) ConfigUtills.PlayersDataGet(p, "Stats", "Health");
        maxhp =(int) ConfigUtills.PlayersDataGet(p, "Stats", "Max Health");
        def =(int) ConfigUtills.PlayersDataGet(p, "Stats", "Defence");
        spe =(int) ConfigUtills.PlayersDataGet(p, "Stats", "Speed");
        magic =(int) ConfigUtills.PlayersDataGet(p, "Stats", "Magic");

    }
    public void set(String var,Object value){
        if (var.equals("isMoving")){
            ConfigUtills.PlayersDataSet(p, "Info", "IsMoving", value);
        }
        if (var.equals("status")){
            ConfigUtills.PlayersDataSet(p, "Info", "Status", value);
        }
        if (var.equals("substatus")){
            ConfigUtills.PlayersDataSet(p, "Info", "SubStatus", value);
        }
        if (var.equals("set")){
            ConfigUtills.PlayersDataSet(p, "Info", "Setuped", value);
        }
        if (var.equals("cla")){
            ConfigUtills.PlayersDataSet(p, "Info", "Class", value);
        }
        if (var.equals("smn")){
            ConfigUtills.PlayersDataSet(p, "Info", "Selected Move Number", value);
        }
        if (var.equals("grde")){
            ConfigUtills.PlayersDataSet(p, "Info", "Floor", value);
        }
        if (var.equals("sks")){
            ConfigUtills.PlayersDataSetList(p, "Skills", "Status", (List<?>) value);
        }
        if (var.equals("sksusl")){
            ConfigUtills.PlayersDataSetList(p, "Skills", "SubStatusList", (List<List<?>>) value);
        }
        if (var.equals("skc")) {
            ConfigUtills.PlayersDataSetList(p, "Skills", "Cooldown", (List<?>) value);
        }
        if (var.equals("skmana")) {
            ConfigUtills.PlayersDataSetList(p, "Skills", "Mana Cost", (List<?>) value);
        }
        if (var.equals("stp")){
            ConfigUtills.PlayersDataSet(p, "Points", "Stat", value);
        }
        if (var.equals("mana")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Mana", value);
        }
        if (var.equals("maxmana")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Max Mana", value);
        }
        if (var.equals("lvl")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Level", value);
        }

        if (var.equals("xp")){
            ConfigUtills.PlayersDataSet(p, "Stats","Xp", value);
        }
        if (var.equals("mxp")){
            ConfigUtills.PlayersDataSet(p, "Stats","MaxXp", value);
        }
        if (var.equals("str")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Strength", value);
        }
        if (var.equals("hp")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Health", value);
        }
        if (var.equals("maxhp")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Max Health", value);
        }
        if (var.equals("def")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Defence", value);
        }
        if (var.equals("spe")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Speed", value);
        }
        if (var.equals("magic")){
            ConfigUtills.PlayersDataSet(p, "Stats", "Magic", value);
        }
        update();
    }
}
