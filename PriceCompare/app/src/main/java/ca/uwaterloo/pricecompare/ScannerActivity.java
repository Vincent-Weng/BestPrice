package ca.uwaterloo.pricecompare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ca.uwaterloo.pricecompare.models.Product;
import ca.uwaterloo.pricecompare.util.FirebaseUtil;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {

  private static final String TAG = "[ScannerActivity]";
  boolean productExists;
  private ZBarScannerView mScannerView;
  private PopupWindow popupBarcodeConfirmWindow;
  private FirebaseFirestore firestore;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    productExists = false;
    mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
    setContentView(mScannerView);

    int checkCallPhonePermission = ContextCompat
        .checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA);
    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(ScannerActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
    }

    this.firestore = FirebaseUtil.getFirestore();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
    mScannerView.startCamera();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mScannerView.stopCamera();
  }


  public void turnToDisOrAdd(String UPC) {
    firestore
        .collection("products")
        .document(UPC)
        .get()
        .addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
              Product product = document.toObject(Product.class);
              Intent intent = new Intent(this, DisplayItem.class);
              intent.putExtra("upc", document.getId());
              intent.putExtra("activity", "scanner");
              intent.putExtra("category", product.getCategoryId());
              intent.putExtra("name", product.getName());
              startActivity(intent);
            } else {
              // product doesn't exists in the database
              Intent intent = new Intent(this, AddItem.class);
              intent.putExtra("upc", UPC);
              intent.putExtra("activity", "scanner");
              startActivity(intent);
            }
          } else {
            Log.d(TAG, "Failed with: ", task.getException());
          }
        });
  }

  @Override
  public void handleResult(Result result) {

    String UPC = result.getContents().replaceAll("^0+", "");
    View popupConfirmBarcodeView = View.inflate(this, R.layout.popup_barcode_confirm_window, null);
    EditText barcode = popupConfirmBarcodeView.findViewById(R.id.BarcodeInput);
    Log.d("test result", UPC);
    barcode.setText(UPC);

    Button confirm = popupConfirmBarcodeView.findViewById(R.id.ConfirmBarcode);
    confirm.setOnClickListener(v -> {
      String UPC_input = String.valueOf(barcode.getText()).replaceAll("^0+", "");
      Log.d("changed result", UPC);
      turnToDisOrAdd(UPC_input);
    });

    Button dismiss = popupConfirmBarcodeView.findViewById(R.id.DismissConfirmBarcode);
    dismiss.setOnClickListener(v -> {
      this.finish();
    });

    popupBarcodeConfirmWindow = new PopupWindow(popupConfirmBarcodeView,
        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    popupBarcodeConfirmWindow.setFocusable(true);
    popupBarcodeConfirmWindow.setOutsideTouchable(true);
    popupBarcodeConfirmWindow.setAnimationStyle(R.style.fadePopupAnimation);
//        popupConfirmBarcodeView.setBackgroundColor(getResources().getColor(android.R.color.white));

    popupBarcodeConfirmWindow.setOnDismissListener(() -> {
      WindowManager.LayoutParams lp = getWindow().getAttributes();
      lp.alpha = 1.0f;
      getWindow().setAttributes(lp);
    });

    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 0.5f;
    getWindow().setAttributes(lp);

    popupBarcodeConfirmWindow.showAtLocation(popupConfirmBarcodeView, Gravity.CENTER, 0, 0);


  }
}
