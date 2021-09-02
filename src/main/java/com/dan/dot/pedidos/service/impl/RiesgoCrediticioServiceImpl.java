package com.dan.dot.pedidos.service.impl;

import com.dan.dot.pedidos.service.RiesgoCrediticioService;
import org.springframework.stereotype.Service;

@Service
public class RiesgoCrediticioServiceImpl implements RiesgoCrediticioService {

    @Override
    public Boolean reporteBCRAPositivo(Integer idObra) {
        return true;
    }

}
