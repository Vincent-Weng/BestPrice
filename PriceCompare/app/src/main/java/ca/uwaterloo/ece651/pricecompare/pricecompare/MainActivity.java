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
            Store sobeys = new Store("Sobeys", R.drawable.sobeys);
            storeList.add(sobeys);
            Store tnt = new Store("TNT",R.drawable.tnt);
            storeList.add(tnt);
            Store wcentral = new Store("WCentral", R.drawable.wcentral);
            storeList.add(wcentral);
            Store walmart  = new Store("Walmart", R.drawable.walmart);
            storeList.add(walmart);
            Store zehrs = new Store("Zehrs",R.drawable.zehrs);
            storeList.add(zehrs);
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
                  Toast.makeText(v.getContext(),"you clicked view"+store.getName(), Toast.LENGTH_SHORT).show();
              }
            }
            );
            holder.storeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        Store store = mStoreList.get(position);
                        Toast.makeText(v.getContext(),"you clicked image"+ store.getName(),Toast.LENGTH_SHORT).show();
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
