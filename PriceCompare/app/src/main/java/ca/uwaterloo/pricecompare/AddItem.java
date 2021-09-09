package ca.uwaterloo.pricecompare;

import static android.view.WindowManager.LayoutParams;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import ca.uwaterloo.pricecompare.model.Item;
import ca.uwaterloo.pricecompare.model.Product;
import ca.uwaterloo.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.pricecompare.DataReq.http.ApiMethods;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONTokener;


class RetrieveURLContent extends AsyncTask<String, Void, String> {

  private static String convertStreamToString(InputStream is) {

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

  protected String doInBackground(String... urlToRead) {
    try {
      URL url = new URL(urlToRead[0]);
      InputStream is = url.openStream();
      JSONTokener tokener = new JSONTokener(convertStreamToString(is));
      JSONObject object = new JSONObject(tokener);
      JSONObject courses = (JSONObject) object.getJSONArray("items").get(0);
      return (String) courses.get("title");
    } catch (Exception e) {
      return null;
    }
  }
}


public class AddItem extends AppCompatActivity {

  // Member variables
  public static final int REQUEST_CAMERA = 1;
  public static final int REQUEST_ALBUM = 2;
  private static final String ITEM_REQUEST_URL = "https://api.upcitemdb.com/prod/trial/lookup?upc=";
  private static final int REQUEST_PERMISSION_CODE = 3;
  private static final String[] PERMISSIONS_STORAGE = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE};
  PopupWindow popupPhotoWindow;
  PopupWindow popupCategorySelectWindow;
  PopupWindow popupStoreSelectWindow;
  // Data
  double lat, lng;
  String nearestStore = "";
  private File output;
  private Uri imageUri;
  private ImageView image;
  private Button categorySelectButton;
  private int categorySelected = 0;
  private Button storeSelectButton;
  private String storeSelected;
  private Boolean categorySelectedBoolean = false;
  private EditText textUPC;
  private EditText textName;
  private EditText textPrice;
  private final int newStoreFlag = 0;
  private int productNameChangeFlag = 0;
  private FusedLocationProviderClient mFusedLocationClient;
  private final HashMap<String, List<Double>> stores = new HashMap<>();

  // Utility functions

