package com.next.api.controller;

import com.next.api.service.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController()
public class TarjetaController {

    @Autowired
    TarjetaService tarjetaService;

    @PostMapping("/tarjeta/activar")
    @ResponseBody
    public Object realizarTransferencia(Integer tarjeta, String pin) {
        if (tarjetaService.isActiva(tarjeta)) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Su tarjeta ya está activada.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else {
            return tarjetaService.activarTarjeta(tarjeta, pin);
        }
    }

}
