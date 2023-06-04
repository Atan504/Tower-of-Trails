package ToT.Objects;

import ToT.Quests.QuestManger;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.UUID;

public class TPlayer extends TEntity implements Serializable {

    ItemStack[] armors;
    ItemStack weapon;
    int[] stats;

    private QuestManger qm;
    public TPlayer(UUID uuid) {
        super(uuid);
        qm = new QuestManger(uuid);
        this.armors = new ItemStack[4];
        this.weapon = null;
        this.stats = new int[6];
    }

    public QuestManger getQm() {
        return qm;
    }

    public ItemStack[] getArmors() {
        return armors;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public void setWeapon(ItemStack weapon) {
        this.weapon = weapon;
    }

    public int[] getStats() {
        return stats;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }
}
