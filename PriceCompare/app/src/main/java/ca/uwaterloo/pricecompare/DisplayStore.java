package ca.uwaterloo.pricecompare;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> data = new ArrayList<>();
    // ObserverOnNextListener<List<RecommendationStore>> RecommandationStore = recommendationStores -> {
    //
    //   for (int i = 0; i < 10; i++) {
    //     HashMap<String, Object> user = new HashMap<String, Object>();
    //     user.put("product", String.valueOf(recommendationStores.get(i).getName()));
    //     user.put("price", String.valueOf(recommendationStores.get(i).getPrice()));
    //     user.put("category", String.valueOf(recommendationStores.get(i).getCategory()));
    //     nameToUPC.put(String.valueOf(recommendationStores.get(i).getName()),
    //         String.valueOf(recommendationStores.get(i).getUPC()));
    //     users.add(user);
    //   }
    data.add(ImmutableMap.of("product", "aaa",
        "price", "1.00",
        "category", "ssss"));
    SimpleAdapter saImageItems = new SimpleAdapter(this,
        data,
        R.layout.recommendations,
        new String[]{"product", "price", "category"},
        new int[]{R.id.product, R.id.price, R.id.note});
    //
    ListView usersListview = findViewById(R.id.recommendations);
    //   usersListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //     @Override
    //     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //       Object listItem = usersListview.getItemAtPosition(position);
    //       String UPC = listItem.toString().replaceFirst(".*?product=", "");
    //       UPC = UPC.replaceFirst(",\\s+price=.*", "");
    //       UPC = nameToUPC.get(UPC);
    //       String category = listItem.toString().replaceFirst(".*?category=", "");
    //       category = category.replaceFirst("\\}", "");
    //       Log.d("UPC", UPC);
    //       Log.d("item", listItem.toString());
    //       Log.d("category", category);
    //       Intent intent = new Intent(DisplayStore.this, DisplayItem.class);
    //       intent.putExtra("upc", UPC);
    //       intent.putExtra("activity", "scanner");
    //       intent.putExtra("category", category);
    //       startActivity(intent);
    //     }
    //   });
    usersListview.setAdapter(saImageItems);
    //
    //
    // };
    //
    // ApiMethods.getRecommendationByStore(new MyObserver<>(this, RecommandationStore), Store);
  }


}
