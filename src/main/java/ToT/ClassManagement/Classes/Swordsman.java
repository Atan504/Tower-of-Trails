//package fabulus.fabulus.ClassManagement.Classes;
//
//import fabulus.fabulus.ClassManagement.Class;
//import fabulus.fabulus.ClassManagement.Skill;
//import fabulus.fabulus.Fabulus;
//import fabulus.fabulus.PlayerData;
//import fabulus.fabulus.Utills;
//import net.md_5.bungee.api.ChatMessageType;
//import net.md_5.bungee.api.chat.TextComponent;
//import org.bukkit.*;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.entity.Player;
//import org.bukkit.util.Vector;
//
//import java.util.List;
//
//public class Swordsman
//{
//    Class Swordsman = Utills.CreateClass("Swordsman", List.of(
//            new Skill("Slash",0,3,2,null,false),
//            new Skill("Thrust",0,5,5,null,false)
//    ));
//
//    //Set Moves Code
//    {
//        Swordsman.getSkills().get(0).setSkillCode(new Runnable() {
//            @Override
//            public void run() {
//                Player p = Swordsman.getSkills().get(0).getPlayer();
//                PlayerData pd = new PlayerData(p.getUniqueId());
//
//                //Do it
//
//                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "Slash"));
//                float sp = 0.5F;
//                Location loc1 =p.getLocation().add(p.getLocation().getDirection().add(new Vector(0,1.5,0)).multiply(2));
//                Location loc2 =p.getLocation().add(p.getLocation().getDirection().multiply(2));
//                double distance = loc1.distance(loc2);
//                Vector p1 = loc1.toVector();
//                Vector p2 = loc2.toVector();
//                Vector vector = p2.clone().subtract(p1).normalize().multiply(sp);
//                double length = 0;
//
//                Bukkit.getScheduler().scheduleSyncDelayedTask(Fabulus.plugin, new Runnable() {
//                    int a = pd.str;
//                    @Override
//                    public void run() {
//                        pd.set("str",a);
//                    }
//                },1);
//                pd.set("str",pd.str+(pd.str/2));
//
//
//                for (; length < distance; p1.add(vector)) {
//                    Location loc = new Location(p.getWorld(),p1.getX(), p1.getY(), p1.getZ());
//                    p.getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,0,0,0,0, 0);
//                    for (Entity entity :  p.getWorld().getNearbyEntities(loc,2,2,2)){
//                        if (entity instanceof LivingEntity){
//                            LivingEntity lv = (LivingEntity) entity;
//                            if (lv != p){
//                                lv.damage(pd.str,p);
//                            }
//                        }
//                    }
//
//                    length += sp;
//                }
//            }
//        });
//
//        Swordsman.getSkills().get(1).setSkillCode(new Runnable() {
//            @Override
//            public void run() {
//                Player p = Swordsman.getSkills().get(0).getPlayer();
//                PlayerData pd = new PlayerData(p.getUniqueId());
//
//                //Do it
//
//                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "Thrust"));
//
//                Location loc1 = p.getLocation().add(p.getLocation().getDirection()).add(0,1.5,0);
//                Location loc2 = p.getLocation().add(p.getLocation().getDirection().multiply(6.5));
//                double distance = loc1.distance(loc2);
//                Vector p1 = loc1.toVector();
//                Vector p2 = loc2.toVector();
//                Vector vector = p2.clone().subtract(p1).normalize().multiply(0.35);
//                double length = 0;
//
//                Bukkit.getScheduler().scheduleSyncDelayedTask(Fabulus.plugin, new Runnable() {
//                    int a = pd.str;
//                    @Override
//                    public void run() {
//                        pd.set("str",a);
//                    }
//                },1);
//                pd.set("str",pd.str*2);
//
//                for (; length < distance; p1.add(vector)) {
//                    Location loc = new Location(p.getWorld(), p1.getX(), p1.getY(), p1.getZ());
//                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 2.0F);
//                    p.getWorld().spawnParticle(Particle.REDSTONE, loc, 20, dustOptions);
//                    for (Entity entity : p.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
//                        if (entity instanceof LivingEntity) {
//                            LivingEntity lv = (LivingEntity) entity;
//                            if (lv != p) {
//                                lv.damage(pd.str, p);
//                            }
//                        }
//                    }
//
//                    length += 0.35;
//                }
//            }
//        });
//
//
//    }
//}
