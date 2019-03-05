package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button button1 = (Button)findViewById(R.id.cat_button_4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(MainActivity.this, DisplayCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category","Wellness");
                int1.putExtras(bundle);
                startActivity(int1);
            }
        });

        Button button2 = (Button)findViewById(R.id.cat_button_5);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2 = new Intent (MainActivity.this, DisplayCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category","Office");
                int2.putExtras(bundle);
                startActivity(int2);
            }
        });

        Button button3 = findViewById(R.id.cat_button_0);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int3 = new Intent(MainActivity.this,DisplayCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category","Entertainment");
                int3.putExtras(bundle);
                startActivity(int3);
            }
        });

        Button button4 = findViewById(R.id.cat_button_1);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int4 = new Intent(MainActivity.this,DisplayCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category","Food");
                int4.putExtras(bundle);
                startActivity(int4);
            }
        });

        Button button5 = findViewById(R.id.cat_button_2);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int5 = new Intent(MainActivity.this,DisplayCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category","Drink");
                int5.putExtras(bundle);
                startActivity(int5);
            }
        });

        Button button6 = findViewById(R.id.cat_button_3);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int6 = new Intent(MainActivity.this,DisplayCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category","Home");
                int6.putExtras(bundle);
                startActivity(int6);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });


    }
}
