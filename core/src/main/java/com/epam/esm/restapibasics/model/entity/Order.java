package com.epam.esm.restapibasics.model.entity;

import com.epam.esm.restapibasics.model.entity.audit.AuditListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
@EntityListeners(AuditListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal cost;
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToMany
    @JoinTable(
            name = "certificates_in_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GiftCertificate> certificates;
}
