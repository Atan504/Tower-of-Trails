package ToT.System.MobHealth.Listener;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ToT.Main.plugin;
import static ToT.System.MobHealth.Util.*;

public class EntityDamage implements Listener {

    private Map<LivingEntity, Integer> damageCountMap = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            double remainingHealth = livingEntity.getHealth() - event.getFinalDamage();
            double maxHealth = Objects.requireNonNull(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();

            if(remainingHealth <= 0) remainingHealth = 0.0;

            removeHealthDisplay(entity);

            updateHealthDisplay(livingEntity, remainingHealth, maxHealth, event.getFinalDamage());

            scheduleHealthDisplayRemoval(livingEntity);
        }
    }
}
