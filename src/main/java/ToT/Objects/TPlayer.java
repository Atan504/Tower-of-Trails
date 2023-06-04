package ToT.Objects;

import ToT.Quests.QuestManger;

import java.io.Serializable;
import java.util.UUID;

public class TPlayer extends TEntity implements Serializable {

    private QuestManger qm;
    public TPlayer(UUID uuid) {
        super(uuid);
        qm = new QuestManger(uuid);

    }

    public QuestManger getQm() {
        return qm;
    }
}
