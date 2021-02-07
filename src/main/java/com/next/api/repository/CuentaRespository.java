package com.next.api.repository;

import com.next.api.entity.Cuenta;
import com.next.api.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRespository extends JpaRepository<Cuenta, Integer> {

}
