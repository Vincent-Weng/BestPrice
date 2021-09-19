package ca.uwaterloo.pricecompare;

import static android.view.WindowManager.LayoutParams;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import ca.uwaterloo.pricecompare.models.Item;
import ca.uwaterloo.pricecompare.models.Product;
import ca.uwaterloo.pricecompare.models.Store;
import ca.uwaterloo.pricecompare.util.FirebaseUtil;
import ca.uwaterloo.pricecompare.util.StoreCache;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.File;
import java.util.List;

public class AddItem extends AppCompatActivity {

  public static final int REQUEST_CAMERA = 1;
  public static final int REQUEST_ALBUM = 2;
  private static final int REQUEST_PERMISSION_CODE = 3;
  private static final String[] PERMISSIONS_STORAGE = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE};
  private static final String TAG = "[AddItem]";
  PopupWindow popupPhotoWindow;
  PopupWindow popupCategorySelectWindow;
  PopupWindow popupStoreSelectWindow;
  // Data
  double lat, lng;
  Store nearestStore;
  private File output;
  private Uri imageUri;
  private ImageView image;
  private Button categorySelectButton;
  private String categorySelected;
  private Button storeSelectButton;
  private Store storeSelected;
  private Boolean categorySelectedBoolean = false;
  private EditText textUPC;
  private EditText textName;
  private EditText textPrice;
  private boolean productExists = false;
  private final int newStoreFlag = 0;
  private int productNameChangeFlag = 0;
  private FusedLocationProviderClient mFusedLocationClient;
  private FirebaseFirestore firestore;
  private List<Store> stores;

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
      categorySelected = "entertainment";
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btFood.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_food));
      categorySelected = "food";
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btDrink.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_drink));
      categorySelected = "drink";
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btHome.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_home));
      categorySelected = "home";
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btWellness.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_wellness));
      categorySelected = "wellness";
      popupCategorySelectWindow.dismiss();
      categorySelectedBoolean = true;
    });
    btOffice.setOnClickListener(v -> {
      categorySelectButton.setText(getResources().getString(R.string.cat_office));
      categorySelected = "office";
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
    nearestStore = stores.get(0);

    // Use manhattan distance for now
    for (Store store : stores) {
      double storeLat = store.getLocation().getLatitude();
      double storeLng = store.getLocation().getLongitude();
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
    for (Store store : stores) {
      Button newButton = new Button(this);
      newButton.setText(store.getName());
      newButton.setBackgroundColor(getResources().getColor(R.color.white));
      newButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
      newButton.setPaddingRelative(130, 0, 0, 0);
      newButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
      newButton.setTextSize(18);
      newButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
          LayoutParams.WRAP_CONTENT));
      storeScrollView.addView(newButton);
      newButton.setOnClickListener(v -> {
        storeSelectButton.setText(store.getName());
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

  @RequiresApi(api = VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    StoreCache.getStoreCache().init(stores -> this.stores = stores);

    firestore = FirebaseUtil.getFirestore();

    // Update nearest store
    getLocation();

    // Category selection
    categorySelectButton = findViewById(R.id.button_select_category);
    categorySelectButton.setOnClickListener(v -> selectCategory());
    // Store selection
    storeSelectButton = findViewById(R.id.button_select_store);
    storeSelectButton.setOnClickListener(v -> selectStore());

    Intent intent = getIntent();
    String upc = intent.getStringExtra("upc");
    String storeName = intent.getStringExtra("store");
    String activity = intent.getStringExtra("activity");
    // Set UPC textEdit
    textUPC = findViewById(R.id.edt_add_UPC);
    textUPC.setText(upc);

    // Keep the dollar sign of the price textEdit
    textPrice = findViewById(R.id.editText_price);
    textPrice.setText("$");
    Selection.setSelection(textPrice.getText(), textPrice.getText().length());
    textPrice.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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

    textName = findViewById(R.id.edt_add_name);

    //the activity is from display
    if (activity.equals("display")) {
      productExists = true;

      String prodName = intent.getStringExtra("prodName");
      EditText textName = findViewById(R.id.edt_add_name);
      textName.setText(prodName);

      String category = intent.getStringExtra("category");
      switch (category) {
        case "home": categorySelectButton.setText(R.string.cat_home); break;
        case "office": categorySelectButton.setText(R.string.cat_office); break;
        case "food": categorySelectButton.setText(R.string.cat_food); break;
        case "drink": categorySelectButton.setText(R.string.cat_drink); break;
        case "wellness": categorySelectButton.setText(R.string.cat_wellness); break;
        case "entertainment": categorySelectButton.setText(R.string.cat_entertainment); break;
      }
      categorySelected = category;
      categorySelectedBoolean = true;

      storeSelected = StoreCache.getStoreCache().lookUpStoreName(storeName);
      storeSelectButton.setText(storeName);

      categorySelectedBoolean = true;
      productNameChangeFlag = 1;
    }
    //the activity is from scanner
    else {
      // Set the nearest store
      getNearestStore();
      storeSelectButton.setText(nearestStore.getName());
      storeSelected = nearestStore;
      // get product name from the website and set
      try {
        firestore
            .collection("products")
            .document(upc)
            .get()
            .addOnCompleteListener(task -> {
              if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                String productName = (String) document.get("name");
                textName.setText(productName);
                Log.d(TAG, document.getId() + " => " + document.getData());
              } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
              }
            });
      } catch (Exception e) {
        Log.v("ASYNC_ERROR", e.toString());
      }
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_add_item, menu);
    return true;
  }

  @RequiresApi(api = VERSION_CODES.R)
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_done) {
      String UPC = textUPC.getText().toString();
      String productName = textName.getText().toString();
      String price = textPrice.getText().toString().substring(1);

      if (categorySelectedBoolean && !UPC.equals("") && !productName.equals("") && !price
          .equals("")) {
        Product product = new Product(productName, categorySelected);
        product.getViewedBy().add(FirebaseUtil.getAuth().getUid());
        Item it = new Item(UPC, storeSelected.getId(), Double.parseDouble(price));
        if (!productExists) {
          // product already exists. only item (price point) needs to be added.
          firestore
              .collection("products")
              .document(UPC)
              .set(product)
              .addOnCompleteListener(
                  task -> Toast.makeText(this, "Added product: " + product, Toast.LENGTH_SHORT)
                      .show());
        }
        firestore
            .collection("items")
            .add(it)
            .addOnCompleteListener(
                task -> Toast.makeText(this, "Added item: " + it, Toast.LENGTH_SHORT).show());
        this.finish();
      } else {
        Toast.makeText(this, "Please fill all the information", Toast.LENGTH_SHORT).show();
      }
    }
    if (id == R.id.action_delete) {
      this.finish();
    }
    return true;
  }


}
