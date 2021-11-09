package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.*;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String TAGS = "tags";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "createDate";
    private static final String LAST_UPDATE_DATE = "lastUpdateDate";


    @PersistenceContext
    private final EntityManager entityManager;

    public GiftCertificateDaoImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }



    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }


    @Override
    public Optional<GiftCertificate> getById(Long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return Optional.ofNullable(giftCertificate);
    }


    @Override
    public List<GiftCertificate> find(Paginator paginator, Map<String, SearchParameter> parameters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<GiftCertificate, Tag> join = certificateRoot.join(TAGS, JoinType.LEFT);

        for(Map.Entry<String, SearchParameter> param : parameters.entrySet()) {
            String key = param.getKey();
            SearchParameter values = param.getValue();

            switch (values.getType()) {
                case SEARCH -> {
                    for (String v : values.getValue()) {
                        Predicate searchPredicate = createPredicate(v, key, criteriaBuilder, certificateRoot);
                        predicates.add(searchPredicate);
                    }
                }
                case ORDER_BY -> {
                    for (String v : values.getValue()) {
                        OrderType orderType = OrderType.valueOf(v.toUpperCase());
                        setOrdering(orderType, key, certificateRoot, criteriaBuilder, criteriaQuery);
                    }
                }
            }
        }

        criteriaQuery.select(certificateRoot).distinct(true);

        List<String> tagNames = null;
        for(Map.Entry<String, SearchParameter> param : parameters.entrySet()) {
            SearchParameter values = param.getValue();
            if (values.getType() == SearchParameterType.TAGS) {
                tagNames = values.getValue();
            }
        }

        if (tagNames != null) {
            Predicate inPredicate = join.get(NAME).in(tagNames);
            predicates.add(inPredicate);

            criteriaQuery = criteriaQuery
                    .where(predicates.toArray(new Predicate[0]))
                    .groupBy(
                            certificateRoot.get(ID),
                            certificateRoot.get(NAME),
                            certificateRoot.get(DESCRIPTION),
                            certificateRoot.get(PRICE),
                            certificateRoot.get(DURATION),
                            certificateRoot.get(CREATE_DATE),
                            certificateRoot.get(LAST_UPDATE_DATE)
                    )
                    .having(
                            criteriaBuilder.equal(
                                    criteriaBuilder.countDistinct(join.get(ID)), tagNames.size()
                            )
                    );
        }
        else {
            criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(paginator.getStartValue())
                .setMaxResults(paginator.getAmount())
                .getResultList();
    }

    private Predicate createPredicate(String value, String paramName, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> certificateRoot) {
        String sqlValue = String.format("%%%s%%", value);
        return criteriaBuilder.like(certificateRoot.get(paramName), sqlValue);
    }

    private void setOrdering(OrderType orderType, String paramName, Root<GiftCertificate> certificateRoot,
                             CriteriaBuilder criteriaBuilder, CriteriaQuery<GiftCertificate> criteriaQuery) {
        Order order = switch (orderType) {
            case ASC -> criteriaBuilder.asc(certificateRoot.get(paramName));
            case DESC -> criteriaBuilder.desc(certificateRoot.get(paramName));
        };
        criteriaQuery.orderBy(order);
    }


    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }



    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
        return giftCertificate;
    }
}
