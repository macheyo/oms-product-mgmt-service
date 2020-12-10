package com.footprint.omsproductmgmtservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{
    private String name;
    private String description;
    private Double price;
    private String imageURL;
    @ManyToMany
    Set<Category> categories;
}
