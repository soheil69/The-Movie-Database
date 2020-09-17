package com.example.core.model

import android.os.Parcel
import android.os.Parcelable

data class Movie(

    val overview: String,

    val originalLanguage: String,

    val originalTitle: String,

    val title: String,

    val posterPath: String?,

    val backdropPath: String?,

    val releaseDate: String,

    val popularity: Double,

    val voteAverage: Double,

    val id: Int,

    val voteCount: Int
) : Parcelable {
    private constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString(),
        source.readString(),
        source.readString()!!,
        source.readDouble(),
        source.readDouble(),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(overview)
        writeString(originalLanguage)
        writeString(originalTitle)
        writeString(title)
        writeString(posterPath)
        writeString(backdropPath)
        writeString(releaseDate)
        writeDouble(popularity)
        writeDouble(voteAverage)
        writeInt(id)
        writeInt(voteCount)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}