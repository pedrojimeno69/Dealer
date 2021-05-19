package com.example.dealer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CAR")
@SequenceGenerator(
        name = "CRD_ID",
        sequenceName = "RTDS_ADSINPUT_SEQ", allocationSize = 1)
@Getter
@Setter
public class CarEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue( strategy= GenerationType.SEQUENCE, generator = "CRD_ID")
    private Integer id;

    @Column(name = "MODEL_NAME")
    private String modelName;

    @Column(name = "COLOR")
    private String color;

    @OneToOne (cascade = CascadeType.ALL)
    private MarkEntity mark;

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "car")
    private List<PriceEntity> priceEntities;
}
