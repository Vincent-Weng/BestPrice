package ca.uwaterloo.pricecompare.util;

import android.os.Build.VERSION_CODES;
import android.util.Log;
import androidx.annotation.RequiresApi;
import ca.uwaterloo.pricecompare.R;
import ca.uwaterloo.pricecompare.models.Store;
import com.google.common.collect.ImmutableList;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.List;
import java.util.function.Consumer;

/**
 * DEMO OF PROTOTYPE ONLY!
 *
 * Local static cache for all stores in the db. Now it's highly hardcoded for demo purposes: - Store
 * names are hardcoded in the UI and used to identify stores. In the future it should be replaced by
 * using IDs.
 *
 * In the demo, all data points are manually populated and hardcoded, so the data is not updated
 * once cached.
 *
 * In the future it should: 1. Reference store only by ID, not name. 2. use an actual cache design
 * with expiration and pulling, e.g. Guava Cache 3. avoid caching all stores locally because the
 * list could be huge
 */
public class StoreCache {

  private static final String TAG = "[StoreCache]";
  private static StoreCache storeCache = new StoreCache();

  private List<Store> stores;

  private StoreCache() {
  }

  public static StoreCache getStoreCache() {
    return storeCache;
  }

  @RequiresApi(api = VERSION_CODES.N)
  public void init(Consumer<List<Store>> consumer) {
    if (stores != null) {
      consumer.accept(stores);
      return;
    }
    Log.i(TAG, "initiate store reading");
    FirebaseFirestore firestore = FirebaseUtil.getFirestore();
    firestore
        .collection("stores")
        .get()
        .addOnCompleteListener(task -> {
          Log.i(TAG, "listener triggered to read all stores from firestore");
          if (task.isSuccessful()) {
            ImmutableList.Builder<Store> storeListBuilder = new ImmutableList.Builder<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
              Store store = document.toObject(Store.class);
              switch (store.getName()) {
                case "Sobeys Columbia":
                case "Sobeys Bridgeport":
                  store.setImageId(R.drawable.sobeys);
                  break;
                case "Zehrs Conestoga":
                case "Zehrs Lincoln":
                  store.setImageId(R.drawable.zehrs);
                  break;
                case "T&T Waterloo":
                  store.setImageId(R.drawable.tnt);
                  break;
                case "Waterloo Central":
                  store.setImageId(R.drawable.wcentral);
                  break;
                case "Walmart Waterloo":
                  store.setImageId(R.drawable.walmart);
                  break;
                case "Food Basics Laurelwood":
                  store.setImageId(R.drawable.foodbasics);
                  break;
              }
              store.setId(document.getId());
              storeListBuilder.add(store);
            }
            stores = storeListBuilder.build();
            consumer.accept(stores);
          } else {
            Log.d(TAG, "Error getting documents: ", task.getException());
          }
        });
  }

  public List<Store> getStores() {
    return stores;
  }

  public Store lookUpStoreId(String id) {
    for (Store s : getStores()) {
      if (s.getId().equals(id)) {
        return s;
      }
    }
    return null;
  }

  public Store lookUpStoreName(String name) {
    for (Store s : getStores()) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    return null;
  }
}
