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

    private boolean special, extra, showSeats;
    @Bindable
    private int id, reservationCount, cargoTotalPackages, cargoTransactions, reservedSeatsCount, validatedSeats;
    @Bindable
    private double fare, ferryFare, cargoTotalWeights, cargoTotalAmount,
            cargoTotalPorters, choiceFare, upperFare;
    private String mongoId, dltbId, linerName, origin, destination, office, createdBy, code, via;
    private Bus bus;
    private Date time, date, dateCreated, startDate, expiryDate, officeTime;
    private ThirdParty thirdParty;

    @Bindable
    private Liner liner = new Liner();

    @Bindable
    private TripAssign tripAssign = new TripAssign();

    @Bindable
    private TripStatus status = new TripStatus();

    private ArrayList<Integer> choiceSeats = new ArrayList<>(), upperSeats = new ArrayList<>();

    private ArrayList<String> choiceSeatsAlias = new ArrayList<>();

    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<Date> disabledDates = new ArrayList<>();

    public Trip() {}
    public Trip(Parcel parcel) {
        boolean[] booleans = new boolean[3];
        parcel.readBooleanArray(booleans);
        special = booleans[0];
        extra = booleans[1];
        showSeats = booleans[2];

        int disabledDatesCount = 0;
        int[] ints = new int[7];
        parcel.readIntArray(ints);
        id = ints[0];
        reservationCount = ints[1];
        disabledDatesCount = ints[2];
        cargoTotalPackages = ints[3];
        cargoTransactions = ints[4];
        reservedSeatsCount = ints[5];
        validatedSeats = ints[6];

        double[] doubles = new double[7];
        parcel.readDoubleArray(doubles);
        fare = doubles[0];
        ferryFare = doubles[1];
        cargoTotalWeights = doubles[2];
        cargoTotalAmount = doubles[3];
        cargoTotalPorters = doubles[4];
        choiceFare = doubles[5];
        upperFare = doubles[6];

        String[] strings = new String[9];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        linerName = strings[1];
        origin = strings[2];
        destination = strings[3];
        office = strings[4];
        createdBy = strings[5];
        code = strings[6];
        dltbId = strings[7];
        via = strings[8];

        long[] datesData = new long[6];
        parcel.readLongArray(datesData);
        time = new Date(datesData[0]);
        date = new Date(datesData[1]);

        if (datesData[2] > -1) startDate = new Date(datesData[2]);
        if (datesData[3] > -1) expiryDate = new Date(datesData[3]);
        if (datesData[4] > -1) dateCreated = new Date(datesData[4]);
        if (datesData[5] > -1) officeTime = new Date(datesData[5]);

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
        liner = parcel.readParcelable(Liner.class.getClassLoader());
        thirdParty = parcel.readParcelable(ThirdParty.class.getClassLoader());
        tripAssign = parcel.readParcelable(TripAssign.class.getClassLoader());
        status = parcel.readParcelable(TripStatus.class.getClassLoader());

        int[] choiceSeatsArray = new int[parcel.readInt()];
        parcel.readIntArray(choiceSeatsArray);

        choiceSeats = new ArrayList<>();
        for (int i = 0; i < choiceSeatsArray.length; i++) {
            choiceSeats.add(choiceSeatsArray[i]);
        }

        int[] upperSeatsArray = new int[parcel.readInt()];
        parcel.readIntArray(upperSeatsArray);

        upperSeats = new ArrayList<>();
        for (int i = 0; i < upperSeatsArray.length; i++) {
            upperSeats.add(upperSeatsArray[i]);
        }

        choiceSeatsAlias = new ArrayList<>();
        parcel.readStringList(choiceSeatsAlias);
    }

    public Trip(JSONObject object, Date date) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("id")) dltbId = object.getString("id");
            if (object.has("isExtra")) extra = object.getBoolean("isExtra");
            if (object.has("show_seats")) showSeats = object.getBoolean("show_seats");
            if (object.has("reservation_count")) reservationCount = object.getInt("reservation_count");
            if (object.has("fare")) fare = object.getDouble("fare");
            if (object.has("ferry_fare")) ferryFare = object.getDouble("ferry_fare");
            if (object.has("choice_fare")) choiceFare = object.getDouble("choice_fare");
            if (object.has("upper_fare")) upperFare = object.getDouble("upper_fare");

            if (object.has("cargo_total_packages")) cargoTotalPackages = object.getInt("cargo_total_packages");
            if (object.has("cargo_transactions")) cargoTransactions = object.getInt("cargo_transactions");
            if (object.has("cargo_total_weights"))
                cargoTotalWeights = object.getDouble("cargo_total_weights");
            if (object.has("cargo_total_amount"))
                cargoTotalAmount = object.getDouble("cargo_total_amount");
            if (object.has("cargo_total_porters"))
                cargoTotalPorters = object.getDouble("cargo_total_porters");

            if (object.has("reservedSeatsCount"))
                reservedSeatsCount = object.getInt("reservedSeatsCount");

            if (object.has("validatedSeats"))
                validatedSeats = object.getInt("validatedSeats");

            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("code")) code = object.getString("code");
            if (object.has("liner_name")) linerName = object.getString("liner_name");
            if (object.has("office")) office = object.getString("office");
            if (object.has("via")) via = object.getString("via");
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

            if (object.has("liner_id")) {
                liner = new Liner(object.getJSONObject("liner_id"));
            }

            if (object.has("third_party"))
                thirdParty = new ThirdParty(object.getJSONObject("third_party"));

            if (object.has("trip_assign")) {
                tripAssign = new TripAssign(object.getJSONObject("trip_assign"));
            }

            if (object.has("status")) {
                status = new TripStatus(object.getJSONObject("status"));
            }

            if (object.has("bus")) {
                bus = new Bus(object.getJSONObject("bus"));
            }

            if (object.has("choice_seats")) {
                JSONArray seatsArray = object.getJSONArray("choice_seats");
                choiceSeats = new ArrayList<>();
                for (int i = 0; i < seatsArray.length(); i++) {
                    choiceSeats.add(seatsArray.getInt(i));
                }
            }

            if (object.has("upper_seats")) {
                JSONArray seatsArray = object.getJSONArray("upper_seats");
                upperSeats = new ArrayList<>();
                for (int i = 0; i < seatsArray.length(); i++) {
                    upperSeats.add(seatsArray.getInt(i));
                }
            }

            if (object.has("choice_seats_alias")) {
                JSONArray seatsArray = object.getJSONArray("choice_seats_alias");
                choiceSeatsAlias = new ArrayList<>();
                for (int i = 0; i < seatsArray.length(); i++) {
                    choiceSeatsAlias.add(seatsArray.getString(i));
                }
            }

            if (object.has("routes")) {
                JSONArray routeJSONArray = object.getJSONArray("routes");
                ArrayList<Route> routeArray = new ArrayList<>();
                for (int j = 0; j < routeJSONArray.length(); j++) {
                    JSONObject routeObject = routeJSONArray.getJSONObject(j);
                    Route nRoute = new Route(routeObject);
                    /*
                    int type = 0;
                    double fareDeduct = 0.0;
                    String name = "";
                    if (routeObject.has("type")) type = routeObject.getInt("type");
                    if (routeObject.has("fare_deduct")) fareDeduct = routeObject.getDouble("fare_deduct");
                    if (routeObject.has("name")) name = routeObject.getString("name");
                    Route nRoute = new Route(name, fareDeduct, type); */
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

    public int describeContents() { return 0; }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { special, extra, showSeats });
        parcel.writeIntArray(new int[] { id, reservationCount, disabledDates.size(),
                cargoTotalPackages, cargoTransactions, reservedSeatsCount, validatedSeats });
        parcel.writeDoubleArray(new double[] { fare, ferryFare,
                cargoTotalWeights, cargoTotalAmount, cargoTotalPorters,
                choiceFare, upperFare });
        parcel.writeStringArray(new String[] { mongoId, linerName, origin, destination,
                office, createdBy, code, dltbId, via });
        parcel.writeLongArray(new long[] { time.getTime(), date.getTime(),
                startDate != null ? startDate.getTime() : -1,
                expiryDate != null ? expiryDate.getTime() : -1,
                dateCreated != null ? dateCreated.getTime() : -1,
                officeTime != null ? officeTime.getTime() : -1 });

        long[] disabledDatesArray = new long[disabledDates.size()];
        for (int i = 0; i < disabledDates.size(); i++) {
            disabledDatesArray[i] = disabledDates.get(i).getTime();
        }
        parcel.writeLongArray(disabledDatesArray);

        parcel.writeTypedList(routes);
        parcel.writeParcelable(bus, flags);
        parcel.writeParcelable(liner, flags);
        parcel.writeParcelable(thirdParty, flags);
        parcel.writeParcelable(tripAssign, flags);
        parcel.writeParcelable(status, flags);

        int[] choiceSeatsArray = new int[choiceSeats.size()];
        for (int i = 0; i < choiceSeats.size(); i++) {
            choiceSeatsArray[i] = choiceSeats.get(i);
        }
        parcel.writeInt(choiceSeats.size());
        parcel.writeIntArray(choiceSeatsArray);

        int[] upperSeatsArray = new int[upperSeats.size()];
        for (int i = 0; i < upperSeats.size(); i++) {
            upperSeatsArray[i] = upperSeats.get(i);
        }
        parcel.writeInt(upperSeats.size());
        parcel.writeIntArray(upperSeatsArray);

        parcel.writeStringList(choiceSeatsAlias);
    }

    public boolean isSpecial() { return special; }
    public boolean isExtra() { return extra; }
    public boolean isShowSeats() { return showSeats; }
    public int getId() { return id; }
    public int getReservationCount() { return reservationCount; }
    public int getCargoTotalPackages() { return cargoTotalPackages; }
    public int getCargoTransactions() { return cargoTransactions; }
    public int getReservedSeatsCount() { return reservedSeatsCount; }
    public int getValidatedSeats() { return validatedSeats; }
    public double getFare() { return fare; }
    public double getFerryFare() { return ferryFare; }
    public double getCargoTotalWeights() { return cargoTotalWeights; }
    public double getCargoTotalAmount() { return cargoTotalAmount; }
    public double getCargoTotalPorters() { return cargoTotalPorters; }
    public double getChoiceFare() { return choiceFare; }
    public double getUpperFare() { return upperFare; }
    public String getMongoId() { return mongoId; }
    public String getLinerName() { return linerName; }
    public String getOffice() { return office; }
    public String getCreatedBy() { return createdBy; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getCode() { return code; }
    public String getDltbId() { return dltbId; }
    public String getVia() { return via; }
    public Date getTime() { return time; }
    public Date getDate() { return date; }
    public Date getStartDate() { return startDate; }
    public Date getExpiryDate() { return expiryDate; }
    public Date getDateCreated() { return dateCreated; }
    public Date getOfficeTime() { return officeTime; }
    public Bus getBus() { return bus; }
    public Liner getLiner() { return liner; }
    public ThirdParty getThirdParty() { return thirdParty; }
    public TripAssign getTripAssign() { return tripAssign; }
    public TripStatus getStatus() { return status; }
    public ArrayList<Date> getDisabledDates() { return disabledDates; }
    public ArrayList<Route> getRoutes() { return routes; }
    public ArrayList<Integer> getChoiceSeats() { return choiceSeats; }
    public ArrayList<Integer> getUpperSeats() { return upperSeats; }
    public ArrayList<String> getChoiceSeatsAlias() { return choiceSeatsAlias; }

    public void setSpecial(boolean special) { this.special = special; }
    public void setExtra(boolean extra) { this.extra = extra; }
    public void setShowSeats(boolean showSeats) { this.showSeats = showSeats; }
    public void setId(int id) { this.id = id; }
    public void setReservationCount(int reservationCount) {
        this.reservationCount = reservationCount;
        notifyPropertyChanged(BR.reservationCount);
    }
    public void setCargoTotalPackages(int cargoTotalPackages) {
        this.cargoTotalPackages = cargoTotalPackages;
        notifyPropertyChanged(BR.cargoTotalPackages);
    }
    public void setCargoTransactions(int cargoTransactions) {
        this.cargoTransactions = cargoTransactions;
        notifyPropertyChanged(BR.cargoTransactions);
    }
    public void setReservedSeatsCount(int reservedSeatsCount) {
        this.reservedSeatsCount = reservedSeatsCount;
        notifyPropertyChanged(BR.reservedSeatsCount);
    }
    public void setValidatedSeats(int validatedSeats) {
        this.validatedSeats = validatedSeats;
        notifyPropertyChanged(BR.validatedSeats);
    }
    public void setCargoTotalWeights(double cargoTotalWeights) {
        this.cargoTotalWeights = cargoTotalWeights;
        notifyPropertyChanged(BR.cargoTotalWeights);
    }
    public void setCargoTotalAmount(double cargoTotalAmount) {
        this.cargoTotalAmount = cargoTotalAmount;
        notifyPropertyChanged(BR.cargoTotalAmount);
    }
    public void setCargoTotalPorters(double cargoTotalPorters) {
        this.cargoTotalPorters = cargoTotalPorters;
        notifyPropertyChanged(BR.cargoTotalPorters);
    }
    public void setChoiceFare(double choiceFare) {
        this.choiceFare = choiceFare;
    }
    public void setUpperFare(double upperFare) {
        this.upperFare = upperFare;
    }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setOffice(String office) { this.office = office; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setCode(String code) { this.code = code; }
    public void setDltbId(String dltbId) { this.dltbId = dltbId; }
    public void setVia(String via) { this.via = via; }
    public void setFare(double fare) { this.fare = fare; }
    public void setFerryFare(double ferryFare) { this.ferryFare = ferryFare; }
    public void setTime(Date time) { this.time = time; }
    public void setDate(Date date) { this.date = date; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setOfficeTime(Date officeTime) { this.officeTime = officeTime; }
    public void setBus(Bus bus) { this.bus = bus; }
    public void setLiner(Liner liner) { this.liner = liner; }
    public void setThirdParty(ThirdParty thirdParty) { this.thirdParty = thirdParty; }
    public void setTripAssign(TripAssign tripAssign) {
        this.tripAssign = tripAssign;
        notifyPropertyChanged(BR.tripAssign);
    }
    public void setStatus(TripStatus status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
    public void setChoiceSeats(ArrayList<Integer> choiceSeats) {
        this.choiceSeats = choiceSeats;
    }
    public void setUpperSeats(ArrayList<Integer> upperSeats) {
        this.upperSeats = upperSeats;
    }
    public void setChoiceSeatsAlias(ArrayList<String> choiceSeatsAlias) {
        this.choiceSeatsAlias = choiceSeatsAlias;
    }
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
