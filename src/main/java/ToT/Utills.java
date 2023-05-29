package ToT;

import ToT.ClassManagement.Skill;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.mcmonkey.sentinel.SentinelTrait;
import org.mcmonkey.sentinel.targeting.SentinelTarget;

import java.util.*;

import static ToT.Main.plugin;

public class Utills {
    //Misc
    public static String chat (String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    //Itemsstacks
    public static ItemStack createItemByte(Inventory inv, String materialString, int byteId, int amount, int invSlot, String displayName, String... loreString){
        ItemStack item;
        List<String> lore = new ArrayList<>();

        item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(materialString)), amount,(byte) byteId);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(Utills.chat(displayName));
        for (String s : loreString) {
            lore.add(Utills.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot - 1, item);
        return item;
    }
    public static ItemStack createItem(Inventory inv, String materialString, int amount, int invSlot, String displayName, String... loreString) {
        List<String> lore = new ArrayList<>();
        ItemStack item;
        item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(materialString)), amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(Utills.chat(displayName));
        for (String s : loreString) {
            lore.add(Utills.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot - 1, item);
        return item;
    }
    //Block, Vectors, Locations And Shapes
    public static boolean isZeroVelocity(final Vector velocityVector) {
        return (velocityVector.getX() < 0.5) && (velocityVector.getY() == 0)
                && (velocityVector.getZ() < 0.5);
    }
    public static void moveToward(Entity entity, Location to, double speed){
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity);
    }
    public static void moveTowardTp(Entity entity, Location to, double speed){
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.teleport(velocity.toLocation(entity.getWorld()));
    }
    public static void moveAwayFrom(Entity entity, Location from, double speed){
        Location loc = entity.getLocation();
        double x = loc.getX() - from.getX();
        double y = loc.getY() - from.getY();
        double z = loc.getZ() - from.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity.multiply(-1));
    }
    public static Vector getRightHeadDirection(LivingEntity player) {
        Vector direction = player.getLocation().getDirection().normalize();
        return new Vector(-direction.getZ(), 0.0, direction.getX()).normalize();
    }
    public static Vector getLeftHeadDirection(LivingEntity player) {
        Vector direction = player.getLocation().getDirection().normalize();
        return new Vector(direction.getZ(), 0.0, -direction.getX()).normalize();
    }

    public static void drawLine(Location point1, Location point2, double space, Particle.DustOptions dustOptions) {
        World world = point1.getWorld();
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {

            Location loc = new Location(point1.getWorld(),p1.getX(), p1.getY(), p1.getZ());
            assert world != null;
            world.spawnParticle(Particle.REDSTONE, loc, 3, dustOptions);
            length += space;
        }
    }
    public static void drawLine(Location point1, Location point2, double space,Particle particle) {
        World world = point1.getWorld();
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            assert world != null;
            world.spawnParticle(particle, p1.getX(), p1.getY(), p1.getZ(),0,0,0,0, 1);
            length += space;
        }
    }

    public static boolean day() {
        Server server = Bukkit.getServer();
        long time = Objects.requireNonNull(server.getWorld("world")).getTime();

        return time > 0 && time < 12300;
    }
    public static List<Location> getHollowCube(Location corner1, Location corner2, double particleDistance) {
        List<Location> result = new ArrayList<>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (double x = minX; x <= maxX; x+=particleDistance) {
            for (double y = minY; y <= maxY; y+=particleDistance) {
                for (double z = minZ; z <= maxZ; z+=particleDistance) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }

        return result;
    }
    public static ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<>();
        for(int i = 0;i < amount; i++)
        {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(Objects.requireNonNull(location.getWorld()).getBlockAt(x,y, z));
                }
            }
        }
        return blocks;
    }
    public static List<Location> generateSphere(Location centerBlock, int radius, boolean hollow) {
        if (centerBlock == null) {
            return new ArrayList<>();
        }

        List<Location> circleBlocks = new ArrayList<>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {

                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));

                    if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {

                        Location l = new Location(centerBlock.getWorld(), x, y, z);

                        circleBlocks.add(l);

                    }

                }
            }
        }

        return circleBlocks;
    }
    public static List<Location> generateDomainShape(Player p, int radius, boolean hollow) {
        List<Location> circleBlocks = new ArrayList<>();
        Random random = new Random();

        if (hollow){
            circleBlocks.addAll(Utills.generateSphere(p.getLocation(), radius, false));
            circleBlocks.addAll(Utills.generateSphere(p.getLocation(), radius, true));
            circleBlocks.addAll(Utills.generateCyl(p.getLocation().subtract(0, 1, 0), radius, false));
        }else{
            circleBlocks.addAll(Utills.generateSphere(p.getLocation(), radius, false));
            circleBlocks.addAll(Utills.generateSphere(p.getLocation(), radius, false));
            circleBlocks.addAll(Utills.generateCyl(p.getLocation().subtract(0, 1, 0), radius, false));
        }
        return circleBlocks;
    }
    public static List<Location> generateCyl(Location centerBlock, int radius, boolean hollow) {
        if (centerBlock == null) {
            return new ArrayList<>();
        }

        List<Location> circleBlocks = new ArrayList<>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int z = bz - radius; z <= bz + radius; z++) {

                double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-centerBlock.getY()) * (by-centerBlock.getY())));

                if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {

                    Location l = new Location(centerBlock.getWorld(), x, centerBlock.getY(), z);

                    circleBlocks.add(l);

                }
            }
        }

        return circleBlocks;
    }
    public static void createCylinder(Block center, int radius, int height){

        for(int currentheight = 0; currentheight<height; currentheight++){ //loop through all the y values(height)
            for(int x = -radius; x<radius;x++){
                for(int z = -radius; z<radius;z++){

                }
            }
        }
    }
    //Moves And Class
