package ToT.Listener;

import ToT.Objects.TPlayer;
import ToT.Utils.PartyManagment;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause murder = event.getCause();
        Player player = (Player) event.getEntity();
        int damage = (int) event.getDamage();

        event.setCancelled(true);

        TPlayer data = PartyManagment.getData(player.getUniqueId());
        int[] stats = data.getStats();
        int[] statPoints = data.getStatPoints();

        System.out.println(damage);
        int hp = stats[3] - 1;
        if(hp < 0) hp = 0;
        System.out.println(hp);

        statPoints[3] = hp;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity murder = event.getDamager();
        Player player = (Player) event.getEntity();
        int damage = (int) event.getDamage();

        event.setCancelled(true);
    }
}
