package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    //FIXME
    private static final String SELECT_ALL_SQL = "SELECT t FROM Tag t";
    private static final String SELECT_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String NAME_PARAMETER = "name";


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
    public Long create(Tag tag) {
        entityManager.persist(tag);
        return tag.getId();
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

    /**
     * Retrieve all tags attached to a certain certificate.
     *
     * @param id certificate id
     * @return list of {@link Tag}
     */
    @Override
    public List<Tag> getByCertificateId(Long id) {
        return null;
    }

    /**
     * Delete an existing tag from certificate.
     *
     * @param id tag id
     * @return {@code true} if {@link Tag} existed and was deleted, otherwise {@code false}
     */
    @Override
    public boolean deleteFromCertificates(Long id) {
        return false;
    }
}
