package com.epam.esm.restapibasics.model.dao.util;

import com.epam.esm.restapibasics.model.dao.ColumnName;
import com.epam.esm.restapibasics.model.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(ColumnName.ID));
        tag.setName(rs.getString(ColumnName.TAG_NAME));
        return tag;
    }
}
