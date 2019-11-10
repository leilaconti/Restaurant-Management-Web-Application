package team14.arms.backend.data.entity.util;

import team14.arms.backend.data.entity.AbstractEntity;

public final class EntityUtil {
    public static final String getName(Class<? extends AbstractEntity> type) {
        return type.getSimpleName();
    }
}
