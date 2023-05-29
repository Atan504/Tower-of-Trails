package ToT.ClassManagement.Classes.UniqeSkills.Archer;

import ToT.PlayerData;
import ToT.Utills;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ToT.Main.plugin;

public class GuaranteedHit implements Listener {
    @EventHandler
    public void Ha(EntityShootBowEvent e){
        UUID target;
        if (e.getEntity() instanceof Player&&!CitizensAPI.getNPCRegistry().isNPC(e.getEntity())){
            Player p = (Player) e.getEntity();
            PlayerData pd = new PlayerData(p.getUniqueId());
            if (Objects.equals(pd.cla, "Archer")){
                for (int i =0;i< pd.sksusl.get(0).size();i++){

                    if (pd.sksusl.get(0).get(i).startsWith("Target,")){

                        String[] words =  pd.sksusl.get(0).get(i).split(",");
                        target = UUID.fromString(words[1]);

                        if (Utills.getEntityByUniqueId(target)!=null){
                            Utills.moveToward(e.getProjectile(), Objects.requireNonNull(Utills.getEntityByUniqueId(target)).getLocation().add(0,1,0),2+(e.getForce()*3));
                        }
                        else{
                            List<List<String>> sksuslcopy = pd.sksusl;
                            for (int i3 =0;i3< sksuslcopy.get(0).size();i3++){
                                if (sksuslcopy.get(0).get(i3).startsWith("Target,")){
                                    sksuslcopy.get(0).remove(i);
                                }
                            }
                            pd.set("sksusl",sksuslcopy);
                        }

                    }
                }
            }
        }else if (CitizensAPI.getNPCRegistry().isNPC(e.getEntity())){
        }
    }
    @EventHandler
    public void Ha(EntitySpawnEvent e){
        if(e.getEntity() instanceof Arrow){
            Arrow ar = (Arrow) e.getEntity();
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (CitizensAPI.getNPCRegistry().isNPC((Entity) ar.getShooter())){
                    NPC npc = CitizensAPI.getNPCRegistry().getNPC((Entity) ar.getShooter());

                    UUID target2;

                    if (npc.data().has("Class")){
                        if (npc.data().get("Class").equals("Archer")){
                            if (npc.data().get("Skill"+"."+"Surehit"+"."+"Active"+":").equals(true)) {




                                if (!npc.data().get("Skill"+"."+"Surehit"+"."+"Target"+":").equals(false)) {

                                    target2 = UUID.fromString(npc.data().get("Skill" + "." + "Surehit" + "." + "Target" + ":").toString());
                                    Utills.moveToward(ar, Objects.requireNonNull(Utills.getEntityByUniqueId(target2)).getLocation().add(0, 1.5, 0), 6.5);
                                    UUID finalTarget2 = target2;
                                }
                            }
                        }
                    }

                }
            },1);
        }

    }
    @EventHandler
    public void Ha(EntityDamageByEntityEvent e){
        if (e.getDamager().getType() != EntityType.ARROW ) return;
        if (e.getEntity().isDead()) return;
        Arrow arrow = (Arrow) e.getDamager();
        if (arrow.getShooter() instanceof Player && !CitizensAPI.getNPCRegistry().isNPC((Entity) arrow.getShooter())){
            Player p = (Player) arrow.getShooter();
            PlayerData pd = new PlayerData(p.getUniqueId());
            if (Objects.equals(pd.cla, "Archer")){
                for (int i =0;i< pd.sksusl.get(0).size();i++) {
                    if (pd.sksusl.get(0).get(i).equals("Active")) {
                        for (int i2 =0;i2< pd.sksusl.get(0).size();i2++) {
                            if (e.getEntity() instanceof LivingEntity){
                                LivingEntity lv = (LivingEntity) e.getEntity();
                            }
                            if (pd.sksusl.get(0).get(i2).equals("NoTarget")) {
                                pd.sksusl.get(0).set(i2, "Target,"+ e.getEntity().getUniqueId());
                            }
                        }
                    }
                }
            }
        }else if (CitizensAPI.getNPCRegistry().isNPC((Entity) arrow.getShooter())){
            NPC npc = CitizensAPI.getNPCRegistry().getNPC((Entity) arrow.getShooter());
            if (npc.data().has("Class")){
                if (npc.data().get("Class").equals("Archer")){
                    if (npc.data().get("Skill"+"."+"Surehit"+"."+"Active"+":").equals(true)) {

                        if (npc.data().get("Skill"+"."+"Surehit"+"."+"Target"+":").equals(false)) {
                            npc.data().set("Skill"+"."+"Surehit"+"."+"Target"+":",String.valueOf(e.getEntity().getUniqueId()));

                        }
                    }
                }
            }
        }
    }

}
