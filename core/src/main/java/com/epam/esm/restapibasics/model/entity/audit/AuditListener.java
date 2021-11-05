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

    private final EntityManager entityManager;

    public AuditListener(@Lazy EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostPersist
    public void postPersist(Object entity) {
        createRecord(entity, CREATE);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        createRecord(entity, UPDATE);
    }

    @PostRemove
    public void postRemove(Object entity) {
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
