package ph.easybus.ebmodels.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

/**
 * Created by Kimuel on 11/11/2016.
 */
public class Reservation extends BaseObservable implements Parcelable {

    @Bindable
    private String mongoId, email, mobile = "", boarding, dropping,
            reservedBy, seatTypes, shortAlias, linerName, office = "",
            confirmationCode = "", referenceNumber = "", paymentType = "Cash", paymentRemarks, receiptNo,
            remarks, otherDetails = "", sellerCode = "", paymentQr = "", printedBy = "", openTicketBy = "";

    @Bindable
    private boolean webReservation = true, printed, modified, clustered = false, doUpdate = true;

    @Bindable
    private int id = 0, status = 0, role = 0, source, sendStatus = 0,
            transactionId = 0, printCount = 0;

    @Bindable
    private double webFee, farePerSeat, totalFare, penaltyFee, ferryFare = 0,
            systemFee, boardingTimeAdjustment = 0;

    @Bindable
    private Date reservedDate = Calendar.getInstance().getTime(), expiresAt, printedDate, openTicketDate;

    private Name name = new Name();
    private Trip trip;
    private Liner liner;
    private Bus bus;
    private Adjustment adjustment;
    private Reservation parent;

    @Bindable
    private Discount discount = new Discount();
    private Fees fees = new Fees();
    private ThirdParty thirdParty;

    @Bindable
    private ObservableArrayList<Integer> reservedSeats = new ObservableArrayList<>();

    @Bindable
    private ObservableArrayList<String> reservedSeatsAlias;

    @Bindable
    private ObservableArrayList<Passenger> passengers = new ObservableArrayList<>();

    @Bindable
    private ObservableArrayList<Infant> infants = new ObservableArrayList<>();

    @Bindable
    private ObservableArrayList<Reservation> children = new ObservableArrayList<>();

    public Reservation() {}

    public Reservation(Reservation reservation) {
        webReservation = reservation.isWebReservation();
        printed = reservation.isPrinted();
        clustered = reservation.isClustered();
        doUpdate = reservation.isDoUpdate();

        id = reservation.getId();
        status = reservation.getStatus();
        role = reservation.getRole();
        source = reservation.getSource();
        sendStatus = reservation.getSendStatus();
        transactionId = reservation.getTransactionId();
        printCount = reservation.getPrintCount();

        webFee = reservation.getWebFee();
        farePerSeat = reservation.getFarePerSeat();
        totalFare = reservation.getTotalFare();
        penaltyFee = reservation.getPenaltyFee();
        ferryFare = reservation.getFerryFare();
        systemFee = reservation.getSystemFee();
        boardingTimeAdjustment = reservation.getBoardingTimeAdjustment();

        mongoId = reservation.getMongoId();
        email = reservation.getEmail();
        mobile = reservation.getMobile();
        boarding = reservation.getBoarding();
        dropping = reservation.getDropping();
        reservedBy = reservation.getReservedBy();
        seatTypes = reservation.getSeatTypes();
        shortAlias = reservation.getShortAlias();
        linerName = reservation.getLinerName();
        office = reservation.getOffice();
        confirmationCode = reservation.getConfirmationCode();
        referenceNumber = reservation.getReferenceNumber();
        paymentType = reservation.getPaymentType();
        paymentRemarks = reservation.getPaymentRemarks();
        receiptNo = reservation.getReceiptNo();
        remarks = reservation.getRemarks();
        otherDetails = reservation.getOtherDetails();
        paymentQr = reservation.getPaymentQr();
        sellerCode = reservation.getSellerCode();
        printedBy = reservation.getPrintedBy();
        openTicketBy = reservation.getOpenTicketBy();

        reservedDate = reservation.getReservedDate();
        expiresAt = reservation.getExpiresAt();
        printedDate = reservation.getPrintedDate();
        openTicketDate = reservation.getOpenTicketDate();

        name = reservation.getName();
        trip = reservation.getTrip();
        liner = reservation.getLiner();
        bus = reservation.getBus();
        discount = reservation.getDiscount();
        fees = reservation.getFees();
        thirdParty = reservation.getThirdParty();
        adjustment = reservation.getAdjustment();

        reservedSeats = reservation.getReservedSeats();

        passengers = reservation.getPassengers();
        infants = reservation.getInfants();
        children = reservation.getChildren();

        parent = reservation.getParent();
    }

