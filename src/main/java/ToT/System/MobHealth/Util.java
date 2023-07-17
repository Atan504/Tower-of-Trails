package ToT.System.MobHealth;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static ToT.Main.plugin;

public class Util {

    public static Map<Entity, ArmorStand> healthDisplays = new HashMap<>();
    public static Map<Entity, BukkitTask> displayTasks = new HashMap<>();

    public static void updateHealthDisplay(LivingEntity entity, double remainingHealth, double maxHealth, double damage) {
        ArmorStand armorStand = healthDisplays.get(entity);
        Location location = entity.getLocation().add(0, entity.getHeight() + 0.3, 0);

        if (armorStand == null) {
            armorStand = spawnHealthDisplay(entity, location);
            healthDisplays.put(entity, armorStand);
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String healthText = decimalFormat.format(remainingHealth) + "/" + decimalFormat.format(maxHealth);

        armorStand.setCustomName(ChatColor.RED + healthText);
    }

    public static ArmorStand spawnHealthDisplay(LivingEntity entity, Location location) {
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);

        armorStand.setMarker(true);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("");

        // Make the armor stand persistent and invulnerable
        armorStand.setPersistent(true);
        armorStand.setInvulnerable(true);

        // Prevent the armor stand from being pushed by other entities
        armorStand.setGravity(false);

        // Disable equipment and AI of the armor stand
        armorStand.getEquipment().clear();
        armorStand.setAI(false);

        return armorStand;
    }

    public static void removeHealthDisplay(Entity entity) {
        ArmorStand armorStand = healthDisplays.remove(entity);
        if (armorStand != null) {
            armorStand.remove();
        }
    }

    public static void scheduleHealthDisplayRemoval(LivingEntity entity) {
        BukkitTask task = displayTasks.get(entity);
        if (task != null) {
            task.cancel();
        }

        task = new BukkitRunnable() {
            @Override
            public void run() {
                removeHealthDisplay(entity);
                cancelHealthDisplayRemovalTask(entity);
            }
        }.runTaskLater(plugin, 60L); // 3 seconds = 60 ticks

        displayTasks.put(entity, task);
    }

    public static void cancelHealthDisplayRemovalTask(Entity entity) {
        BukkitTask task = displayTasks.remove(entity);
        if (task != null) {
            task.cancel();
        }
    }

}
