package com.example.ece651BestPrice.controller;

import com.example.ece651BestPrice.bean.Store;
import com.example.ece651BestPrice.mapper.StoreMapper;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Store")
public class StoreController {
    @Autowired
    private StoreMapper storeMapper;

    @RequestMapping(value = "/Insert", method = RequestMethod.GET)
    public Object createStore(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = 0;
        try {
            createResult = this.storeMapper.createStore(store);
        } catch (DataAccessException e) {
            final Throwable cause = e.getCause();
            if(cause instanceof SQLIntegrityConstraintViolationException) {
                result.put("msg", "existed store");
                JSONArray result1 = JSONArray.fromObject(result);
                return result1;
            }
        }
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "failure");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Query/{storename}", method = RequestMethod.GET)
    public JSONArray getStore(@PathVariable("storename") String storename){
        Store store = this.storeMapper.queryStorebyStorename(storename);
        if(store == null){
            Map<String, String> result1 = new HashMap<>();
            result1.put("msg", "No such storename");
            JSONArray result2 = JSONArray.fromObject(result1);
            return result2;
        }

        JSONArray result = JSONArray.fromObject(store);
        return result;
    }

    @RequestMapping(value = "/Update", method = RequestMethod.GET)
    public Object updateStore(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.updateStore(store);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }


    @RequestMapping(value = "/Delete/{storename}", method = RequestMethod.GET)
    public Object deleteStore(@PathVariable("storename") String storename){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.deleteStore(storename);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }



    @RequestMapping(value = "/Updatestoreaddress", method = RequestMethod.GET)
    public Object updatestoreAddress(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.updateStoreaddress(store);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }

    @RequestMapping(value = "/Updatestorelatitude", method = RequestMethod.GET)
    public Object updatestorelatitude(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.updateStorelatitude(store);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Updatestorelongitude", method = RequestMethod.GET)
    public Object updatestorelongitude(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.updateStorelongitude(store);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Updatestorecity", method = RequestMethod.GET)
    public Object updatestorecity(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.updateStorecity(store);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Updatestoreprovince", method = RequestMethod.GET)
    public Object updatestoreprovince(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.updateStoreprovince(store);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Updatestorepostcode", method = RequestMethod.GET)
    public Object updatestorepostcode(@ModelAttribute Store store){
        Map<String, String> result = new HashMap<>();
        int createResult = this.storeMapper.updateStorepostcode(store);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }

    @RequestMapping(value = "/Queryallstore", method = RequestMethod.GET)
    public JSONArray getallStore(){
        List<String> result = this.storeMapper.queryAllstore();
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }






}
