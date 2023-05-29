//package fabulus.fabulus.Work;
//
//import Fabulus.Fabulus.Z_Essentials.A_HighP.Menus;
//import Fabulus.Fabulus.Z_Essentials.A_HighP.PlayerData;
//import Fabulus.Fabulus.Z_Essentials.B_MidP.Utills;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.block.Action;
//import org.bukkit.event.player.PlayerInteractEvent;
//import org.bukkit.inventory.EquipmentSlot;
//
//public class PlayerHandler implements Listener {
//    @EventHandler
//    public void HandleEvent(PlayerInteractEvent e) {
//        Player p = e.getPlayer();
//
//        //DATA
//        PlayerData pd = new PlayerData(p.getUniqueId());
//        //MENU ITEM HANDLER
//        if (e.getHand() == EquipmentSlot.HAND && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getDisplayName() != null && p.getItemInHand().getItemMeta().getDisplayName().equals(Utills.chat("&l&9Profile Menu"))) {
//            e.setCancelled(true);
//            if (pd.set == false) {
//                Menus.OpenMenu(p, "charactercreator");
//            } else {
//                Menus.OpenMenu(p, "profilemenu");
//            }
//        }
//        //ABILITY SCROLLER
//        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            if (e.getHand() == EquipmentSlot.HAND && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getDisplayName() != null && p.getItemInHand().getItemMeta().getDisplayName().equals(Utills.chat("&l&0Class Controller")) && p.isSneaking()==true){
//
//            }
//        }
//
//    }
//}