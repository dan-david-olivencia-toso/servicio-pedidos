package com.dan.dot.lab01.service.artemis;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class DispatcherService {

    @Autowired
    JmsTemplate jms;

    public void enviarMensaje(String msg) throws JMSException {
       jms.convertAndSend("ColaPedidos", msg);
    }

}
