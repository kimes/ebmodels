package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ph.easybus.ebmodels.BR;
import ph.easybus.ebmodels.utils.DateTimeUtils;

public class CargoStatusLog extends BaseObservable implements Parcelable {

    @Bindable
    private String loggedBy = "";

    @Bindable
    private Date date = Calendar.getInstance().getTime();

    public CargoStatusLog() {}

    public CargoStatusLog(Parcel parcel) {
        String[] strings = new String[1];
        parcel.readStringArray(strings);
        loggedBy = strings[0];

        long[] dates = new long[1];
        parcel.readLongArray(dates);
        date = new Date(dates[0]);
    }

    public CargoStatusLog(JSONObject object) {
        try {
            if (object.has("logged_by")) loggedBy = object.getString("logged_by");

            if (object.has("date")) {
                date = DateTimeUtils.toDateUtc(object.getString("date"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { loggedBy });
        dest.writeLongArray(new long[] { date.getTime() });
    }

    public int describeContents() { return 0; }

    public String getLoggedBy() { return loggedBy; }
    public Date getDate() { return date; }

    public void setLoggedBy(String loggedBy) {
        this.loggedBy = loggedBy;
        notifyPropertyChanged(BR.loggedBy);
    }

    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    public static final Parcelable.Creator<CargoStatusLog> CREATOR = new Parcelable.Creator<CargoStatusLog>() {
        public CargoStatusLog createFromParcel(Parcel source) {
            return new CargoStatusLog(source);
        }

        public CargoStatusLog[] newArray(int size) {
            return new CargoStatusLog[size];
        }
    };
}
