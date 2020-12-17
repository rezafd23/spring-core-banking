package com.core.restapi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

public class ApiReceiver {
    private String receiverMessage;

    public String receiveFromDatabase(String queueName)  {
        receiverMessage="";
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            System.out.println(" [*] Waiting for messages from database");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                receiverMessage=message;

            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
//            System.out.println(" [x] IsiPowerMessagge '" + powerMessage + "'");
            while (receiverMessage.equals("")){
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return receiverMessage;
        } catch (Exception e){
            System.out.println("Add Power Api Receiver Error: ");
            e.printStackTrace();
            return "0";
        }

    }
}
