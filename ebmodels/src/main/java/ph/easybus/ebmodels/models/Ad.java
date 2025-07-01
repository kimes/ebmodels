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

public class Ad extends BaseObservable implements Parcelable {

    @Bindable
    private int appearPerCycle = 1, appearanceCount = 0;

    @Bindable
    private String mongoId = "", video = "";

    private Date startDate = Calendar.getInstance().getTime(),
            expiryDate = Calendar.getInstance().getTime();

    public Ad() {}

    public Ad(String mongoId, String video, int appearPerCycle) {
        this.mongoId = mongoId;
        this.video = video;
        this.appearPerCycle = appearPerCycle;
    }

    public Ad(Parcel parcel) {
        int[] ints = new int[2];
        parcel.readIntArray(ints);
        appearPerCycle = ints[0];
        appearanceCount = ints[1];

        long[] dates = new long[2];
        parcel.readLongArray(dates);
        startDate = new Date(dates[0]);
        expiryDate = new Date(dates[1]);

        String[] strings = new String[2];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        video = strings[1];
    }

    public Ad(JSONObject object) {
        try {
            this.mongoId = object.getString("_id");
            this.video = object.getString("video");
            this.appearPerCycle = object.getInt("appear_per_cycle");
            this.startDate = DateTimeUtils.toDateUtc(object.getString("start_date"));
            this.expiryDate = DateTimeUtils.toDateUtc(object.getString("expiry_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("video", video);
            object.put("appear_per_cycle", appearPerCycle);
            object.put("start_date", DateTimeUtils.toISODateUtc(startDate));
            object.put("expiry_date", DateTimeUtils.toISODateUtc(expiryDate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeIntArray(new int[] { appearPerCycle, appearanceCount });
        dest.writeLongArray(new long[] {
                startDate.getTime(), expiryDate.getTime()
        });
        dest.writeStringArray(new String[] { mongoId, video });
    }

    @Override
    public int describeContents() { return 0; }

    public int getAppearPerCycle() { return appearPerCycle; }
    public int getAppearanceCount() { return appearanceCount; }

    public String getMongoId() { return mongoId; }
    public String getVideo() { return video; }
    public Date getStartDate() { return startDate; }
    public Date getExpiryDate() { return expiryDate; }

    public void setAppearPerCycle(int appearPerCycle) {
        this.appearPerCycle = appearPerCycle;
        notifyPropertyChanged(BR.appearPerCycle);
    }

    public void setAppearanceCount(int appearanceCount) {
        this.appearanceCount = appearanceCount;
        notifyPropertyChanged(BR.appearanceCount);
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }

    public void setVideo(String video) {
        this.video = video;
        notifyPropertyChanged(BR.video);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public static Parcelable.Creator<Ad> CREATOR = new Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[0];
        }
    };
}
