package com.next.api.repository;

import com.next.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository extends JpaRepository<Cliente, Integer> {

}
