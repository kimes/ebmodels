package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

public class ReservationValidation extends BaseObservable implements Parcelable {

    @Bindable
    private String mongoId, validatedBy = "";

    @Bindable
    private Date validatedDate = new Date();

    @Bindable
    private ObservableArrayList<Passenger> passengers = new ObservableArrayList<>();

    public ReservationValidation() {}

    public ReservationValidation(Parcel parcel) {
        String[] strings = new String[2];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        validatedBy = strings[1];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        validatedDate = new Date(longs[0]);

        Parcelable[] passParcel = parcel.readParcelableArray(Passenger.class.getClassLoader());
        ObservableArrayList<Passenger> pass = new ObservableArrayList<>();
        for (Parcelable parcelable : passParcel) {
            pass.add((Passenger)parcelable);
        }
        passengers = pass;
    }

    public ReservationValidation(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("validated_by")) validatedBy = object.getString("validated_by");

            if (object.has("validated_date")) {
                validatedDate = DateTimeUtils.toDateUtc(object.getString("validated_date"));
            }

            if (object.has("passengers")) {
                JSONArray passengersJSONArray = object.getJSONArray("passengers");
                ObservableArrayList<Passenger> passengers = new ObservableArrayList<>();
                for (int i = 0; i < passengersJSONArray.length(); i++) {
                    passengers.add(new Passenger(passengersJSONArray.getJSONObject(i)));
                }
                this.passengers = passengers;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("validated_by", validatedBy);
            object.put("validated_date", DateTimeUtils.toISODateUtc(validatedDate));

            if (passengers != null) {
                JSONArray passengersJSONArray = new JSONArray();
                for (int i = 0; i < passengers.size(); i++) {
                    passengersJSONArray.put(passengers.get(i).toJSON());
                }
                object.put("passengers", passengersJSONArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { mongoId, validatedBy });
        parcel.writeLongArray(new long[] { validatedDate.getTime() });

        Passenger[] pass = new Passenger[passengers.size()];
        for (int i = 0; i < passengers.size(); i++) {
            pass[i] = passengers.get(i);
        }
        parcel.writeParcelableArray(pass, flags);
    }

    @Override
    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public String getValidatedBy() { return validatedBy; }
    public Date getValidatedDate() { return validatedDate; }
    public ObservableArrayList<Passenger> getPassengers() { return passengers; }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }
    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
        notifyPropertyChanged(BR.validatedBy);
    }
    public void setValidatedDate(Date validatedDate) {
        this.validatedDate = validatedDate;
        notifyPropertyChanged(BR.validatedDate);
    }
    public void setPassengers(ObservableArrayList<Passenger> passengers) {
        this.passengers = passengers;
        notifyPropertyChanged(BR.passengers);
    }

    public static final Creator<ReservationValidation> CREATOR = new Creator<ReservationValidation>() {
        public ReservationValidation createFromParcel(Parcel parcel) { return new ReservationValidation(parcel); }
        public ReservationValidation[] newArray(int size) { return new ReservationValidation[size]; }
    };
}
