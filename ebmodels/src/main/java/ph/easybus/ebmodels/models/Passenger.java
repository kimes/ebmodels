package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Passenger extends BaseObservable implements Parcelable {

    @Bindable
    private boolean hasNameError = false, hasDiscountError = false;

    private int seatNo = 0, seatSpecialType = 0;

    @Bindable
    private String referenceNo = "";

    @Bindable
    private Name name = new Name();

    @Bindable
    private Discount discount = new Discount();

    public Passenger() {}

    public Passenger(int seatNo) {
        this.seatNo = seatNo;
    }

    public Passenger(Name name, Discount discount) {
        this.name = name;
        this.discount = discount;
    }

    public Passenger(JSONObject object) {
        try {
            if (object.has("name")) name = new Name(object.getJSONObject("name"));
            if (object.has("discount")) discount = new Discount(object.getJSONObject("discount"));
            if (object.has("reference_number")) referenceNo = object.getString("reference_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Passenger(Parcel parcel) {
        int[] ints = new int[2];
        parcel.readIntArray(ints);
        seatNo = ints[0];
        seatSpecialType = ints[1];

        referenceNo = parcel.readString();
        name = parcel.readParcelable(Name.class.getClassLoader());
        discount = parcel.readParcelable(Discount.class.getClassLoader());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name.toJSON());
            object.put("discount", discount.toJSON());
            object.put("reference_number", referenceNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { seatNo, seatSpecialType });
        parcel.writeString(referenceNo);
        parcel.writeParcelable(name, flags);
        parcel.writeParcelable(discount, flags);
    }

    public int describeContents() { return 0; }

    public boolean isHasNameError() { return hasNameError; }
    public boolean isHasDiscountError() { return hasDiscountError; }
    public int getSeatNo() { return seatNo; }
    public int getSeatSpecialType() { return seatSpecialType; }
    public String getReferenceNo() { return referenceNo; }
    public Name getName() { return name; }
    public Discount getDiscount() { return discount; }
    public void setSeatNo(int seatNo) { this.seatNo = seatNo; }
    public void setSeatSpecialType(int seatSpecialType) { this.seatSpecialType = seatSpecialType; }

    public void setHasNameError(boolean hasNameError) {
        this.hasNameError = hasNameError;
        notifyPropertyChanged(BR.hasNameError);
    }
    public void setHasDiscountError(boolean hasDiscountError) {
        this.hasDiscountError = hasDiscountError;
        notifyPropertyChanged(BR.hasDiscountError);
    }
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
        notifyPropertyChanged(BR.referenceNo);
    }
    public void setName(Name name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    public void setDiscount(Discount discount) {
        this.discount = discount;
        notifyPropertyChanged(BR.discount);
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        public Passenger createFromParcel(Parcel parcel) { return new Passenger(parcel); }
        public Passenger[] newArray(int size) { return new Passenger[size]; }
    };
}
