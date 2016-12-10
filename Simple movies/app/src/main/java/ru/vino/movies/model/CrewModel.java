package ru.vino.movies.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import ru.vino.movies.base.IPerson;

@Parcel
public class CrewModel implements IPerson {

    @SerializedName("credit_id")
    @Expose
    String creditId;
    @SerializedName("department")
    @Expose
    String department;
    @SerializedName("id")
    @Expose
    long id;
    @SerializedName("job")
    @Expose
    String job;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("profile_path")
    @Expose
    String profilePath;

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return job;
    }

    @Override
    public String getPhoto() {
        return profilePath;
    }

    @Override
    public int getType() {
        return IPerson.TYPE_CREW;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
