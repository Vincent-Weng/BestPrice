package ca.uwaterloo.pricecompare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ca.uwaterloo.pricecompare.models.Item;
import ca.uwaterloo.pricecompare.models.Product;
import ca.uwaterloo.pricecompare.util.StoreCache;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class DisplayCategory extends AppCompatActivity {

  private static final String TAG = "[DisplayCategory]";
  private String category = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_category);
    category = getIntent().getStringExtra("category");
    setTitle("Recommendations for " + category);

    List<Map<String, String>> data = new ArrayList<>();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    firestore
        .collection("categories")
        .document(category)
        .get()
        .addOnCompleteListener(t1 -> {
          if (t1.isSuccessful()) {
            DocumentSnapshot doc = t1.getResult();
            List<DocumentReference> recommendations = Objects.requireNonNull((List<DocumentReference>) doc.get("recommendations"));
            AtomicInteger counter = new AtomicInteger(recommendations.size());
            for (DocumentReference docRef : recommendations) {
              docRef.get().addOnCompleteListener(t2 -> {
                if (t2.isSuccessful()) {
                  Item item = Objects.requireNonNull(t2.getResult().toObject(Item.class));
                  firestore
                      .collection("products")
                      .document(item.getUpc())
                      .get()
                      .addOnCompleteListener(t3 -> {
                        if (t3.isSuccessful()) {
                          Product product = Objects
                              .requireNonNull(t3.getResult().toObject(Product.class));
                          data.add(ImmutableMap.of(
                              "name", product.getName(),
                              "price", String.valueOf(item.getPrice()),
                              "store", StoreCache.getStoreCache().lookUpStoreId(item.getStoreId()).getName(),
                              "UPC", item.getUpc()));
                          if (counter.decrementAndGet() == 0) {
                            SimpleAdapter saImageItems = new SimpleAdapter(this,
                                data,
                                R.layout.recommendations,
                                new String[]{"name", "price", "store"},
                                new int[]{R.id.product, R.id.price, R.id.note});
                            ListView recommendationListview = findViewById(R.id.recommendations);
                            recommendationListview.setOnItemClickListener((parent, view, position, id) -> {
                              Map<String, String> itemAtPosition = (Map<String, String>) recommendationListview
                                  .getItemAtPosition(position);
                              Intent intent = new Intent(DisplayCategory.this, DisplayItem.class);
                              intent.putExtra("upc", itemAtPosition.get("UPC"));
                              intent.putExtra("activity", "scanner");
                              intent.putExtra("name", itemAtPosition.get("name"));
                              intent.putExtra("category", category);
                              startActivity(intent);
                            });
                            recommendationListview.setAdapter(saImageItems);
                          }
                        }
                      });
                }
              });
            }
          } else {
            Log.d(TAG, "Error getting documents: ", t1.getException());
            if (t1.getException() instanceof FirebaseFirestoreException && ((FirebaseFirestoreException) t1.getException()).getCode() == Code.PERMISSION_DENIED) {
              Toast.makeText(getBaseContext(), "Please sign-in first!", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }
}

