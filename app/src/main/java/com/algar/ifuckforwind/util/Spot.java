package com.algar.ifuckforwind.util;

/**
 * Created by algar on 2016-08-29.
 */
public class Spot {

    private String mLocation;
    private String mDistanceTo;
    private String mWindDay;
    private String mWindAfternoon;
    private int mAvatar;

    public Spot(String location, String distanceTo, String windDay, String windAfternoon, int avatar) {
        mLocation = location;
        mDistanceTo = distanceTo;
        mWindDay = windDay;
        mWindAfternoon = windAfternoon;
        mAvatar = avatar;
    }

    public String getLocation() {
        return mLocation;
    }
    public void setLocation(String location) {
        mLocation = location;
    }
    public String getDistanceTo() {
        return mDistanceTo;
    }
    public void setDistanceTo(String distanceTo) {
        mDistanceTo = distanceTo;
    }
    public String getWindDay() {
        return mWindDay;
    }
    public void setWindDay(String windDay) {
        mWindDay = windDay;
    }
    public String getWindAfternoon() {
        return mWindAfternoon;
    }
    public void setWindAfternoon(String windAfternoon) {
        mWindAfternoon = windAfternoon;
    }
    public int getAvatar() {
        return mAvatar;
    }
    public void setAvatar(int avatar) {
        mAvatar = avatar;
    }
}
