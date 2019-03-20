package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.List;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.ece651.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.ece651.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.ece651.pricecompare.DataReq.http.ApiMethods;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;
    boolean productExists;
    private PopupWindow popupBarcodeConfirmWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productExists = false;
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);

        int checkCallPhonePermission = ContextCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA);
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
        }
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


    public void turnToDisOrAdd(String UPC){

        ObserverOnNextListener<List<Product>> ProductListener = products -> {
            //product doesn't exists in the database
            if (products.get(0).getMsg() != null) {
                Log.d("item", "" + products.get(0).getMsg());
                Intent intent = new Intent(this, AddItem.class);
                intent.putExtra("upc", UPC);
                intent.putExtra("activity", "scanner");
                startActivity(intent);
            }
            //exists
            else {
                Intent intent = new Intent(this, DisplayItem.class);
                intent.putExtra("upc", UPC);
                intent.putExtra("activity", "scanner");
                startActivity(intent);
            }
        };
        ApiMethods.getProduct(new MyObserver<>(this, ProductListener), UPC);
    }

    @Override
    public void handleResult(Result result) {


        String UPC = result.getContents().replaceAll("^0+","");
        View popupConfirmBarcodeView = View.inflate(this, R.layout.popup_barcode_confirm_window, null);
        EditText barcode = (EditText)popupConfirmBarcodeView.findViewById(R.id.BarcodeInput);
        Log.d("test result", UPC);
        barcode.setText(UPC);



        Button confirm = (Button)popupConfirmBarcodeView.findViewById(R.id.ConfirmBarcode);
        confirm.setOnClickListener(v -> {
            String UPC_input = String.valueOf(barcode.getText()).replaceAll("^0+","");
            Log.d("changed result", UPC);
            turnToDisOrAdd(UPC_input);
        });

        Button dismiss = (Button)popupConfirmBarcodeView.findViewById(R.id.DismissConfirmBarcode);
        dismiss.setOnClickListener(v->{this.finish();});


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
