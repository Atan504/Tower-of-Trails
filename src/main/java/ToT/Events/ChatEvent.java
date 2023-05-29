package ToT.Events;

import ToT.PlayerData;
import ToT.Utills;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static ToT.Main.plugin;

public class ChatEvent implements Listener {
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        PlayerData pd = new PlayerData(p.getUniqueId());
        if (e.getMessage().equalsIgnoreCase("ara")){
            Vector v = p.getLocation().getDirection();
            Vector v1 = v.clone().setX(-v.getZ()).setZ(v.getX());
            Vector v2 = v.clone().setX(v.getZ()).setZ(-v.getX());
            Location right = p.getLocation().add(v1.toLocation(p.getWorld())).add(0,1,0);
            Location left = p.getLocation().add(v2.toLocation(p.getWorld())).add(0,1,0);

            Random random = new Random();

            int r1 = random.nextInt(2);
            int r2 = random.nextInt(2-(-2))-2;
            Location j = p.getLocation().add(v1.multiply(r1).toLocation(p.getWorld()));
            Location loc1 =j.add(v.multiply(1.5)).add(0,r2,0);

            int r11 = random.nextInt(2);
            int r22 = random.nextInt(2-(-2))-2;
            Location j1 = p.getLocation().add(v2.multiply(r11).toLocation(p.getWorld()));
            Location loc2 =j1.add(v.multiply(1.5)).add(0,r22,0);

            Utills.drawLine(loc1,loc2,0.5, Particle.NOTE);
        }

        if (e.getMessage().equalsIgnoreCase("HAtul")){
            Random random = new Random();
            Location loc = p.getLocation();

            Vector forwardDir = loc.getDirection().clone();
            Vector toLeft = loc.getDirection().rotateAroundY(-0.4 * Math.PI);
//            Vector toRight = loc.getDirection().rotateAroundY(0.2 * Math.PI);
            Location leftLoc = loc.add(toLeft.multiply(1.5));
//            Location rightLoc = loc.add(toRight.multiply(1.5));



            Objects.requireNonNull(loc.getWorld()).spawnParticle(Particle.SONIC_BOOM,leftLoc,1);

//            loc.getWorld().spawnParticle(Particle.SONIC_BOOM,rightLoc,1);

//            Utills.drawLine(leftLoc,rightLoc,0.5, Particle.NOTE);

        }