    public Reservation(String firstName, String lastName, String email) {
        this.name = new Name(firstName, lastName);
        this.email = email;
    }

    public Reservation(Parcel parcel) {
        String[] strings = new String[21];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        email = strings[1];
        mobile = strings[2];
        boarding = strings[3];
        dropping = strings[4];
        reservedBy = strings[5];
        seatTypes = strings[6];
        shortAlias = strings[7];
        linerName = strings[8];
        office = strings[9];
        confirmationCode = strings[10];
        referenceNumber = strings[11];
        paymentType = strings[12];
        paymentRemarks = strings[13];
        receiptNo = strings[14];
        remarks = strings[15];
        otherDetails = strings[16];
        paymentQr = strings[17];
        sellerCode = strings[18];
        printedBy = strings[19];
        openTicketBy = strings[20];

        double[] doubles = new double[7];
        parcel.readDoubleArray(doubles);
        totalFare = doubles[0];
        webFee = doubles[1];
        farePerSeat = doubles[2];
        penaltyFee = doubles[3];
        ferryFare = doubles[4];
        systemFee = doubles[5];
        boardingTimeAdjustment = doubles[6];

        int size = parcel.readInt();
        int[] seatsData = new int[size];
        parcel.readIntArray(seatsData);

        reservedSeats = new ObservableArrayList<>();
        for (int seat : seatsData) {
            reservedSeats.add(seat);
        }

        name = parcel.readParcelable(Name.class.getClassLoader());
        trip = parcel.readParcelable(Trip.class.getClassLoader());
        liner = parcel.readParcelable(Liner.class.getClassLoader());
        bus = parcel.readParcelable(Bus.class.getClassLoader());
        adjustment = parcel.readParcelable(Adjustment.class.getClassLoader());
        thirdParty = parcel.readParcelable(ThirdParty.class.getClassLoader());
        parent = parcel.readParcelable(Reservation.class.getClassLoader());

        int[] ints = new int[6];
        parcel.readIntArray(ints);
        id = ints[0];
        status = ints[1];
        role = ints[2];
        source = ints[3];
        transactionId = ints[4];
        printCount = ints[5];

        boolean[] booleans = new boolean[5];
        parcel.readBooleanArray(booleans);
        webReservation = booleans[0];
        modified = booleans[1];
        printed = booleans[2];
        clustered = booleans[3];
        doUpdate = booleans[4];

        long[] longData = new long[4];
        parcel.readLongArray(longData);
        reservedDate = new Date(longData[0]);
        if (longData[1] > 0) { expiresAt = new Date(longData[1]); }
        if (longData[2] > 0) { printedDate = new Date(longData[2]); }
        if (longData[3] > 0) { openTicketDate = new Date(longData[3]); }

        fees = parcel.readParcelable(Fees.class.getClassLoader());

        Parcelable[] passParcel = parcel.readParcelableArray(Passenger.class.getClassLoader());
        ObservableArrayList<Passenger> pass = new ObservableArrayList<>();
        for (Parcelable parcelable : passParcel) {
            pass.add((Passenger)parcelable);
        }
        passengers = pass;

        Parcelable[] infantsParcel = parcel.readParcelableArray(Infant.class.getClassLoader());
        ObservableArrayList<Infant> infs = new ObservableArrayList<>();
        for (Parcelable parcelable : infantsParcel) {
            infs.add((Infant)parcelable);
        }
        infants = infs;

        Parcelable[] childrenParcel = parcel.readParcelableArray(Reservation.class.getClassLoader());
        ObservableArrayList<Reservation> childs = new ObservableArrayList<>();
        for (Parcelable parcelable : childrenParcel) {
            childs.add((Reservation)parcelable);
        }
        children = childs;

        boolean[] booleansHasSeatAlias = new boolean[1];
        parcel.readBooleanArray(booleansHasSeatAlias);
        boolean hasSeatAlias = booleansHasSeatAlias[0];
        if (hasSeatAlias) {
            ObservableArrayList<String> reservedSeatsAlias = new ObservableArrayList<>();
            parcel.readStringList(reservedSeatsAlias);
            this.reservedSeatsAlias = reservedSeatsAlias;
        }
        /*
        boolean hasSeatAlias = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            hasSeatAlias = parcel.readBoolean();

            if (hasSeatAlias) {
                ObservableArrayList<String> reservedSeatsAlias = new ObservableArrayList<>();
                parcel.readStringList(reservedSeatsAlias);
                this.reservedSeatsAlias = reservedSeatsAlias;
            }
        } */
    }

