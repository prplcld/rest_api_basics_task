package com.epam.esm.rest_api_basics.model.dao.util;

import com.epam.esm.rest_api_basics.model.dao.ColumnName;
import com.epam.esm.rest_api_basics.model.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setTagId(rs.getLong(ColumnName.ID));
        tag.setName(rs.getString(ColumnName.TAG_NAME));
        return tag;
    }
}
