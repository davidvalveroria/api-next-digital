package com.next.api.entity;

import javax.persistence.*;

@Entity
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String tipo;

    private Long cantidad;

    @ManyToOne()
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
}
