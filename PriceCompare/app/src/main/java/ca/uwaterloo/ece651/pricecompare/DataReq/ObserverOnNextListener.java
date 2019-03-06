package ca.uwaterloo.ece651.pricecompare.DataReq;

public interface ObserverOnNextListener<T> {
    void onNext(T t);
}
