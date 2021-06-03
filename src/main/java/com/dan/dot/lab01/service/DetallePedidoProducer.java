package com.dan.dot.lab01.service;

import com.dan.dot.lab01.domain.DetallePedido;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;
import java.util.List;

@Service
@EnableJms
public class DetallePedidoProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    Logger log = LoggerFactory.getLogger(DetallePedidoProducer.class);

    public void enviarDetallePedido(List<DetallePedido> detallePedidoList) {
        //Serializa y envia el Detalle Pedido
        jmsTemplate.convertAndSend("COLA_PEDIDOS", new Gson().toJson(detallePedidoList));

        log.info("Enviado <" + detallePedidoList + ">");

    }

}
