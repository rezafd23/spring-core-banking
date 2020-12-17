package com.core.database.rabbitmq;

import com.core.database.service.NasabahCardService;
import com.core.database.service.NasabahService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DatabaseReceiver {
    DatabaseSender sender = new DatabaseSender();
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private NasabahService nasabahService = new NasabahService();
    private NasabahCardService nasabahCardService = new NasabahCardService();


    public void connectRabbitMQ() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    public int addNasabahCard(int id_nasabah,String nasabahData){
        try {
            int res =nasabahCardService.addNasabahCard(id_nasabah,nasabahData);
            return res;
        }
        catch (Exception e){
            System.out.println("ErrorAddNasabahCard: ");
            e.printStackTrace();
            return 0;
        }
    }

    public void addNasabah() {
        String queueNameReceive="addNasabahMessage";
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("addNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nasabahData = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahData + "'");
                int res = nasabahService.addNasabah(nasabahData);
                int res2= addNasabahCard(res,nasabahData);
                System.out.println("HasilResService2: "+res2);
                if (res != 0&&res2!=0) {
                    try {
                        sender.sendToRestApi(String.valueOf(res),queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Add Nasabah: ");
                        e.printStackTrace();
                    }
                } else {
                    try {
                        sender.sendToRestApi("0",queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Add Nasabah: ");
                        e.printStackTrace();
                    }
                }
            };
            channel.basicConsume("addNasabah", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error Add Nasabah = " + e);
        }
    }
}
