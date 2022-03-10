package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

/**
 * Created by Kimuel on 11/7/2016.
 */
public class Trip extends BaseObservable implements Parcelable {

    private boolean special, extra;
    @Bindable
    private int id, reservationCount;
    private double fare;
    private String mongoId, dltbId, linerName, origin, destination, office, createdBy, code;
    private Bus bus;
    private Date time, date, dateCreated, startDate, expiryDate;
    private ThirdParty thirdParty;
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<Date> disabledDates = new ArrayList<>();

    public Trip(Parcel parcel) {
        fare = parcel.readDouble();

        boolean[] booleans = new boolean[2];
        parcel.readBooleanArray(booleans);
        special = booleans[0];
        extra = booleans[1];

        int disabledDatesCount = 0;
        int[] ints = new int[3];
        parcel.readIntArray(ints);
        id = ints[0];
        reservationCount = ints[1];
        disabledDatesCount = ints[2];

        String[] strings = new String[8];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        linerName = strings[1];
        origin = strings[2];
        destination = strings[3];
        office = strings[4];
        createdBy = strings[5];
        code = strings[6];
        dltbId = strings[7];

        long[] datesData = new long[5];
        parcel.readLongArray(datesData);
        time = new Date(datesData[0]);
        date = new Date(datesData[1]);

        if (datesData[2] > -1) startDate = new Date(datesData[2]);
        if (datesData[3] > -1) expiryDate = new Date(datesData[3]);
        if (datesData[4] > -1) dateCreated = new Date(datesData[4]);

        long[] disabledDatesData = new long[disabledDatesCount];
        parcel.readLongArray(disabledDatesData);
        disabledDates = new ArrayList<>();
        for (long dateLong : disabledDatesData) {
            disabledDates.add(new Date(dateLong));
        }

        ArrayList<Route> routeList = new ArrayList<>();
        parcel.readTypedList(routeList, Route.CREATOR);
        routes = routeList;

        bus = parcel.readParcelable(Bus.class.getClassLoader());
        thirdParty = parcel.readParcelable(ThirdParty.class.getClassLoader());
    }

