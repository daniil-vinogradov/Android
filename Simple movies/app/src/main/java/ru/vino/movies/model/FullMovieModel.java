package ru.vino.movies.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FullMovieModel implements Serializable {

    @SerializedName("adult")
    @Expose
    Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    String backdropPath;
    @SerializedName("budget")
    @Expose
    Integer budget;
    @SerializedName("genres")
    @Expose
    List<GenreModel> genres = null;
    @SerializedName("homepage")
    @Expose
    String homepage;
    @SerializedName("id")
    @Expose
    Long id;
    @SerializedName("imdb_id")
    @Expose
    String imdbId;
    @SerializedName("original_language")
    @Expose
    String originalLanguage;
    @SerializedName("original_title")
    @Expose
    String originalTitle;
    @SerializedName("overview")
    @Expose
    String overview;
    @SerializedName("popularity")
    @Expose
    Double popularity;
    @SerializedName("poster_path")
    @Expose
    String posterPath;
    @SerializedName("release_date")
    @Expose
    String releaseDate;
    @SerializedName("runtime")
    @Expose
    Integer runtime;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("tagline")
    @Expose
    String tagline;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("video")
    @Expose
    Boolean video;
    @SerializedName("vote_average")
    @Expose
    Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    Integer voteCount;
    @SerializedName("credits")
    @Expose
    private CreditsModel credits;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreModel> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public CreditsModel getCredits() {
        return credits;
    }

    public void setCredits(CreditsModel credits) {
        this.credits = credits;
    }

}
