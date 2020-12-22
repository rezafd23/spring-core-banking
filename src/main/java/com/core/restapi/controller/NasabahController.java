package com.core.restapi.controller;

import com.core.database.model.Nasabah;
import com.core.database.model.RegisterNasabah;
import com.core.restapi.rabbitmq.ApiReceiver;
import com.core.restapi.rabbitmq.ApiSender;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
