package ToT.Quests;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public class QuestManger implements Serializable {
    private UUID pUUID;
    private LinkedList<Quest> Quests;

    public QuestManger(UUID pUUID) {
        this.pUUID = pUUID;
        Quests= new LinkedList<>();
    }
    public UUID getpUUID() {
        return pUUID;
    }

    public void add(Quest q){
        Quests.add(q);
    }
    public Quest getByName(String name){
        Quest temp = null;
        int count=0;
        while (temp.getName()!=name){
            temp=Quests.get(count);
        }
        return temp;
    }

}
