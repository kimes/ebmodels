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

public class GroupedReservation extends BaseObservable implements Parcelable {

    @Bindable
    private Date date = Calendar.getInstance().getTime();

    @Bindable
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public GroupedReservation(Parcel parcel) {
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

    public GroupedReservation(JSONObject object) {
        try {
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
        parcel.readLongArray(new long[] { (date != null) ? date.getTime() : -1 });

        Reservation[] res = new Reservation[reservations.size()];
        for (int i = 0; i < reservations.size(); i++) {
            res[i] = reservations.get(i);
        }
        parcel.writeParcelableArray(res, flags);
    }

    public int describeContents() { return 0; }

    public Date getDate() { return date; }
    public ArrayList<Reservation> getReservations() { return reservations; }
    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
        notifyPropertyChanged(BR.reservations);
    }

    public static final Parcelable.Creator<GroupedReservation> CREATOR = new Parcelable.Creator<GroupedReservation>() {
        public GroupedReservation createFromParcel(Parcel parcel) {
            return new GroupedReservation(parcel);
        }

        public GroupedReservation[] newArray(int size) {
            return new GroupedReservation[size];
        }
    };
}
