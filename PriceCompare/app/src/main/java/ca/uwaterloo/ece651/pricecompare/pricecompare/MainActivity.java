package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.Button;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private List<Store> storeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initStore();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        StoreAdapter adapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(adapter);



        Button button1 = (Button)findViewById(R.id.cat_button_4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(MainActivity.this, DisplayCategory.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Category",4);
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
                bundle.putInt("Category",5);
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
                bundle.putInt("Category",0);
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
                bundle.putInt("Category",1);
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
                bundle.putInt("Category",2);
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
                bundle.putInt("Category",3);
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
    private void initStore(){
        for (int i = 0; i < 2; i++){
            Store sobeys1 = new Store("Sobeys Columbia", R.drawable.sobeys);
            storeList.add(sobeys1);
            Store zehrs1 = new Store("Zehrs Conestoga",R.drawable.zehrs);
            storeList.add(zehrs1);
            Store tnt = new Store("TNT Waterloo",R.drawable.tnt);
            storeList.add(tnt);
            Store wcentral = new Store("Waterloo Central", R.drawable.wcentral);
            storeList.add(wcentral);
            Store walmart  = new Store("Walmart Waterloo", R.drawable.walmart);
            storeList.add(walmart);
            Store foodBasic  = new Store("Food Basics Laurelwood", R.drawable.foodbasics);
            storeList.add(foodBasic);
            Store sobeys2 = new Store("Sobeys Bridgeport", R.drawable.sobeys);
            storeList.add(sobeys2);
            Store zehrs2 = new Store("Zehrs Lincoln",R.drawable.zehrs);
            storeList.add(zehrs2);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
        private List<Store> mStoreList;
        class ViewHolder extends RecyclerView.ViewHolder{
            View storeView;
            ImageView storeImage;
            TextView storeName;

            public ViewHolder(View view){
                super(view);
                storeView = view;
                storeImage = (ImageView)view.findViewById(R.id.store_image);
                storeName = (TextView)view.findViewById(R.id.store_name);
            }
        }
    public StoreAdapter(List<Store> storeList){
            mStoreList = storeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item,parent,false);

            final ViewHolder holder = new ViewHolder(view);

            holder.storeView.setOnClickListener(new View.OnClickListener(){
              @Override
                public void onClick(View v){
                  int position = holder.getAdapterPosition();
                  Store store = mStoreList.get(position);

                  Intent intent = new Intent(MainActivity.this,DisplayStore.class);
                  Bundle bundle = new Bundle();
                  bundle.putString("Store", store.getName());
                  intent.putExtras(bundle);
                  startActivity(intent);
                  //Toast.makeText(v.getContext(),"you clicked view"+store.getName(), Toast.LENGTH_SHORT).show();
              }
            }
            );
            holder.storeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        Store store = mStoreList.get(position);

                        Intent intent = new Intent(MainActivity.this,DisplayStore.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Store", store.getName());
                        intent.putExtras(bundle);
                        startActivity(intent);

                        //Toast.makeText(v.getContext(),"you clicked image"+ store.getName(),Toast.LENGTH_SHORT).show();
                    }

            });
            return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store  = mStoreList.get(position);
        holder.storeImage.setImageResource(store.getImageId());
        holder.storeName.setText(store.getName());
    }
    @Override
        public int getItemCount(){
            return  mStoreList.size();
    }
    }



    public class Store{
        private String name;
        private int imageId;
        public Store(String name, int imageId){
            this.name = name;
            this.imageId = imageId;
        }

        public String getName(){
            return name;
        }
        public int getImageId(){
            return imageId;
        }
    }


}
