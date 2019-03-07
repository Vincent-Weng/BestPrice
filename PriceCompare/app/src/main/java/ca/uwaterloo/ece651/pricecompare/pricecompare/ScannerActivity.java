package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.ece651.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.ece651.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.ece651.pricecompare.DataReq.http.ApiMethods;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;
    boolean productExists;

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

    @Override
    public void handleResult(Result result) {
//        //The item does not exist in the database, go to AddItem class

        String message = result.getContents();

        Log.d("test result", message);

//        ObserverOnNextListener<List<Product>> productListener = new ObserverOnNextListener<List<Product>>() {
//            @Override
//            public void onNext(List<Product> products) {
//                try {
//                    Log.d("product massage", products.get(0).getMsg());
//                } catch (Exception e) {
//                    productExists = true;
//                }
//            }
//        };
//        ApiMethods.getProduct(new MyObserver<>(this, productListener), message);
//        Log.d("productExistsBoolean", Boolean.toString(productExists));

        Intent intent = new Intent(this, AddItem.class);
        intent.putExtra("upc", message);
        intent.putExtra("activity", "scanner");
        startActivity(intent);

//        // The item is in the database, go to DisplayItem class
//        Intent intent = new Intent(this, DisplayItem.class);
//        String message = result.getContents();
//        intent.putExtra("upc", message);
//        intent.putExtra("activity","scanner");
//        startActivity(intent);


//        // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this);
    }
}
