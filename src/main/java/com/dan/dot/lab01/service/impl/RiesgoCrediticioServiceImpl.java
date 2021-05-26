package com.dan.dot.lab01.service.impl;

import com.dan.dot.lab01.service.RiesgoCrediticioService;
import org.springframework.stereotype.Service;

@Service
public class RiesgoCrediticioServiceImpl implements RiesgoCrediticioService {

    @Override
    public Boolean reporteBCRAPositivo(Integer idObra) {
        return true;
    }

}
