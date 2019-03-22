package com.example.ece651BestPrice.controller;


import com.example.ece651BestPrice.bean.Bestprice;
import com.example.ece651BestPrice.bean.Category;
import com.example.ece651BestPrice.bean.RStore;
import com.example.ece651BestPrice.mapper.DisplayMapper;
import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Display")
public class DisplayController {
    @Autowired
    private DisplayMapper displayMapper;

    @RequestMapping(value = "/querycategory/{num}")
    public JSONArray recommendCategory(@PathVariable("num")int num){
        String category = selectEnum(num);
        List<Category> recommend = displayMapper.queryCategorybyUPC(category);
        if(recommend.isEmpty()){
            Map<String, String> result1 = new HashMap<>();
            result1.put("msg", "No such category");
            JSONArray result2 = JSONArray.fromObject(result1);
            return result2;
        }
        JSONArray result1 = JSONArray.fromObject(recommend);
        return result1;
    }

    @RequestMapping(value = "/querystore/{storename}")
    public JSONArray recommendStore(@PathVariable("storename")String storename){
        List<RStore> recommend = displayMapper.queryRecommendbyStore(storename);
        if(recommend.isEmpty()){
            Map<String, String> result1 = new HashMap<>();
            result1.put("msg", "No such store");
            JSONArray result2 = JSONArray.fromObject(result1);
            return result2;
        }
        JSONArray result1 = JSONArray.fromObject(recommend);
        return result1;
    }

    @RequestMapping(value = "/bestprice/{UPC}")
    public JSONArray recommendPrice(@PathVariable("UPC") String UPC){
        Bestprice result = displayMapper.queryBestpricebyUPC(UPC);
        if(result == null){
            Map<String, String> result1 = new HashMap<>();
            result1.put("msg", "No such UPC");
            JSONArray result2 = JSONArray.fromObject(result1);
            return result2;
        }
        JSONArray result2 = JSONArray.fromObject(result);
        return result2;
    }

    private String selectEnum(int num) {
        String result = null;
        switch (num){
            case 0:
                result = "Entertainment";
                break;
            case 1:
                result = "Food";
                break;
            case 2:
                result = "Drink";
                break;
            case 3:
                result = "Home";
                break;
            case 4:
                result = "Wellness";
                break;
            case 5:
                result = "Office";
                break;
            default:
                break;
        }
        return result;
    }


}
