package com.footprint.omsproductmgmtservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{
    private String name;
    private String description;

}
