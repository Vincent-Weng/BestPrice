package com.example.ece651BestPrice;

//import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Ece651BestpriceApplicationTests {


    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testProductCreate() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("UPC", "12345678910");
        map.put("name", "test1");
        map.put("category", "0");

        MvcResult result = mockMvc.perform(get("/Product/Insert?UPC=12345678910&name=test1&category=0").content(JSONObject.toJSONString(map)))
                        .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        Map<String, Object> map2 = new HashMap<>();
        map.put("UPC", "12345678910");
        map.put("name", "test1");
        map.put("category", "0");

        MvcResult result2 = mockMvc.perform(get("/Product/Insert?UPC=12345678910&name=test1&category=0").content(JSONObject.toJSONString(map)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());

    }

    @Test
    public void testProductQuery() throws Exception{

        MvcResult result = mockMvc.perform(get("/Product/Query/12345678910").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        MvcResult result2 = mockMvc.perform(get("/Product/Query/12345678915").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testProductUpdate() throws Exception{
        MvcResult result = mockMvc.perform(get("/Product/Update").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("UPC", "12345678910")
                .param("name","test2")
                .param("category","4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        MvcResult result2 = mockMvc.perform(get("/Product/Update").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("UPC", "12345678915")
                .param("name","test2")
                .param("category","4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testProductDelete() throws Exception{
        MvcResult result = mockMvc.perform(get("/Product/Delete/12345678910").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        MvcResult result2 = mockMvc.perform(get("/Product/Delete/12345678915").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStoreInsert() throws Exception{
        MvcResult result = mockMvc.perform(get("/Store/Insert").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("storename", "test1")
                .param("address", "test2")
                .param("latitude", "2.2")
                .param("longitude", "2.2")
                .param("city", "test4")
                .param("province", "test5")
                .param("postcode", "test6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Store/Insert").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("storename", "test1")
                .param("address", "test2")
                .param("latitude", "2.2")
                .param("longitude", "2.2")
                .param("city", "test4")
                .param("province", "test5")
                .param("postcode", "test6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStoreQuery() throws Exception{
        MvcResult result = mockMvc.perform((get("/Store/Query/test1")).contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform((get("/Store/Query/test2")).contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStoreUpdate() throws Exception{
        MvcResult result = mockMvc.perform(get("/Store/Update").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("storename", "test1")
                .param("address", "test2")
                .param("latitude", "2.2")
                .param("longitude", "2.2")
                .param("city", "test4")
                .param("province", "test5")
                .param("postcode", "test6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Store/Update").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("storename", "test2")
                .param("address", "test2")
                .param("latitude", "2.2")
                .param("longitude", "2.2")
                .param("city", "test4")
                .param("province", "test5")
                .param("postcode", "test6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStoreDelete() throws Exception{
        MvcResult result = mockMvc.perform(get("/Store/Delete/test1").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Store/Delete/test3").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStockInsert() throws Exception{
        MvcResult result = mockMvc.perform(get("/Stock/Insert").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("storename", "test1")
        .param("UPC", "12345678910")
        .param("price","2.59"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Stock/Insert").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("storename", "test1")
                .param("UPC", "12345678910")
                .param("price","2.59"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStockQuery() throws Exception{
        MvcResult result = mockMvc.perform(get("/Stock/Query/12345678910/test1").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Stock/Query/12345678910/test2").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result2.getResponse().getContentAsString());


    }

    @Test
    public void testStockUpdate() throws Exception{
        MvcResult result = mockMvc.perform(get("/Stock/Update").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("UPC", "12345678910")
        .param("storename", "test1")
        .param("price","5.11"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Stock/Update").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("UPC", "12345678915")
                .param("storename", "test1")
                .param("price","5.11"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStockDelete() throws Exception{
        MvcResult result = mockMvc.perform(get("/Stock/Delete/12345678910/test1").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Stock/Delete/12345678915/test1").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }

    @Test
    public void testStoreQueryall() throws Exception{
        MvcResult result = mockMvc.perform(get("/Store/Queryallstore").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testItemInsert() throws Exception{
        MvcResult result = mockMvc.perform(get("/Item/Insert?item=0?=0?=12345678911?=Test product(3)?=0?=test1?=12.34")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(get("/Item/Insert?item=0?=0?=12345678911?=Test product(3)?=0?=test1?=12.34")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());




    }

    @Test
    public void testDisplayQcategory() throws Exception{
        MvcResult result = mockMvc.perform(get("/Display/querycategory/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    public void testDisplayQstore() throws Exception{
        MvcResult result = mockMvc.perform(get("/Display/querystore/Sobeys Columbia")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDisplayBestprice() throws Exception{
        MvcResult result = mockMvc.perform(get("/Display/bestprice/60410017715")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}

