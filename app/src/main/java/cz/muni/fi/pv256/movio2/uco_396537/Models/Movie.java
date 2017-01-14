package cz.muni.fi.pv256.movio2.uco_396537.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david on 23.10.16.
 */

public class Movie implements Parcelable {

    private long id;
    private String title;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String cover;
    @SerializedName("backdrop_path")
    private String backdrop;
    private float popularity;
    private String overview;

    public Movie() { }

    public Movie(Parcel parcel) {
        id = parcel.readLong();
        title = parcel.readString();
        releaseDate = parcel.readString();
        cover = parcel.readString();
        backdrop = parcel.readString();
        popularity = parcel.readFloat();
        overview = parcel.readString();
    }

    public Movie(long id, String title, String releaseDate, String cover, String backdrop, float popularity, String overview) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.cover = cover;
        this.backdrop = backdrop;
        this.popularity = popularity;
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(cover);
        parcel.writeString(backdrop);
        parcel.writeFloat(popularity);
        parcel.writeString(overview);
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

        result = prime * result + (int)id;
        result = prime * result + (title == null ? 0 : title.hashCode());
        result = prime * result + (title == null ? 0 : title.hashCode());
        result = prime * result + (releaseDate == null ? 0 : releaseDate.hashCode());
        result = prime * result + (cover == null ? 0 : cover.hashCode());
        result = prime * result + (backdrop == null ? 0 : backdrop.hashCode());
        result = prime * result + (int)popularity;
        result = prime * result + (overview == null ? 0 : overview.hashCode());

        return result;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
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

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }
}
