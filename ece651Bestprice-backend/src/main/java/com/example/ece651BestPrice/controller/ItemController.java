package com.example.ece651BestPrice.controller;

import com.example.ece651BestPrice.bean.Product;
import com.example.ece651BestPrice.bean.Stock;
import com.example.ece651BestPrice.bean.Store;
import com.example.ece651BestPrice.mapper.ProductMapper;
import com.example.ece651BestPrice.mapper.StockMapper;
import com.example.ece651BestPrice.mapper.StoreMapper;
import com.example.ece651BestPrice.util.ProductDecoder;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Item")
public class ItemController {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StoreMapper storeMapper;

    @RequestMapping(value = "/Insert", method = RequestMethod.GET)
    public Object insertItem(@RequestParam String item){
        ProductDecoder pd = new ProductDecoder(item);
        boolean flagnewstore = pd.getFlagNewStore();
        boolean flagchangename = pd.getFlagChangeName();
        int category = pd.getCategory();
        String UPC = pd.getUPC();
        String name = pd.getName();
        String storename = pd.getStore();
        double price = pd.getPrice();
        Product p = new Product();
        Store S = new Store();
        p.setUPC(UPC);
        p.setName(name);
        Stock sk = new Stock();
        sk.setPrice(price);
        sk.setUPC(UPC);
        sk.setStorename(storename);

        switch (category){
            case 0:
                p.setCategory("Entertainment");
                break;
            case 1:
                p.setCategory("Food");
                break;
            case 2:
                p.setCategory("Drink");
                break;
            case 3:
                p.setCategory("Home");
                break;
            case 4:
                p.setCategory("Wellness");
                break;
            case 5:
                p.setCategory("Office");
                break;
            default:
                break;
        }

        if(flagnewstore){
            S.setStorename(storename);
            Map<String, String> result = new HashMap<>();
            int createResult = 0;
            try {
                createResult = storeMapper.createStore(S);
            } catch (DataAccessException e) {
                final Throwable cause = e.getCause();
                if(cause instanceof SQLIntegrityConstraintViolationException) {
                    result.put("msg", "existed store");
                    JSONArray result1 = JSONArray.fromObject(result);
                    return result1;
                }
            }
            if(createResult>0){
                result.put("msg", "newstoresuccess");
            }
            else{
                result.put("msg", "newstorefailure");
            }
        }

        if(flagchangename){
            Map<String, String> result = new HashMap<>();
            int createReuslt1 = 0;
            int createResult = productMapper.updateProductname(p.getName(), p.getUPC());
            int stocknum = stockMapper.queryStocknum(UPC,storename);
            if(stocknum==0) {
                createReuslt1 = stockMapper.createStock(sk);
            }
            if(createResult>0 || createReuslt1>0){
                result.put("msg", "success");
            }
            else if(createResult==0 && createReuslt1==0){
                result.put("msg", "failure");
            }
            JSONArray result1 = JSONArray.fromObject(result);
            return result1;

        }

        Map<String, String> result = new HashMap<>();
        int createResult1 = 0;
        int productnum = productMapper.queryProductnum(UPC);
        if(productnum==0) {
            createResult1 = productMapper.createProduct(p);
        }

        int createReuslt2 = 0;
        try {
            createReuslt2 = stockMapper.createStock(sk);
        } catch (DataAccessException e) {
            final Throwable cause = e.getCause();
            if(cause instanceof SQLIntegrityConstraintViolationException) {
                result.put("msg", "existed stock");
                JSONArray result1 = JSONArray.fromObject(result);
                return result1;
            }
        }
        if(createReuslt2>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "failure");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }


}
