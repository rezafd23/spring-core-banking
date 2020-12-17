package com.core.database;

import com.core.database.rabbitmq.DatabaseReceiver;

public class DatabaseMain {
    public static DatabaseReceiver receiver = new DatabaseReceiver();
    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages..");
            receiver.addNasabah();
        }catch (Exception e){
            System.out.println("Error DatabaseMain = " + e);
        }
    }
}
