package com.epam.esm.restapibasics.model.entity.audit;

import com.epam.esm.restapibasics.model.dao.config.BeansUtil;

import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.time.LocalDateTime;

import static com.epam.esm.restapibasics.model.entity.audit.Audit.Operation.*;
import static java.time.ZoneOffset.UTC;

public class AuditListener {


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
        EntityManager entityManager = BeansUtil.getBean(EntityManager.class);
        Audit auditEntity = new Audit();
        auditEntity.setEntityName(entity.getClass().getSimpleName());
        auditEntity.setTimestamp(LocalDateTime.now(UTC));
        auditEntity.setOperation(operation);

        entityManager.persist(auditEntity);
    }
}
