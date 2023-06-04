package ToT.Objects;

import ToT.Quests.QuestManger;

import java.io.Serializable;
import java.util.UUID;

public class Player extends Entity implements Serializable {

    private QuestManger qm;
    public Player(UUID uuid) {
        super(uuid);
        qm = new QuestManger(uuid);

    }
}
