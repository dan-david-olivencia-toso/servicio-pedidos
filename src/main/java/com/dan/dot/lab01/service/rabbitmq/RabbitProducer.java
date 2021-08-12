package com.dan.dot.lab01.service.rabbitmq;

import com.dan.dot.lab01.domain.DetallePedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitProducer {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${fanout.exchange}")
    private String fanoutExchange;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(List<DetallePedido> detallePedido) throws Exception {
        logger.info("Guardando detalle del pedido...");
        rabbitTemplate.setExchange(fanoutExchange);
        rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(detallePedido));
        logger.info("Detalle del pedido almacenado en cola correctamente");
    }

}