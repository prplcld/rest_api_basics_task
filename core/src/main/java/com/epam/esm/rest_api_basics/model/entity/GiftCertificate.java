package com.epam.esm.rest_api_basics.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificate {

    private Long giftCertificateId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateDate;

    private List<Tag> tags;

    public GiftCertificate() {
        tags = new ArrayList<>();
    }

    public Long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(Long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }


    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return "GiftCertificate{" +
                "giftCertificateId=" + giftCertificateId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (!giftCertificateId.equals(that.giftCertificateId)) return false;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        if (!price.equals(that.price)) return false;
        if (!duration.equals(that.duration)) return false;
        if (!createDate.equals(that.createDate)) return false;
        if (!lastUpdateDate.equals(that.lastUpdateDate)) return false;
        return tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        int result = giftCertificateId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        result = 31 * result + tags.hashCode();
        return result;
    }
}
