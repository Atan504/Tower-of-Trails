package ToT.Objects;

import ToT.Quests.QuestManger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TPlayer extends TEntity implements Serializable {

    ItemStack[] armors;
    ItemStack weapon;
    int[] stats;
    int[] points;
    String[] skills;

    List<Player> party;

    private final QuestManger qm;
    public TPlayer(UUID uuid) {
        super(uuid);
        qm = new QuestManger(uuid);
        this.party = new ArrayList<>(); // currently max party is 4 players;
        this.armors = new ItemStack[4];     // helmet, chestplate, leggings, boots
        this.weapon = null;
        this.stats = new int[8];            // mana, max_mana, str, hp, max_hp, def, magic, speed
        this.points = new int[7];           // points, mana, str, hp, def, magic, speed
        this.skills = new String[5];        // slot#3, slot#4, slot#5, slot#6, slot#6
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

    public int[] getStats() { return stats; }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public int[] getPoints() {
        return points;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }

    public List<Player> getParty() {
        return party;
    }

    public void setParty(List<Player> party) {
        this.party = party;
    }
}
