package com.epam.esm.restapibasics.model.entity.audit;

import org.springframework.context.annotation.Lazy;

import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.time.LocalDateTime;

import static com.epam.esm.restapibasics.model.entity.audit.Audit.Operation.*;
import static java.time.ZoneOffset.UTC;

public class AuditListener {
    private static final String CREATED_MESSAGE = "ENTITY CREATED: %s";
    private static final String UPDATED_MESSAGE = "ENTITY UPDATED: %s";
    private static final String DELETED_MESSAGE = "ENTITY DELETED: %s";

    private EntityManager entityManager;

    public AuditListener(@Lazy EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostPersist
    public void postPersist(Object entity) {
        String auditMessage = String.format(CREATED_MESSAGE, entity);

        createRecord(entity, CREATE);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        String auditMessage = String.format(UPDATED_MESSAGE, entity);

        createRecord(entity, UPDATE);
    }

    @PostRemove
    public void postRemove(Object entity) {
        String auditMessage = String.format(DELETED_MESSAGE, entity);

        createRecord(entity, DELETE);
    }

    private void createRecord(Object entity, Audit.Operation operation) {
        Audit auditEntity = new Audit();
        auditEntity.setEntityName(entity.getClass().getSimpleName());
        auditEntity.setTimestamp(LocalDateTime.now(UTC));
        auditEntity.setOperation(operation);

        entityManager.persist(auditEntity);
    }
}
