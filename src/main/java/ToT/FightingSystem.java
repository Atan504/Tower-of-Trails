package ToT;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

import static ToT.Main.plugin;

public class FightingSystem implements Listener {
    @EventHandler
    public void onAttackEvent(EntityDamageEvent e){

    }

    @EventHandler
    public void onAttackOrAttackedEvent(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof LivingEntity){
            LivingEntity lv = (LivingEntity) e.getEntity();
            if (e.getDamager().getType() == EntityType.ARROW) {
                Arrow arrow = (Arrow) e.getDamager();
                if (CitizensAPI.getNPCRegistry().isNPC((Entity) arrow.getShooter())) {
                    if (CitizensAPI.getNPCRegistry().getNPC((Entity) arrow.getShooter()).data().has("Speed")){
                        int Spe = CitizensAPI.getNPCRegistry().getNPC((Entity) arrow.getShooter()).data().get("Speed");
                        e.setDamage(EntityDamageEvent.DamageModifier.BASE, Spe);
                    }
                }else if (arrow.getShooter() instanceof Player) {
                    Player p = (Player) arrow.getShooter();
                    PlayerData pd = new PlayerData(p.getUniqueId());
                    int dmg = (int) (e.getDamage()+ pd.spe);
                    if (Objects.equals(pd.cla, "Archer")) {
                        dmg = (int) (dmg*1.3);
                    }
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE, dmg);
                } else if (!(arrow.getShooter() instanceof Player)) {
                    if (((Entity) Objects.requireNonNull(arrow.getShooter())).hasMetadata("SPE")){
                        int Spe = ((Entity) arrow.getShooter()).getMetadata("SPE").get(0).asInt();
                        e.setDamage(EntityDamageEvent.DamageModifier.BASE, Spe);
                    }
                }
            }else if (e.getDamager()instanceof LivingEntity){
                LivingEntity lvD = (LivingEntity) e.getDamager();

                if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
                    if (CitizensAPI.getNPCRegistry().isNPC(lvD)){
                        NPC npc = CitizensAPI.getNPCRegistry().getNPC(lvD);
                        if (npc.data().has("Strength")){
                            e.setDamage(e.getDamage()+(int)npc.data().get("Strength"));
                        }else if (lvD instanceof Player){
                            Player p = (Player) lvD;
                            PlayerData pd = new PlayerData(p.getUniqueId());
                            int dmg = (int) (e.getDamage()+ pd.str);
                            if (Objects.equals(pd.cla, "Swordsman")) {
                                if (Objects.requireNonNull(p.getEquipment()).getItemInMainHand().getType() == Material.IRON_SWORD){
                                    dmg= (int) (dmg*1.50);
                                }
                            }
                            e.setDamage(dmg);
                        }else {
                            if (lvD.hasMetadata("STR")){
                                int Str = lvD.getMetadata("STR").get(0).asInt();
                                e.setDamage(e.getDamage()+ Str);
                            }
                        }
                    }
                }
            }



            if (lv instanceof Player && !CitizensAPI.getNPCRegistry().isNPC(lv)){
                Player p = (Player)lv;
                p.sendMessage(String.valueOf(e.getDamage()));
                PlayerData pd = new PlayerData(p.getUniqueId());
                float yata = (float) e.getDamage();
                if (pd.def>0){
                    yata = (float) (e.getDamage()-(((float)pd.def)/100f)*((float)e.getDamage()));
                }
                pd.set("hp",((int)(pd.hp-yata)));
                e.setDamage(EntityDamageEvent.DamageModifier.BASE,0);
            }
            else if (CitizensAPI.getNPCRegistry().isNPC(lv)){
                NPC npc = CitizensAPI.getNPCRegistry().getNPC(lv);
                if (npc.data().has("MaxHealth")&&npc.data().has("Health")&&npc.data().has("Defence")){
                    int Hp = npc.data().get("Health");
                    int Def = npc.data().get("Defence");
                    float yata = (float) e.getDamage();
                    if (Def>0){
                        yata = (float) (e.getDamage()-(((float)Def)/100f)*((float)e.getDamage()));
                    }
                    if (Hp-yata<=0){
                        npc.despawn();
                    }
                    npc.data().set("Health",((int)(Hp-yata)));
                    e.setDamage(EntityDamageEvent.DamageModifier.BASE,0);
                }

            }
            else if (!(lv instanceof Player) && !CitizensAPI.getNPCRegistry().isNPC(lv)){
                if (lv.hasMetadata("DEF")&&lv.hasMetadata("HP")&&lv.hasMetadata("MAXHP")){
                    int def = lv.getMetadata("DEF").get(0).asInt();
                    int hp = lv.getMetadata("HP").get(0).asInt();
                    int maxhp = lv.getMetadata("MAXHP").get(0).asInt();

                    float yata = (float) e.getDamage();
                    if (def>0){
                        yata = (float) (e.getDamage()-(((float)def)/100f)*((float)e.getDamage()));
                    }

                    if (hp-yata<=0){
                        lv.damage(9999999);
                    }
                    lv.setMetadata("HP",new FixedMetadataValue(plugin,hp-yata));

                    e.setDamage(EntityDamageEvent.DamageModifier.BASE,0);


                    //maybe later

                    if(lv.hasMetadata("Boss")){
                        if (lv.getMetadata("Boss").get(0).asString().equals("Wolf King")){
                            if (lv.getMetadata("Status").get(0).asString().equals("Relaxed")){
                                lv.setMetadata("Status",new FixedMetadataValue(plugin,"Enraged"));
                                lv.removePotionEffect(PotionEffectType.SLOW);
                                lv.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,99999,2,true,true));
                            }
                            lv.setCustomName(Utills.chat("&l&7Wolf King &r|| " + "&b&c( "+lv.getMetadata("HP").get(0).asInt()+"/"+lv.getMetadata("MAXHP").get(0).asInt()+" )"));
                        }
                    }
                }
            }



        }
    }

    public static void Damage(LivingEntity lvD,LivingEntity lv,int dmg){
        if (lv instanceof Player && !(lvD instanceof Player)){
            Player p = (Player)lv;
            PlayerData pd = new PlayerData(p.getUniqueId());
            if (lvD.hasMetadata("STR")&&lvD.hasMetadata("HP")&&lvD.hasMetadata("MAXHP")){
                float yata = dmg;
                if (pd.def>0){
                    yata = (((float)pd.def)/100f)*((float)dmg);
                }
                pd.set("hp",((int)(pd.hp-yata)));
            }

        }else if (lvD instanceof Player && !(lv instanceof Player)){
            Player p = (Player)lvD;
            PlayerData pd = new PlayerData(p.getUniqueId());
            if (lv.hasMetadata("DEF")&&lv.hasMetadata("HP")&&lv.hasMetadata("MAXHP")){
                int def = lv.getMetadata("DEF").get(0).asInt();
                int hp = lv.getMetadata("HP").get(0).asInt();
                int maxhp = lv.getMetadata("MAXHP").get(0).asInt();

                float yata = dmg;
                if (def>0){
                    yata = (((float)def)/100f)*((float)dmg);
                }


                if (hp-yata<=0){
                    lv.damage(9999);
                }else{
                    lv.setMetadata("HP",new FixedMetadataValue(plugin,hp-yata));
                }
            }
        }else if (lvD instanceof Player){

            Player p = (Player)lvD;
            Player p2 = (Player)lv;
            PlayerData pd = new PlayerData(p.getUniqueId());
            PlayerData pd2 = new PlayerData(p2.getUniqueId());


            float yata = (float)dmg;
            if (pd2.def>0){
                yata = (((float)pd2.def)/100f)*((float)dmg);
            }

            pd2.set("hp",((int)(pd2.hp-yata)));
        }
    }
}
