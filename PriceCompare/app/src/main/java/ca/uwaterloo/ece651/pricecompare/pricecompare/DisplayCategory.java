package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.RecommendationCategory;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.RecommendationStore;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Stock;
import ca.uwaterloo.ece651.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.ece651.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.ece651.pricecompare.DataReq.http.Api;
import ca.uwaterloo.ece651.pricecompare.DataReq.http.ApiMethods;

public class DisplayCategory extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category);
        // get different labels from 6 different category intents
        Bundle bundle = this.getIntent().getExtras();
        //  Category is the flag to get diffenent items from daatabase
        int Category = bundle.getInt("Category");

        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        ObserverOnNextListener<List<RecommendationCategory>> RecommandationCategory = recommendationCategories -> {

            for (int i = 0; i < 10; i++) {
                HashMap<String, Object> user = new HashMap<String, Object>();
                user.put("product", String.valueOf(recommendationCategories.get(i).getName()));
                user.put("price", String.valueOf(recommendationCategories.get(i).getPrice()));
                user.put("store", String.valueOf(recommendationCategories.get(i).getStorename()));
                users.add(user);
            }

            SimpleAdapter saImageItems = new SimpleAdapter(this,
                    users,// 数据来源
                    R.layout.user,//每一个user xml 相当ListView的一个组件
                    new String[]{"product", "price", "store"},
                    // 分别对应view 的id
                    new int[]{R.id.product, R.id.price, R.id.category});
            // 获取listview
            ((ListView) findViewById(R.id.users)).setAdapter(saImageItems);
        };
        ApiMethods.getRecommendationByCategory(new MyObserver<>(this, RecommandationCategory), Category);

    }

}
