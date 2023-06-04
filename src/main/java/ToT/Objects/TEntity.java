package ToT.Objects;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
    private UUID uuid;

    public Entity(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