//    public static Move CreateMove(String name,int cooldown,int energycost, Runnable run){
//        Move move = new Move(name,cooldown,energycost,run);
//
//        return move;
//    }


    public static Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uniqueId))
                    return entity;
            }
        }

        return null;
    }

    public static void SpawnGoblinSwordsman(int level,Location location){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Utills.chat("&6&lGoblin Swordsman ( "+level+" )"));
            npc.addTrait(SentinelTrait.class);
            SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);

            npc.data().set("Class","Swordsman");

            Random rand = new Random();
            int statpoints= level*2;


            int u = rand.nextInt(statpoints);
            npc.data().set("MaxHealth",15 + (u*10));
            npc.data().set("Health", npc.data().get("MaxHealth"));
            statpoints=statpoints-u;

            int u3 = rand.nextInt(statpoints);
            npc.data().set("MaxMana", 10 + u3);
            npc.data().set("Mana", npc.data().get("MaxMana"));
            statpoints=statpoints-u3;

            npc.data().set("Strength", 2 +statpoints);

            npc.data().set("Defence", rand.nextInt(statpoints));


            npc.data().set("Monster", true);

            sentinel.damage=0;

            sentinel.addTarget(SentinelTarget.PLAYERS.name());
            npc.spawn(location);
            Objects.requireNonNull(sentinel.getLivingEntity().getEquipment()).setItemInMainHand(new ItemStack(Material.IRON_SWORD));
            SkinnableEntity skinnableEntity = npc.getEntity() instanceof SkinnableEntity ? (SkinnableEntity)npc.getEntity() : null;
            assert skinnableEntity != null;
            skinnableEntity.setSkinPersistent("Goblin Swordsman","K3qkEW4N0gLkH8sTpNCaJOxinquY101pNbQAje2AxicSfNCb6Ml1BLaxxrztVF3lhbAX5bwAxt2X0z4ViF9vs6GkcrmaOzLC4NhzxPS0RaR/GnITO0tGkTVjJf5j+Lp2S+ICzuipT3+3ytw++MhxR2f+llcpjWyA0z+WPrwTcgE0yFaw+JWjKWvAqKDk/O/Jax+qtX+IthUmZf/pIE0oGZiGFRsktnFQawD+iF4DM70r2jWDxa1dw1Y6+hFqFP5VaaRAxVAPzOI30srCh0UxKxZabI9H9bW9sUJDXh4jOUpZjJdF627nWw+Hw1aeA5mEwvdB1PNqFAZEuoKOBQqOMxfYhno6Ts0370bo+mkC+rskrWEnJAnd3qLLeR7uY95vVds//gdKPz9yNIuWiAPUKfV/Ynu6EpH5CtJai9Ww2X4wCJrPU2kXLZ3Wev+bjZlN0H8pZ5ShwvACao4BUDHKk9wPebVlBZA04rPp+ahm/WDcVZeNNCub4Gow6QobbnRg8YLz7+WobsbZM189frSw9iORVBN+ZNwP8mKFt/TCcu/zY7s6YOqkczBcFaKczzolWOQhJtcXK5q1RksDCokGrJg8fDiffj2WLTE0qPHlC4InzC2f93D8k4q8k+fFvmb7HqOib6c/OIQx4oHw+cluYxBgoirgT8G6mhJmpcdZT5A=","ewogICJ0aW1lc3RhbXAiIDogMTYwNDk3ODM0MzU4MywKICAicHJvZmlsZUlkIiA6ICI5ZDEzZjcyMTcxM2E0N2U0OTAwZTMyZGVkNjBjNDY3MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUYWxvZGFvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkNmQ1OWU0MmIyYWMwMjEyNDQyZGIxYjAwY2UzZTY3NzE0NTM1MTJiN2NiNGZhNDcyZjMxY2VlYjRhOTk1NWIiCiAgICB9CiAgfQp9");

            int MaxHp = npc.data().get("MaxHealth");
            int Hp = npc.data().get("Health");
            int MaxMana = npc.data().get("MaxMana");
            int Mana = npc.data().get("Mana");
            int Str = npc.data().get("Strength");
            int Def = npc.data().get("Defence");

            Bukkit.getServer().broadcastMessage(String.valueOf(MaxHp));
            Bukkit.getServer().broadcastMessage(String.valueOf(Str));
            Bukkit.getServer().broadcastMessage(String.valueOf(Mana));


            new BukkitRunnable() {
                @Override
                public void run() {
                    if(!npc.isSpawned()){
                        npc.destroy();
                        cancel();
                    }
                    int j = 1;
                    if (Mana<0){
                        npc.data().set("Mana", 0);
                    }
                    npc.data().set("Mana", Mana+j);
                    if (npc.data().has("Skill"+"."+"Slash"+"."+"Cooldown"+":")){
                        if ((int)npc.data().get("Skill"+"."+"Slash"+"."+"Cooldown"+":")>0){
                            npc.data().set("Skill"+"."+"Slash"+"."+"Cooldown"+":",(int)npc.data().get("Skill"+"."+"Slash"+"."+"Cooldown"+":")-1);
                        }else{
                            npc.data().remove("Skill"+"."+"Slash"+"."+"Cooldown"+":");
                        }
                    }else{
                        //Slash skill
                        if (Mana>=4){
                            npc.data().set("Mana", Mana-4);
                            float sp = 0.5F;
                            Location loc1 =sentinel.getLivingEntity().getLocation().add(sentinel.getLivingEntity().getLocation().getDirection().add(new Vector(0,1.5,0)).multiply(2));
                            Location loc2 =sentinel.getLivingEntity().getLocation().add(sentinel.getLivingEntity().getLocation().getDirection().multiply(2));
                            double distance = loc1.distance(loc2);
                            Vector p1 = loc1.toVector();
                            Vector p2 = loc2.toVector();
                            Vector vector = p2.clone().subtract(p1).normalize().multiply(sp);
                            double length = 0;
                            for (; length < distance; p1.add(vector)) {
                                Location loc = new Location(location.getWorld(),p1.getX(), p1.getY(), p1.getZ());
                                sentinel.getLivingEntity().getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,0,0,0,0, 0);
                                for (Entity entity :  Objects.requireNonNull(location.getWorld()).getNearbyEntities(loc,2,2,2)){
                                    if (entity instanceof LivingEntity){
                                        LivingEntity lv = (LivingEntity) entity;
                                        if (lv != sentinel.getLivingEntity()){
                                            if (CitizensAPI.getNPCRegistry().isNPC(lv)){
                                                if (!CitizensAPI.getNPCRegistry().getNPC(lv).data().has("Monster")){
                                                    lv.damage((double) Str /2,sentinel.getLivingEntity());
                                                }
                                            }else{
                                                lv.damage((double) Str /2,sentinel.getLivingEntity());
                                            }

                                        }
                                    }
                                }
                                length += sp;
                            }
                            npc.data().set("Skill"+"."+"Slash"+"."+"Cooldown"+":",3);
                        }
                    }
                }
            }.runTaskTimer(plugin,0,20);
        },10);
    }
    public static void SpawnGoblinArcher(int level,Location location){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Utills.chat("&7&lGoblin Archer ( "+level+" )"));
            npc.addTrait(SentinelTrait.class);
            SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);

            npc.data().set("Class","Archer");

            Random rand = new Random();
            int statpoints= level*2;
            npc.data().set("MaxHealth",15 + (rand.nextInt(statpoints)*10));
            npc.data().set("Health", npc.data().get("MaxHealth"));
            npc.data().set("MaxMana", 10 + rand.nextInt(statpoints));
            npc.data().set("Mana", npc.data().get("MaxMana"));
            npc.data().set("Speed", 2 + rand.nextInt(statpoints));
            npc.data().set("Defence", rand.nextInt(statpoints));

            npc.data().set("Skill" + "." + "Surehit" + "." + "Active" + ":", false);

            npc.data().set("Monster", true);

            sentinel.damage=0;

            sentinel.addTarget(SentinelTarget.PLAYERS.name());
            sentinel.addAvoid(SentinelTarget.PLAYERS.name());

            npc.spawn(location.add(location.getDirection()));
            Objects.requireNonNull(sentinel.getLivingEntity().getEquipment()).setItemInMainHand(new ItemStack(Material.BOW));
            SkinnableEntity skinnableEntity = npc.getEntity() instanceof SkinnableEntity ? (SkinnableEntity)npc.getEntity() : null;
            assert skinnableEntity != null;
            skinnableEntity.setSkinPersistent("Goblin Swordsman","K3qkEW4N0gLkH8sTpNCaJOxinquY101pNbQAje2AxicSfNCb6Ml1BLaxxrztVF3lhbAX5bwAxt2X0z4ViF9vs6GkcrmaOzLC4NhzxPS0RaR/GnITO0tGkTVjJf5j+Lp2S+ICzuipT3+3ytw++MhxR2f+llcpjWyA0z+WPrwTcgE0yFaw+JWjKWvAqKDk/O/Jax+qtX+IthUmZf/pIE0oGZiGFRsktnFQawD+iF4DM70r2jWDxa1dw1Y6+hFqFP5VaaRAxVAPzOI30srCh0UxKxZabI9H9bW9sUJDXh4jOUpZjJdF627nWw+Hw1aeA5mEwvdB1PNqFAZEuoKOBQqOMxfYhno6Ts0370bo+mkC+rskrWEnJAnd3qLLeR7uY95vVds//gdKPz9yNIuWiAPUKfV/Ynu6EpH5CtJai9Ww2X4wCJrPU2kXLZ3Wev+bjZlN0H8pZ5ShwvACao4BUDHKk9wPebVlBZA04rPp+ahm/WDcVZeNNCub4Gow6QobbnRg8YLz7+WobsbZM189frSw9iORVBN+ZNwP8mKFt/TCcu/zY7s6YOqkczBcFaKczzolWOQhJtcXK5q1RksDCokGrJg8fDiffj2WLTE0qPHlC4InzC2f93D8k4q8k+fFvmb7HqOib6c/OIQx4oHw+cluYxBgoirgT8G6mhJmpcdZT5A=","ewogICJ0aW1lc3RhbXAiIDogMTYwNDk3ODM0MzU4MywKICAicHJvZmlsZUlkIiA6ICI5ZDEzZjcyMTcxM2E0N2U0OTAwZTMyZGVkNjBjNDY3MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUYWxvZGFvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkNmQ1OWU0MmIyYWMwMjEyNDQyZGIxYjAwY2UzZTY3NzE0NTM1MTJiN2NiNGZhNDcyZjMxY2VlYjRhOTk1NWIiCiAgICB9CiAgfQp9");

            int MaxHp = npc.data().get("MaxHealth");
            int Hp = npc.data().get("Health");
            int MaxMana = npc.data().get("MaxMana");
            int Mana = npc.data().get("Mana");
            int Spe = npc.data().get("Speed");
            int Def = npc.data().get("Defence");



            new BukkitRunnable() {
                @Override
                public void run() {
                    if(!npc.isSpawned()){
                        npc.destroy();
                        cancel();
                    }
                    int j = 1;
                    if (Mana<0){
                        npc.data().set("Mana", 0);
                    }
                    npc.data().set("Mana", Mana+j);
                    if (npc.data().has("Skill"+"."+"Surehit"+"."+"Cooldown"+":")){
                        if ((int)npc.data().get("Skill"+"."+"Surehit"+"."+"Cooldown"+":")>0){
                            npc.data().set("Skill"+"."+"Surehit"+"."+"Cooldown"+":",(int)npc.data().get("Skill"+"."+"Surehit"+"."+"Cooldown"+":")-1);
                        }else{
                            npc.data().remove("Skill"+"."+"Surehit"+"."+"Cooldown"+":");
                        }
                    }else{
                        //Slash skill
                        if (Mana>=10){
                            npc.data().set("Mana", Mana-10);

                            if (npc.data().get("Skill" + "." + "Surehit" + "." + "Active" + ":").equals(false)){
                                npc.data().set("Skill" + "." + "Surehit" + "." + "Active" + ":", true);
//                                        npc.getEntity().getServer().broadcastMessage("Skill On!");
                            }
                            if(!npc.data().has("Skill"+"."+"Surehit"+"."+"Target"+":")){
                                npc.data().set("Skill"+"."+"Surehit"+"."+"Target"+":",false);
                            }

                            int per = 8 + (Spe / 15);
                            sentinel.getLivingEntity().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, (per-2)*20, 1, false, false));
                            new BukkitRunnable() {
                                int secends = 0;


                                @Override
                                public void run() {
                                    secends++;
                                    if (secends == per) {
                                        cancel();
//                                                npc.getEntity().getServer().broadcastMessage("Skill done");
                                        if (npc.data().get("Skill" + "." + "Surehit" + "." + "Active" + ":").equals(true)){
                                            npc.data().set("Skill" + "." + "Surehit" + "." + "Active" + ":", false);
                                        }
                                        if (!npc.data().get("Skill" + "." + "Surehit" + "." + "Target" + ":").equals(false)){
                                            npc.data().remove("Skill"+"."+"Surehit"+"."+"Target"+":");
//                                                    npc.getEntity().getServer().broadcastMessage("Removing target setting false");
                                        }
                                    }
                                }
                            }.runTaskTimer(plugin, 0, 20);
                        }
                        npc.data().set("Skill"+"."+"Surehit"+"."+"Cooldown"+":",15);
                    }
                }
            }.runTaskTimer(plugin,0,20);
        },10);
    }
    public static Class CreateClass(String name, List<Skill> moves){
        return new Class(name, moves);
    }

