package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    private static final String SELECT_ALL_SQL = "SELECT t FROM Tag t";
    private static final String SELECT_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String NAME_PARAMETER = "name";

    private static final String SELECT_MOST_WIDELY_USED_TAG = "SELECT t.id, t.name " +
            "FROM user u " +
            "INNER JOIN orders o ON o.user_id = u.id " +
            "INNER JOIN certificates_in_order co ON co.order_id = o.id " +
            "INNER JOIN gift_certificate c ON c.id = co.certificate_id " +
            "INNER JOIN tags_in_certificate ct ON ct.certificate_id = c.id " +
            "INNER JOIN tag t ON ct.tag_id = t.id " +
            "WHERE u.id = (SELECT u.id " +
            "FROM user u " +
            "INNER JOIN orders o ON o.user_id = u.id " +
            "GROUP BY u.id " +
            "ORDER BY SUM(o.cost) DESC " +
            "LIMIT 1) " +
            "GROUP BY t.id, t.name " +
            "ORDER BY COUNT(t.name) DESC " +
            "LIMIT 1;";

    @PersistenceContext
    private final EntityManager entityManager;

    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Retrieve all tags from storage.
     *
     * @return list of {@link Tag}
     */
    @Override
    public List<Tag> getAll(Paginator paginator) {
        return entityManager.createQuery(SELECT_ALL_SQL, Tag.class)
                .setFirstResult(paginator.getStartValue())
                .setMaxResults(paginator.getAmount())
                .getResultList();
    }

    /**
     * Retrieve tag by its unique id.
     *
     * @param id tag id
     * @return {@link Tag}
     */
    @Override
    public Optional<Tag> getById(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    /**
     * Retrieve tag by its unique name.
     *
     * @param name tag name
     * @return {@link Tag}
     */
    @Override
    public Optional<Tag> getByName(String name) {
        TypedQuery<Tag> tagQuery = entityManager.createQuery(SELECT_BY_NAME, Tag.class);
        tagQuery.setParameter(NAME_PARAMETER, name);
        return tagQuery.getResultList()
                .stream()
                .findFirst();
    }

    /**
     * Create a new tag in the storage.
     *
     * @param tag {@link Tag} instance
     * @return unique id of the saved {@link Tag}
     */
    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    /**
     * Delete an existing tag from the storage.
     *
     * @param id tag id
     */
    @Override
    public void delete(Long id) {
        entityManager.remove(id);
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTag() {
        try {
            Tag tag = (Tag) entityManager.createNativeQuery(SELECT_MOST_WIDELY_USED_TAG, Tag.class)
                    .getSingleResult();
            return Optional.of(tag);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
