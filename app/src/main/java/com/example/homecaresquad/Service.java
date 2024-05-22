//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import android.os.Parcel;
import android.os.Parcelable;

//creating a class Service
public class Service implements Parcelable {
    //declaring required variables
    private String name;
    private int imageResourceId;
    private double basicCost;
    private double standardCost;
    private double premiumCost;

    //constructor
    public Service(String name, double basicCost, double standardCost, double premiumCost) {
        this.name = name;
        this.basicCost = basicCost;
        this.standardCost = standardCost;
        this.premiumCost = premiumCost;
    }

    //constructor for Parcel
    protected Service(Parcel in) {
        name = in.readString();
        imageResourceId = in.readInt();
        basicCost = in.readDouble();
        standardCost = in.readDouble();
        premiumCost = in.readDouble();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };


    //getters and setters
    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public double getBasicCost() {
        return basicCost;
    }

    public double getStandardCost() {
        return standardCost;
    }

    public double getPremiumCost() {
        return premiumCost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(imageResourceId);
        parcel.writeDouble(basicCost);
        parcel.writeDouble(standardCost);
        parcel.writeDouble(premiumCost);
    }
}
