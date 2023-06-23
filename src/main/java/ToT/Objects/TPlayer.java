package ToT.Objects;

import ToT.Quests.QuestManger;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class TPlayer extends TEntity implements Serializable {

    private final ItemStack[] armors;
    private ItemStack weapon;
    private int[] stats;
    private int[] statPoints;
    private int[] points;
    private String[] skills;
    private ArrayList<UUID> party;
    private int lvl;
    private int xp;

    private final QuestManger qm;
    public TPlayer(UUID uuid) {
        super(uuid);
        qm = new QuestManger(uuid);
        this.party = new ArrayList<>();
        this.armors = new ItemStack[4];     // helmet, chestplate, leggings, boots
        this.weapon = null;
        this.stats = new int[8]; // mana, max_mana, str, hp, max_hp, def, magic, speed
        this.statPoints = new int[]{10,10,0,20,20,0,0,0};
        this.points = new int[7];           // points, mana, str, hp, def, magic, speed
        this.skills = new String[5];        // slot#3, slot#4, slot#5, slot#6, slot#6
        this.lvl = 1;
        this.xp = 0;
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

    public int[] getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(int[] statPoints) {
        this.statPoints = statPoints;
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

    public ArrayList<UUID> getParty() {
        return party;
    }

    public void setParty(ArrayList<UUID> party) {
        this.party = party;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
