package com.next.api.repository;

import com.next.api.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovimientoRespository extends JpaRepository<Movimiento, Integer> {

    @Query(value = "SELECT m.cantidad, m.tipo, m.fecha FROM Movimiento m WHERE m.cuenta.id = :cuenta")
    List<Object> findByCuenta(Integer cuenta);

}
