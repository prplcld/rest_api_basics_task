package com.epam.esm.restapibasics.model.entity;

import com.epam.esm.restapibasics.model.entity.audit.AuditListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "gift_certificate")
//@EntityListeners(AuditListener.class)
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "tags_in_certificate",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Tag> tags = new ArrayList<>();
}
