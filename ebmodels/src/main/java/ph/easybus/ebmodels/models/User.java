package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends BaseObservable implements Parcelable {

    @Bindable
    private boolean isAgent = false;

    private int id, status = 0, role = 0;
    private String mongoId, email, password, linerName = "", office = "", shortAlias = "0", hashedPassword,
        provider, salt;

    private Name name = new Name();
    private Liner liner = new Liner();

    @Bindable
    private Agent agent = new Agent();

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(JSONObject object) {
        try {
            if (object.has("status")) status = object.getInt("status");
            if (object.has("role")) role = object.getInt("role");
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("email")) email = object.getString("email");
            if (object.has("password")) password = object.getString("password");
            if (object.has("liner_name")) linerName = object.getString("liner_name");
            if (object.has("office")) office = object.getString("office");
            if (object.has("short_alias")) shortAlias = object.getString("short_alias");
            if (object.has("hashedPassword")) hashedPassword = object.getString("hashedPassword");
            if (object.has("provider")) provider = object.getString("provider");
            if (object.has("salt")) salt = object.getString("salt");
            if (object.has("name")) name = new Name(object.getJSONObject("name"));

            if (object.has("liner")) liner = new Liner(object.getJSONObject("liner"));

            if (object.has("isAgent")) isAgent = object.getBoolean("isAgent");
            if (object.has("agent")) agent = new Agent(object.getJSONObject("agent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(Parcel parcel) {
        boolean[] booleans = new boolean[1];
        parcel.readBooleanArray(booleans);
        isAgent = booleans[0];

        int[] ints = new int[3];
        parcel.readIntArray(ints);
        id = ints[0];
        status = ints[1];
        role = ints[2];

        String[] strings = new String[9];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        email = strings[1];
        password = strings[2];
        linerName = strings[3];
        office = strings[4];
        shortAlias = strings[5];
        hashedPassword = strings[6];
        provider = strings[7];
        salt = strings[8];

        name = parcel.readParcelable(Name.class.getClassLoader());
        liner = parcel.readParcelable(Liner.class.getClassLoader());
        agent = parcel.readParcelable(Agent.class.getClassLoader());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("role", role);
            object.put("email", email);
            object.put("password", password);
            object.put("liner_name", linerName);
            object.put("office", office);
            object.put("short_alias", shortAlias);
            object.put("hashedPassword", hashedPassword);
            object.put("provider", provider);
            object.put("salt", salt);
            object.put("isAgent", isAgent);
            object.put("name", name.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { isAgent });
        parcel.writeIntArray(new int[] { id, status, role });
        parcel.writeStringArray(new String[] {
                mongoId, email, password, linerName, office, shortAlias, hashedPassword,
                provider, salt
        });

        parcel.writeParcelable(name, flags);
        parcel.writeParcelable(liner, flags);
        parcel.writeParcelable(agent, flags);
    }

    public int describeContents() { return 0; }

    public boolean getIsAgent() { return isAgent; }
    public int getId() { return id; }
    public int getStatus() { return status; }
    public int getRole() { return role; }
    public String getMongoId() { return mongoId; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getLinerName() { return linerName; }
    public String getOffice() { return office; }
    public String getShortAlias() { return shortAlias; }
    public String getHashedPassword() { return hashedPassword; }
    public String getProvider() { return provider; }
    public String getSalt() { return salt; }
    public Name getName() { return name; }
    public Liner getLiner() { return liner; }
    public Agent getAgent() { return agent; }

    public void setIsAgent(boolean isAgent) {
        this.isAgent = isAgent;
        notifyPropertyChanged(BR.isAgent);
    }
    public void setId(int id) { this.id = id; }
    public void setStatus(int status) { this.status = status; }
    public void setRole(int role) { this.role = role; }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setOffice(String office) { this.office = office; }
    public void setShortAlias(String shortAlias) { this.shortAlias = shortAlias; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
    public void setProvider(String provider) { this.provider = provider; }
    public void setSalt(String salt) { this.salt = salt; }
    public void setName(Name name) { this.name = name; }
    public void setLiner(Liner liner) { this.liner = liner; }
    public void setAgent(Agent agent) {
        this.agent = agent;
        notifyPropertyChanged(BR.agent);
    }

    public String getOfficeLiner() { return office + "-" + linerName; }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel parcel) { return new User(parcel); }
        public User[] newArray(int size) { return new User[size]; }
    };

}
