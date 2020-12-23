package com.core.database.rabbitmq;

import com.core.database.service.NasabahCardService;
import com.core.database.service.NasabahService;
import com.core.database.service.TransaksiService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DatabaseReceiver {
    DatabaseSender sender = new DatabaseSender();
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private NasabahService nasabahService = new NasabahService();
    private TransaksiService transaksiService = new TransaksiService();
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

                if (res != 0) {
                    try {
                        int res2= addNasabahCard(res,nasabahData);
                        System.out.println("HasilResService2: "+res2);
                        if (res2!=0){
                        sender.sendToRestApi(String.valueOf(res),queueNameReceive);
                        }
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

    public void addTransaksi() {
        String queueNameReceive="addTransaksiMessage";
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("addTransaksi", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String transaksiData = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + transaksiData + "'");
                String res = transaksiService.addTransaksi(transaksiData);

                if (!res.equals("0")) {
                    try {
                        sender.sendToRestApi(res,queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Add Transaksi: ");
                        e.printStackTrace();
                    }
                } else {
                    try {
                        sender.sendToRestApi("0",queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Add Transaksi: ");
                        e.printStackTrace();
                    }
                }
            };
            channel.basicConsume("addTransaksi", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error Add Transaksi = " + e);
        }
    }
    public void mutasi() {
        String queueNameReceive="getMutasiMessage";
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getMutasi", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String transaksiData = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + transaksiData + "'");
                String res = transaksiService.mutasi(transaksiData);

                if (!res.equals("0")) {
                    try {
                        sender.sendToRestApi(res,queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Mutasi: ");
                        e.printStackTrace();
                    }
                } else {
                    try {
                        sender.sendToRestApi("0",queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Mutasi: ");
                        e.printStackTrace();
                    }
                }
            };
            channel.basicConsume("getMutasi", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error Mutasi = " + e);
        }
    }
    public void getNasabahInfo() {
        String queueNameReceive="getNasabahInfoMessage";
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getNasabahInfo", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String transaksiData = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + transaksiData + "'");
                String res = nasabahService.getNasabahInfo(transaksiData);
                String response="";

                JSONParser parser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) parser.parse(res);
                    int saldo=transaksiService.getSaldoById(jsonObject.get("no_rekening").toString());
                    jsonObject.put("saldo",saldo);
                    response=jsonObject.toJSONString();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                JSONParser parser =new JSONParser();
//                JSONObject jsonObject = (JSONObject) parser.parse(res);

                if (!res.equals("0")) {
                    try {
                        sender.sendToRestApi(response,queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Mutasi: ");
                        e.printStackTrace();
                    }
                } else {
                    try {
                        sender.sendToRestApi("0",queueNameReceive);
                    } catch (Exception e) {
                        System.out.println("Error Mutasi: ");
                        e.printStackTrace();
                    }
                }
            };
            channel.basicConsume("getNasabahInfo", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error Mutasi = " + e);
        }
    }
}
