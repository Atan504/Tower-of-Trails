package ToT.Listener;

import ToT.Objects.TPlayer;
import ToT.System.PartyManagment.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.UUID;

import static ToT.Utils.Utils.getPlayerData;

public class PlayerDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause murder = event.getCause();
        Entity entity = event.getEntity();
        int damage = (int) event.getDamage();

        if(!(entity instanceof Player player)) return;

        TPlayer data = getPlayerData(player.getUniqueId());
        int[] stats = data.getStats();
        int[] statPoints = data.getStatPoints();

        int hp = stats[3] - damage;
        if(hp < 0) hp = 0;

        event.setDamage(damage);

        statPoints[3] = hp;

        ArrayList<UUID> party = Utils.getParty(player.getUniqueId());

        if(!Utils.inParty(party, player.getUniqueId())) {
            return;
        }

        ArrayList<UUID> members = Utils.getMembers(party);

        members.forEach(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) Utils.updatePartyScoreboard(p);
        });

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity murder = event.getDamager();
        Player player = (Player) event.getEntity();
        int damage = (int) event.getDamage();

        event.setCancelled(true);
    }
}
