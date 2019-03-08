package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.Manifest;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Item;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.ece651.pricecompare.DataReq.MyObserver;
import ca.uwaterloo.ece651.pricecompare.DataReq.ObserverOnNextListener;
import ca.uwaterloo.ece651.pricecompare.DataReq.ProductExistException;
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

        String UPC = result.getContents();
        Log.d("test result", UPC);

        ObserverOnNextListener<List<Product>> ProductListener = products -> {
            //Do data manipulation here
            //TODO: context, the parameter for Toast.makeText()?
            Toast addItemToast = Toast.makeText(this, "GetProducts: " + products.get(0).getMsg(),
                    Toast.LENGTH_SHORT);
            addItemToast.show();
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
}
