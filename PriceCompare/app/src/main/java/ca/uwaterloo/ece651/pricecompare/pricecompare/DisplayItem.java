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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DisplayItem extends AppCompatActivity {
    private Button displayStoreButton;
    private HashMap<String, List<Double>> stores = new HashMap<>();
    PopupWindow popupStoreSelectWindow;

    public void displayStore(String upc){

        View popupStoreView = View.inflate(this, R.layout.popup_store_display_window, null);
        LinearLayout storeScrollView = popupStoreView.findViewById(R.id.storeDisplayScrollLayout);
        TableLayout table = new TableLayout(this);
        table.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT));
        storeScrollView.addView(table);

        for (String store : stores.keySet()) {

//            TableRow tablerow = new TableRow(this);
//            table.addView(tablerow);

            Drawable img = getResources().getDrawable(R.drawable.ic_store_black_24dp);
            if(store.toLowerCase().contains("sobey"))
                img = getResources().getDrawable( R.drawable.sobeys );
            else if(store.toLowerCase().contains("food basics"))
                img = getResources().getDrawable( R.drawable.foodbasics );
            else if(store.toLowerCase().contains("t&t"))
                img = getResources().getDrawable( R.drawable.tnt );
            else if(store.toLowerCase().contains("walmart"))
                img = getResources().getDrawable( R.drawable.walmart );
            else if(store.toLowerCase().contains("waterloo central"))
                img = getResources().getDrawable( R.drawable.wcentral );
            else if(store.toLowerCase().contains("zehrs"))
                img = getResources().getDrawable( R.drawable.zehrs );

            img.setBounds( 0, 0, 100, 100 );

            TextView newTextStore = new TextView(this);
            newTextStore.setText(store+":");
            newTextStore.setBackgroundColor(getResources().getColor(R.color.white));
            newTextStore.setCompoundDrawables( img, null, null, null );
            newTextStore.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            newTextStore.setPaddingRelative(130, 0, 0, 0);
            newTextStore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            newTextStore.setTextSize(17);
            newTextStore.setTextColor(getResources().getColor(R.color.blue));
            newTextStore.setPaintFlags(newTextStore.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            newTextStore.setCompoundDrawablePadding(21);
            newTextStore.setOnClickListener(v->{Intent intent = new Intent(this, AddItem.class);
                intent.putExtra("upc",upc);
                intent.putExtra("activity","activity_display_category");
                intent.putExtra("store",store);
                startActivity(intent);});
            table.addView(newTextStore);

            // if the price exists in the database, show the pircetext
            TextView newTextPrice = new TextView(this);
            newTextPrice.setBackgroundColor(getResources().getColor(R.color.white));
            newTextPrice.setPaddingRelative(230, 0, 0, 0);
            newTextPrice.setTextSize(17);
//            newTextPrice.setGravity(Gravity.RIGHT);
            newTextPrice.setText("$120");
            table.addView(newTextPrice);

//            // otherwise show the add button to Add_Item page.
//            Button newButtonPrice = new Button(this);
//            img = getResources().getDrawable(R.drawable.ic_add_circle_black_24dp);
//            img.setBounds( 0, 0, 100, 100 );
//            newButtonPrice.setCompoundDrawables( img, null, null, null );
//            newButtonPrice.setPaddingRelative(230, 0, 0, 0);
//            newButtonPrice.setBackgroundColor(getResources().getColor(R.color.white));
//            newButtonPrice.setBackground(null);
//            newButtonPrice.setOnClickListener(v->{Intent intent = new Intent(this, AddItem.class);
//                intent.putExtra("upc",upc);
//                intent.putExtra("activity","activity_display_category");
//                intent.putExtra("store",store);
//                startActivity(intent);});
//            table.addView(newButtonPrice);

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
        String massage = intent.getStringExtra("upc");

        EditText textUPC = (EditText)findViewById(R.id.edt_dis_upc);
        textUPC.setText(massage);



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
        displayStoreButton = (Button)findViewById(R.id.display_store);
        displayStoreButton.setOnClickListener(v -> displayStore(massage));
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
