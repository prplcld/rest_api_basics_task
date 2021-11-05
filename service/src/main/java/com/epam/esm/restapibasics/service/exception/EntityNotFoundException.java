package com.epam.esm.restapibasics.service.exception;

public class EntityNotFoundException extends RuntimeException {
    private final long entityId;

    public EntityNotFoundException(long entityId) {
        this.entityId = entityId;
    }

    public long getEntityId() {
        return entityId;
    }
}
