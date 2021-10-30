package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.model.dao.exception.NoTagFoundException;

import java.util.List;

public interface TagDao {
    List<Tag> getTags();

    Tag getById(Long id);

    Long create(Tag tag);

    boolean delete(Long id);

    Tag getByName(String name) throws NoTagFoundException;

    List<Tag> getByCertificateId(Long id);

    boolean deleteFromCertificates(Long id);

}
