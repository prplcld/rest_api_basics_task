package com.epam.esm.restapibasics.model.entity.audit;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Operation operation;
    private String entityName;
    private LocalDateTime timestamp;

    public enum Operation {
        CREATE, UPDATE, DELETE
    }
}
