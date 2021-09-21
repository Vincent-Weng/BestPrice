package ca.uwaterloo.pricecompare;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ca.uwaterloo.pricecompare.models.Item;
import ca.uwaterloo.pricecompare.models.Store;
import ca.uwaterloo.pricecompare.util.FirebaseUtil;
import ca.uwaterloo.pricecompare.util.StoreCache;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DisplayItem extends AppCompatActivity {

  private static final String TAG = "[DisplayItem]";
  PopupWindow popupStoreSelectWindow;
  private ImageView image;
  private FirebaseFirestore firestore;

  public void displayStore(String upc, String category, String prodName) {

    View popupStoreView = View.inflate(this, R.layout.popup_store_display_window, null);
    LinearLayout storeScrollView = popupStoreView.findViewById(R.id.storeDisplayScrollLayout);
    TableLayout table = new TableLayout(this);
    table.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT));
    storeScrollView.addView(table);

    for (Store store : StoreCache.getStoreCache().getStores()) {
      Drawable img = getResources().getDrawable(R.drawable.ic_store_black_24dp);
      if (store.getName().toLowerCase().contains("sobey")) {
        img = getResources().getDrawable(R.drawable.sobeys_inv);
      } else if (store.getName().toLowerCase().contains("food basics")) {
        img = getResources().getDrawable(R.drawable.foodbasics_inv);
      } else if (store.getName().toLowerCase().contains("t&t")) {
        img = getResources().getDrawable(R.drawable.tnt_inv);
      } else if (store.getName().toLowerCase().contains("walmart")) {
        img = getResources().getDrawable(R.drawable.walmart_inv);
      } else if (store.getName().toLowerCase().contains("waterloo central")) {
        img = getResources().getDrawable(R.drawable.wcentral_inv);
      } else if (store.getName().toLowerCase().contains("zehrs")) {
        img = getResources().getDrawable(R.drawable.zehrs_inv);
      }

      img.setBounds(0, 0, 100, 100);

      TextView storeNameText = new TextView(this);
      storeNameText.setText(store.getName() + ":");
      storeNameText.setBackgroundColor(getResources().getColor(R.color.white));
      storeNameText.setCompoundDrawables(img, null, null, null);
      storeNameText.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
      storeNameText.setPaddingRelative(130, 0, 0, 0);
      storeNameText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
      storeNameText.setTextSize(17);
      storeNameText.setTextColor(getResources().getColor(R.color.blue));
      storeNameText.setPaintFlags(storeNameText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
      storeNameText.setCompoundDrawablePadding(21);
      storeNameText.setOnClickListener(v -> {
        Intent intent = new Intent(this, AddItem.class);
        intent.putExtra("upc", upc);
        intent.putExtra("activity", "display");
        intent.putExtra("store", store.getName());
        intent.putExtra("category", category);
        intent.putExtra("prodName", prodName);
        startActivity(intent);
      });
      table.addView(storeNameText);
      TextView storePriceText = new TextView(this);
      // if the price exists in the database, show the pircetext
      firestore
          .collection("items")
          .whereEqualTo("storeId", store.getId())
          .whereEqualTo("upc", upc)
          .get()
          .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
              for (QueryDocumentSnapshot document : task.getResult()) {
                // should have only one result
                storePriceText.setBackgroundColor(getResources().getColor(R.color.white));
                storePriceText.setPaddingRelative(230, 0, 0, 0);
                storePriceText.setTextSize(17);
                storePriceText.setText(String.valueOf(document.get("price")));
                return;
              }
              // does not exist in db
              storePriceText.setBackgroundColor(getResources().getColor(R.color.white));
              storePriceText.setPaddingRelative(230, 0, 0, 0);
              storePriceText.setTextSize(17);
              storePriceText.setText("N/A");
            } else {
              Log.d(TAG, "Error getting documents: ", task.getException());
            }
          });
      table.addView(storePriceText);
    }

    Button btCancel = popupStoreView.findViewById(R.id.pop_store_diplay_button_cancel);
    btCancel.setOnClickListener(v -> {
      popupStoreSelectWindow.dismiss();
    });

    popupStoreSelectWindow = new PopupWindow(popupStoreView,
        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    popupStoreSelectWindow.setFocusable(true);
    popupStoreSelectWindow.setOutsideTouchable(true);
    popupStoreSelectWindow.setAnimationStyle(R.style.fadePopupAnimation);

    popupStoreSelectWindow.setOnDismissListener(() -> {
      WindowManager.LayoutParams lp = getWindow().getAttributes();
      lp.alpha = 1.0f;
      getWindow().setAttributes(lp);
    });
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 0.5f;
    getWindow().setAttributes(lp);

    popupStoreSelectWindow.showAtLocation(popupStoreView, Gravity.CENTER, 0, 0);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_item);

    firestore = FirebaseUtil.getFirestore();
    //get upc code from scanner and set it to edit_text
    Intent intent = getIntent();
    String UPC = intent.getStringExtra("upc");
    EditText textUPC = findViewById(R.id.edt_dis_upc);
    textUPC.setText(UPC);

    image = findViewById(R.id.imageView);
    String category = intent.getStringExtra("category");
    switch (category) {
      case "Food":
        image.setImageResource(R.drawable.food);
        break;
      case "Entertainment":
        image.setImageResource(R.drawable.entertainment);
        break;
      case "Home":
        image.setImageResource(R.drawable.home);
        break;
      case "Office":
        image.setImageResource(R.drawable.office);
        break;
      case "Drink":
        image.setImageResource(R.drawable.drink);
        break;
      case "Wellness":
        image.setImageResource(R.drawable.wellness);
        break;
    }
    String prodName = intent.getStringExtra("prodName");
    EditText textName = findViewById(R.id.DisplayProductName);
    textName.setText(prodName);

    // show best price
    firestore
        .collection("items")
        .whereEqualTo("upc", UPC)
        .get()
        .addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
            String bestPriceStoreID = null;
            double bestPrice = Double.MAX_VALUE;
            for (QueryDocumentSnapshot document : task.getResult()) {
              // should have at least one result, display best price
              Item item = document.toObject(Item.class);
              if (item.getPrice() < bestPrice) {
                bestPrice = item.getPrice();
                bestPriceStoreID = item.getStoreId();
              }
            }
            if (bestPriceStoreID != null) {
              EditText textBestPrice = findViewById(R.id.DisplayBestprice);
              textBestPrice.setText(String.valueOf(bestPrice));
              EditText textStore = findViewById(R.id.DisplayRecStore);
              textStore.setText(StoreCache.getStoreCache().lookUpStoreId(bestPriceStoreID).getName());
            }
          } else {
            Log.d(TAG, "Error getting documents: ", task.getException());
          }
        });
    //View all stores
    Button displayStoreButton = findViewById(R.id.display_store);
    displayStoreButton.setOnClickListener(v -> displayStore(UPC, category, prodName));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_add_item, menu);
    menu.findItem(R.id.action_delete).setVisible(false);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_done || id == R.id.action_delete) {
      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
