package com.example.ti22_a1_mgs.Database.blindwalls;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul de Mast on 05-Nov-18.
 */

public class BlindWall implements Parcelable {

    // Basic info:
    private int wallId;
    private String title;
    private String artist;
    private int year;

    // Descriptions:
    private String descriptionEnglish;
    private String descriptionDutch;
    private String materialEnglish;
    private String materialDutch;


    // Geographic info:
    private int numberOnMap;
    private double latitude;
    private double longitude;
    private String address;

    // Image and video info:
    private String videoAuthor;
    private String videoUrl;
    private String photographer;
    private List<String> imagesUrls;

    public BlindWall() { }

    public BlindWall(JSONObject jsonWall) {

        try {

            // parse basic information:
            wallId =  jsonWall.getInt("id");
            title = jsonWall.getJSONObject("title").getString("en").trim();
            artist = jsonWall.getString("author").trim();
            year =  jsonWall.getInt("year");

            // parse description information:
            descriptionEnglish = jsonWall.getJSONObject("description").getString("en");
            descriptionDutch = jsonWall.getJSONObject("description").getString("nl");
            materialEnglish = jsonWall.getJSONObject("material").getString("en");
            materialDutch =  jsonWall.getJSONObject("material").getString("nl");

             // parse geographic information:
            numberOnMap = jsonWall.getInt("numberOnMap");
            latitude = jsonWall.getDouble("latitude");
            longitude = jsonWall.getDouble("longitude");
            address = jsonWall.getString("address");

            // parse image and video information:
            videoAuthor = jsonWall.getString("videoAuthor");
            videoUrl = jsonWall.getString("videoUrl");
            photographer = jsonWall.getString("photographer");
            // get all the images URLS:
            JSONArray imagesUrl = jsonWall.getJSONArray("images");
            List<String> imagesUrls = new ArrayList<>();
            for (int j = 0; j < imagesUrl.length(); j++) {
                String imageUrl = imagesUrl.getJSONObject(j).getString("url");
                imagesUrls.add(imageUrl);
            }
            imagesUrls = imagesUrls;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getWallId() {
        return wallId;
    }

    public void setWallId(int wallId) {
        this.wallId = wallId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    public void setDescriptionDutch(String descriptionDutch) {
        this.descriptionDutch = descriptionDutch;
    }

    public String getMaterialEnglish() {
        return materialEnglish;
    }

    public void setMaterialEnglish(String materialEnglish) {
        this.materialEnglish = materialEnglish;
    }

    public String getMaterialDutch() {
        return materialDutch;
    }

    public void setMaterialDutch(String materialDutch) {
        this.materialDutch = materialDutch;
    }

    public int getNumberOnMap() {
        return numberOnMap;
    }

    public void setNumberOnMap(int numberOnMap) {
        this.numberOnMap = numberOnMap;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVideoAuthor() {
        return videoAuthor;
    }

    public void setVideoAuthor(String videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Override
    public String toString() {
        return "BlindWall{" +
                "wallId=" + wallId +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected BlindWall(Parcel in) {
        wallId = in.readInt();
        title = in.readString();
        artist = in.readString();
        year = in.readInt();
        descriptionEnglish = in.readString();
        descriptionDutch = in.readString();
        materialEnglish = in.readString();
        materialDutch = in.readString();
        numberOnMap = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        videoAuthor = in.readString();
        videoUrl = in.readString();
        photographer = in.readString();
        if (in.readByte() == 0x01) {
            imagesUrls = new ArrayList<String>();
            in.readList(imagesUrls, String.class.getClassLoader());
        } else {
            imagesUrls = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wallId);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeInt(year);
        dest.writeString(descriptionEnglish);
        dest.writeString(descriptionDutch);
        dest.writeString(materialEnglish);
        dest.writeString(materialDutch);
        dest.writeInt(numberOnMap);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(address);
        dest.writeString(videoAuthor);
        dest.writeString(videoUrl);
        dest.writeString(photographer);
        if (imagesUrls == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imagesUrls);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<BlindWall> CREATOR = new Creator<BlindWall>() {
        @Override
        public BlindWall createFromParcel(Parcel in) {
            return new BlindWall(in);
        }

        @Override
        public BlindWall[] newArray(int size) {
            return new BlindWall[size];
        }
    };
}
