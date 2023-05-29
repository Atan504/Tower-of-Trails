package ToT.ClassManagement.Classes.UniqeSkills.Archer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.metadata.FixedMetadataValue;

import static ToT.Main.plugin;

public class ArrowHandler implements Listener {
    @EventHandler
    public void Ha(EntityDamageByEntityEvent e){
        if (e.getDamager().getType() != EntityType.ARROW ) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;
        if (e.getEntity().isDead()) return;
        Arrow arrow = (Arrow) e.getDamager();
        LivingEntity pa = (LivingEntity) arrow.getShooter();

        if (arrow.hasMetadata("ArrowType:")){
            if (arrow.getMetadata("ArrowType:").get(0).asString().equals("AutoTarget")){
                assert pa != null;
                pa.setMetadata("ArrowAutoTarget:",new FixedMetadataValue(plugin,e.getEntity().getUniqueId().toString()));
                if (pa instanceof Player){
                    Player p = (Player) pa;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Arrow Target Has Been Set"));
                }
            }
        }



    }

    @EventHandler
    public void onDraw(PlayerInteractEvent e) {
        //On interact
        if(e.getItem() != null && e.getItem().getType() == Material.BOW) {
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.getPlayer().setMetadata("IsPlayerDrawingHisBow", new FixedMetadataValue(plugin,true));
            }
        }
    }
    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player) {
            Player shooter = (Player) e.getEntity();
            if(shooter.hasMetadata("IsPlayerDrawingHisBow")) {
                if (shooter.getMetadata("IsPlayerDrawingHisBow").get(0).asBoolean()){
                    shooter.setMetadata("IsPlayerDrawingHisBow", new FixedMetadataValue(plugin,"Shot"));
                }
            }
        }
    }
    @EventHandler
    public void onChangeSlot(PlayerItemHeldEvent e)  {
        if(e.getPlayer().hasMetadata("IsPlayerDrawingHisBow")) {
            if (e.getPlayer().getMetadata("IsPlayerDrawingHisBow").get(0).asBoolean()){
                e.getPlayer().setMetadata("IsPlayerDrawingHisBow", new FixedMetadataValue(plugin,false));
            }
        }
    }
}
