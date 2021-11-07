package com.epam.esm.restapibasics.service.exception;

public class EntityNotFoundException extends RuntimeException {
    private final long entityId;

    private final Class<?> causeEntity;

    public EntityNotFoundException(Class<?> causeEntity) {
        this(null, causeEntity);
    }

    public EntityNotFoundException(Long entityId, Class<?> causeEntity) {
        this.entityId = entityId;
        this.causeEntity = causeEntity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public Class<?> getCauseEntity() {
        return causeEntity;
    }
}
