package com.epam.esm.restapibasics.model.dao.util;

import com.epam.esm.restapibasics.model.dao.ColumnName;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(ColumnName.ID));
        giftCertificate.setName(rs.getString(ColumnName.CERTIFICATE_NAME));
        giftCertificate.setDescription(rs.getString(ColumnName.CERTIFICATE_DESCRIPTION));
        giftCertificate.setPrice(rs.getBigDecimal(ColumnName.CERTIFICATE_PRICE));
        giftCertificate.setDuration(rs.getInt(ColumnName.CERTIFICATE_DURATION));
        giftCertificate.setCreateDate(rs.getObject(ColumnName.CERTIFICATE_CREATE_DATE, LocalDateTime.class));
        giftCertificate.setLastUpdateDate(rs.getObject(ColumnName.CERTIFICATE_LAST_UPDATE_DATE, LocalDateTime.class));
        return giftCertificate;
    }
}
