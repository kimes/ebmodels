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

public class TripAssign extends BaseObservable implements Parcelable {

    @Bindable
    private String tripId = "", driver = "", conductor = "";

    @Bindable
    private Date tripDate = Calendar.getInstance().getTime();

    @Bindable
    private BusDetail busDetail = new BusDetail();

    public TripAssign() {}
    public TripAssign(JSONObject object) {
        try {
            if (object.has("trip_id")) tripId = object.getString("trip_id");
            if (object.has("driver")) driver = object.getString("driver");
            if (object.has("conductor")) conductor = object.getString("conductor");
            if (object.has("trip_date")) {
                tripDate = DateTimeUtils.toDate(object.getString("trip_date"));
            }
            if (object.has("bus_detail")) {
                busDetail = new BusDetail(object.getJSONObject("bus_detail"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TripAssign(Parcel parcel) {
        String[] strings = new String[3];
        parcel.readStringArray(strings);
        tripId = strings[0];
        driver = strings[1];
        conductor = strings[2];

        long[] dates = new long[1];
        parcel.readLongArray(dates);
        tripDate = new Date(dates[0]);

        busDetail = parcel.readParcelable(BusDetail.class.getClassLoader());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("trip_id", tripId);
            object.put("driver", driver);
            object.put("conductor", conductor);
            object.put("trip_date", DateTimeUtils.toISODate(tripDate));
            object.put("bus_detail", busDetail.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { tripId, driver, conductor });
        parcel.writeLongArray(new long[] { tripDate.getTime() });
        parcel.writeParcelable(busDetail, flags);
    }

    public int describeContents() { return 0; }

    public String getTripId() { return tripId; }
    public String getDriver() { return driver; }
    public String getConductor() { return conductor; }
    public Date getTripDate() { return tripDate; }
    public BusDetail getBusDetail() { return busDetail; }

    public void setTripId(String tripId) { this.tripId = tripId; }
    public void setDriver(String driver) {
        this.driver = driver;
        notifyPropertyChanged(BR.driver);
    }
    public void setConductor(String conductor) {
        this.conductor = conductor;
        notifyPropertyChanged(BR.conductor);
    }
    public void setTripDate(Date tripDate) { this.tripDate = tripDate; }
    public void setBusDetail(BusDetail busDetail) {
        this.busDetail = busDetail;
        notifyPropertyChanged(BR.busDetail);
    }

    public static final Creator<TripAssign> CREATOR = new Creator<TripAssign>() {
        public TripAssign createFromParcel(Parcel parcel) { return new TripAssign(parcel); }

        public TripAssign[] newArray(int size) {
            return new TripAssign[size];
        }
    };
}
