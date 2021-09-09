package ca.uwaterloo.pricecompare.DataReq;

import android.content.Context;
import android.util.Log;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyObserver<T> implements Observer<T> {

  private static final String TAG = "MyObserver";
  private final ObserverOnNextListener listener;
  private final Context context;

  public MyObserver(Context context, ObserverOnNextListener listener) {
    this.listener = listener;
    this.context = context;
  }

  @Override
  public void onSubscribe(Disposable d) {
    Log.d(TAG, "onSubscribe");
    //add business here
  }

  @Override
  public void onNext(T t) {
    listener.onNext(t);
  }

  @Override
  public void onError(Throwable e) {
    Log.e(TAG, "onError:" + e.getLocalizedMessage());
    //add business here
  }

  @Override
  public void onComplete() {
    Log.d(TAG, "onComplete");
  }
}
