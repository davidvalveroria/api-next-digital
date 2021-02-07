package com.next.api.service;

import com.next.api.entity.Cajero;
import com.next.api.entity.Tarjeta;
import com.next.api.repository.CajeroRespository;
import com.next.api.repository.TarjetaRespository;
import com.next.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TarjetaService {

    @Autowired
    TarjetaRespository tarjetaRespository;
    @Autowired
    CajeroRespository cajeroRespository;
    @Autowired
    Utils utils;

    public Boolean isActiva(Integer tarjetaId) {
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        return tarjeta.getActivada();
    }

    public Boolean tarjetaMismoBancoCajero(Integer tarjetaId, Integer cajeroId) {
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        Cajero cajero = cajeroRespository.getOne(cajeroId);
        return tarjeta.getCuenta().getBanco().getId().intValue() == cajero.getBanco().getId().intValue();
    }

    public Map<String, Object> activarTarjeta(Integer tarjetaId, String pin) {
        Map<String, Object> result = new HashMap<>();
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        tarjeta.setActivada(true);
        try {
            tarjeta.setPin(utils.encriptar(pin));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("mensaje", "No se ha podido encriptar el pin");
            result.put("ok", false);
            return result;
        }
        tarjetaRespository.save(tarjeta);
        result.put("mensaje", "Activada correctamente");
        result.put("ok", true);
        return result;
    }

    public Map<String, Object> cambiarPin(Integer tarjetaId, String pinNuevo, String pinAntiguo) {
        Map<String, Object> result = new HashMap<>();
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        String pinDesencriptado = "";
        try {
            pinDesencriptado = utils.desencriptar(tarjeta.getPin());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("mensaje", "No se ha podido desencriptar el pin");
            result.put("ok", false);
            return result;
        }
        if (pinAntiguo.equals(pinDesencriptado)) {
            tarjeta.setActivada(true);
            try {
                tarjeta.setPin(utils.encriptar(pinNuevo));
            } catch (Exception e) {
                e.printStackTrace();
                result.put("mensaje", "No se ha podido encriptar el pin");
                result.put("ok", false);
                return result;
            }
            tarjetaRespository.save(tarjeta);
            result.put("mensaje", "Pin cambiado correctamente.");
            result.put("ok", true);
        } else {
            result.put("mensaje", "El pin introducido no es correcto.");
            result.put("ok", false);
        }
        return result;
    }


}
