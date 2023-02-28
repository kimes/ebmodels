package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

public class GroupedAgentTransaction extends BaseObservable implements Parcelable {

    @Bindable
    private int type = 0, totalSeats = 0;

    @Bindable
    private double amount = 0d;

    @Bindable
    private Date date = Calendar.getInstance().getTime();

    @Bindable
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public GroupedAgentTransaction(Parcel parcel) {
        int[] ints = new int[2];
        parcel.readIntArray(ints);
        type = ints[0];
        totalSeats = ints[1];

        double[] doubles = new double[1];
        parcel.readDoubleArray(doubles);
        amount = doubles[0];

        long[] dates = new long[1];
        parcel.readLongArray(dates);
        if (dates[0] > 0) { date = new Date(dates[0]); }

        Parcelable[] resParcel = parcel.readParcelableArray(Reservation.class.getClassLoader());
        ArrayList<Reservation> res = new ArrayList<>();
        for (Parcelable reservation : resParcel) {
            res.add((Reservation)reservation);
        }
        reservations = res;
    }

    public GroupedAgentTransaction(JSONObject object) {
        try {
            if (object.has("type")) type = object.getInt("type");
            if (object.has("total_seats")) totalSeats = object.getInt("total_seats");
            if (object.has("amount")) amount = object.getDouble("amount");
            if (object.has("date")) date = DateTimeUtils.toDate(object.getString("date"), "YYYY-MM-dd");

            if (object.has("reservations")) {
                JSONArray resArray = object.getJSONArray("reservations");
                reservations = new ArrayList<>();
                for (int i = 0; i < resArray.length(); i++) {
                    reservations.add(new Reservation(resArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.readIntArray(new int[] { type, totalSeats });
        parcel.readDoubleArray(new double[] { amount });
        parcel.readLongArray(new long[] { (date != null) ? date.getTime() : -1 });

        Reservation[] res = new Reservation[reservations.size()];
        for (int i = 0; i < reservations.size(); i++) {
            res[i] = reservations.get(i);
        }
        parcel.writeParcelableArray(res, flags);
    }

    public int describeContents() { return 0; }

    public int getType() { return type; }
    public int getTotalSeats() { return totalSeats; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public ArrayList<Reservation> getReservations() { return reservations; }

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
        notifyPropertyChanged(BR.totalSeats);
    }
    public void setAmount(double amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }
    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
        notifyPropertyChanged(BR.reservations);
    }

    public static final Parcelable.Creator<GroupedAgentTransaction> CREATOR = new Parcelable.Creator<GroupedAgentTransaction>() {
        public GroupedAgentTransaction createFromParcel(Parcel parcel) {
            return new GroupedAgentTransaction(parcel);
        }

        public GroupedAgentTransaction[] newArray(int size) {
            return new GroupedAgentTransaction[size];
        }
    };
}
