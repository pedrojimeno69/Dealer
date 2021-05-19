package com.example.dealer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRICE")
@SequenceGenerator(name = "CRD_ID_3",sequenceName = "RTDS_ADSINPUT_SEQ_PRI_3", allocationSize = 1)
@Getter
@Setter
public class PriceEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue( strategy= GenerationType.SEQUENCE, generator = "CRD_ID_3")
    private Integer id;

    @Column(name = "INIT_DATE")
    @Temporal(TemporalType.DATE)
    private Date initDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "PRICE")
    private Double price;

    @ManyToOne (cascade = CascadeType.ALL)
//    @JoinColumn(name = "ID", nullable = false, updatable = false)
    private CarEntity car;
}
