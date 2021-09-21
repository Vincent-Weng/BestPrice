package ca.uwaterloo.pricecompare;

import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ca.uwaterloo.pricecompare.models.Store;
import ca.uwaterloo.pricecompare.models.User;
import ca.uwaterloo.pricecompare.util.FirebaseUtil;
import ca.uwaterloo.pricecompare.util.StoreCache;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

  private static final String TAG = "[MainActivity]";
  private Menu menu;
  private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
      new FirebaseAuthUIActivityResultContract(),
      result -> {
        MenuItem signInButton = menu.findItem(R.id.action_sign_in);
        signInButton.setVisible(false);
        MenuItem signOutOption = menu.findItem(R.id.action_sign_out);
        signOutOption.setVisible(true);
        FirebaseUtil.getMsg().getToken()
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
            return;
          }

          // Get new FCM registration token
          String token = task.getResult();

          FirebaseUtil.getFirestore()
              .collection("users")
              .document(FirebaseUtil.getAuth().getCurrentUser().getUid())
              .set(new User(new ArrayList<>(), token));
        });

        StoreCache.getStoreCache().init(stores -> {
          StoreAdapter adapter = new StoreAdapter(stores);
          RecyclerView recyclerView = findViewById(R.id.recycler_view);
          recyclerView.setAdapter(adapter);
        });
      }
  );

  @RequiresApi(api = VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Button buttonWellness = findViewById(R.id.cat_button_4);
    buttonWellness.setOnClickListener(v -> {
      Intent intentWellness = new Intent(MainActivity.this, DisplayCategory.class);
      intentWellness.putExtra("category", "wellness");
      startActivity(intentWellness);
    });

    Button buttonOffice = findViewById(R.id.cat_button_5);
    buttonOffice.setOnClickListener(v -> {
      Intent intentOffice = new Intent(MainActivity.this, DisplayCategory.class);
      intentOffice.putExtra("category", "office");
      startActivity(intentOffice);
    });

    Button buttonEntmt = findViewById(R.id.cat_button_0);
    buttonEntmt.setOnClickListener(v -> {
      Intent intentEntmt = new Intent(MainActivity.this, DisplayCategory.class);
      intentEntmt.putExtra("category", "entertainment");
      startActivity(intentEntmt);
    });

    Button buttonFood = findViewById(R.id.cat_button_1);
    buttonFood.setOnClickListener(v -> {
      Intent intentFood = new Intent(MainActivity.this, DisplayCategory.class);
      intentFood.putExtra("category", "food");
      startActivity(intentFood);
    });

    Button buttonDrink = findViewById(R.id.cat_button_2);
    buttonDrink.setOnClickListener(v -> {
      Intent intentDrink = new Intent(MainActivity.this, DisplayCategory.class);
      intentDrink.putExtra("category", "drink");
      startActivity(intentDrink);
    });

    Button buttonHome = findViewById(R.id.cat_button_3);
    buttonHome.setOnClickListener(v -> {
      Intent intentHome = new Intent(MainActivity.this, DisplayCategory.class);
      intentHome.putExtra("category", "home");
      startActivity(intentHome);
    });

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    recyclerView.setLayoutManager(layoutManager);

    StoreCache.getStoreCache().init(stores -> {
      StoreAdapter adapter = new StoreAdapter(stores);
      recyclerView.setAdapter(adapter);
    });

    FloatingActionButton fab = findViewById(R.id.add_button);
    fab.setOnClickListener(view -> {
      if (FirebaseUtil.getAuth().getCurrentUser() == null) {
        Toast.makeText(getBaseContext(), "Please sign-in first!", Toast.LENGTH_SHORT).show();
      } else {
        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    this.menu = menu;
    if (FirebaseUtil.getAuth().getCurrentUser() != null) {
      MenuItem signInButton = menu.findItem(R.id.action_sign_in);
      signInButton.setVisible(false);
    } else {
      MenuItem signOutOption = menu.findItem(R.id.action_sign_out);
      signOutOption.setVisible(false);
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_sign_in) {
      Intent signInIntent = FirebaseUtil.getAuthUI().createSignInIntentBuilder().build();
      FirebaseUtil.startSignIn(signInLauncher);
    }

    if (id == R.id.action_sign_out) {
      FirebaseUtil.getAuth().signOut();
      MenuItem signInButton = menu.findItem(R.id.action_sign_in);
      signInButton.setVisible(true);
      item.setVisible(false);
    }

    if (id == R.id.action_refresh_login) {
      if (FirebaseUtil.getAuth().getCurrentUser() != null) {
        MenuItem signInButton = menu.findItem(R.id.action_sign_in);
        signInButton.setVisible(false);
        MenuItem signOutOption = menu.findItem(R.id.action_sign_out);
        signOutOption.setVisible(true);
      } else {
        MenuItem signInButton = menu.findItem(R.id.action_sign_in);
        signInButton.setVisible(true);
        MenuItem signOutOption = menu.findItem(R.id.action_sign_out);
        signOutOption.setVisible(false);
      }
    }

    return super.onOptionsItemSelected(item);
  }


  public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private final List<Store> mStoreList;

    public StoreAdapter(List<Store> storeList) {
      mStoreList = storeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.store_item, parent, false);

      final ViewHolder holder = new ViewHolder(view);

      holder.storeView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Store store = mStoreList.get(position);

            Intent intent = new Intent(MainActivity.this, DisplayStore.class);
            Bundle bundle = new Bundle();
            bundle.putString("Store", store.getName());
            intent.putExtras(bundle);
            startActivity(intent);
            //Toast.makeText(v.getContext(),"you clicked view"+store.getName(), Toast.LENGTH_SHORT).show();
          }
      );
      holder.storeImage.setOnClickListener(v -> {
        int position = holder.getAdapterPosition();
        Store store = mStoreList.get(position);

        Intent intent = new Intent(MainActivity.this, DisplayStore.class);
        Bundle bundle = new Bundle();
        bundle.putString("Store", store.getId());
        intent.putExtras(bundle);
        startActivity(intent);
      });
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Store store = mStoreList.get(position);
      holder.storeImage.setImageResource(store.getImageId());
      holder.storeName.setText(store.getName());
    }

    @Override
    public int getItemCount() {
      return mStoreList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

      View storeView;
      ImageView storeImage;
      TextView storeName;

      public ViewHolder(View view) {
        super(view);
        storeView = view;
        storeImage = view.findViewById(R.id.store_image);
        storeName = view.findViewById(R.id.store_name);
      }
    }
  }
}
