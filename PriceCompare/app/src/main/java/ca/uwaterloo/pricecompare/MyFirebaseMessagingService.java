package ca.uwaterloo.pricecompare;

import android.util.Log;
import androidx.annotation.NonNull;
import ca.uwaterloo.pricecompare.util.FirebaseUtil;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "[MyFirebaseMessagingService]";

  @Override
  public void onNewToken(@NonNull String token) {
    Log.d(TAG, "Refreshed token: " + token);
    if (FirebaseUtil.getAuth().getCurrentUser() != null) {
      FirebaseUtil.getFirestore()
          .collection("users")
          .document(FirebaseUtil.getAuth().getCurrentUser().getUid())
          .update("messageToken", token);
    }
  }
}
