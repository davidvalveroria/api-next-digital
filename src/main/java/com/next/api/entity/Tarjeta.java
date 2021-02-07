package com.next.api.entity;

import javax.persistence.*;

@Entity
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Boolean activada;

    private String tipo;

    @JoinColumn(name = "cuenta_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Cuenta cuenta;

    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String pin;

    private Long credito;
}
