package ToT.ClassManagement.Classes.UniqeSkills.Archer;

import ToT.ClassManagement.ClassList;
import ToT.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class SwordHandler implements Listener {

    @EventHandler
    public void onHit(PlayerInteractEvent e) {
        //On interact
        Player p = e.getPlayer();
        PlayerData pd = new PlayerData(p.getUniqueId());
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if (pd.cla.equals("Swordsman")){
                if (p.getInventory().getItemInMainHand().getType().equals(Material.IRON_SWORD)){
                    if (pd.sksusl.get(10).get(0).equals("Active")){
                        Random random = new Random();
                        int rand = random.nextInt((pd.sksusl.size()-2));
                        if (rand!=2){
                            ClassList.list.get(0).getSkills().get(rand).getSkillCode().run();
                        }
                    }
                }
            }
        }
    }
}
