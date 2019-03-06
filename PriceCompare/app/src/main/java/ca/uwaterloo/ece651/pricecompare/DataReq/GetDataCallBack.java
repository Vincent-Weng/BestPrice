package ca.uwaterloo.ece651.pricecompare.DataReq;
import retrofit2.Response;

//TODO: to deprecate
public interface GetDataCallBack {
        public void onGetData(Response rp);
        public void onError();
}

