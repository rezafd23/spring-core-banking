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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                    object.put("response", "200");
                    object.put("status", "Success");
                    object.put("message", "Success Add Data Transaksi");
                    object.put("trx_code", res);
                    return new ResponseEntity<>(object, HttpStatus.OK);
                } else if (res.equals("2")) {
                    object.put("response", "400");
                    object.put("status", "Error");
                    object.put("message", "Transaction Failed! Please Check your Balance.");
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
                object.put("message", "Error Add Data Transaksi");
                return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
            }
        } else {
            object.put("response", "401");
            object.put("status", "Unauthorized");
            object.put("message", "Invalid Apikey Access");
            return new ResponseEntity<>(object, HttpStatus.UNAUTHORIZED);
        }

    }

    // -------------------get Mutasi-------------------------------------------
    @RequestMapping(value = "/mutasi/{no_rekening}/{start_date}/{end_date}", method = RequestMethod.GET)
    public ResponseEntity<?> mutasi(@PathVariable("no_rekening") String no_rekening,
                                    @PathVariable("start_date") String start_date,
                                    @PathVariable("end_date") String end_date,
                                    @RequestHeader String apikey) {
        JSONObject object = new JSONObject();
        if (apikey.equals("1001")) {
            try {
                String queueNameReceive = "getMutasiMessage";
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

//                Date date_start = myFormat.parse(transaksi.getStart_date());
//                Date date_end = myFormat.parse(transaksi.getEnd_date());
                Date date_start = myFormat.parse(start_date);
                Date date_end = myFormat.parse(end_date);
                Date date_now = new Date();
                long diff = date_now.getTime() - date_start.getTime();
                long diff2 = date_now.getTime() - date_end.getTime();
                System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                System.out.println("Days2: " + TimeUnit.DAYS.convert(diff2, TimeUnit.MILLISECONDS));

                if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)>30){
                    object.put("response", "400");
                    object.put("status", "Error");
                    object.put("message", "Date Start More than 30 days from today");
                    return new ResponseEntity<>(object, HttpStatus.OK);

                } else if (TimeUnit.DAYS.convert(diff2, TimeUnit.MILLISECONDS)<0){
                    object.put("response", "400");
                    object.put("status", "Error");
                    object.put("message", "Date End More than today");
                    return new ResponseEntity<>(object, HttpStatus.OK);
                }
                else {
                    MutasiParams transaksi = new MutasiParams();
                    transaksi.setNo_rekening(no_rekening);
                    transaksi.setStart_date(start_date+" 00:00:00");
                    transaksi.setEnd_date(end_date+" 23:59:59");
                    ApiSender.sendToDb(new Gson().toJson(transaksi), "getMutasi");
                    String res = receiver.receiveFromDatabase(queueNameReceive);

//              System.out.println("isi RES: " + res);
                    if (!res.equals("0")&&!res.equals("2")) {
//                        Type listType = new TypeToken<List<ViewTransaksi>>() {}.getType();
//                        List<ViewTransaksi> transaksiList = new Gson().fromJson(res, listType);
                        JSONParser parser = new JSONParser();
                        JSONArray payload = new JSONArray();
                        JSONArray jsonArray = (JSONArray) parser.parse(res);
                        for (int i=0;i<jsonArray.size();i++){
                            JSONArray array = (JSONArray) jsonArray.get(i);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("no_rekening",array.get(0));
                            jsonObject.put("nama_transaksi",array.get(1));
                            jsonObject.put("desc_transaksi",array.get(2));
                            jsonObject.put("nominal",array.get(3));
                            jsonObject.put("status_transaksi",array.get(4));
                            jsonObject.put("card_no",array.get(5));
                            jsonObject.put("tgl_transaksi",array.get(6));
                            payload.add(jsonObject);
                        }

                        object.put("response", "200");
                        object.put("status", "Success");
                        object.put("message", "Success Get Mutasi");
                        object.put("payload", payload);
                        return new ResponseEntity<>(object, HttpStatus.OK);
                    } else if (res.equals("2")){
                        object.put("response", "200");
                        object.put("status", "Success");
                        object.put("message", "Tidak Ada Transaksi");
                        return new ResponseEntity<>(object, HttpStatus.OK);
                    }
                    else {
                        object.put("response", "200");
                        object.put("status", "Error");
                        object.put("message", "Internal Server Error");
                        return new ResponseEntity<>(object, HttpStatus.OK);
                    }
                }


            } catch (Exception e) {
                System.out.println("error = " + e);
                object.put("response", "400");
                object.put("status", "Error");
                object.put("message", "Error Get Mutasi");
                return new ResponseEntity<>(object, HttpStatus.OK);
            }
        } else {
            object.put("response", "401");
            object.put("status", "Unauthorized");
            object.put("message", "Invalid Apikey Access");
            return new ResponseEntity<>(object, HttpStatus.UNAUTHORIZED);
        }

    }

//    Update Transaksi
    @RequestMapping(value = "/update/{trx_code}/{desc}", method = RequestMethod.PUT)
    public ResponseEntity<?> redeemToken(@PathVariable("trx_code") String trx_code,
                                         @PathVariable("desc") String desc,
                                         @RequestHeader String apikey) {
        JSONObject object = new JSONObject();
        if (apikey.equals("1001")) {
            try {
                String queueNameReceive = "updateTrxMessage";
                JSONObject jsonObject =new JSONObject();
                jsonObject.put("trx_code",trx_code);
                jsonObject.put("desc",desc);
                ApiSender.sendToDb(jsonObject.toJSONString(), "updateTrx");
                String res = receiver.receiveFromDatabase(queueNameReceive);
                if (!res.equals("0")) {
                    object.put("response", 200);
                    object.put("status", "Success");
                    object.put("message", "Success Redeem Token");
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
                object.put("message", "Internal Server Error");
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