//TODO

//    public static void CreateCustomMob(String name, EntityType entityType, String Class, int level,String mineskinorgtexturesignature,String mineskinorgtextureurl){
//        NPC npc = CitizensAPI.getNPCRegistry().createNPC(entityType, name);
//        npc.addTrait(SentinelTrait.class);
//        SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
//        npc.addTrait(CatTrait.class);
//        Random rand = new Random();
//        int statpoints= level*2;
//        npc.data().set("MaxHealth",40 + (rand.nextInt(statpoints)*10));
//        npc.data().set("Health", npc.data().get("MaxHealth"));
//        npc.data().set("MaxMana", 10 + rand.nextInt(statpoints));
//        npc.data().set("Mana", npc.data().get("MaxMana"));
//        npc.data().set("Strength", 2 + rand.nextInt(statpoints));
//
//        sentinel.addTarget(SentinelTarget.PLAYERS.name());
//
//        npc.spawn(p.getLocation().add(p.getLocation().getDirection().multiply(5).setY(p.getLocation().getY())));
//        sentinel.getLivingEntity().getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
//        SkinnableEntity skinnableEntity = npc.getEntity() instanceof SkinnableEntity ? (SkinnableEntity)npc.getEntity() : null;
//        skinnableEntity.setSkinPersistent("Goblin Swordsman","K3qkEW4N0gLkH8sTpNCaJOxinquY101pNbQAje2AxicSfNCb6Ml1BLaxxrztVF3lhbAX5bwAxt2X0z4ViF9vs6GkcrmaOzLC4NhzxPS0RaR/GnITO0tGkTVjJf5j+Lp2S+ICzuipT3+3ytw++MhxR2f+llcpjWyA0z+WPrwTcgE0yFaw+JWjKWvAqKDk/O/Jax+qtX+IthUmZf/pIE0oGZiGFRsktnFQawD+iF4DM70r2jWDxa1dw1Y6+hFqFP5VaaRAxVAPzOI30srCh0UxKxZabI9H9bW9sUJDXh4jOUpZjJdF627nWw+Hw1aeA5mEwvdB1PNqFAZEuoKOBQqOMxfYhno6Ts0370bo+mkC+rskrWEnJAnd3qLLeR7uY95vVds//gdKPz9yNIuWiAPUKfV/Ynu6EpH5CtJai9Ww2X4wCJrPU2kXLZ3Wev+bjZlN0H8pZ5ShwvACao4BUDHKk9wPebVlBZA04rPp+ahm/WDcVZeNNCub4Gow6QobbnRg8YLz7+WobsbZM189frSw9iORVBN+ZNwP8mKFt/TCcu/zY7s6YOqkczBcFaKczzolWOQhJtcXK5q1RksDCokGrJg8fDiffj2WLTE0qPHlC4InzC2f93D8k4q8k+fFvmb7HqOib6c/OIQx4oHw+cluYxBgoirgT8G6mhJmpcdZT5A=","ewogICJ0aW1lc3RhbXAiIDogMTYwNDk3ODM0MzU4MywKICAicHJvZmlsZUlkIiA6ICI5ZDEzZjcyMTcxM2E0N2U0OTAwZTMyZGVkNjBjNDY3MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUYWxvZGFvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkNmQ1OWU0MmIyYWMwMjEyNDQyZGIxYjAwY2UzZTY3NzE0NTM1MTJiN2NiNGZhNDcyZjMxY2VlYjRhOTk1NWIiCiAgICB9CiAgfQp9");
//
//        npc.despawn();
//        npc.spawn(p.getLocation().add(p.getLocation().getDirection().multiply(5).setY(p.getLocation().getY())));
//        int MaxHp = npc.data().get("MaxHealth");
//        int Hp = npc.data().get("Health");
//        int MaxMana = npc.data().get("MaxMana");
//        int Mana = npc.data().get("Mana");
//        int Str = npc.data().get("Strength");
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                if(npc.isSpawned()==false){
//                    npc.destroy();
//                    cancel();
//                }
//                int j = 1;
//                if (((1/100)*MaxMana)>0){
//                    j=((int) (1/100)+MaxMana);
//                }
//                npc.data().set("Mana", Mana+j);
//                if (npc.data().has("Skill"+"."+"Slash"+"."+"Cooldown"+":")){
//                    if ((int)npc.data().get("Skill"+"."+"Slash"+"."+"Cooldown"+":")>0){
//                        npc.data().set("Skill"+"."+"Slash"+"."+"Cooldown"+":",(int)npc.data().get("Skill"+"."+"Slash"+"."+"Cooldown"+":")-1);
//                    }else{
//                        npc.data().remove("Skill"+"."+"Slash"+"."+"Cooldown"+":");
//                    }
//                }else{
//                    //Slash skill
//                    if (Mana>=2){
//                        npc.data().set("Mana", Mana-2);
//                        float sp = 0.5F;
//                        Location loc1 =sentinel.getLivingEntity().getLocation().add(sentinel.getLivingEntity().getLocation().getDirection().add(new Vector(0,1.5,0)).multiply(2));
//                        Location loc2 =sentinel.getLivingEntity().getLocation().add(sentinel.getLivingEntity().getLocation().getDirection().multiply(2));
//                        double distance = loc1.distance(loc2);
//                        Vector p1 = loc1.toVector();
//                        Vector p2 = loc2.toVector();
//                        Vector vector = p2.clone().subtract(p1).normalize().multiply(sp);
//                        double length = 0;
//                        for (; length < distance; p1.add(vector)) {
//                            Location loc = new Location(p.getWorld(),p1.getX(), p1.getY(), p1.getZ());
//                            sentinel.getLivingEntity().getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,0,0,0,0, 0);
//                            for (Entity entity :  p.getWorld().getNearbyEntities(loc,2,2,2)){
//                                if (entity instanceof LivingEntity){
//                                    LivingEntity lv = (LivingEntity) entity;
//                                    if (lv != sentinel.getLivingEntity()){
//                                        lv.damage(Str/2,sentinel.getLivingEntity());
//                                    }
//                                }
//                            }
//                            length += sp;
//                        }
//                        npc.data().set("Skill"+"."+"Slash"+"."+"Cooldown"+":",3);
//                    }
//                }
//            }
//        }.runTaskTimer(plugin,0,20);
//    }

}
