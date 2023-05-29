package ToT.Commands;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.Iterator;

import static ToT.Main.plugin;


public class jreload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.isOp()){


                for(Iterator<NPC> itr = CitizensAPI.getNPCRegistry().iterator(); itr.hasNext(); itr.remove()) {
                    NPC npc = itr.next();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        try {
                            if (npc.data().has("Monster")){
                                if (npc.data().get("Monster").equals(true)){
                                    npc.addTrait(SentinelTrait.class);
                                    SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
                                    sentinel.getLivingEntity().damage(999999);
                                }
                            }
                        } catch (Throwable var4) {
                            var4.printStackTrace();
                        }
                    }, 5);
                }
            }
        }

        return true;
    }
}
