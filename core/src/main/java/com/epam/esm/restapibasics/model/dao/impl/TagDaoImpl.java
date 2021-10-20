package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.dao.exception.EntityAlreadyExistsException;
import com.epam.esm.restapibasics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.dao.exception.NoTagFoundException;
import com.epam.esm.restapibasics.model.dao.util.TagRowMapper;
import com.epam.esm.restapibasics.model.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

public class TagDaoImpl implements TagDao {

    private static final String INSERT_TAG_SQL = "INSERT INTO tag (name) VALUES (?)";
    private static final String SELECT_ALL_TAGS_SQL = "SELECT id, name FROM tag";
    private static final String SELECT_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    private static final String SELECT_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";
    private static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?";
    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT t.id, t.name FROM tag t JOIN tags_in_certificate c ON t.id = c.tag_id WHERE c.certificate_id = ?";
    private static final String DELETE_TAG_FROM_CERTIFICATES_SQL = "DELETE FROM tags_in_certificate WHERE tag_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieve all tags from storage.
     *
     * @return list of {@link Tag}
     */
    @Override
    public List<Tag> getTags() {
        List<Tag> tags = jdbcTemplate.query(SELECT_ALL_TAGS_SQL, new TagRowMapper());
        return tags;
    }

    /**
     * Retrieve tag by its unique id.
     *
     * @param id tag id
     * @return {@link Tag}
     */
    @Override
    public Tag getById(Long id) {
        try {
            Tag tag = jdbcTemplate.queryForObject(SELECT_TAG_BY_ID, new Object[]{id}, new int[]{1}, new TagRowMapper());
            return tag;
        } catch (DataAccessException e) {
            throw new EntityNotFoundException(id);
        }
    }

    /**
     * Retrieve tag by its unique name.
     *
     * @param name tag name
     * @return {@link Tag}
     */
    @Override
    public Tag getByName(String name) throws NoTagFoundException {
        try {
            Tag tag = jdbcTemplate.queryForObject(SELECT_TAG_BY_NAME, new Object[]{name}, new int[]{1}, new TagRowMapper());
            return tag;
        } catch (DataAccessException e) {
            throw new NoTagFoundException("no data found", e);
        }
    }

    /**
     * Create a new tag in the storage.
     *
     * @param tag {@link Tag} instance
     * @return unique id of the saved {@link Tag}
     */
    @Override
    public Long create(Tag tag) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_TAG_SQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, tag.getName());
                return preparedStatement;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExistsException();
        }
    }

    /**
     * Delete an existing tag from the storage.
     *
     * @param id tag id
     * @return {@code true} if {@link Tag} existed and was deleted, otherwise {@code false}
     */
    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_TAG_BY_ID, id) > 0;
    }

    /**
     * Retrieve all tags attached to a certain certificate.
     *
     * @param id certificate id
     * @return list of {@link Tag}
     */
    @Override
    public List<Tag> getByCertificateId(Long id) {
        List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{id}, new int[]{1}, new TagRowMapper());
        return tags;
    }

    /**
     * Delete an existing tag from certificate.
     *
     * @param id tag id
     * @return {@code true} if {@link Tag} existed and was deleted, otherwise {@code false}
     */
    @Override
    public boolean deleteFromCertificates(Long id) {
        return jdbcTemplate.update(DELETE_TAG_FROM_CERTIFICATES_SQL, id) > 0;
    }
}
