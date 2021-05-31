package com.dan.dot.lab01.service.artemis;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService {

    Logger log = (Logger) LoggerFactory.getLogger(ReceiverService.class);

    @JmsListener(destination = "ColaPedidos")
    public void recibirMensaje(String msg) throws JmsException {
        log.info("LLEGO MENSAJE: "+msg);
    }

    /* TO DO */
    /* Registrar un movimiento de stock del producto y actualizar el stock actual en la tabla de productos */
    /* Si se llega a un stock debajo del mínimo se crea una nueva orden de provisión. */
}

