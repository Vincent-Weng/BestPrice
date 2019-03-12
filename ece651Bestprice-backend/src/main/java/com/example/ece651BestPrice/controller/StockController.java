package com.example.ece651BestPrice.controller;

import com.example.ece651BestPrice.bean.Stock;
import com.example.ece651BestPrice.mapper.StockMapper;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Stock")

public class StockController {
    @Autowired
    private StockMapper stockMapper;
    @RequestMapping(value = "/Insert", method = RequestMethod.GET)
    public Object createStock(@ModelAttribute Stock stock){
        Map<String, String> result = new HashMap<>();
        int createResult = 0;
        try {
            createResult = this.stockMapper.createStock(stock);
        } catch (DataAccessException e) {
            final Throwable cause = e.getCause();
            if(cause instanceof SQLIntegrityConstraintViolationException) {
                result.put("msg", "existed stock");
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

    @RequestMapping(value = "/Query/{UPC}/{storename}", method = RequestMethod.GET)
    public JSONArray getStock(@PathVariable("UPC") String UPC, @PathVariable("storename") String storename){
        Stock stock = stockMapper.queryStock(UPC, storename);
        if(stock == null){
            Map<String, String> result1 = new HashMap<>();
            result1.put("msg", "No such Stock");
            JSONArray result2 = JSONArray.fromObject(result1);
            return result2;
        }
        JSONArray result = JSONArray.fromObject(stock);
        return result;
    }

    @RequestMapping(value = "/Update", method = RequestMethod.GET)
    public Object updateStock(@ModelAttribute Stock stock){
        Map<String, String> result = new HashMap<>();
        int createResult = this.stockMapper.updateStockprice(stock);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such UPC and storename");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Delete/{UPC}/{storename}", method = RequestMethod.GET)
    public Object deleteStock(@PathVariable("UPC") String UPC, @PathVariable("storename") String storename){
        Map<String, String> result = new HashMap<>();
        int createResult = this.stockMapper.deleteStock(UPC, storename);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such UPC");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }

}
