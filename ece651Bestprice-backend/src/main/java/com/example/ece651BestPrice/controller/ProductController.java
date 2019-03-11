package com.example.ece651BestPrice.controller;

import com.example.ece651BestPrice.bean.Product;
import com.example.ece651BestPrice.mapper.ProductMapper;
import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Product")

public class ProductController {
    @Autowired
    private ProductMapper productMapper;
    @RequestMapping(value = "/Insert", method = RequestMethod.GET)
    public Object createProduct(@RequestParam String UPC, @RequestParam String name, @RequestParam Integer category){
        Product product = new Product();
        product.setUPC(UPC);
        product.setName(name);
        product.setCategory(selectEnum(category));
        Map<String, String> result = new HashMap<>();
        int createResult = 0;
        try {
            createResult = this.productMapper.createProduct(product);
        } catch (DataAccessException e) {
            final Throwable cause = e.getCause();
            if(cause instanceof SQLIntegrityConstraintViolationException) {
                result.put("msg", "existed product");
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

    @RequestMapping(value = "/Query/{UPC}", method = RequestMethod.GET)
    public JSONArray getProduct(@PathVariable("UPC")String UPC){
        JSONArray result = null;
        Product product  = this.productMapper.queryProductByUpc(UPC);
        if(product == null){
            Map<String, String> result1 = new HashMap<>();
            result1.put("msg", "No such UPC");
            JSONArray result2 = JSONArray.fromObject(result1);
            return result2;
        }
        result = JSONArray.fromObject(product);
        //        return this.productMapper.queryProductByUpc(UPC);
        return result;
    }

    @RequestMapping(value = "/Update", method = RequestMethod.GET)
    public Object updateProduct(@RequestParam String UPC, @RequestParam String name, @RequestParam Integer category){
        Product product = new Product();
        product.setUPC(UPC);
        product.setName(name);
        product.setCategory(selectEnum(category));
        Map<String, String> result = new HashMap<>();
        int createResult = 0;
        try {
            createResult = this.productMapper.updateProduct(product);
        } catch (DataAccessException e) {
            final Throwable cause = e.getCause();
            if(cause instanceof SQLIntegrityConstraintViolationException) {
                result.put("msg", "existed product");
                JSONArray result1 = JSONArray.fromObject(result);
                return result1;
            }
        }
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such UPC");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }


    @RequestMapping(value = "/Delete/{UPC}", method = RequestMethod.GET)
    public Object deleteProduct(@PathVariable("UPC") String UPC){

        Map<String, String> result = new HashMap<>();
        int createResult = this.productMapper.deleteProduct(UPC);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such UPC");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }

    @RequestMapping(value = "/Updatename", method = RequestMethod.GET)
    public Object updateProductname(@RequestParam String UPC, @RequestParam String name){
        Map<String, String> result = new HashMap<>();
        int createResult = this.productMapper.updateProductname(name, UPC);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such UPC");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Updatecategory", method = RequestMethod.GET)
    public Object updateProductcategory(@RequestParam String UPC, @RequestParam int category){
        Map<String, String> result = new HashMap<>();
        int createResult = this.productMapper.updateProductcategory(selectEnum(category), UPC);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such UPC");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;

    }

    @RequestMapping(value = "/Updatepicture", method = RequestMethod.GET)
    public Object updateProductpicture(@RequestParam String UPC, @RequestParam String picture){
        Map<String, String> result = new HashMap<>();
        int createResult = this.productMapper.updateProductpicture(picture, UPC);
        if(createResult>0){
            result.put("msg", "success");
        }
        else{
            result.put("msg", "no such UPC");
        }
        JSONArray result1 = JSONArray.fromObject(result);
        return result1;
    }

    private String selectEnum(int num){
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
