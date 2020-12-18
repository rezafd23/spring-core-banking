package com.core.restapi.controller;

import com.core.database.model.MutasiParams;
import com.core.database.model.RegisterNasabah;
import com.core.database.model.Transaksi;
import com.core.database.model.ViewTransaksi;
import com.core.restapi.rabbitmq.ApiReceiver;
import com.core.restapi.rabbitmq.ApiSender;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {
    public final ApiReceiver receiver = new ApiReceiver();
//    private Properties properties = new Properties();
//    private String propName = "config.properties";

    // -------------------Add Transaksi-------------------------------------------
    @RequestMapping(value = "/add/", method = RequestMethod.POST)
    public ResponseEntity<?> addTraansaksi(@RequestBody Transaksi transaksi, @RequestHeader String apikey) {
        JSONObject object = new JSONObject();
        if (apikey.equals("1001")) {
            try {
                String queueNameReceive = "addTransaksiMessage";
                ApiSender.sendToDb(new Gson().toJson(transaksi), "addTransaksi");
                String res = receiver.receiveFromDatabase(queueNameReceive);
                if (!res.equals("0") && !res.equals("2")) {
                    object.put("response", 200);
                    object.put("status", "Success");
                    object.put("message", "Success Add Data Transaksi");
                    object.put("trx_code", res);
                    return new ResponseEntity<>(object, HttpStatus.OK);
                } else if (res.equals("2")) {
                    object.put("response", 400);
                    object.put("status", "Error");
                    object.put("message", "Transaction Failed! Please Check your Balance.");
                    return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
                } else {
                    object.put("response", 400);
                    object.put("status", "Error");
                    object.put("message", "Internal Server Error");
                    return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println("error = " + e);
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Error Add Data Transaksi");
                return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
            }
        } else {
            object.put("response", 401);
            object.put("status", "Unauthorized");
            object.put("message", "Invalid Apikey Access");
            return new ResponseEntity<>(object, HttpStatus.UNAUTHORIZED);
        }

    }

    // -------------------get Mutasi-------------------------------------------
    @RequestMapping(value = "/mutasi/", method = RequestMethod.GET)
    public ResponseEntity<?> mutasi(@RequestBody MutasiParams transaksi, @RequestHeader String apikey) {
        JSONObject object = new JSONObject();
        if (apikey.equals("1001")) {
            try {
                String queueNameReceive = "getMutasiMessage";
                ApiSender.sendToDb(new Gson().toJson(transaksi), "getMutasi");
                String res = receiver.receiveFromDatabase(queueNameReceive);
                Type listType = new TypeToken<List<ViewTransaksi>>() {}.getType();
                List<ViewTransaksi> transaksiList = new Gson().fromJson(res, listType);

//              System.out.println("isi RES: " + res);
                if (!res.equals("0")) {
                    object.put("response", 200);
                    object.put("status", "Success");
                    object.put("message", "Success Get Mutasi");
                    object.put("payload", transaksiList);
                    return new ResponseEntity<>(object, HttpStatus.OK);
                } else {
                    object.put("response", 400);
                    object.put("status", "Error");
                    object.put("message", "Internal Server Error");
                    return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println("error = " + e);
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Error Get Mutasi");
                return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
            }
        } else {
            object.put("response", 401);
            object.put("status", "Unauthorized");
            object.put("message", "Invalid Apikey Access");
            return new ResponseEntity<>(object, HttpStatus.UNAUTHORIZED);
        }

    }
}
