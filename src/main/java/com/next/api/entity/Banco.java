package com.next.api.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String entidad;

    private String codigo;

    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cajero> cajeros;
    
}
