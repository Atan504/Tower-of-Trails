package Atan.Quests;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class QuestState implements Serializable {
    private Quest Quest;
    private String info;
    private ItemStack icon;

    public QuestState(String info, Quest q) {
        this.info = info;
        this.Quest =q;
    }

    public QuestState(Atan.Quests.Quest quest, String info, ItemStack icon) {
        Quest = quest;
        this.info = info;
        this.icon = icon;
    }

    public void finish(){
        Quest.progress();
    }

    public String getInfo() {
        return info;
    }

    public Atan.Quests.Quest getQuest() {
        return Quest;
    }
}
