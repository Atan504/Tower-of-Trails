package ToT.Objects;

import java.io.Serializable;
import java.util.UUID;

public class TEntity implements Serializable {
    private final UUID uuid;

    public TEntity(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
