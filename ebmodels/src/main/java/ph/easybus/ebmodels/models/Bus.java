package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kimuel on 11/7/2016.
 */
public class Bus implements Parcelable {

    private String name, description, linerName, layout;
    private int blockedSeats = 0, pwdSeats = 0, totalSeats = 0, allocatedSeats = 0;
    private ArrayList<String> seatMap;
    private ArrayList<Integer> amenities = new ArrayList<>();

    public Bus() {}

    public Bus(String name) {
        this.name = name;
    }

    public Bus(Parcel parcel) {
        String[] data = new String[4];
        parcel.readStringArray(data);
        name = data[0];
        description = data[1];
        linerName = data[2];
        layout = data[3];

        int[] busSeatsInfo = new int[4];
        parcel.readIntArray(busSeatsInfo);
        blockedSeats = busSeatsInfo[0];
        pwdSeats = busSeatsInfo[1];
        totalSeats = busSeatsInfo[2];
        allocatedSeats = busSeatsInfo[3];

        int amenSize = parcel.readInt();
        int[] amenList = new int[amenSize];
        parcel.readIntArray(amenList);

        amenities = new ArrayList<>();
        for (int i = 0; i < amenList.length; i++) { amenities.add(amenList[i]); }

        ArrayList<String> seatMapList = new ArrayList<>();
        parcel.readStringList(seatMapList);
        seatMap = seatMapList;
    }

    public Bus(JSONObject object) {
        try {
            if (object.has("name")) name = object.getString("name");
            if (object.has("description")) description = object.getString("description");
            if (object.has("blocked_seats")) blockedSeats = object.getInt("blocked_seats");
            if (object.has("pwd_seats")) pwdSeats =  object.getInt("pwd_seats");
            if (object.has("total_seats")) totalSeats = object.getInt("total_seats");
            if (object.has("allocated_seats")) allocatedSeats = object.getInt("allocated_seats");
            if (object.has("layout")) layout = object.getString("layout");

            /* Getting Amenities */
            if (object.has("amenities")) {
                JSONArray busAmen = object.getJSONArray("amenities");
                ArrayList<Integer> amenities = new ArrayList<Integer>();
                for (int i = 0; i < busAmen.length(); i++) amenities.add(busAmen.getInt(i));
                this.amenities = amenities;
            }

            /* Getting Seat Map */
            if (object.has("seat_map")) {
                JSONArray busSeatMapArray = object.getJSONArray("seat_map");
                ArrayList<String> seatMap = new ArrayList<String>();
                for (int i = 0; i < busSeatMapArray.length(); i++) seatMap.add(busSeatMapArray.getString(i));
                this.seatMap = seatMap;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Deprecated
    public static Bus getBus(JSONObject busObject) {
        Bus nBus = new Bus();
        try {
            if (busObject.has("name")) nBus.setName(busObject.getString("name"));
            if (busObject.has("description")) nBus.setDescription(busObject.getString("description"));
            if (busObject.has("blocked_seats")) nBus.setBlockedSeats(busObject.getInt("blocked_seats"));
            if (busObject.has("pwd_seats")) nBus.setPwdSeats(busObject.getInt("pwd_seats"));
            if (busObject.has("total_seats")) nBus.setTotalSeats(busObject.getInt("total_seats"));
            if (busObject.has("layout")) nBus.setLayout(busObject.getString("layout"));

            /* Getting Amenities */
            if (busObject.has("amenities")) {
                JSONArray busAmen = busObject.getJSONArray("amenities");
                ArrayList<Integer> amenities = new ArrayList<Integer>();
                for (int i = 0; i < busAmen.length(); i++) amenities.add(busAmen.getInt(i));
                nBus.setAmenities(amenities);
            }

            /* Getting Seat Map */
            if (busObject.has("seat_map")) {
                JSONArray busSeatMapArray = busObject.getJSONArray("seat_map");
                ArrayList<String> seatMap = new ArrayList<String>();
                for (int i = 0; i < busSeatMapArray.length(); i++) seatMap.add(busSeatMapArray.getString(i));
                nBus.setSeatMap(seatMap);
            }
            return nBus;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return nBus;
    }

    public String getSeatMapText()
    {
        String seatMapText = "";
        for (int i = 0; i < seatMap.size(); i++)
        {
            seatMapText += seatMap.get(i);
            if (i < seatMap.size() - 1) seatMapText += ",";
        }
        return seatMapText;
    }

    public void loadSeatMap(String seatMapText)
    {
        String[] seatMapArray = seatMapText.split(",");
        seatMap = new ArrayList<>();
        for (int i = 0; i < seatMapArray.length; i++) seatMap.add(seatMapArray[i]);
    }

    public int describeContents() { return 0; }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { name, description, linerName, layout });
        parcel.writeIntArray(new int[] { blockedSeats, pwdSeats, totalSeats, allocatedSeats });

        int[] parcelAmen = new int[amenities.size()];
        for (int i = 0; i < amenities.size(); i++) { parcelAmen[i] = amenities.get(i); }
        parcel.writeInt(amenities.size());
        parcel.writeIntArray(parcelAmen);

        parcel.writeStringList(seatMap);
    }

    public int getTotalSeats() { return totalSeats; }
    public int getBlockedSeats() { return blockedSeats; }
    public int getPwdSeats() { return pwdSeats; }
    public int getAllocatedSeats() { return allocatedSeats; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLinerName() { return linerName; }
    public String getLayout() { return layout; }
    public ArrayList<String> getSeatMap() { return seatMap; }
    public ArrayList<Integer> getAmenities() { return amenities; }

    public void setPwdSeats(int pwdSeats) { this.pwdSeats = pwdSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setBlockedSeats(int blockedSeats) { this.blockedSeats = blockedSeats; }
    public void setAllocatedSeats(int allocatedSeats) { this.allocatedSeats = allocatedSeats; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setLayout(String layout) { this.layout = layout; }
    public void setSeatMap(ArrayList<String> seatMap) { this.seatMap = seatMap; }
    public void setAmenities(ArrayList<Integer> amenities) {
        this.amenities = amenities;
    }

    public static final Creator<Bus> CREATOR = new Creator<Bus>() {
        public Bus createFromParcel(Parcel parcel) { return new Bus(parcel); }
        public Bus[] newArray(int position) { return new Bus[position]; }
    };
}
