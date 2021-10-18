package com.epam.esm.rest_api_basics.model.dao.exception;

public class EntityNotFoundException extends RuntimeException {
    private final long entityId;

    public EntityNotFoundException(long entityId) {
        this.entityId = entityId;
    }

    public long getEntityId() {
        return entityId;
    }
}
