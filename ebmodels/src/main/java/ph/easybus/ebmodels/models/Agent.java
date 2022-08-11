package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Agent extends BaseObservable implements Parcelable {

    @Bindable
    private double walletAmount = 0d;

    public Agent() {}

    public Agent(Parcel parcel) {
        double[] doubles = new double[1];
        parcel.readDoubleArray(doubles);
        walletAmount = doubles[0];
    }

    public Agent(JSONObject jsonObject) {
        try {
            if (jsonObject.has("wallet_amount")) walletAmount = jsonObject.getDouble("wallet_amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDoubleArray(new double[] { walletAmount });
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("wallet_amount", walletAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public double getWalletAmount() { return walletAmount; }
    public void setWalletAmount(double walletAmount) {
        this.walletAmount = walletAmount;
        notifyPropertyChanged(BR.walletAmount);
    }

    public static final Creator<Agent> CREATOR = new Creator<Agent>() {
        public Agent createFromParcel(Parcel parcel) { return new Agent(parcel); }
        public Agent[] newArray(int size) { return new Agent[size]; }
    };
}
