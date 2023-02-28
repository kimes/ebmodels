package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

public class TripStatus extends BaseObservable implements Parcelable {

    @Bindable
    private int status = 0;

    private String tripId = "", lane = "", terminal = "";

    @Bindable
    private Date tripDate = Calendar.getInstance().getTime(), dispatchedDate = null;

    public TripStatus() {}
    public TripStatus(JSONObject object) {
        try {
            if (object.has("status")) status = object.getInt("status");
            if (object.has("trip_id")) tripId = object.getString("trip_id");
            if (object.has("lane")) lane = object.getString("lane");
            if (object.has("terminal")) terminal = object.getString("terminal");

            if (object.has("trip_date")) {
                tripDate = DateTimeUtils.toDate(object.getString("trip_date"));
            }

            if (object.has("dispatched_date")) {
                if (!object.isNull("dispatched_date")) {
                    dispatchedDate = DateTimeUtils.toDateUtc(object.getString("dispatched_date"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TripStatus(Parcel parcel) {
        int[] ints = new int[1];
        parcel.readIntArray(ints);
        status = ints[0];

        String[] strings = new String[3];
        parcel.readStringArray(strings);
        tripId = strings[0];
        lane = strings[1];
        terminal = strings[2];

        long[] dates = new long[2];
        parcel.readLongArray(dates);
        tripDate = new Date(dates[0]);
        if (dates[1] > 0)  dispatchedDate = new Date(dates[1]);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("status", status);
            object.put("trip_id", tripId);
            object.put("lane", lane);
            object.put("terminal", terminal);
            object.put("trip_date", DateTimeUtils.toISODate(tripDate));
            if (dispatchedDate != null) {
                object.put("dispatched_date", DateTimeUtils.toISODateUtc(dispatchedDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { status });
        parcel.writeStringArray(new String[] { tripId, lane, terminal });
        parcel.writeLongArray(new long[] { tripDate.getTime(),
                (dispatchedDate != null) ? dispatchedDate.getTime() : -1 });
    }

    public int describeContents() { return 0; }

    public int getStatus() { return status; }
    public String getTripId() { return tripId; }
    public String getLane() { return lane; }
    public String getTerminal() { return terminal; }
    public Date getTripDate() { return tripDate; }
    public Date getDispatchedDate() { return dispatchedDate; }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
    public void setTripId(String tripId) { this.tripId = tripId; }
    public void setLane(String lane) { this.lane = lane; }
    public void setTerminal(String terminal) { this.terminal = terminal; }
    public void setTripDate(Date tripDate) { this.tripDate = tripDate; }
    public void setDispatchedDate(Date dispatchedDate) {
        this.dispatchedDate = dispatchedDate;
        notifyPropertyChanged(BR.dispatchedDate);
    }

    public static final Creator<TripStatus> CREATOR = new Creator<TripStatus>() {
        public TripStatus createFromParcel(Parcel parcel) { return new TripStatus(parcel); }
        public TripStatus[] newArray(int size) { return new TripStatus[size]; }
    };
}