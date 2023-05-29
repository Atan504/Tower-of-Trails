package ToT.ClassManagement;

import ToT.PlayerData;
import ToT.Utills;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class ClassList {
    //List
    public static List<Class> list = new ArrayList<>();

    public static void register(Plugin plugin){
        //Classs
        Class Swordsman = Utills.CreateClass("Swordsman", List.of(
                new Skill("Slash",0,3,4,null,false),
                new Skill("Thrust",10,5,5,null,false),
                new Skill("Parry",20,5,10,null,false),
                new Skill("Sweep",30,8,25,null,false),
                new Skill("Forward slash",40,4,7,null,false),
                new Skill("Sword Smash",50,15,17,null,false),
                new Skill("Mana cleave",60,12,32,null,false),
                new Skill("Feral instincts",70,15,44,null,false),
                new Skill("Precise cut",80,15,50,null,false),
                new Skill("Sword dome",90,10,50,null,false),
                new Skill("Ultimate SKill: Basic Swords Art",100,2,50,null,true)
        ));

        //Set Moves Code
        {
            Swordsman.getSkills().get(0).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat( "&r&b[ Slash"+ " ]") + ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));

                    float sp = 0.5F;
                    Location loc1 =p.getLocation().add(p.getLocation().getDirection().add(new Vector(0,1.5,0)).multiply(2));
                    Location loc2 =p.getLocation().add(p.getLocation().getDirection().multiply(2));
                    double distance = loc1.distance(loc2);
                    Vector p1 = loc1.toVector();
                    Vector p2 = loc2.toVector();
                    Vector vector = p2.clone().subtract(p1).normalize().multiply(sp);
                    double length = 0;

                    for (; length < distance; p1.add(vector)) {
                        Location loc = new Location(p.getWorld(),p1.getX(), p1.getY(), p1.getZ());
                        p.getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,0,0,0,0, 0);
                        for (Entity entity :  p.getWorld().getNearbyEntities(loc,2,2,2)){
                            if (entity instanceof LivingEntity){
                                LivingEntity lv = (LivingEntity) entity;
                                if (lv != p){
                                    lv.damage((int)pd.str/2,p);
                                }
                            }
                        }

                        length += sp;
                    }
                }
            });

            Swordsman.getSkills().get(1).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&b[ Thrust"+ " ]") + ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));



                    p.setVelocity(p.getLocation().getDirection());

                    Location loc1 = p.getLocation().add(p.getLocation().getDirection()).add(0,1.5,0);
                    Location loc2 = p.getLocation().add(p.getLocation().getDirection().multiply(3.5));
                    double distance = loc1.distance(loc2);
                    Vector p1 = loc1.toVector();
                    Vector p2 = loc2.toVector();
                    Vector vector = p2.clone().subtract(p1).normalize().multiply(0.35);
                    double length = 0;


                    for (; length < distance; p1.add(vector)) {
                        Location loc = new Location(p.getWorld(), p1.getX(), p1.getY(), p1.getZ());
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 2.0F);
                        p.getWorld().spawnParticle(Particle.REDSTONE, loc, 20, dustOptions);
                        for (Entity entity : p.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity lv = (LivingEntity) entity;
                                if (lv != p) {
                                    lv.damage(pd.str*2, p);
                                }
                            }
                        }

                        length += 0.35;
                    }
                }
            });

            Swordsman.getSkills().get(2).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&b[ Parry"+ " ]") + ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));


                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-1));

                    Location loc1 = p.getLocation().add(p.getLocation().getDirection().multiply(1.2));
                    Location loc2 = p.getLocation().add(p.getLocation().getDirection().multiply(1.2)).add(0,2.5,0);
                    double distance = loc1.distance(loc2);
                    Vector p1 = loc1.toVector();
                    Vector p2 = loc2.toVector();
                    Vector vector = p2.clone().subtract(p1).normalize().multiply(0.5);
                    double length = 0;

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        int a = pd.def;
                        @Override
                        public void run() {
                            pd.set("def",a);
                        }
                    },25);

                    pd.set("def",pd.def+2);
                    for (; length < distance; p1.add(vector)) {
                        Location loc = new Location(p.getWorld(), p1.getX(), p1.getY(), p1.getZ());
                        p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 15);
                        for (Entity entity : p.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity lv = (LivingEntity) entity;
                                if (lv != p) {
                                    lv.damage(pd.str/3, p);
                                }
                            }
                        }

                        length += 0.5;
                    }
                }
            });

            Swordsman.getSkills().get(3).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat( "&r&b[ Sweep"+ " ]" )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));

                    Location loc = p.getLocation().add(p.getLocation().getDirection().multiply(2.5)).add(0,1.5,0);

                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1,255,true,true));
                    p.getWorld().spawnParticle(Particle.SONIC_BOOM, loc, 3);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (Entity entity : p.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity lv = (LivingEntity) entity;
                                    if (lv != p) {
                                        int i = (int) (pd.str*2.5);
                                        lv.damage(i, p);
                                        Utills.moveAwayFrom(lv,p.getLocation(),2);
                                    }
                                }
                            }
                        }
                    },12);

                }
            });

            Swordsman.getSkills().get(4).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat( "&r&b[ Forward slash"+ " ]" )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));



                    new BukkitRunnable() {
                        double mult =  0.5;
                        Vector dir =p.getLocation().getDirection();
                        Location loc = p.getLocation().add(dir.multiply(mult)).add(0,1.5,0);
                        @Override
                        public void run() {

                            if (mult>=10){
                                cancel();
                            }

                            p.getWorld().spawnParticle(Particle.SWEEP_ATTACK, loc, 3);


                            for (Entity entity : p.getWorld().getNearbyEntities(loc, 2, 2, 2)) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity lv = (LivingEntity) entity;
                                    if (lv != p) {
                                        int i = (int) (pd.str*1.5);
                                        lv.damage(i, p);
                                    }
                                }
                            }

                            mult= (double) (mult+0.5);
                            loc = p.getLocation().add(dir.multiply(mult)).add(0,1.5,0);
                        }



                    }.runTaskTimer(plugin,0,2);

                }
            });

            Swordsman.getSkills().get(5).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE +Utills.chat(  "&r&b[ Sword Smash!"+ " ] " )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));


                    for (Location loc : Utills.generateCyl(p.getLocation(),3,true)){
                        p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 2);
                    }

                    for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), 3, 3, 3)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity lv = (LivingEntity) entity;
                            if (lv != p) {
                                int i = (int) (pd.str*2.7);
                                lv.damage(i, p);
                                Utills.moveAwayFrom(lv,p.getLocation(),2);
                            }
                        }
                    }


                }
            });

            Swordsman.getSkills().get(6).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat( "&r&b[ Mana Cleave"+ " ] " )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));

                    Location dir = p.getLocation().add(p.getLocation().getDirection().multiply(2.5)).add(0,1.2,0);

                    p.getWorld().spawnParticle(Particle.SWEEP_ATTACK,dir,0,0,0,0, 2);
                    p.getWorld().spawnParticle(Particle.SWEEP_ATTACK,dir,0,0,0.2,0, 2);
                    p.getWorld().spawnParticle(Particle.SWEEP_ATTACK,dir,0,0,-0.2,0, 2);


                    for (Location loc :Utills.generateSphere(dir,2, false)){
                        Random random = new Random();
                        if (random.nextInt(4)==0){
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(200, 0, 0), 3.0F);
                            p.getWorld().spawnParticle(Particle.REDSTONE, loc, 2, dustOptions);
                        }
                    }



                    for (Entity entity :  p.getWorld().getNearbyEntities(dir,2,2,2)){
                        if (entity instanceof LivingEntity){
                            LivingEntity lv = (LivingEntity) entity;
                            if (lv != p){
                                if (lv instanceof Player && !CitizensAPI.getNPCRegistry().isNPC(lv)){
                                    Player pp = (Player)lv;
                                    PlayerData pdpd = new PlayerData(pp.getUniqueId());
                                    float yata = (float) (pd.str*0.5);
                                    pdpd.set("mana",((int)(pdpd.mana-yata)));
                                    pp.damage(pd.str);
                                }
                                else if (CitizensAPI.getNPCRegistry().isNPC(lv)){
                                    NPC npc = CitizensAPI.getNPCRegistry().getNPC(lv);
                                    if (npc.data().has("MaxHealth")&&npc.data().has("Mana")&&npc.data().has("Defence")){
                                        npc.addTrait(SentinelTrait.class);
                                        SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
                                        sentinel.getLivingEntity().damage(pd.str,p);
                                        int mana = npc.data().get("Mana");
                                        int Def = npc.data().get("Defence");
                                        float yata = (float) (pd.str*0.5);


                                        p.sendMessage(npc.data().get("Mana").toString());

                                        npc.data().set("Mana",((int)(mana-yata)));

                                        p.sendMessage(npc.data().get("Mana").toString());
                                    }

                                }
                                else if (lv instanceof Player == false&& !CitizensAPI.getNPCRegistry().isNPC(lv)){
                                    if (lv.hasMetadata("DEF")&&lv.hasMetadata("MANA")&&lv.hasMetadata("MAXHP")){
                                        int def = lv.getMetadata("DEF").get(0).asInt();
                                        int MANA = lv.getMetadata("MANA").get(0).asInt();
                                        lv.damage(pd.str);
                                        float yata = (float) (pd.str*0.5);
                                        lv.setMetadata("MANA",new FixedMetadataValue(plugin,MANA-yata));
                                    }
                                }
                            }
                        }
                    }
                }
            });

            Swordsman.getSkills().get(7).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&b[ Feral instincts!"+ " ] " )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));


                    new BukkitRunnable() {
                        double sec =  0;

                        LivingEntity target;
                        @Override
                        public void run() {

                            if (sec>=4){
                                cancel();
                            }

                            for (Location loc :Utills.generateSphere(p.getLocation(),2, false)){
                                Random random = new Random();
                                if (random.nextInt(3)==0){
                                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 165, 0), 1.0F);
                                    p.getWorld().spawnParticle(Particle.REDSTONE, loc, 2, dustOptions);
                                }
                            }

                            if (target==null||target.isDead()) {
                                Random random = new Random();
                                int r = random.nextInt(p.getNearbyEntities(6, 6, 6).size());
                                if (p.getNearbyEntities(6, 6, 6).get(r) instanceof LivingEntity) {
                                    LivingEntity lv = (LivingEntity) p.getNearbyEntities(6, 6, 6).get(r);
                                    target = lv;
                                }
                            }

                            Utills.moveToward(p,target.getLocation(),2);

                            Swordsman.getSkills().get(0).SkillCode.run();
                            Swordsman.getSkills().get(0).SkillCode.run();

                            sec++;

                        }



                    }.runTaskTimer(plugin,0,20);
                }
            });

            Swordsman.getSkills().get(8).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&b[ Precise cut"+ " ] ") + ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));

                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.0F);
                    Utills.drawLine(p.getLocation().add(p.getLocation().getDirection()).add(0,1,0),p.getLocation().add(p.getLocation().getDirection().multiply(4)).add(0,1,0),0.5,dustOptions);
                    Location loc = p.getLocation().add(p.getLocation().getDirection().multiply(4));

                    List<LivingEntity> lvs = new ArrayList<>();

                    for (Entity entity : p.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity lv = (LivingEntity) entity;
                            if (lv != p) {
                                int i = (int) (pd.str*0.6);
                                lvs.add(lv);
                            }
                        }
                    }


                    new BukkitRunnable() {
                        double sec =  0;

                        @Override
                        public void run() {

                            if (sec>=5){
                                cancel();
                            }



                            for (LivingEntity lv : lvs) {
                                if (lv!=null|lv.isDead()){
                                    int i = (int) (pd.str*0.6);
                                    lv.damage(i, p);
                                    Particle.DustOptions dustOptions2 = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 2.55F);
                                    p.getWorld().spawnParticle(Particle.REDSTONE, lv.getLocation().add(0,1.2,0), 3, dustOptions2);
                                }
                            }

                            sec++;
                        }
                    }.runTaskTimer(plugin,0,20);

                }
            });

            Swordsman.getSkills().get(9).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&b[ Sword dome"+ " ] " )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));

                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.0F);
                    Utills.drawLine(p.getLocation().add(p.getLocation().getDirection()),p.getLocation().add(p.getLocation().getDirection().multiply(4)),1,dustOptions);
                    Location loc = p.getLocation().add(p.getLocation().getDirection().multiply(4));

                    List<LivingEntity> lvs = new ArrayList<>();

                    new BukkitRunnable() {
                        double sec =  0;

                        double tick = 0;
                        @Override
                        public void run() {

                            if (sec>=5){
                                cancel();
                            }

                            for (Location loc : Utills.generateSphere(p.getLocation(),6,true)){
                                Random random = new Random();
                                if (random.nextInt(7)==0){
                                    p.getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,1,0,0,0, 0);
                                    for (Entity entity : p.getWorld().getNearbyEntities(loc, 0.4, 0.4, 0.4)) {
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity lv = (LivingEntity) entity;
                                            if (lv != p) {
                                                int i = (int) (pd.str*0.4);
                                                lv.damage(i,p);
                                            }
                                        }
                                    }
                                }

                            }

                            tick++;
                            if (tick%20==0){
                                sec++;
                            }
                        }
                    }.runTaskTimer(plugin,0,0);

                }
            });

            Swordsman.getSkills().get(10).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Swordsman.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    //Do it
                    List<List<String>> sksuslcopy = pd.sksusl;
                    for (int i =0;i< sksuslcopy.get(10).size();i++){
                        if (sksuslcopy.get(10).get(i).equals("Inactive")){
                            sksuslcopy.get(10).set(i,"Active");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&b[ Basic Sword Arts Activated"+ " ]" )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));
                        }else if (sksuslcopy.get(10).get(i).equals("Active")){
                            sksuslcopy.get(10).set(i,"Inactive");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&c[ Basic Sword Arts Deactivated"+ " ]" )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));
                        }
                    }
                    pd.set("sksusl",sksuslcopy);


                    if (pd.sksusl.get(10).get(0).equals("Active")){
                        new BukkitRunnable() {

                            double tick = 0;
                            @Override
                            public void run() {

                                if (pd.sksusl.get(10).get(0).equals("Inactive")){
                                    cancel();
                                }

                                for (Location loc : Utills.generateSphere(p.getLocation(),6,true)){
                                    Random random = new Random();
                                    if (random.nextInt(7)==0){
                                        p.getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,1,0,0,0, 0);
                                        for (Entity entity : p.getWorld().getNearbyEntities(loc, 0.4, 0.4, 0.4)) {
                                            if (entity instanceof LivingEntity) {
                                                LivingEntity lv = (LivingEntity) entity;
                                                if (lv != p) {
                                                    int i = (int) (pd.str*0.4);
                                                    lv.damage(i,p);
                                                }
                                            }
                                        }
                                    }
                                }

                                tick++;
                                if (tick%20==0){
                                    pd.set("mana",pd.mana-50);
                                }
                                if (pd.mana<=0){
                                    pd.set("mana",0);
                                    sksuslcopy.get(10).set(0,"Inactive");
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + Utills.chat("&r&c[ Basic Sword Arts Deactivated"+ " ]" )+ ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));
                                }
                            }
                        }.runTaskTimer(plugin,0,0);
                    }

                }
            });

        }


        Class Archer = Utills.CreateClass("Archer", List.of(
                new Skill("Guaranteed Hit",0,17,10,null,true),
                new Skill("Guaranteed Hit Testing",0,0,2,null,false),
                new Skill("Parry",20,5,10,null,false),
                new Skill("Sweep",30,8,25,null,false)
//                new Skill("Thrust",0,5,5,null,false),
//                new Skill("Thrust",0,5,5,null,false),
//                new Skill("Thrust",0,5,5,null,false),
//                new Skill("Thrust",0,5,5,null,false),
//                new Skill("Thrust",0,5,5,null,false),
//                new Skill("Thrust",0,5,5,null,false)
        ));




        //Set Moves Code
        {
            Archer.getSkills().get(0).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Archer.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());

                    int per = 20*10;

                    List<List<String>> sksuslcopy = pd.sksusl;
                    for (int i =0;i< sksuslcopy.get(0).size();i++){
                        if (sksuslcopy.get(0).get(i).equals("Inactive")){
                            sksuslcopy.get(0).set(i,"Active");
                            sksuslcopy.get(0).add(i+1,"NoTarget");
                        }
                    }
                    pd.set("sksusl",sksuslcopy);
                    //Do it
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,per , 1, false, false));


                    new BukkitRunnable() {
                        int time=0;
                        int secends=0;


                        @Override
                        public void run() {
                            time++;
                            if (time>=per){
                                cancel();
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.DARK_RED + "Guaranteed Hit Has Deactivated"));
                                List<List<String>> sksuslcopy = pd.sksusl;
                                for (int i =0;i< sksuslcopy.get(0).size();i++){
                                    if (sksuslcopy.get(0).get(i).equals("Active")){
                                        sksuslcopy.get(0).set(i,"Inactive");
                                    }
                                    if (sksuslcopy.get(0).get(i).startsWith("Target,")){
                                        sksuslcopy.get(0).remove(i);
                                    }

                                }
                                pd.set("sksusl",sksuslcopy);
                            }

                            if (time%20==0){
                                secends++;
                            }

                            for (int i =0;i< pd.sksusl.get(0).size();i++) {
                                if (pd.sksusl.get(0).get(i).equals("NoTarget")){
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "Guaranteed Hit Is Ready To Target (Time Left: " +((per/20)-secends)+ ")"));
                                }else if (pd.sksusl.get(0).get(i).startsWith("Target,")) {
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.BLUE + "Guaranteed Hit Is Activated (Time Left: " +((per/20)-secends)+ ")"));
                                }
                            }

                        }
                    }.runTaskTimer(plugin,0,0);
                }
            });

            Archer.getSkills().get(1).setSkillCode(new Runnable() {
                @Override
                public void run() {
                    Player p = Archer.getSkills().get(0).getPlayer();
                    PlayerData pd = new PlayerData(p.getUniqueId());
                    if (p.hasMetadata("IsPlayerDrawingHisBow")) {
                        if (p.getMetadata("IsPlayerDrawingHisBow").get(0).asBoolean() == true) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (p.getMetadata("IsPlayerDrawingHisBow").get(0).asString().equals("Shot")) {
                                        for (Entity ent : p.getNearbyEntities(10, 10, 10)) {
                                            if (ent instanceof Arrow) {
                                                Arrow arrow = (Arrow) ent;
                                                Entity shooter = (Entity) arrow.getShooter();
                                                if (shooter.getUniqueId().equals(p.getUniqueId())) {

                                                    arrow.setMetadata("ArrowType:", new FixedMetadataValue(plugin, "AutoTarget"));

                                                    if (p.hasMetadata("ArrowAutoTarget:")) {
                                                        UUID target2 = UUID.fromString(p.getMetadata("ArrowAutoTarget:").get(0).asString());
                                                        Location loc = Utills.getEntityByUniqueId(target2).getLocation().add(0, Utills.getEntityByUniqueId(target2).getHeight(), 0);
                                                        Utills.moveToward(arrow, loc, 2);
                                                    }
                                                }
                                            }
                                        }
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0, 0);
                        }
                    }
                }
            });

