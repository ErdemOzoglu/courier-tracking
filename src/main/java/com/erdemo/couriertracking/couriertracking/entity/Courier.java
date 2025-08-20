package com.erdemo.couriertracking.couriertracking.entity;

import com.erdemo.couriertracking.generic.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COURIER")
@Getter
@Setter
public class Courier extends BaseEntity {

    @GeneratedValue(generator = "Courier")
    @SequenceGenerator(name = "Courier", sequenceName = "COURIER_ID_SEQ")
    @Id
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "COURIER_PLATE", length = 100,unique = true, nullable = false)
    private String courierPlate;
}
