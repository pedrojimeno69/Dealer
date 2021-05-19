package com.example.dealer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MARK")
@SequenceGenerator(
        name = "CRD_ID_2",
        sequenceName = "RTDS_ADSINPUT_SEQ_2", allocationSize = 1)
@Getter
@Setter
public class MarkEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue( strategy= GenerationType.SEQUENCE, generator = "CRD_ID_2")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "mark")
    private List<CarEntity> cars;


}
