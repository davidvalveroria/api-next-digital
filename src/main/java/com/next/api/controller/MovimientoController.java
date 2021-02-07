package com.next.api.controller;

import com.next.api.service.MovimientoService;
import com.next.api.service.TarjetaService;
import com.next.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
public class MovimientoController {

    @Autowired
    MovimientoService movimientoService;
    @Autowired
    TarjetaService tarjetaService;

    @GetMapping("/movimientos/{cuenta}")
    public List<Object> getAll(@PathVariable Integer cuenta) {
        return movimientoService.getMovimientosClienteCuenta(cuenta);
    }

    @PostMapping("/movimientos/sacar")
    @ResponseBody
    public Object sacarDinero(Integer tarjeta, Long cantidad) {
        if (tarjetaService.isActiva(tarjeta)) {
            Map<String, Object> mensaje = movimientoService.sacarDinero(tarjeta, cantidad);
            return mensaje;
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Active su tarjeta");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/movimientos/ingresar")
    @ResponseBody
    public Object ingresarDinero(Integer tarjeta, Long cantidad, Integer cajero) {
        if (tarjetaService.isActiva(tarjeta)) {
            if (tarjetaService.tarjetaMismoBancoCajero(tarjeta, cajero)) {
                Map<String, Object> mensaje = movimientoService.ingresarDinero(tarjeta, cantidad);
                return mensaje;
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("mensaje", "No puede ingresar dinero desde un cajero de otra entidad");
                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Active su tarjeta");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/movimientos/transferencia")
    @ResponseBody
    public Object realizarTransferencia(Integer tarjeta, Long cantidad, String iban) {
        if (tarjetaService.isActiva(tarjeta)) {
            if(Utils.validar(iban)) {
                return movimientoService.realizarTransferencia(tarjeta, cantidad, iban);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("mensaje", "El código IBAN no es correcto.");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Active su tarjeta");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }

}
