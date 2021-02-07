package com.next.api.service;

import com.next.api.entity.Cajero;
import com.next.api.entity.Tarjeta;
import com.next.api.repository.CajeroRespository;
import com.next.api.repository.CuentaRespository;
import com.next.api.repository.MovimientoRespository;
import com.next.api.repository.TarjetaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarjetaService {

    @Autowired
    TarjetaRespository tarjetaRespository;
    @Autowired
    CajeroRespository cajeroRespository;

    public Boolean isActiva(Integer tarjetaId) {
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        return tarjeta.getActivada();
    }

    public Boolean tarjetaMismoBancoCajero(Integer tarjetaId, Integer cajeroId) {
        Tarjeta tarjeta = tarjetaRespository.getOne(tarjetaId);
        Cajero cajero = cajeroRespository.getOne(cajeroId);
        return tarjeta.getCuenta().getBanco().getId().intValue() == cajero.getBanco().getId().intValue();
    }


}