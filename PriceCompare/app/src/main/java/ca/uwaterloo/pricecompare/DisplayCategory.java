package ca.uwaterloo.pricecompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import ca.uwaterloo.pricecompare.DataReq.Model.RecommendationCategory;
import ca.uwaterloo.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.pricecompare.DataReq.http.ApiMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayCategory extends AppCompatActivity {
    private String category = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category);
        // get different labels from 6 different category intents
        Bundle bundle = this.getIntent().getExtras();
        //  Category is the flag to get different items from database
        int categoryInt = bundle.getInt("Category");

        switch(categoryInt) {
            case 0: category = "Entertainment";break;
            case 1: category = "Food";break;
            case 2: category = "Drink";break;
            case 3: category = "Home";break;
            case 4: category = "Wellness";break;
            case 5: category = "Office";
        }

        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        HashMap<String,String> nameToUPC = new HashMap<>();
        ObserverOnNextListener<List<RecommendationCategory>> RecommandationCategory = recommendationCategories -> {

            for (int i = 0; i < 10; i++) {
                HashMap<String, Object> user = new HashMap<String, Object>();
                user.put("product", String.valueOf(recommendationCategories.get(i).getName()));
                user.put("price", String.valueOf(recommendationCategories.get(i).getPrice()));
                user.put("store", String.valueOf(recommendationCategories.get(i).getStorename()));
                nameToUPC.put(String.valueOf(recommendationCategories.get(i).getName()),String.valueOf(recommendationCategories.get(i).getUPC()));
                users.add(user);
            }

            SimpleAdapter saImageItems = new SimpleAdapter(this,
                    users,// 数据来源
                    R.layout.user,//每一个user xml 相当ListView的一个组件
                    new String[]{"product", "price", "store"},
                    // 分别对应view 的id
                    new int[]{R.id.product, R.id.price, R.id.category});
            // 获取listview
            ListView usersListview =  ((ListView) findViewById(R.id.users));
            usersListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object listItem = usersListview.getItemAtPosition(position);
                    String UPC = listItem.toString().replaceFirst(".*?product=", "");
                    UPC = UPC.replaceFirst(",\\s+price=.*","");
                    UPC = nameToUPC.get(UPC);
                    Log.d("UPC",UPC);
                    Log.d("item",listItem.toString());
                    Log.d("category",category);
                    Intent intent = new Intent(DisplayCategory.this, DisplayItem.class);
                    intent.putExtra("upc", UPC);
                    intent.putExtra("activity", "scanner");
                    intent.putExtra("category",category);
                    startActivity(intent);
                }
            });
            usersListview.setAdapter(saImageItems);
        };
        ApiMethods.getRecommendationByCategory(new MyObserver<>(this, RecommandationCategory), categoryInt);

    }

}
