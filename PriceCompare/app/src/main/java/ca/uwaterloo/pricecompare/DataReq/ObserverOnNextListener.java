package ca.uwaterloo.pricecompare.DataReq;

public interface ObserverOnNextListener<T> {
    void onNext(T t);
}
