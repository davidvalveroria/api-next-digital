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
    public Object activarTarjeta(Integer tarjeta, String pin) {
        if (tarjetaService.isActiva(tarjeta)) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Su tarjeta ya está activada.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else {
            return tarjetaService.activarTarjeta(tarjeta, pin);
        }
    }

    @PostMapping("/tarjeta/cambiarpin")
    @ResponseBody
    public Object cambiarPin(Integer tarjeta, String pinNuevo, String pinAntiguo) {
        Map<String, Object> result = new HashMap<>();
        if (!tarjetaService.isActiva(tarjeta)) {
            result.put("mensaje", "Debe activar primero su tarjeta.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            result = tarjetaService.cambiarPin(tarjeta, pinNuevo, pinAntiguo);
            if(!(Boolean)result.get("ok")){
                return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return result;
            }

        }
    }



}
