package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Infant extends BaseObservable implements Parcelable {

    @Bindable
    private int index = 0;

    @Bindable
    private long ticketNo = 0;

    @Bindable
    private double amount = 0d;

    @Bindable
    private Name name = new Name();

    public Infant() {}

    public Infant(JSONObject object) {
        try {
            if (object.has("index")) index = object.getInt("index");
            if (object.has("ticket_number")) ticketNo = object.getLong("ticket_number");
            if (object.has("amount")) amount = object.getDouble("amount");
            if (object.has("name")) name = new Name(object.getJSONObject("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Infant(Parcel parcel) {
        index = parcel.readInt();
        ticketNo = parcel.readLong();
        amount = parcel.readDouble();

        name = parcel.readParcelable(Name.class.getClassLoader());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("index", index);
            object.put("ticket_number", ticketNo);
            object.put("amount", amount);
            object.put("name", name.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(index);
        parcel.writeLong(ticketNo);
        parcel.writeDouble(amount);
        parcel.writeParcelable(name, flags);
    }

    public int describeContents() { return 0; }

    public int getIndex() { return index; }
    public long getTicketNo() { return ticketNo; }
    public double getAmount() { return amount; }
    public Name getName() { return name; }

    public void setIndex(int index) {
        this.index = index;
        notifyPropertyChanged(BR.index);
    }
    public void setTicketNo(long ticketNo) {
        this.ticketNo = ticketNo;
        notifyPropertyChanged(BR.ticketNo);
    }
    public void setAmount(double amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }
    public void setName(Name name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public static final Creator<Infant> CREATOR = new Creator<Infant>() {
        public Infant createFromParcel(Parcel parcel) { return new Infant(parcel); }
        public Infant[] newArray(int size) { return new Infant[size]; }
    };
}
