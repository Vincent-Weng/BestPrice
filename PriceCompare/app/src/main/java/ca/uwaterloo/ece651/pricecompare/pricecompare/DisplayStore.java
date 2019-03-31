package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.RecommendationStore;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Stock;
import ca.uwaterloo.ece651.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.ece651.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.ece651.pricecompare.DataReq.http.ApiMethods;

public class DisplayStore extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_store);
        // get different labels from 6 different category intents
        Bundle bundle = this.getIntent().getExtras();
        //  Category is the flag to get diffenent items from daatabase
        String Store = bundle.getString("Store");
      //  Toast toast=Toast.makeText(getApplicationContext(), Store+"content has been propogated", Toast.LENGTH_SHORT);
       // toast.show();

        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        ObserverOnNextListener<List<RecommendationStore>> RecommandationStore = recommendationStores -> {

            for (int i = 0; i < 10; i++) {
                HashMap<String, Object> user = new HashMap<String, Object>();
                user.put("product", String.valueOf(recommendationStores.get(i).getName()));
                user.put("price", String.valueOf(recommendationStores.get(i).getPrice()));
                user.put("category",String.valueOf(recommendationStores.get(i).getCategory()));
                users.add(user);
            }
            SimpleAdapter saImageItems = new SimpleAdapter(this,
                    users,// 数据来源
                    R.layout.user,//每一个user xml 相当ListView的一个组件
                    new String[] {  "product", "price","category" },
                    // 分别对应view 的id
                    new int[] {  R.id.product, R.id.price, R.id.category });
            // 获取listview
            ((ListView) findViewById(R.id.users)).setAdapter(saImageItems);
        };

        ApiMethods.getRecommendationByStore(new MyObserver<>(this, RecommandationStore), Store);
    }


}
