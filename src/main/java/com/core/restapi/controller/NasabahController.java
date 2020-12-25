package com.core.restapi.controller;

import com.core.database.model.MutasiParams;
import com.core.database.model.Nasabah;
import com.core.database.model.RegisterNasabah;
import com.core.database.model.ViewTransaksi;
import com.core.restapi.rabbitmq.ApiReceiver;
import com.core.restapi.rabbitmq.ApiSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/nasabah")
public class NasabahController {


    public final ApiReceiver receiver = new ApiReceiver();
//    private Properties properties = new Properties();
//    private String propName = "config.properties";

    // -------------------Add Nasabah-------------------------------------------
    @RequestMapping(value = "/add/", method = RequestMethod.POST)
    public ResponseEntity<?> addNasabah(@RequestBody RegisterNasabah nasabah, @RequestHeader String apikey) {
        JSONObject object = new JSONObject();
        if (apikey.equals("1001")) {
            try {
                String queueNameReceive="addNasabahMessage";
                ApiSender.sendToDb(new Gson().toJson(nasabah),"addNasabah");
                String res = receiver.receiveFromDatabase(queueNameReceive);
                if (!res.equals("0")) {
                    object.put("response", "200");
                    object.put("status", "Success");
                    object.put("message", "Success Add Data Nasabah");
                    return new ResponseEntity<>(object, HttpStatus.OK);
                } else {
                    object.put("response", "400");
                    object.put("status", "Error");
                    object.put("message", "Internal Server Error");
                    return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println("error = " + e);
                object.put("response", "400");
                object.put("status", "Error");
                object.put("message", "Error Add Data Nasabah");
                return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
            }
        } else {
            object.put("response", "401");
            object.put("status", "Unauthorized");
            object.put("message", "Invalid Apikey Access");
            return new ResponseEntity<>(object, HttpStatus.UNAUTHORIZED);
        }
    }


    // -------------------get Nasabah Info-------------------------------------------
    @RequestMapping(value = "/info/{no_ktp}", method = RequestMethod.GET)
    public ResponseEntity<?> mutasi(@PathVariable("no_ktp") String no_ktp, @RequestHeader String apikey) {
        JSONObject object = new JSONObject();
        if (apikey.equals("1001")) {
            try {
                String queueNameReceive = "getNasabahInfoMessage";
                ApiSender.sendToDb(new Gson().toJson(no_ktp), "getNasabahInfo");
                String res = receiver.receiveFromDatabase(queueNameReceive);
                JSONParser parser =new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(res);

                if (!res.equals("0")) {
                    object.put("response", "200");
                    object.put("status", "Success");
                    object.put("payload", jsonObject);
                    return new ResponseEntity<>(object, HttpStatus.OK);
                } else {
                    object.put("response", "400");
                    object.put("status", "Error");
                    object.put("message", "Internal Server Error");
                    return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println("error = " + e);
                object.put("response", "400");
                object.put("status", "Error");
                object.put("message", "Error Get Nasabah Info");
                return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
            }
        } else {
            object.put("response", "401");
            object.put("status", "Unauthorized");
            object.put("message", "Invalid Apikey Access");
            return new ResponseEntity<>(object, HttpStatus.UNAUTHORIZED);
        }

    }
}