    public Reservation(JSONObject object) {
        try {
            Trip reservationTrip = new Trip();
            if (object.has("trip_id")) {
                reservationTrip.setMongoId(object.getString("trip_id"));
            }
            if (object.has("dltb_trip_id")) {
                reservationTrip.setDltbId(object.getString("dltb_trip_id"));
            }
            if (object.has("trip_date")) {
                reservationTrip.setDate(DateTimeUtils.toDate(object.getString("trip_date")));
            }
            if (object.has("trip_id")) {
                try {
                    JSONObject tripObject = object.getJSONObject("trip_id");
                    reservationTrip = new Trip(tripObject, reservationTrip.getDate());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (object.has("tripInfo")) {
                try {
                    JSONObject tripObject = object.getJSONArray("tripInfo").getJSONObject(0);
                    reservationTrip = new Trip(tripObject, reservationTrip.getDate());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (object.has("trip")) {
                try {
                    JSONObject tripObject = object.getJSONObject("trip");
                    reservationTrip = new Trip(tripObject, reservationTrip.getDate());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (object.has("bus")) {
                reservationTrip.setBus(new Bus(object.getJSONObject("bus")));
            }

            trip = reservationTrip;

            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("name")) name = new Name(object.getJSONObject("name"));
            if (object.has("email")) email = object.getString("email");
            if (object.has("mobile")) mobile = object.getString("mobile");
            if (object.has("boarding_point")) boarding = object.getString("boarding_point");
            if (object.has("dropping_point")) dropping = object.getString("dropping_point");
            if (object.has("reserved_by")) reservedBy = object.getString("reserved_by");
            if (object.has("seat_types")) seatTypes = object.getString("seat_types");
            if (object.has("short_alias")) shortAlias = object.getString("short_alias");
            if (object.has("liner_name")) linerName = object.getString("liner_name");
            if (object.has("office")) office = object.getString("office");
            if (object.has("reference_number")) referenceNumber = object.getString("reference_number");
            if (object.has("confirmation_code")) confirmationCode = object.getString("confirmation_code");
            if (object.has("status")) status = object.getInt("status");
            if (object.has("role")) role = object.getInt("role");
            if (object.has("source")) source = object.getInt("source");
            if (object.has("print_count")) printCount = object.getInt("print_count");
            if (object.has("receipt_number")) receiptNo = object.getString("receipt_number");
            if (object.has("remarks")) remarks = object.getString("remarks");
            if (object.has("payment_type")) paymentType = object.getString("payment_type");
            if (object.has("payment_remarks")) paymentRemarks = object.getString("payment_remarks");
            if (object.has("other_details")) otherDetails = object.getString("other_details");
            if (object.has("paymentQr")) paymentQr = object.getString("paymentQr");
            if (object.has("seller_code")) sellerCode = object.getString("seller_code");
            if (object.has("printed_by")) printedBy = object.getString("printed_by");
            if (object.has("open_ticket_by")) openTicketBy = object.getString("open_ticket_by");

            if (object.has("total_fare")) totalFare = object.getDouble("total_fare");
            if (object.has("farePerSeat")) farePerSeat = object.getDouble("farePerSeat");
            if (object.has("web_fee")) webFee = object.getDouble("web_fee");
            if (object.has("system_fee")) systemFee = object.getDouble("system_fee");
            if (object.has("penalty_fee")) penaltyFee = object.getDouble("penalty_fee");
            if (object.has("ferry_fare")) ferryFare = object.getDouble("ferry_fare");
            if (object.has("boarding_time_adjustment"))
                boardingTimeAdjustment = object.getDouble("boarding_time_adjustment");
            if (object.has("discount")) discount = new Discount(object.getJSONObject("discount"));
            if (object.has("fees")) fees = new Fees(object.getJSONObject("fees"));

            if (object.has("adjustments")) {
                adjustment = new Adjustment(object.getJSONObject("adjustments"));
            }

            if (object.has("parent")) {
                parent = new Reservation(object.getJSONObject("parent"));
            }

            if (object.has("doUpdate")) doUpdate = object.getBoolean("doUpdate");

            if (object.has("reserved_date")) {
                reservedDate = DateTimeUtils.toDateUtc(object.getString("reserved_date"));
            }

            if (object.has("expires_at")) {
                if (!object.isNull("expires_at")) {
                    expiresAt = DateTimeUtils.toDateUtc(object.getString("expires_at"));
                }
            }

            if (object.has("printed_date")) {
                if (!object.isNull("printed_date")) {
                    printedDate = DateTimeUtils.toDateUtc(object.getString("printed_date"));
                }
            }

            if (object.has("open_ticket_date")) {
                openTicketDate = DateTimeUtils.toDateUtc(object.getString("open_ticket_date"));
            }

            if (object.has("passengers")) {
                JSONArray passengersJSONArray = object.getJSONArray("passengers");
                ObservableArrayList<Passenger> passengers = new ObservableArrayList<>();
                for (int i = 0; i < passengersJSONArray.length(); i++) {
                    passengers.add(new Passenger(passengersJSONArray.getJSONObject(i)));
                }
                this.passengers = passengers;
            }

            if (object.has("infants")) {
                JSONArray infantsJSONArray = object.getJSONArray("infants");
                ObservableArrayList<Infant> infants = new ObservableArrayList<>();
                for (int i = 0; i < infantsJSONArray.length(); i++) {
                    infants.add(new Infant(infantsJSONArray.getJSONObject(i)));
                }
                this.infants = infants;
            }

            if (object.has("children")) {
                JSONArray childrenJSONArray = object.getJSONArray("children");
                ObservableArrayList<Reservation> childs = new ObservableArrayList<>();
                for (int i = 0; i < childrenJSONArray.length(); i++) {
                    childs.add(new Reservation(childrenJSONArray.getJSONObject(i)));
                }
                children = childs;
            }

            if (object.has("reserved_seats")) {
                JSONArray reservedSeatsJSONArray = object.getJSONArray("reserved_seats");
                ObservableArrayList<Integer> reservedSeats = new ObservableArrayList<>();
                for (int i = 0; i < reservedSeatsJSONArray.length(); i++) {
                    reservedSeats.add(reservedSeatsJSONArray.getInt(i));
                }
                this.reservedSeats = reservedSeats;
            }

            if (object.has("reserved_seats_alias")) {
                JSONArray reservedSeatsAliasJSONArray = object.getJSONArray("reserved_seats_alias");
                ObservableArrayList<String> reservedSeatsAlias = new ObservableArrayList<>();
                for (int i = 0; i < reservedSeatsAliasJSONArray.length(); i++) {
                    reservedSeatsAlias.add(reservedSeatsAliasJSONArray.getString(i));
                }
                this.reservedSeatsAlias = reservedSeatsAlias;
            }

            if (object.has("third_party"))
                thirdParty = new ThirdParty(object.getJSONObject("third_party"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public boolean partialCompareTo(Reservation reservation) {
        String localTripId = "", reservationTripId = "";
        if (thirdParty != null) localTripId = thirdParty.getTrip() + "";
        else localTripId = trip.getMongoId();

        if (reservation.getThirdParty() != null)
            reservationTripId = reservation.getThirdParty().getTrip() + "";
        else reservationTripId = reservation.getTrip().getMongoId();

        if (reservationTripId.equals(localTripId) &&
            reservation.getTrip().getDate().equals(trip.getDate())) {
            for (int seat: reservation.getReservedSeats()) {
                if (!getReservedSeats().contains(seat)) return false;
            }
            return true;
        }
        return false;
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("trip_id", trip.getMongoId());
            object.put("trip_date", DateTimeUtils.toISODate(trip.getDate()));
            object.put("trip_time", DateTimeUtils.toISODateUtc(trip.getTime()));
            object.put("liner_name", linerName);
            object.put("origin", trip.getOrigin());
            object.put("destination", trip.getDestination());
            object.put("boarding_point", boarding);
            object.put("dropping_point", dropping);
            object.put("total_fare", totalFare);
            object.put("farePerSeat", farePerSeat);
            object.put("web_fee", webFee);
            object.put("system_fee", systemFee);
            object.put("penalty_fee", penaltyFee);
            object.put("source", source);
            object.put("print_count", printCount);
            object.put("role", role);
            object.put("status", status);
            object.put("reference_number", referenceNumber);
            object.put("confirmation_code", confirmationCode);
            object.put("receipt_number", receiptNo);
            object.put("short_alias", shortAlias);
            object.put("reserved_by", reservedBy);
            object.put("office", office);
            object.put("seat_types", seatTypes);
            object.put("email", email);
            object.put("mobile", mobile);
            object.put("remarks", remarks);
            object.put("payment_type", paymentType);
            object.put("payment_remarks", paymentRemarks);
            object.put("other_details", otherDetails);
            object.put("seller_code", sellerCode);
            object.put("printed_by", printedBy);
            object.put("open_ticket_by", openTicketBy);
            object.put("reserved_date", DateTimeUtils.toISODateUtc(reservedDate));

            object.put("ferry_fare", ferryFare);

            object.put("boarding_time_adjustment", boardingTimeAdjustment);

            if (bus != null) object.put("bus", bus.toJSON());
            if (adjustment != null) object.put("adjustments", adjustment.toJSON());
            if (thirdParty != null) object.put("third_party", thirdParty.toJSON());

            if (expiresAt != null) object.put("expires_at",
                    DateTimeUtils.toISODateUtc(expiresAt));

            if (printedDate != null) object.put("printed_date",
                    DateTimeUtils.toISODateUtc(printedDate));

            if (openTicketDate != null) object.put("open_ticket_date",
                    DateTimeUtils.toISODateUtc(openTicketDate));

            if (passengers != null) {
                JSONArray passengersJSONArray = new JSONArray();
                for (int i = 0; i < passengers.size(); i++) {
                    passengersJSONArray.put(passengers.get(i).toJSON());
                }
                object.put("passengers", passengersJSONArray);
            }

            if (infants != null) {
                JSONArray infantsJSONArray = new JSONArray();
                for (int i = 0; i < infants.size(); i++) {
                    infantsJSONArray.put(infants.get(i).toJSON());
                }
                object.put("infants", infantsJSONArray);
            }

            object.put("name", name.toJSON());

            object.put("reserved_seats", new JSONArray(reservedSeats));

            if (reservedSeatsAlias != null) {
                JSONArray reservedSeatsAliasJSONArray = new JSONArray();
                for (int i = 0; i < reservedSeatsAlias.size(); i++) {
                    reservedSeatsAliasJSONArray.put(reservedSeatsAlias.get(i));
                }
                object.put("reserved_seats_alias", reservedSeatsAliasJSONArray);
            }

            object.put("discount", discount.toJSON());
            object.put("fees", fees.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { mongoId, email, mobile,
                boarding, dropping, reservedBy, seatTypes, shortAlias, linerName, office,
                confirmationCode, referenceNumber, paymentType, paymentRemarks, receiptNo,
                remarks, otherDetails, paymentQr, sellerCode, printedBy, openTicketBy });
        parcel.writeDoubleArray(new double[] { totalFare, webFee, farePerSeat,
                penaltyFee, ferryFare, systemFee, boardingTimeAdjustment });

        int[] parcelReservedSeats = new int[reservedSeats.size()];
        for (int i = 0; i < reservedSeats.size(); i++) {
            parcelReservedSeats[i] = reservedSeats.get(i);
        }
        parcel.writeInt(reservedSeats.size());
        parcel.writeIntArray(parcelReservedSeats);

        parcel.writeParcelable(name, flags);
        parcel.writeParcelable(trip, flags);
        parcel.writeParcelable(liner, flags);
        parcel.writeParcelable(bus, flags);
        parcel.writeParcelable(adjustment, flags);
        parcel.writeParcelable(thirdParty, flags);
        parcel.writeParcelable(parent, flags);
        parcel.writeIntArray(new int[] { id, status, role, source, transactionId, printCount });
        parcel.writeBooleanArray(new boolean[] { webReservation, modified, printed, clustered, doUpdate });

        parcel.writeLongArray(new long[] { reservedDate.getTime(),
                (expiresAt != null ? expiresAt.getTime() : -1),
                (printedDate != null ? printedDate.getTime() : -1),
                (openTicketDate != null ? openTicketDate.getTime() : -1 )});

        parcel.writeParcelable(fees, flags);

        Passenger[] pass = new Passenger[passengers.size()];
        for (int i = 0; i < passengers.size(); i++) {
            pass[i] = passengers.get(i);
        }
        parcel.writeParcelableArray(pass, flags);

        Infant[] infs = new Infant[infants.size()];
        for (int i = 0; i < infants.size(); i++) {
            infs[i] = infants.get(i);
        }
        parcel.writeParcelableArray(infs, flags);

        Reservation[] childArray = new Reservation[children.size()];
        for (int i = 0; i < children.size(); i++) {
            childArray[i] = children.get(i);
        }
        parcel.writeParcelableArray(childArray, flags);

        boolean hasSeatsAlias = (reservedSeatsAlias != null);
        parcel.writeBooleanArray(new boolean[] { hasSeatsAlias });
        if (hasSeatsAlias) {
            parcel.writeStringList(reservedSeatsAlias);
        }
    }

    public int describeContents() { return 0; }

    public boolean isWebReservation() { return webReservation; }
    public boolean isPrinted() { return printed; }
    public boolean isModified() { return modified; }
    public boolean isClustered() { return clustered; }
    public boolean isDoUpdate() { return doUpdate; }
    public int getId() { return id; }
    public int getStatus() { return status; }
    public int getRole() { return role; }
    public int getSource() { return source; }
    public int getSendStatus() { return sendStatus; }
    public int getTransactionId() { return transactionId; }
    public int getPrintCount() { return printCount; }
    public double getWebFee() { return webFee; }
    public double getTotalFare() { return totalFare; }
    public double getFarePerSeat() { return farePerSeat; }
    public double getTotalAmount() { return (systemFee + webFee + totalFare); }
    public double getPenaltyFee() { return penaltyFee; }
    public double getFerryFare() { return ferryFare; }
    public double getSystemFee() { return systemFee; }
    public double getBoardingTimeAdjustment() { return boardingTimeAdjustment; }
    public String getMongoId() { return mongoId; }
    public String getReceiptNo() { return receiptNo; }
    public String getConfirmationCode() { return confirmationCode; }
    public String getBoarding() { return boarding; }
    public String getDropping() { return dropping; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getReferenceNumber() { return referenceNumber; }
    public String getLinerName() { return linerName; }
    public String getReservedBy() { return reservedBy; }
    public String getSeatTypes() { return seatTypes; }
    public String getShortAlias() { return shortAlias; }
    public String getOffice() { return office; }
    public String getPaymentType() { return paymentType; }
    public String getPaymentRemarks() { return paymentRemarks; }
    public String getRemarks() { return remarks; }
    public String getOtherDetails() { return otherDetails; }
    public String getPaymentQr() { return paymentQr; }
    public String getSellerCode() { return sellerCode; }
    public String getPrintedBy() { return printedBy; }
    public String getOpenTicketBy() { return openTicketBy; }
    public Date getReservedDate() { return reservedDate; }
    public Date getExpiresAt() { return expiresAt; }
    public Date getPrintedDate() { return printedDate; }
    public Date getOpenTicketDate() { return openTicketDate; }
    public ObservableArrayList<Integer> getReservedSeats() {
        return reservedSeats;
    }
    public ObservableArrayList<String> getReservedSeatsAlias() { return reservedSeatsAlias; }
    public Liner getLiner() { return liner; }
    public Name getName() { return name; }
    public Trip getTrip() { return trip; }
    public Bus getBus() { return bus; }
    public Adjustment getAdjustment() { return adjustment; }
    public Discount getDiscount() { return discount; }
    public Fees getFees() { return fees; }
    public ThirdParty getThirdParty() { return thirdParty; }
    public Reservation getParent() { return parent; }
    public ObservableArrayList<Passenger> getPassengers() { return passengers; }
    public ObservableArrayList<Infant> getInfants() { return infants; }
    public ObservableArrayList<Reservation> getChildren() { return children; }

    public void setWebReservation(boolean webReservation) {
        this.webReservation = webReservation;
        notifyPropertyChanged(BR.webReservation);
    }
    public void setModified(boolean modified) {
        this.modified = modified;
        notifyPropertyChanged(BR.modified);
    }
    public void setPrinted(boolean printed) {
        this.printed = printed;
        notifyPropertyChanged(BR.printed);
    }
    public void setClustered(boolean clustered) {
        this.clustered = clustered;
    }
    public void setDoUpdate(boolean doUpdate) { this.doUpdate = doUpdate; }
    public void setId(int id) { this.id = id; }
    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
    public void setRole(int role) {
        this.role = role;
        notifyPropertyChanged(BR.role);
    }
    public void setSource(int source) {
        this.source = source;
        notifyPropertyChanged(BR.source);
    }
    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
        notifyPropertyChanged(BR.sendStatus);
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public void setPrintCount(int printCount) {
        this.printCount = printCount;
        notifyPropertyChanged(BR.printCount);
    }
    public void setWebFee(double webFee) {
        this.webFee = webFee;
        notifyPropertyChanged(BR.webFee);
    }
    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
        notifyPropertyChanged(BR.totalFare);
    }
    public void setFarePerSeat(double farePerSeat) {
        this.farePerSeat = farePerSeat;
        notifyPropertyChanged(BR.farePerSeat);
    }
    public void setPenaltyFee(double penaltyFee) {
        this.penaltyFee = penaltyFee;
        notifyPropertyChanged(BR.penaltyFee);
    }
    public void setFerryFare(double ferryFare) {
        this.ferryFare = ferryFare;
        notifyPropertyChanged(BR.ferryFare);
    }
    public void setSystemFee(double systemFee) {
        this.systemFee = systemFee;
        notifyPropertyChanged(BR.systemFee);
    }
    public void setBoardingTimeAdjustment(double boardingTimeAdjustment) {
        this.boardingTimeAdjustment = boardingTimeAdjustment;
        notifyPropertyChanged(BR.boardingTimeAdjustment);
    }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setReceiptNo(String receiptNo) { this.receiptNo = receiptNo; }
    public void setBoarding(String boarding) { this.boarding = boarding; }
    public void setDropping(String dropping) { this.dropping = dropping; }
    public void setConfirmationCode(String code) {
        confirmationCode = code;
        notifyPropertyChanged(BR.confirmationCode);
    }
    public void setEmail(String email) { this.email = email; }
    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        notifyPropertyChanged(BR.referenceNumber);
    }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setReservedBy(String reservedBy) { this.reservedBy = reservedBy; }
    public void setSeatTypes(String seatTypes) { this.seatTypes = seatTypes; }
    public void setShortAlias(String shortAlias) { this.shortAlias = shortAlias; }
    public void setOffice(String office) { this.office = office; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    public void setPaymentRemarks(String paymentRemarks) { this.paymentRemarks = paymentRemarks; }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
        notifyPropertyChanged(BR.remarks);
    }
    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
        notifyPropertyChanged(BR.otherDetails);
    }
    public void setPaymentQr(String paymentQr) {
        this.paymentQr = paymentQr;
        notifyPropertyChanged(BR.paymentQr);
    }
    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
        notifyPropertyChanged(BR.sellerCode);
    }
    public void setPrintedBy(String printedBy) {
        this.printedBy = printedBy;
        notifyPropertyChanged(BR.printedBy);
    }
    public void setOpenTicketBy(String openTicketBy) {
        this.openTicketBy = openTicketBy;
        notifyPropertyChanged(BR.openTicketBy);
    }

    public void setReservedDate(Date reservedDate) {
        this.reservedDate = reservedDate;
        notifyPropertyChanged(BR.reservedDate);
    }
    public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }
    public void setPrintedDate(Date printedDate) {
        this.printedDate = printedDate;
        notifyPropertyChanged(BR.printedDate);
    }
    public void setOpenTicketDate(Date openTicketDate) {
        this.openTicketDate = openTicketDate;
        notifyPropertyChanged(BR.openTicketDate);

    }
    public void setLiner(Liner liner) { this.liner = liner;}
    public void setName(Name name) { this.name = name; }
    public void setTrip(Trip trip) { this.trip = trip; }
    public void setBus(Bus bus) { this.bus = bus; }
    public void setAdjustment(Adjustment adjustment) { this.adjustment = adjustment; }
    public void setDiscount(Discount discount) {
        this.discount = discount;
        notifyPropertyChanged(BR.discount);
    }
    public void setFees(Fees fees) { this.fees = fees; }
    public void setThirdParty(ThirdParty thirdParty) { this.thirdParty = thirdParty; }
    public void setPassengers(ObservableArrayList<Passenger> passengers) {
        this.passengers = passengers;
        notifyPropertyChanged(BR.passengers);
    }
    public void setInfants(ObservableArrayList<Infant> infants) {
        this.infants = infants;
        notifyPropertyChanged(BR.infants);
    }
    public void setChildren(ObservableArrayList<Reservation> children) {
        this.children = children;
        notifyPropertyChanged(BR.children);
    }
    public void setReservedSeats(ObservableArrayList<Integer> reservedSeats) {
        this.reservedSeats = reservedSeats;
        notifyPropertyChanged(BR.reservedSeats);
    }
    public void setReservedSeatsAlias(ObservableArrayList<String> reservedSeatsAlias) {
        this.reservedSeatsAlias = reservedSeatsAlias;
        notifyPropertyChanged(BR.reservedSeatsAlias);
    }
    public void setParent(Reservation parent) {
        this.parent = parent;
    }
    public void setBoardingDropping(String boarding, String dropping) {
        this.boarding = boarding;
        this.dropping = dropping;
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        public Reservation createFromParcel(Parcel parcel) { return new Reservation(parcel); }
        public Reservation[] newArray(int size) { return new Reservation[size]; }
    };
}
