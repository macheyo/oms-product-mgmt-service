package com.footprint.omsproductmgmtservice.entity;

import com.footprint.omsproductmgmtservice.common.Status;
import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class BaseEntity extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false, updatable = false)
    private Long id;
    private Status status=Status.ACTIVE;

}
