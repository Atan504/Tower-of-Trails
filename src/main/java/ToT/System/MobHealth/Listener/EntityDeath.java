package ToT.System.MobHealth.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static ToT.System.MobHealth.Util.cancelHealthDisplayRemovalTask;
import static ToT.System.MobHealth.Util.removeHealthDisplay;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        // Remove the health display and the scheduled removal task when the entity dies
        removeHealthDisplay(entity);
        cancelHealthDisplayRemovalTask(entity);
    }

}
