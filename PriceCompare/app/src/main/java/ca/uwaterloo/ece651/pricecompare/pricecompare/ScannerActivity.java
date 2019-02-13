package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        Intent intent = new Intent(this, AddItem.class);
//        String message = result.getContents();
//        intent.putExtra("upc", message);
//        intent.putExtra("activity","scanner");
//        startActivity(intent);

        // The item is in the database, go to DisplayItem class
        Intent intent = new Intent(this, DisplayItem.class);
        String message = result.getContents();
        intent.putExtra("upc", message);
        intent.putExtra("activity","scanner");
        startActivity(intent);


//        // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this);
    }
}