  private void addimage() {
    View popupPhotoView = View.inflate(this, R.layout.popup_photo_window, null);
    Button bt_album = popupPhotoView.findViewById(R.id.btn_pop_album);
    Button bt_camera = popupPhotoView.findViewById(R.id.btn_pop_camera);
    Button bt_cancel = popupPhotoView.findViewById(R.id.btn_pop_cancel);
    int weight = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels * 1 / 3;
    popupPhotoWindow = new PopupWindow(popupPhotoView, weight, height);
    popupPhotoWindow.setFocusable(true);
    popupPhotoWindow.setAnimationStyle(R.style.bottomPopupAnimation);
    popupPhotoWindow.setOutsideTouchable(true);
    bt_album.setOnClickListener(v -> {
      Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(i, REQUEST_ALBUM);
      popupPhotoWindow.dismiss();

    });

    bt_camera.setOnClickListener(v -> {
      if (ActivityCompat
          .checkSelfPermission(AddItem.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat
            .requestPermissions(AddItem.this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
      } else {
        takeCamera();
      }
    });

    bt_cancel.setOnClickListener(v -> popupPhotoWindow.dismiss());

    popupPhotoWindow.setOnDismissListener(() -> {
      LayoutParams lp = getWindow().getAttributes();
      lp.alpha = 1.0f;
      getWindow().setAttributes(lp);
    });
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 0.5f;
    getWindow().setAttributes(lp);
    popupPhotoWindow.showAtLocation(popupPhotoView, Gravity.BOTTOM, 0, 50);
  }

  public void takeCamera() {
    File file = new File(Environment.getExternalStorageDirectory(), "photos");
    if (!file.exists()) {
      file.mkdir();
    }
    output = new File(file, System.currentTimeMillis() + ".jpg");

    try {
      if (output.exists()) {
        output.delete();
      }
      output.createNewFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
//        imageUri = Uri.fromFile(output);
    imageUri = FileProvider
        .getUriForFile(AddItem.this, getPackageName() + ".my.package.name.provider", output);
    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    int checkCallPhonePermission = ContextCompat.checkSelfPermission(AddItem.this,
        Manifest.permission.CAMERA);
    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(AddItem.this, new String[]{Manifest.permission.CAMERA}, 222);
    } else {
      startActivityForResult(intent, REQUEST_CAMERA);
      popupPhotoWindow.dismiss();
    }

  }

  public void selectCategory() {
    View popupCategoryView = View.inflate(this, R.layout.popup_cat_select_window, null);

    Button btEntertainment = popupCategoryView.findViewById(R.id.pop_cat_button_0);
    Button btFood = popupCategoryView.findViewById(R.id.pop_cat_button_1);
    Button btDrink = popupCategoryView.findViewById(R.id.pop_cat_button_2);
    Button btHome = popupCategoryView.findViewById(R.id.pop_cat_button_3);
    Button btWellness = popupCategoryView.findViewById(R.id.pop_cat_button_4);
    Button btOffice = popupCategoryView.findViewById(R.id.pop_cat_button_5);
    Button btCancel = popupCategoryView.findViewById(R.id.pop_cat_button_cancel);

    btEntertainment.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_entertainment));
      //categorySelected = "Entertainment";
      categorySelected = 0;  //0: entertainment
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btFood.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_food));
      categorySelected = 1; //1: Food
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btDrink.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_drink));
      categorySelected = 2; //drink;
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btHome.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_home));
      categorySelected = 3; //getResources().getString(R.string.cat_home);
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btWellness.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_wellness));
      categorySelected = 4;// getResources().getString(R.string.cat_wellness);
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btOffice.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_office));
      categorySelected = 5;//getResources().getString(R.string.cat_office);
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btCancel.setOnClickListener(v -> {
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });

    popupCategorySelectWindow = new PopupWindow(popupCategoryView,
        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    popupCategorySelectWindow.setFocusable(true);
    popupCategorySelectWindow.setOutsideTouchable(true);
    popupCategorySelectWindow.setAnimationStyle(R.style.fadePopupAnimation);

    popupCategorySelectWindow.setOnDismissListener(() -> {
      WindowManager.LayoutParams lp = getWindow().getAttributes();
      lp.alpha = 1.0f;
      getWindow().setAttributes(lp);
    });
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 0.5f;
    getWindow().setAttributes(lp);

    popupCategorySelectWindow.showAtLocation(popupCategoryView, Gravity.CENTER, 0, 0);
  }

  public void getLocation() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(AddItem.this,
          new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 222);
      ActivityCompat
          .requestPermissions(AddItem.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
              222);
    }

    mFusedLocationClient.getLastLocation()
        .addOnSuccessListener(this, location -> {
          if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            Toast.makeText(getBaseContext(),
                "Current location: " + location.getLatitude() + ", " + location.getLongitude(),
                Toast.LENGTH_SHORT).show();
          } else {
            lat = 0;
            lng = 0;
            Toast.makeText(getBaseContext(), "Can't get current location", Toast.LENGTH_SHORT)
                .show();
          }
        });
  }

  public void getNearestStore() {
    Log.v("INFO", String.valueOf(lat));
    Log.v("INFO", String.valueOf(lng));

    double minDistanceSq = Double.MAX_VALUE;

    for (String store : stores.keySet()) {
      double storeLat = stores.get(store).get(0);
      double storeLng = stores.get(store).get(1);
      if ((lat - storeLat) * (lat - storeLat) + (lng - storeLng) * (lng - storeLng)
          < minDistanceSq) {
        nearestStore = store;
        minDistanceSq = (lat - storeLat) * (lat - storeLat) + (lng - storeLng) * (lng - storeLng);
      }
    }
  }

  public void selectStore() {

    View popupStoreView = View.inflate(this, R.layout.popup_store_select_window, null);

    LinearLayout storeScrollView = popupStoreView.findViewById(R.id.storeScrollLayout);
    for (String store : stores.keySet()) {
      Button newButton = new Button(this);
      newButton.setText(store);
      newButton.setBackgroundColor(getResources().getColor(R.color.white));
      newButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
      newButton.setPaddingRelative(130, 0, 0, 0);
      newButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
      newButton.setTextSize(18);
      newButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
          LayoutParams.WRAP_CONTENT));
      storeScrollView.addView(newButton);
      newButton.setOnClickListener(v -> {
        storeSelectButton.setText(store);
        storeSelected = store;
        popupStoreSelectWindow.dismiss();
      });
    }

    Button btCancel = popupStoreView.findViewById(R.id.pop_store_button_cancel);
    btCancel.setOnClickListener(v -> {
      popupStoreSelectWindow.dismiss();
    });

    popupStoreSelectWindow = new PopupWindow(popupStoreView,
        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    popupStoreSelectWindow.setFocusable(true);
    popupStoreSelectWindow.setOutsideTouchable(true);
    popupStoreSelectWindow.setAnimationStyle(R.style.fadePopupAnimation);

    popupStoreSelectWindow.setOnDismissListener(() -> {
      LayoutParams lp = getWindow().getAttributes();
      lp.alpha = 1.0f;
      getWindow().setAttributes(lp);
    });
    LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 0.5f;
    getWindow().setAttributes(lp);

    popupStoreSelectWindow.showAtLocation(popupStoreView, Gravity.CENTER, 0, 0);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case REQUEST_CAMERA:
        if (resultCode == RESULT_OK) {
          try {
            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            image.setImageBitmap(bit);
          } catch (Exception e) {
            Toast.makeText(this, "Crashed", Toast.LENGTH_SHORT).show();
          }
        } else {
          Log.i("tag", "Failed");
        }

        break;

      case REQUEST_ALBUM:
        if (resultCode == RESULT_OK) {
          try {
            Uri uri = data.getData();
            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            image.setImageBitmap(bit);
          } catch (Exception e) {
            e.printStackTrace();
            Log.d("tag", e.getMessage());
            Toast.makeText(this, "Crashed", Toast.LENGTH_SHORT).show();
          }
        } else {
          Log.i("liang", "Failed");
        }

        break;

      default:
        break;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_PERMISSION_CODE) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        takeCamera();
      } else {
        // Permission Denied
        Toast.makeText(AddItem.this, "Permission Denied", Toast.LENGTH_SHORT).show();
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    // Add stores. Will be replaced with database visit in the future.
    InputStream inputStream = getResources().openRawResource(R.raw.stores);
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(",");
        stores.put(tokens[0],
            Arrays.asList(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])));
        Log.v("INFO", tokens[0] + ": " + tokens[1] + ", " + tokens[2]);
      }
    } catch (Exception e) {
      Log.v("error", e.toString());
    }

    // Update nearest store
    getLocation();

    // Get UPC from scanner
    Intent intent = getIntent();
    String upc_string = intent.getStringExtra("upc");
    String store_string = intent.getStringExtra("store");
    String activity = intent.getStringExtra("activity");

    // Set UPC textEdit
    textUPC = findViewById(R.id.edt_add_UPC);
    textUPC.setText(upc_string);

    // Keep the dollar sign of the price textEdit - Han
    textPrice = findViewById(R.id.editText_price);
    textPrice.setText("$");
    Selection.setSelection(textPrice.getText(), textPrice.getText().length());
    textPrice.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Do nothing

      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
          int after) {
        // Do nothing
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (!s.toString().startsWith("$")) {
          textPrice.setText("$");
          Selection.setSelection(textPrice.getText(), textPrice.getText().length());
        }

      }
    });

    image = findViewById(R.id.add_image);
    image.setImageResource(R.drawable.ic_launcher);
    image.setOnClickListener(v -> addimage());

    // Category selection
    categorySelectButton = findViewById(R.id.button_select_category);
    categorySelectButton.setOnClickListener(v -> selectCategory());
    // Store selection
    storeSelectButton = findViewById(R.id.button_select_store);
    storeSelectButton.setOnClickListener(v -> selectStore());

    textName = findViewById(R.id.edt_add_name);

    //the activity is from display
    if (activity.equals("display")) {
      // get product information from database and display
      // Set produce name
      ObserverOnNextListener<List<Product>> ProductListener = products -> {
        EditText textName = findViewById(R.id.edt_add_name);
        textName.setText(products.get(0).getName());
        categorySelectButton.setText(products.get(0).getCategory());
      };
      ApiMethods.getProduct(new MyObserver<>(this, ProductListener), upc_string);
      storeSelected = store_string;
      storeSelectButton.setText(store_string);
      categorySelectedBoolean = true;
      productNameChangeFlag = 1;

    }
    //the activity is from scanner
    else {
      // Set the nearest store
      getNearestStore();
      nearestStore = "Waterloo Central";
      storeSelectButton.setText(nearestStore);
      storeSelected = nearestStore;
      // get product name from the website and set
      try {
        String name = new RetrieveURLContent().execute(ITEM_REQUEST_URL + upc_string).get();
        textName.setText(name);
      } catch (Exception e) {
        Log.v("ASYNC_ERROR", e.toString());
      }
    }

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
    switch (id) {
      case R.id.action_done: {
        //this.finish();
        //When all the inputs are done, click '√'

//---------------------request and data received----------------------------

        storeSelected.replaceAll("\\s", "%20");
        String UPC = textUPC.getText().toString();
        String productName = textName.getText().toString();
        String price = textPrice.getText().toString().substring(1);

        if (categorySelectedBoolean && !UPC.equals("") && !productName.equals("") && !price
            .equals("")) {
          ObserverOnNextListener<List<Item>> itemListener = items -> {
            Toast addItemToast = Toast.makeText(this, "AddItem: " + items.get(0).getMsg(),
                Toast.LENGTH_SHORT);
            addItemToast.show();
            Log.d("item", "" + items.get(0).getMsg());
          };

          String url = String.format("/Item/Insert?item=%d?=%d?=%s?=%s?=%d?=%s?=%f",
              newStoreFlag, productNameChangeFlag, UPC, productName, categorySelected,
              storeSelected, Float.parseFloat(price));
          ApiMethods.createItem(new MyObserver<>(this, itemListener),
              url);
          Intent intent = new Intent(this, MainActivity.class);
          startActivity(intent);
        } else {
          Toast fillInToast = Toast.makeText(this, "Please fill in the form completely",
              Toast.LENGTH_SHORT);
          fillInToast.show();
        }

//---------------------------------------------------------------------------
        break;
      }
      case R.id.action_delete: {
        this.finish();
        break;
      }
    }
    //return super.onOptionsItemSelected(item);
    return true;
    //return true;
  }


}
