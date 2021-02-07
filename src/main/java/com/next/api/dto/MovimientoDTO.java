package com.next.api.dto;

import java.util.Date;

public class MovimientoDTO {
    private Long cantidad;
    private String tipo;
    private Date fecha;

    public MovimientoDTO(Long cantidad, String tipo, Date fecha) {
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
