package ca.uwaterloo.pricecompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import ca.uwaterloo.pricecompare.DataReq.Model.RecommendationStore;
import ca.uwaterloo.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.pricecompare.DataReq.http.ApiMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        HashMap<String,String> nameToUPC = new HashMap<>();
        ObserverOnNextListener<List<RecommendationStore>> RecommandationStore = recommendationStores -> {

            for (int i = 0; i < 10; i++) {
                HashMap<String, Object> user = new HashMap<String, Object>();
                user.put("product", String.valueOf(recommendationStores.get(i).getName()));
                user.put("price", String.valueOf(recommendationStores.get(i).getPrice()));
                user.put("category",String.valueOf(recommendationStores.get(i).getCategory()));
                nameToUPC.put(String.valueOf(recommendationStores.get(i).getName()),String.valueOf(recommendationStores.get(i).getUPC()));
                users.add(user);
            }
            SimpleAdapter saImageItems = new SimpleAdapter(this,
                    users,// 数据来源
                    R.layout.user,//每一个user xml 相当ListView的一个组件
                    new String[] {  "product", "price","category" },
                    // 分别对应view 的id
                    new int[] {  R.id.product, R.id.price, R.id.category });

            // 获取listview,set onclick
            ListView usersListview = (ListView)findViewById(R.id.users);
            usersListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object listItem = usersListview.getItemAtPosition(position);
                    String UPC = listItem.toString().replaceFirst(".*?product=", "");
                    UPC = UPC.replaceFirst(",\\s+price=.*","");
                    UPC = nameToUPC.get(UPC);
                    String category = listItem.toString().replaceFirst(".*?category=","");
                    category = category.replaceFirst("\\}","");
                    Log.d("UPC",UPC);
                    Log.d("item",listItem.toString());
                    Log.d("category",category);
                    Intent intent = new Intent(DisplayStore.this, DisplayItem.class);
                    intent.putExtra("upc", UPC);
                    intent.putExtra("activity", "scanner");
                    intent.putExtra("category",category);
                    startActivity(intent);
                }
            });
            usersListview.setAdapter(saImageItems);


        };

        ApiMethods.getRecommendationByStore(new MyObserver<>(this, RecommandationStore), Store);
    }


}
