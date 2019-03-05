package ca.uwaterloo.ece651.pricecompare.pricecompare;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.HashMap;

public class DisplayCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category);
        // get different labels from 6 different category intents
        Bundle bundle = this.getIntent().getExtras();
        //  Category is the flag to get diffenent items from daatabase
        String Category = bundle.getString("Category");
        Toast toast=Toast.makeText(getApplicationContext(), Category+"content has been propogated", Toast.LENGTH_SHORT);
        toast.show();

        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 40; i++) {
            HashMap<String, Object> user = new HashMap<String, Object>();
            user.put("img", R.drawable.user);
            user.put("product", "product(" + i+")");
            user.put("price", (20 + i) + "");
            user.put("store","store("+i+")");
            users.add(user);
        }
        SimpleAdapter saImageItems = new SimpleAdapter(this,
                users,// 数据来源
                R.layout.user,//每一个user xml 相当ListView的一个组件
                new String[] { "img", "product", "price","store" },
                // 分别对应view 的id
                new int[] { R.id.img, R.id.product, R.id.price,R.id.store });
        // 获取listview
        ((ListView) findViewById(R.id.users)).setAdapter(saImageItems);
    }
}
