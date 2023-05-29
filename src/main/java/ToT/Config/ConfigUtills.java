package ToT.Config;

import ToT.Config.Files.PlayersDataFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConfigUtills {
    public ConfigUtills() {
    }

    public static void PlayersDataSet(Player p, String Loction, String Var, Object Value) {
        PlayersDataFile.get().set("Players." + p.getUniqueId() + "." + Loction + "." + Var + ":", Value);
        PlayersDataFile.save();
    }

    public static Object PlayersDataGet(Player p, String Loction, String Var) {
        return PlayersDataFile.get().get("Players." + p.getUniqueId() + "." + Loction + "." + Var + ":");
    }

    public static void PlayersDataSetList(Player p, String Loction, String Var, List<?> Value) {
        if (Value.size() <= 100){
            for(int i = 0; i < Value.size(); i++) {
                PlayersDataFile.get().set("Players." + p.getUniqueId() + "." + Loction + "." + Var + "." + i + ":", Value.get(i));
            }
            PlayersDataFile.save();
            //For Testing
//            Bukkit.getLogger().log(Level.INFO,"[ConfigUtills] ConfigUtills inserted values" + "[NEXT MSG's] into: " + "Players | " + p.getUniqueId() + " | " + Loction + " | " + Var);
//            for (Object st : Value){
//                String s = String.valueOf(st);
//                Bukkit.getLogger().log(Level.INFO,"[ConfigUtills] MSG's: " + s);
//            }
        }else{
            Bukkit.getLogger().log(Level.WARNING,"[ConfigUtills] Cant handle more then 100 object in a list!");
        }

    }

    public static List<?> PlayersDataGetList(Player p, String Loction, String Var) {
        List<Object> list = new ArrayList<>();

        for(int i = 0; i <= PlayersDataGetListSize(p,Loction,Var); i++) {
            list.add(PlayersDataFile.get().get("Players." + p.getUniqueId() + "." + Loction + "." + Var + "." + i + ":"));
        }
        PlayersDataFile.save();
        return list;
    }

    public static void ClearPlayerData(Player p){
        PlayersDataFile.get().set("Players." + p.getUniqueId(),"");
    }

    public static int PlayersDataGetListSize(Player p, String Loction, String Var) {
        boolean srch = true;
        int r = 0;
        for(int i = 0; i < 100; i++) {
            if (srch){
                if (PlayersDataFile.get().get("Players." + p.getUniqueId() + "." + Loction + "." + Var + "." + i + ":")==null){
                    r=i-1;
                    srch=false;
                }
            }
        }
        return r;
    }


    //Temporary Location
    public static List<String> AddToConfigListString(List<String> list,String add){
        list.add(add);
        return list;
    }

    public static List<Integer> AddToConfigListInteger(List<Integer> list,Integer add){
        list.add(add);
        return list;
    }


}
