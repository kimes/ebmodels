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
    private String mongoId, validatedBy = "", reservationId = "";

    @Bindable
    private Date validatedDate = new Date();

    @Bindable
    private ObservableArrayList<Passenger> passengers = new ObservableArrayList<>();

    @Bindable
    private ObservableArrayList<Integer> reservedSeats = new ObservableArrayList<>();

    public ReservationValidation() {}

    public ReservationValidation(Parcel parcel) {
        String[] strings = new String[3];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        validatedBy = strings[1];
        reservationId = strings[2];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        validatedDate = new Date(longs[0]);

        Parcelable[] passParcel = parcel.readParcelableArray(Passenger.class.getClassLoader());
        ObservableArrayList<Passenger> pass = new ObservableArrayList<>();
        for (Parcelable parcelable : passParcel) {
            pass.add((Passenger)parcelable);
        }
        passengers = pass;

        int size = parcel.readInt();
        int[] seatsData = new int[size];
        parcel.readIntArray(seatsData);

        reservedSeats = new ObservableArrayList<>();
        for (int seat : seatsData) {
            reservedSeats.add(seat);
        }
    }

    public ReservationValidation(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("reservation_id")) reservationId = object.getString("reservation_id");
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

            if (object.has("reserved_seats")) {
                JSONArray reservedSeatsJSONArray = object.getJSONArray("reserved_seats");
                ObservableArrayList<Integer> reservedSeats = new ObservableArrayList<>();
                for (int i = 0; i < reservedSeatsJSONArray.length(); i++) {
                    reservedSeats.add(reservedSeatsJSONArray.getInt(i));
                }
                this.reservedSeats = reservedSeats;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("reservation_id", reservationId);
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
        parcel.writeStringArray(new String[] { mongoId, validatedBy, reservationId });
        parcel.writeLongArray(new long[] { validatedDate.getTime() });

        Passenger[] pass = new Passenger[passengers.size()];
        for (int i = 0; i < passengers.size(); i++) {
            pass[i] = passengers.get(i);
        }
        parcel.writeParcelableArray(pass, flags);

        int[] parcelReservedSeats = new int[reservedSeats.size()];
        for (int i = 0; i < reservedSeats.size(); i++) {
            parcelReservedSeats[i] = reservedSeats.get(i);
        }
        parcel.writeInt(reservedSeats.size());
        parcel.writeIntArray(parcelReservedSeats);
    }

    @Override
    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public String getValidatedBy() { return validatedBy; }
    public String getReservationId() { return reservationId; }
    public Date getValidatedDate() { return validatedDate; }
    public ObservableArrayList<Integer> getReservedSeats() { return reservedSeats; }
    public ObservableArrayList<Passenger> getPassengers() { return passengers; }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
        notifyPropertyChanged(BR.reservationId);
    }
    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
        notifyPropertyChanged(BR.validatedBy);
    }
    public void setValidatedDate(Date validatedDate) {
        this.validatedDate = validatedDate;
        notifyPropertyChanged(BR.validatedDate);
    }
    public void setReservedSeats(ObservableArrayList<Integer> reservedSeats) {
        this.reservedSeats = reservedSeats;
        notifyPropertyChanged(BR.reservedSeats);
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
