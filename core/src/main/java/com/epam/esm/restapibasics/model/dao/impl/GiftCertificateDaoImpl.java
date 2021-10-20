package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.GiftCertificateDao;
import com.epam.esm.restapibasics.model.dao.OrderType;
import com.epam.esm.restapibasics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.dao.util.ExpressionBuilder;
import com.epam.esm.restapibasics.model.dao.util.GiftCertificateRowMapper;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String INSERT_GIFT_CERTIFICATE_SQL = "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES(?, ?, ? ,?, now(), now())";
    private static final String INSERT_TAG_TO_CERTIFICATE_SQL = "INSERT INTO tags_in_certificate(certificate_id, tag_id) VALUES (?, ?)";
    private static final String SELECT_GIFT_CERTIFICATES_SQL = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate";
    private static final String SELECT_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_SQl = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_FROM_TAGS_SQL = "DELETE FROM tags_in_certificate WHERE certificate_id = ?";
    private static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE gift_certificate set name=IFNULL(?, name), " +
            "description=IFNULL(?, description), price=IFNULL(?, price), " +
            "duration=IFNULL(?, duration), last_update_date = now() WHERE id = ?";

    private static final String BASE_SELECT_CERTIFICATES_SQL = "SELECT DISTINCT g.id, g.name, description, price, duration, create_date, last_update_date " +
            "FROM gift_certificate g " +
            "LEFT OUTER JOIN tags_in_certificate tc " +
            "ON tc.certificate_id = g.id " +
            "LEFT OUTER JOIN tag t " +
            "ON tc.tag_id = t.id " +
            "%s " +
            "%s";

    private JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * Create a new certificate in the storage.
     *
     * @param giftCertificate {@link GiftCertificate} instance
     * @return unique id of the saved {@link GiftCertificate}
     */
    @Override
    public Long create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_GIFT_CERTIFICATE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            preparedStatement.setInt(4, giftCertificate.getDuration());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * Retrieve certificate by its unique id.
     *
     * @param id certificate id
     * @return {@link GiftCertificate}
     */
    @Override
    public GiftCertificate getById(Long id) {
        try {
            GiftCertificate giftCertificate = jdbcTemplate.queryForObject(SELECT_GIFT_CERTIFICATE_BY_ID_SQL,
                    new Object[]{id}, new int[]{1}, new GiftCertificateRowMapper());
            return giftCertificate;
        } catch (DataAccessException e) {
            throw new EntityNotFoundException(id);
        }
    }

    /**
     * Retrieve all certificates.
     *
     *
     * @return list of{@link GiftCertificate}
     */
    @Override
    public List<GiftCertificate> find(String tagName, String certificateName, String certificateDescription,
                                      OrderType orderByName, OrderType orderByCreateDate) {

        ExpressionBuilder expressionBuilder = new ExpressionBuilder("WHERE ", " AND ");
        ExpressionBuilder orderExpressionBuilder = new ExpressionBuilder("ORDER BY ", ", ");
        List<Object> params = new ArrayList<>();


        if (tagName != null) {
            expressionBuilder.addComponent("t.name = ?");
            params.add(tagName);
        }
        if (certificateName != null) {
            expressionBuilder.addComponent("g.name LIKE ?");
            params.add("%" + certificateName + "%");
        }
        if (certificateDescription != null) {
            expressionBuilder.addComponent("g.description LIKE ?");
            params.add("%" + certificateDescription + "%");
        }
        if (orderByName != null) {
            orderExpressionBuilder.addComponent("g.name " + orderByName.name());
        }
        if (orderByCreateDate != null) {
            orderExpressionBuilder.addComponent("create_date " + orderByCreateDate.name());
        }

        String query = String.format(BASE_SELECT_CERTIFICATES_SQL, expressionBuilder.build(), orderExpressionBuilder.build());
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(query, new GiftCertificateRowMapper(), params.toArray());
        return giftCertificates;
    }

    /**
     * Delete an existing certificate from the storage.
     *
     * @param id certificate id
     * @return {@code true} if {@link GiftCertificate} existed and was deleted, otherwise {@code false}
     */
    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_SQl, id) > 0;
    }

    /**
     * Delete certificate from many to many table.
     *
     * @param id certificate id
     * @return {@code true} if {@link GiftCertificate} existed and was deleted, otherwise {@code false}
     */
    @Override
    public boolean deleteFromTags(Long id) {
        return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_FROM_TAGS_SQL, id) > 0;
    }

    /**
     * Update an existing certificate in the storage.
     *
     * @param giftCertificate {@link GiftCertificate} instance
     * @return {@code true} if {@link GiftCertificate} existed and was updated, otherwise {@code false}
     */
    @Override
    public boolean update(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_SQL, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getPrice(), giftCertificate.getId()) > 0;
    }

    /**
     * Attach tag to existing certificate.
     *
     * @param certificateId certificate id
     * @param tagId         tag id
     */
    @Override
    public boolean attachTag(Long certificateId, Long tagId) {
        return jdbcTemplate.update(INSERT_TAG_TO_CERTIFICATE_SQL, certificateId, tagId) > 0;
    }
}
