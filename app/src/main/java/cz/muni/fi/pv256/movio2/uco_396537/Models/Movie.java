package cz.muni.fi.pv256.movio2.uco_396537.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david on 23.10.16.
 */

public class Movie implements Parcelable {

    private String title;
    private long releaseDate;
    @SerializedName("poster_path")
    private String coverPath;
    @SerializedName("backdrop_path")
    private String backdrop;
    private float popularity;


    public Movie() { }

    public Movie(Parcel parcel) {
        title = parcel.readString();
        releaseDate = parcel.readLong();
        coverPath = parcel.readString();
        backdrop = parcel.readString();
        popularity = parcel.readFloat();
    }

    public Movie(String title, long releaseDate, String coverPath, String backdrop, float popularity) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.backdrop = backdrop;
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeLong(releaseDate);
        parcel.writeString(coverPath);
        parcel.writeString(backdrop);
        parcel.writeFloat(popularity);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + (title == null ? 0 : title.hashCode());
        result = prime * result + (int)releaseDate;
        result = prime * result + (coverPath == null ? 0 : coverPath.hashCode());
        result = prime * result + (backdrop == null ? 0 : backdrop.hashCode());
        result = prime * result + (int)popularity;

        return result;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public long getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCoverPath() {
        return coverPath;
    }
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getBackdrop() {
        return backdrop;
    }
    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public float getPopularity() {
        return popularity;
    }
    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

}