    public Trip(JSONObject object, Date date) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("id")) dltbId = object.getString("id");
            if (object.has("isExtra")) extra = object.getBoolean("isExtra");
            if (object.has("reservation_count")) reservationCount = object.getInt("reservation_count");
            if (object.has("fare")) fare = object.getDouble("fare");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("code")) code = object.getString("code");
            if (object.has("liner_name")) linerName = object.getString("liner_name");
            if (object.has("office")) office = object.getString("office");
            if (object.has("time")) {
                time = DateTimeUtils.toDateUtc(object.getString("time"));
            }
            if (object.has("start_date")) {
                startDate = DateTimeUtils.toDateUtc(object.getString("start_date"));
            }
            if (object.has("expiry_date")) {
                expiryDate = DateTimeUtils.toDateUtc(object.getString("expiry_date"));
            }
            if (object.has("date_created")) {
                dateCreated = DateTimeUtils.toDateUtc(object.getString("date_created"));
            }
            this.date = date;

            if (object.has("third_party"))
                thirdParty = new ThirdParty(object.getJSONObject("third_party"));

            if (object.has("bus")) {
                bus = new Bus(object.getJSONObject("bus"));
            }

            if (object.has("routes")) {
                JSONArray routeJSONArray = object.getJSONArray("routes");
                ArrayList<Route> routeArray = new ArrayList<>();
                for (int j = 0; j < routeJSONArray.length(); j++) {
                    JSONObject routeObject = routeJSONArray.getJSONObject(j);
                    int type = 0;
                    double fareDeduct = 0.0;
                    String name = "";
                    if (routeObject.has("type")) type = routeObject.getInt("type");
                    if (routeObject.has("fare_deduct")) fareDeduct = routeObject.getDouble("fare_deduct");
                    if (routeObject.has("name")) name = routeObject.getString("name");
                    Route nRoute = new Route(name, fareDeduct, type);
                    routeArray.add(nRoute);
                }
                routes = routeArray;
            }

            if (object.has("disabled_dates")) {
                JSONArray datesArray = object.getJSONArray("disabled_dates");
                ArrayList<Date> dates = new ArrayList<>();
                for (int i = 0; i < datesArray.length(); i++) {
                    dates.add(DateTimeUtils.toDate(datesArray.getString(i)));
                }
                disabledDates = dates;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public Trip() {}

    public int describeContents() { return 0; }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDouble(fare);
        parcel.writeBooleanArray(new boolean[] { special, extra });
        parcel.writeIntArray(new int[] { id, reservationCount, disabledDates.size() });
        parcel.writeStringArray(new String[] { mongoId, linerName, origin, destination,
                office, createdBy, code, dltbId });
        parcel.writeLongArray(new long[] { time.getTime(), date.getTime(),
                startDate != null ? startDate.getTime() : -1,
                expiryDate != null ? expiryDate.getTime() : -1,
                dateCreated != null ? dateCreated.getTime() : -1 });

        long[] disabledDatesArray = new long[disabledDates.size()];
        for (int i = 0; i < disabledDates.size(); i++) {
            disabledDatesArray[i] = disabledDates.get(i).getTime();
        }
        parcel.writeLongArray(disabledDatesArray);

        parcel.writeTypedList(routes);
        parcel.writeParcelable(bus, flags);
        parcel.writeParcelable(thirdParty, flags);
    }

    public boolean isSpecial() { return special; }
    public boolean isExtra() { return extra; }
    public int getId() { return id; }
    public int getReservationCount() { return reservationCount; }
    public double getFare() { return fare; }
    public String getMongoId() { return mongoId; }
    public String getLinerName() { return linerName; }
    public String getOffice() { return office; }
    public String getCreatedBy() { return createdBy; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getCode() { return code; }
    public String getDltbId() { return dltbId; }
    public Date getTime() { return time; }
    public Date getDate() { return date; }
    public Date getStartDate() { return startDate; }
    public Date getExpiryDate() { return expiryDate; }
    public Date getDateCreated() { return dateCreated; }
    public Bus getBus() { return bus; }
    public ThirdParty getThirdParty() { return thirdParty; }
    public ArrayList<Date> getDisabledDates() { return disabledDates; }
    public ArrayList<Route> getRoutes() { return routes; }

    public void setSpecial(boolean special) { this.special = special; }
    public void setExtra(boolean extra) { this.extra = extra; }
    public void setId(int id) { this.id = id; }
    public void setReservationCount(int reservationCount) {
        this.reservationCount = reservationCount;
        notifyPropertyChanged(BR.reservationCount);
    }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setOffice(String office) { this.office = office; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setCode(String code) { this.code = code; }
    public void setDltbId(String dltbId) { this.dltbId = dltbId; }
    public void setFare(double fare) { this.fare = fare; }
    public void setTime(Date time) { this.time = time; }
    public void setDate(Date date) { this.date = date; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setBus(Bus bus) { this.bus = bus; }
    public void setThirdParty(ThirdParty thirdParty) { this.thirdParty = thirdParty; }
    public void setRoutes(ArrayList<Route> routes) { this.routes = routes; }
    public void setDisabledDates(ArrayList<Date> disabledDates) { this.disabledDates = disabledDates; }

    public ArrayList<Route> getBoardingPoints() {
        ArrayList<Route> r = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getType() == 0) r.add(routes.get(i));
        }
        return r;
    }

    public ArrayList<Route> getDroppingPoints() {
        ArrayList<Route> r = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getType() == 1) r.add(routes.get(i));
        }
        return r;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        public Trip createFromParcel(Parcel parcel) { return new Trip(parcel); }
        public Trip[] newArray(int position) { return new Trip[position]; }
    };
}
