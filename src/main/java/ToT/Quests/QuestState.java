package ToT.Quests;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class QuestState implements Serializable {
    private final Quest Quest;
    private final String info;
    private ItemStack icon;

    public QuestState(String info, Quest q) {
        this.info = info;
        this.Quest =q;
    }

    public QuestState(ToT.Quests.Quest quest, String info, ItemStack icon) {
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

    public ToT.Quests.Quest getQuest() {
        return Quest;
    }
}
