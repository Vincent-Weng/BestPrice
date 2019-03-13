package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.BestPrice;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Item;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Stock;
import ca.uwaterloo.ece651.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.ece651.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.ece651.pricecompare.DataReq.http.ApiMethods;

public class DisplayItem extends AppCompatActivity {
    private Button displayStoreButton;
    private HashMap<String, List<Double>> stores = new HashMap<>();
    PopupWindow popupStoreSelectWindow;

    public void displayStore(String upc) {

        View popupStoreView = View.inflate(this, R.layout.popup_store_display_window, null);
        LinearLayout storeScrollView = popupStoreView.findViewById(R.id.storeDisplayScrollLayout);
        TableLayout table = new TableLayout(this);
        table.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT));
        storeScrollView.addView(table);

        for (String store : stores.keySet()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_store_black_24dp);
            if (store.toLowerCase().contains("sobey"))
                img = getResources().getDrawable(R.drawable.sobeys_inv);
            else if (store.toLowerCase().contains("food basics"))
                img = getResources().getDrawable(R.drawable.foodbasics_inv);
            else if (store.toLowerCase().contains("t&t"))
                img = getResources().getDrawable(R.drawable.tnt_inv);
            else if (store.toLowerCase().contains("walmart"))
                img = getResources().getDrawable(R.drawable.walmart_inv);
            else if (store.toLowerCase().contains("waterloo central"))
                img = getResources().getDrawable(R.drawable.wcentral_inv);
            else if (store.toLowerCase().contains("zehrs"))
                img = getResources().getDrawable(R.drawable.zehrs_inv);

            img.setBounds(0, 0, 100, 100);

            TextView newTextStore = new TextView(this);
            newTextStore.setText(store + ":");
            newTextStore.setBackgroundColor(getResources().getColor(R.color.white));
            newTextStore.setCompoundDrawables(img, null, null, null);
            newTextStore.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            newTextStore.setPaddingRelative(130, 0, 0, 0);
            newTextStore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            newTextStore.setTextSize(17);
            newTextStore.setTextColor(getResources().getColor(R.color.blue));
            newTextStore.setPaintFlags(newTextStore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            newTextStore.setCompoundDrawablePadding(21);
            newTextStore.setOnClickListener(v -> {
                Intent intent = new Intent(this, AddItem.class);
                intent.putExtra("upc", upc);
                intent.putExtra("activity", "display");
                intent.putExtra("store", store);
                startActivity(intent);
            });
            table.addView(newTextStore);
            TextView newTextPrice = new TextView(this);
            // if the price exists in the database, show the pircetext
            ObserverOnNextListener<List<Stock>> StockListener = stocks -> {
                //product doesn't exists in the database
                if (stocks.get(0).getMsg() != null) {
                    newTextPrice.setBackgroundColor(getResources().getColor(R.color.white));
                    newTextPrice.setPaddingRelative(230, 0, 0, 0);
                    newTextPrice.setTextSize(17);
                    newTextPrice.setText("no pirce yet");
                }
                //exists
                else {
                    newTextPrice.setBackgroundColor(getResources().getColor(R.color.white));
                    newTextPrice.setPaddingRelative(230, 0, 0, 0);
                    newTextPrice.setTextSize(17);
                    newTextPrice.setText(String.valueOf(stocks.get(0).getPrice()));
                }
            };
            table.addView(newTextPrice);
            ApiMethods.getStock(new MyObserver<>(this, StockListener), upc,store);

        }

        Button btCancel = (Button) popupStoreView.findViewById(R.id.pop_store_diplay_button_cancel);
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

        //get upc code from scanner and set it to edit_text
        Intent intent = getIntent();
        String UPC = intent.getStringExtra("upc");

        // get product information from database and display
        ObserverOnNextListener<List<Product>> ProductListener = products -> {
            EditText textName = findViewById(R.id.DisplayProductName);
            textName.setText(products.get(0).getName());
        };
        ApiMethods.getProduct(new MyObserver<>(this, ProductListener), UPC);

        //get stock information from database and display
        ObserverOnNextListener<List<BestPrice>> bestPriceListener = bestPrices -> {

            //Toast.makeText(getBaseContext(), "AddI" + products.get(0).getMsg(), Toast.LENGTH_LONG);
            Log.d("BP", "" + bestPrices.get(0).getStorename());
            //DisplayBestprice
            EditText textBestPrice = findViewById(R.id.DisplayBestprice);
            textBestPrice.setText(String.valueOf(bestPrices.get(0).getPrice()));
            EditText textStore = findViewById(R.id.DisplayRecStore);
            textStore.setText(bestPrices.get(0).getStorename());
        };
        ApiMethods.getBestPrice(new MyObserver<>(this, bestPriceListener), UPC);
        EditText textUPC = findViewById(R.id.edt_dis_upc);
        textUPC.setText(UPC);

        // Add stores. Will be replaced with database visit in the future.
        InputStream inputStream = getResources().openRawResource(R.raw.stores);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                stores.put(tokens[0], Arrays.asList(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])));
                Log.v("INFO", tokens[0] + ": " + tokens[1] + ", " + tokens[2]);
            }
        } catch (Exception e) {
            Log.v("error", e.toString());
        }


        //View all stores
        displayStoreButton = (Button) findViewById(R.id.display_store);
        displayStoreButton.setOnClickListener(v -> displayStore(UPC));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