//            Swordsman.getSkills().get(1).setSkillCode(new Runnable() {
//                @Override
//                public void run() {
//                    Player p = Swordsman.getSkills().get(0).getPlayer();
//                    PlayerData pd = new PlayerData(p.getUniqueId());
//
//                    //Do it
//
//                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "Thrust"));
//
//                    p.setVelocity(p.getLocation().getDirection());
//
//                    Location loc1 = p.getLocation().add(p.getLocation().getDirection()).add(0,1.5,0);
//                    Location loc2 = p.getLocation().add(p.getLocation().getDirection().multiply(3.5));
//                    double distance = loc1.distance(loc2);
//                    Vector p1 = loc1.toVector();
//                    Vector p2 = loc2.toVector();
//                    Vector vector = p2.clone().subtract(p1).normalize().multiply(0.35);
//                    double length = 0;
//
//
//                    for (; length < distance; p1.add(vector)) {
//                        Location loc = new Location(p.getWorld(), p1.getX(), p1.getY(), p1.getZ());
//                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 2.0F);
//                        p.getWorld().spawnParticle(Particle.REDSTONE, loc, 20, dustOptions);
//                        for (Entity entity : p.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
//                            if (entity instanceof LivingEntity) {
//                                LivingEntity lv = (LivingEntity) entity;
//                                if (lv != p) {
//                                    lv.damage(pd.str*2, p);
//                                }
//                            }
//                        }
//
//                        length += 0.35;
//                    }
//                }
//            });
//
//            Swordsman.getSkills().get(2).setSkillCode(new Runnable() {
//                @Override
//                public void run() {
//                    Player p = Swordsman.getSkills().get(0).getPlayer();
//                    PlayerData pd = new PlayerData(p.getUniqueId());
//
//                    //Do it
//
//                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.BLUE + "Parry"));
//
//                    p.setVelocity(p.getLocation().getDirection().normalize().multiply(-1));
//
//                    Location loc1 = p.getLocation().add(p.getLocation().getDirection().multiply(1.2));
//                    Location loc2 = p.getLocation().add(p.getLocation().getDirection().multiply(1.2)).add(0,2.5,0);
//                    double distance = loc1.distance(loc2);
//                    Vector p1 = loc1.toVector();
//                    Vector p2 = loc2.toVector();
//                    Vector vector = p2.clone().subtract(p1).normalize().multiply(0.5);
//                    double length = 0;
//
//                    Bukkit.getScheduler().scheduleSyncDelayedTask(Fabulus.plugin, new Runnable() {
//                        int a = pd.def;
//                        @Override
//                        public void run() {
//                            pd.set("def",a);
//                        }
//                    },25);
//
//                    pd.set("def",pd.def+2);
//                    for (; length < distance; p1.add(vector)) {
//                        Location loc = new Location(p.getWorld(), p1.getX(), p1.getY(), p1.getZ());
//                        p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 15);
//                        for (Entity entity : p.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
//                            if (entity instanceof LivingEntity) {
//                                LivingEntity lv = (LivingEntity) entity;
//                                if (lv != p) {
//                                    lv.damage(pd.str/3, p);
//                                }
//                            }
//                        }
//
//                        length += 0.5;
//                    }
//                }
//            });
//
//            Swordsman.getSkills().get(3).setSkillCode(new Runnable() {
//                @Override
//                public void run() {
//                    Player p = Swordsman.getSkills().get(0).getPlayer();
//                    PlayerData pd = new PlayerData(p.getUniqueId());
//
//                    //Do it
//
//                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "Sweep"));
//
//                    Location loc = p.getLocation().add(p.getLocation().getDirection().multiply(2.5)).add(0,1.5,0);
//
//                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1,255,true,true));
//                    p.getWorld().spawnParticle(Particle.SONIC_BOOM, loc, 3);
//
//                    Bukkit.getScheduler().scheduleSyncDelayedTask(Fabulus.plugin, new Runnable() {
//                        @Override
//                        public void run() {
//                            for (Entity entity : p.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)) {
//                                if (entity instanceof LivingEntity) {
//                                    LivingEntity lv = (LivingEntity) entity;
//                                    if (lv != p) {
//                                        int i = (int) (pd.str*2.5);
//                                        lv.damage(i, p);
//                                        Utills.moveAwayFrom(lv,p.getLocation(),2);
//                                    }
//                                }
//                            }
//                        }
//                    },12);
//
//                }
//            });
        }

        list.add(Swordsman);
        list.add(Archer);

    }
}


