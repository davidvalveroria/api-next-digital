package com.next.api.entity;

import javax.persistence.*;

@Entity
public class Cajero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "banco_id")
    private Banco banco;
}
