package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Ad extends BaseObservable implements Parcelable {

    @Bindable
    private String mongoId = "", video = "";

    public Ad(String mongoId, String video) {
        this.mongoId = mongoId;
        this.video = video;
    }

    public Ad(Parcel parcel) {
        String[] strings = new String[2];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        video = strings[1];
    }

    public Ad(JSONObject object) {
        try {
            this.mongoId = object.getString("_id");
            this.video = object.getString("video");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("video", video);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeStringArray(new String[] { mongoId, video });
    }

    @Override
    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public String getVideo() { return video; }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }

    public void setVideo(String video) {
        this.video = video;
        notifyPropertyChanged(BR.video);
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
