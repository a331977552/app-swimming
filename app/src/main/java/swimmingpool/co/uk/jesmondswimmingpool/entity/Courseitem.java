package swimmingpool.co.uk.jesmondswimmingpool.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Courseitem implements Serializable, Parcelable {
    private Long id;

    private String name;

    private Long courseid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeValue(this.courseid);
    }

    public Courseitem() {
    }

    protected Courseitem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.courseid = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Courseitem> CREATOR = new Parcelable.Creator<Courseitem>() {
        @Override
        public Courseitem createFromParcel(Parcel source) {
            return new Courseitem(source);
        }

        @Override
        public Courseitem[] newArray(int size) {
            return new Courseitem[size];
        }
    };
}