        if (e.getMessage().equalsIgnoreCase("Woof!")){
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Wolf WolfKing = (Wolf) p.getWorld().spawnEntity(p.getLocation().add(p.getLocation().getDirection().multiply(4)),EntityType.WOLF);
                WolfKing.setCustomNameVisible(true);
                WolfKing.setMetadata("Boss",new FixedMetadataValue(plugin,"Wolf King"));
                WolfKing.setMetadata("Status",new FixedMetadataValue(plugin,"Relaxed"));
                WolfKing.setMetadata("MAXHP",new FixedMetadataValue(plugin,90));
                WolfKing.setMetadata("HP",new FixedMetadataValue(plugin,WolfKing.getMetadata("MAXHP").get(0).asInt()));
                WolfKing.setMetadata("STR",new FixedMetadataValue(plugin,15));
                WolfKing.setMetadata("DEF",new FixedMetadataValue(plugin,0));
                WolfKing.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,99999,255,true,true));
                WolfKing.setCustomName(Utills.chat("&l&7Wolf King &r|| " + "&b&c( "+WolfKing.getMetadata("HP").get(0).asInt()+"/"+WolfKing.getMetadata("MAXHP").get(0).asInt()+" )"));

                for (Location loc : Utills.generateCyl(WolfKing.getLocation(),5,true)){
                    Random r = new Random();
                    int i = r.nextInt(1);
                    if (i==0){
                        Wolf WolfSolider = (Wolf) p.getWorld().spawnEntity(loc,EntityType.WOLF);
                        WolfSolider.setAngry(true);
                        WolfSolider.setCustomNameVisible(true);
                        WolfSolider.setMetadata("MAXHP",new FixedMetadataValue(plugin,20));
                        WolfSolider.setMetadata("HP",new FixedMetadataValue(plugin,WolfSolider.getMetadata("MAXHP").get(0).asInt()));
                        WolfSolider.setMetadata("STR",new FixedMetadataValue(plugin,2));
                        WolfSolider.setMetadata("DEF",new FixedMetadataValue(plugin,0));
                        Objects.requireNonNull(WolfSolider.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK)).setBaseValue(3);
                        WolfSolider.setCustomName(Utills.chat("&l&8Wolf Solider"));
                    }
                }
                for (Location loc : Utills.generateCyl(WolfKing.getLocation(),6,true)){
                    Random r = new Random();
                    int i = r.nextInt(1);
                    if (i==0){
                        Wolf WolfSolider = (Wolf) p.getWorld().spawnEntity(loc,EntityType.WOLF);
                        WolfSolider.setAngry(true);
                        WolfSolider.setCustomNameVisible(true);
                        WolfSolider.setMetadata("MAXHP",new FixedMetadataValue(plugin,20));
                        WolfSolider.setMetadata("HP",new FixedMetadataValue(plugin,WolfSolider.getMetadata("MAXHP").get(0).asInt()));
                        WolfSolider.setMetadata("STR",new FixedMetadataValue(plugin,2));
                        WolfSolider.setMetadata("DEF",new FixedMetadataValue(plugin,0));
                        Objects.requireNonNull(WolfSolider.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK)).setBaseValue(3);
                        WolfSolider.setCustomName(Utills.chat("&l&8Wolf Solider"));
                    }
                }

                for (Location loc : Utills.generateCyl(WolfKing.getLocation(),2,true)){
                    Wolf WolfGuard = (Wolf) p.getWorld().spawnEntity(loc,EntityType.WOLF);
                    WolfGuard.setAngry(true);
                    WolfGuard.setCustomNameVisible(true);
                    WolfGuard.setMetadata("MAXHP",new FixedMetadataValue(plugin,65));
                    WolfGuard.setMetadata("HP",new FixedMetadataValue(plugin,WolfGuard.getMetadata("MAXHP").get(0).asInt()));
                    WolfGuard.setMetadata("STR",new FixedMetadataValue(plugin,5));
                    WolfGuard.setMetadata("DEF",new FixedMetadataValue(plugin,5));
                    Objects.requireNonNull(WolfGuard.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK)).setBaseValue(3);
                    WolfGuard.setCustomName(Utills.chat("&l&dWolf Guard"));

                    new BukkitRunnable() {
                        final Location location = loc;
                        public void run() {
                            WolfGuard.teleport(loc);
                            if (!WolfKing.getMetadata("Status").get(0).asString().equals("Relaxed")){
                                cancel();
                            }
                        }
                    };
                }


            },1);

        }

        if (e.getMessage().equalsIgnoreCase("ahahah")){
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Arrow arrow = p.getWorld().spawnArrow(p.getLocation().add(0,1,0).add(p.getLocation().getDirection()),p.getLocation().getDirection(),2,0);
                arrow.setShooter(p);
                arrow.setMetadata("ArrowType:",new FixedMetadataValue(plugin,"AutoTarget"));
                p.sendMessage("jjjj");
                if (p.hasMetadata("ArrowAutoTarget:")){
                    UUID target2 = UUID.fromString(p.getMetadata("ArrowAutoTarget:").get(0).asString());
                    p.sendMessage("jjjj2");
                    Location loc = Objects.requireNonNull(Utills.getEntityByUniqueId(target2)).getLocation().add(0, Objects.requireNonNull(Utills.getEntityByUniqueId(target2)).getHeight(),0);
                    Utills.moveToward(arrow, loc,2);
                }

            },1);
        }



        if (e.getMessage().equalsIgnoreCase("Im A Swordsman!")){
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                p.performCommand("reset");
                pd.set("cla", "Swordsman");
                pd.set("lvl",200);
                pd.set("maxhp",pd.maxhp+2250);
                pd.set("hp",pd.maxhp);
                pd.set("maxmana",pd.maxmana+1750);
                pd.set("mana",pd.maxmana);
                pd.set("str",pd.str+170);
                pd.set("spe",pd.spe+30);

                p.sendMessage("Yes! YOU ARE!");
            },2);

        }


        if (e.getMessage().equalsIgnoreCase("Im A Archer!")){
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                p.performCommand("reset");
                pd.set("cla", "Archer");
                pd.set("maxhp",pd.maxhp);
                pd.set("hp",pd.maxhp);
//                    pd.set("maxmana",pd.maxmana+20);
//                    pd.set("mana",pd.maxmana);
                pd.set("spe",pd.spe+14);

                p.sendMessage("Yes! YOU ARE!");
            },2);



        }

        if (e.getMessage().equalsIgnoreCase("Money!")){
            for (Location loc : Utills.generateCyl(p.getLocation(),3,true)){
                Utills.SpawnGoblinSwordsman(20,loc);
            }
            for (Location loc : Utills.generateCyl(p.getLocation(),8,true)){
                Utills.SpawnGoblinArcher(20,loc);
            }
        }

        if (e.getMessage().equalsIgnoreCase("Goob")){
            Utills.SpawnGoblinSwordsman(10,p.getLocation().add(p.getLocation().getDirection().multiply(5)));
//            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//                @Override
//                public void run() {
//                    NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Utills.chat("&6&lGoblin Swordsman"));
//                    npc.addTrait(SentinelTrait.class);
//                    SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
//                    npc.addTrait(CatTrait.class);
//
//                    npc.data().set("Class","Swordsman");
//
//                    Random rand = new Random();
//                    int level = rand.nextInt(3)+1;
//                    int statpoints= level*2;
//                    npc.data().set("MaxHealth",15 + (rand.nextInt(statpoints)*10));
//                    npc.data().set("Health", npc.data().get("MaxHealth"));
//                    npc.data().set("MaxMana", 10 + rand.nextInt(statpoints));
//                    npc.data().set("Mana", npc.data().get("MaxMana"));
//                    npc.data().set("Strength", 2 + rand.nextInt(statpoints));
//                    npc.data().set("Defence", rand.nextInt(statpoints));
//
//
//                    npc.data().set("Monster", true);
//
//                    sentinel.damage=0;
//
//                    sentinel.addTarget(SentinelTarget.PLAYERS.name());
//
//                    npc.spawn(p.getLocation().add(p.getLocation().getDirection().multiply(5)));
//                    sentinel.getLivingEntity().getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
//                    SkinnableEntity skinnableEntity = npc.getEntity() instanceof SkinnableEntity ? (SkinnableEntity)npc.getEntity() : null;
//                    skinnableEntity.setSkinPersistent("Goblin Swordsman","K3qkEW4N0gLkH8sTpNCaJOxinquY101pNbQAje2AxicSfNCb6Ml1BLaxxrztVF3lhbAX5bwAxt2X0z4ViF9vs6GkcrmaOzLC4NhzxPS0RaR/GnITO0tGkTVjJf5j+Lp2S+ICzuipT3+3ytw++MhxR2f+llcpjWyA0z+WPrwTcgE0yFaw+JWjKWvAqKDk/O/Jax+qtX+IthUmZf/pIE0oGZiGFRsktnFQawD+iF4DM70r2jWDxa1dw1Y6+hFqFP5VaaRAxVAPzOI30srCh0UxKxZabI9H9bW9sUJDXh4jOUpZjJdF627nWw+Hw1aeA5mEwvdB1PNqFAZEuoKOBQqOMxfYhno6Ts0370bo+mkC+rskrWEnJAnd3qLLeR7uY95vVds//gdKPz9yNIuWiAPUKfV/Ynu6EpH5CtJai9Ww2X4wCJrPU2kXLZ3Wev+bjZlN0H8pZ5ShwvACao4BUDHKk9wPebVlBZA04rPp+ahm/WDcVZeNNCub4Gow6QobbnRg8YLz7+WobsbZM189frSw9iORVBN+ZNwP8mKFt/TCcu/zY7s6YOqkczBcFaKczzolWOQhJtcXK5q1RksDCokGrJg8fDiffj2WLTE0qPHlC4InzC2f93D8k4q8k+fFvmb7HqOib6c/OIQx4oHw+cluYxBgoirgT8G6mhJmpcdZT5A=","ewogICJ0aW1lc3RhbXAiIDogMTYwNDk3ODM0MzU4MywKICAicHJvZmlsZUlkIiA6ICI5ZDEzZjcyMTcxM2E0N2U0OTAwZTMyZGVkNjBjNDY3MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUYWxvZGFvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkNmQ1OWU0MmIyYWMwMjEyNDQyZGIxYjAwY2UzZTY3NzE0NTM1MTJiN2NiNGZhNDcyZjMxY2VlYjRhOTk1NWIiCiAgICB9CiAgfQp9");
//
//                    int MaxHp = npc.data().get("MaxHealth");
//                    int Hp = npc.data().get("Health");
//                    int MaxMana = npc.data().get("MaxMana");
//                    int Mana = npc.data().get("Mana");
//                    int Str = npc.data().get("Strength");
//                    int Def = npc.data().get("Defence");
//
//
//                    p.sendMessage("MaxHp: "+ MaxHp);
//                    p.sendMessage("MaxMana: "+MaxMana);
//                    p.sendMessage("Str: " +Str);
//                    new BukkitRunnable() {
//                        @Override
//                        public void run() {
//                            if(npc.isSpawned()==false){
//                                npc.destroy();
//                                cancel();
//                            }
//                            int j = 1;
//                            if (((1/100)*MaxMana)>0){
//                                j=((int) (1/100)+MaxMana);
//                            }
//                            npc.data().set("Mana", Mana+j);
//                            if (npc.data().has("Skill"+"."+"Slash"+"."+"Cooldown"+":")){
//                                if ((int)npc.data().get("Skill"+"."+"Slash"+"."+"Cooldown"+":")>0){
//                                    npc.data().set("Skill"+"."+"Slash"+"."+"Cooldown"+":",(int)npc.data().get("Skill"+"."+"Slash"+"."+"Cooldown"+":")-1);
//                                }else{
//                                    npc.data().remove("Skill"+"."+"Slash"+"."+"Cooldown"+":");
//                                }
//                            }else{
//                                //Slash skill
//                                if (Mana>=2){
//                                    npc.data().set("Mana", Mana-2);
//                                    float sp = 0.5F;
//                                    Location loc1 =sentinel.getLivingEntity().getLocation().add(sentinel.getLivingEntity().getLocation().getDirection().add(new Vector(0,1.5,0)).multiply(2));
//                                    Location loc2 =sentinel.getLivingEntity().getLocation().add(sentinel.getLivingEntity().getLocation().getDirection().multiply(2));
//                                    double distance = loc1.distance(loc2);
//                                    Vector p1 = loc1.toVector();
//                                    Vector p2 = loc2.toVector();
//                                    Vector vector = p2.clone().subtract(p1).normalize().multiply(sp);
//                                    double length = 0;
//                                    for (; length < distance; p1.add(vector)) {
//                                        Location loc = new Location(p.getWorld(),p1.getX(), p1.getY(), p1.getZ());
//                                        sentinel.getLivingEntity().getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,0,0,0,0, 0);
//                                        for (Entity entity :  p.getWorld().getNearbyEntities(loc,2,2,2)){
//                                            if (entity instanceof LivingEntity){
//                                                LivingEntity lv = (LivingEntity) entity;
//                                                if (lv != sentinel.getLivingEntity()){
//                                                    if (CitizensAPI.getNPCRegistry().isNPC(lv)){
//                                                        if (!CitizensAPI.getNPCRegistry().getNPC(lv).data().has("Monster")){
//                                                            lv.damage(Str/2,sentinel.getLivingEntity());
//                                                        }
//                                                    }else{
//                                                        lv.damage(Str/2,sentinel.getLivingEntity());
//                                                    }
//
//                                                }
//                                            }
//                                        }
//                                        length += sp;
//                                    }
//                                    npc.data().set("Skill"+"."+"Slash"+"."+"Cooldown"+":",3);
//                                }
//                            }
//                        }
//                    }.runTaskTimer(plugin,0,20);
//                }
//            },10);
        }


        if (e.getMessage().equalsIgnoreCase("Goob2")){
            Utills.SpawnGoblinArcher(7,p.getLocation().add(p.getLocation().getDirection().multiply(5)));
//            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//                @Override
//                public void run() {
//                    NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Utills.chat("&7&lGoblin Archer"));
//                    npc.addTrait(SentinelTrait.class);
//                    SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
//                    npc.addTrait(CatTrait.class);
//
//                    npc.data().set("Class","Archer");
//
//                    Random rand = new Random();
//                    int level = rand.nextInt(3)+1;
//                    int statpoints= level*2;
//                    npc.data().set("MaxHealth",15 + (rand.nextInt(statpoints)*10));
//                    npc.data().set("Health", npc.data().get("MaxHealth"));
//                    npc.data().set("MaxMana", 10 + rand.nextInt(statpoints));
//                    npc.data().set("Mana", npc.data().get("MaxMana"));
//                    npc.data().set("Speed", 2 + rand.nextInt(statpoints));
//                    npc.data().set("Defence", rand.nextInt(statpoints));
//
//                    npc.data().set("Skill" + "." + "Surehit" + "." + "Active" + ":", false);
//
//                    npc.data().set("Monster", true);
//
//                    sentinel.damage=0;
//
//                    sentinel.addTarget(SentinelTarget.PLAYERS.name());
//                    sentinel.addAvoid(SentinelTarget.PLAYERS.name());
//
//                    npc.spawn(p.getLocation().add(p.getLocation().getDirection().multiply(5)));
//                    sentinel.getLivingEntity().getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
//                    SkinnableEntity skinnableEntity = npc.getEntity() instanceof SkinnableEntity ? (SkinnableEntity)npc.getEntity() : null;
//                    skinnableEntity.setSkinPersistent("Goblin Swordsman","K3qkEW4N0gLkH8sTpNCaJOxinquY101pNbQAje2AxicSfNCb6Ml1BLaxxrztVF3lhbAX5bwAxt2X0z4ViF9vs6GkcrmaOzLC4NhzxPS0RaR/GnITO0tGkTVjJf5j+Lp2S+ICzuipT3+3ytw++MhxR2f+llcpjWyA0z+WPrwTcgE0yFaw+JWjKWvAqKDk/O/Jax+qtX+IthUmZf/pIE0oGZiGFRsktnFQawD+iF4DM70r2jWDxa1dw1Y6+hFqFP5VaaRAxVAPzOI30srCh0UxKxZabI9H9bW9sUJDXh4jOUpZjJdF627nWw+Hw1aeA5mEwvdB1PNqFAZEuoKOBQqOMxfYhno6Ts0370bo+mkC+rskrWEnJAnd3qLLeR7uY95vVds//gdKPz9yNIuWiAPUKfV/Ynu6EpH5CtJai9Ww2X4wCJrPU2kXLZ3Wev+bjZlN0H8pZ5ShwvACao4BUDHKk9wPebVlBZA04rPp+ahm/WDcVZeNNCub4Gow6QobbnRg8YLz7+WobsbZM189frSw9iORVBN+ZNwP8mKFt/TCcu/zY7s6YOqkczBcFaKczzolWOQhJtcXK5q1RksDCokGrJg8fDiffj2WLTE0qPHlC4InzC2f93D8k4q8k+fFvmb7HqOib6c/OIQx4oHw+cluYxBgoirgT8G6mhJmpcdZT5A=","ewogICJ0aW1lc3RhbXAiIDogMTYwNDk3ODM0MzU4MywKICAicHJvZmlsZUlkIiA6ICI5ZDEzZjcyMTcxM2E0N2U0OTAwZTMyZGVkNjBjNDY3MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUYWxvZGFvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkNmQ1OWU0MmIyYWMwMjEyNDQyZGIxYjAwY2UzZTY3NzE0NTM1MTJiN2NiNGZhNDcyZjMxY2VlYjRhOTk1NWIiCiAgICB9CiAgfQp9");
//
//                    int MaxHp = npc.data().get("MaxHealth");
//                    int Hp = npc.data().get("Health");
//                    int MaxMana = npc.data().get("MaxMana");
//                    int Mana = npc.data().get("Mana");
//                    int Spe = npc.data().get("Speed");
//                    int Def = npc.data().get("Defence");
//
//
//                    p.sendMessage("MaxHp: "+ MaxHp);
//                    p.sendMessage("MaxMana: "+MaxMana);
//                    p.sendMessage("Spe: " +Spe);
//
//                    new BukkitRunnable() {
//                        @Override
//                        public void run() {
//                            if(npc.isSpawned()==false){
//                                npc.destroy();
//                                cancel();
//                            }
//                            int j = 1;
//                            if (((1/100)*MaxMana)>0){
//                                j=((int) (1/100)+MaxMana);
//                            }
//                            npc.data().set("Mana", Mana+j);
//                            if (npc.data().has("Skill"+"."+"Surehit"+"."+"Cooldown"+":")){
//                                if ((int)npc.data().get("Skill"+"."+"Surehit"+"."+"Cooldown"+":")>0){
//                                    npc.data().set("Skill"+"."+"Surehit"+"."+"Cooldown"+":",(int)npc.data().get("Skill"+"."+"Surehit"+"."+"Cooldown"+":")-1);
//                                }else{
//                                    npc.data().remove("Skill"+"."+"Surehit"+"."+"Cooldown"+":");
//                                }
//                            }else{
//                                //Slash skill
//                                if (Mana>=10){
//                                    npc.data().set("Mana", Mana-10);
//
//                                    if (npc.data().get("Skill" + "." + "Surehit" + "." + "Active" + ":").equals(false)){
//                                        npc.data().set("Skill" + "." + "Surehit" + "." + "Active" + ":", true);
////                                        npc.getEntity().getServer().broadcastMessage("Skill On!");
//                                    }
//                                    if(!npc.data().has("Skill"+"."+"Surehit"+"."+"Target"+":")){
//                                        npc.data().set("Skill"+"."+"Surehit"+"."+"Target"+":",false);
//                                    }
//
//                                    int per = 8 + ((int) (Spe / 15));
//                                    sentinel.getLivingEntity().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, (per-2)*20, 1, false, false));
//                                    new BukkitRunnable() {
//                                        int secends = 0;
//
//
//                                        @Override
//                                        public void run() {
//                                            secends++;
//                                            if (secends == per) {
//                                                cancel();
////                                                npc.getEntity().getServer().broadcastMessage("Skill done");
//                                                if (npc.data().get("Skill" + "." + "Surehit" + "." + "Active" + ":").equals(true)){
//                                                    npc.data().set("Skill" + "." + "Surehit" + "." + "Active" + ":", false);
//                                                }
//                                                if (!npc.data().get("Skill" + "." + "Surehit" + "." + "Target" + ":").equals(false)){
//                                                    npc.data().remove("Skill"+"."+"Surehit"+"."+"Target"+":");
////                                                    npc.getEntity().getServer().broadcastMessage("Removing target setting false");
//                                                }
//                                            }
//                                        }
//                                    }.runTaskTimer(Fabulus.plugin, 0, 20);
//                                }
//                                    npc.data().set("Skill"+"."+"Surehit"+"."+"Cooldown"+":",15);
//                                }
//                            }
//                    }.runTaskTimer(plugin,0,20);
//                }
//            },10);
        }

    }





}
