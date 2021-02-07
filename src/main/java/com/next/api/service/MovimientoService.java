package com.next.api.service;

import com.next.api.entity.Cuenta;
import com.next.api.entity.Movimiento;
import com.next.api.entity.Tarjeta;
import com.next.api.repository.CuentaRespository;
import com.next.api.repository.MovimientoRespository;
import com.next.api.repository.TarjetaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovimientoService {

    @Autowired
    MovimientoRespository movimientoRespository;
    @Autowired
    CuentaRespository cuentaRespository;
    @Autowired
    TarjetaRespository tarjetaRespository;

    public List<Object> getMovimientosClienteCuenta(Integer cuentaId) {
        return movimientoRespository.findByCuenta(cuentaId);
    }

    public Map<String, Object> sacarDinero(Integer tarjetaId, Long cantidad) {
        Map<String, Object> result = new HashMap<>();
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        Cuenta cuenta = tarjeta.getCuenta();
        if (cantidad > cuenta.getSaldo()) {
            if (tarjeta.getTipo().equals("debito")) {
                result.put("mensaje", "No tiene saldo suficiente");
                result.put("ok", false);
                return result;
            } else if ((cuenta.getSaldo() - cantidad) * -1 > tarjeta.getCredito()) {
                result.put("mensaje", "Sobrepasa su crédito.");
                result.put("ok", false);
                return result;
            } else {
                Long nuevoSaldo = cuenta.getSaldo() - cantidad;
                cuenta.setSaldo(nuevoSaldo);
                cuentaRespository.save(cuenta);
                Movimiento movimiento = new Movimiento();
                movimiento.setCantidad(cantidad);
                movimiento.setTipo("Sacar Dinero");
                movimiento.setCuenta(cuenta);
                movimiento.setFecha(new Date());
                movimientoRespository.save(movimiento);
                result.put("saldo", cuenta.getSaldo());
                result.put("ok", true);
                return result;
            }
        } else {
            Long nuevoSaldo = cuenta.getSaldo() - cantidad;
            cuenta.setSaldo(nuevoSaldo);
            cuentaRespository.save(cuenta);
            Movimiento movimiento = new Movimiento();
            movimiento.setCantidad(cantidad);
            movimiento.setTipo("Sacar Dinero");
            movimiento.setCuenta(cuenta);
            movimiento.setFecha(new Date());
            movimientoRespository.save(movimiento);
            result.put("saldo", cuenta.getSaldo());
            result.put("ok", true);
            return result;
        }
    }

    public Map<String, Object> ingresarDinero(Integer tarjetaId, Long cantidad) {
        Map<String, Object> result = new HashMap<>();
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        Cuenta cuenta = tarjeta.getCuenta();
        Long nuevoSaldo = cuenta.getSaldo() + cantidad;
        cuenta.setSaldo(nuevoSaldo);
        cuentaRespository.save(cuenta);
        Movimiento movimiento = new Movimiento();
        movimiento.setCantidad(cantidad);
        movimiento.setTipo("Ingresar Dinero");
        movimiento.setCuenta(cuenta);
        movimiento.setFecha(new Date());
        movimientoRespository.save(movimiento);
        result.put("saldo", cuenta.getSaldo());
        result.put("ok", true);
        return result;
    }

}
