package com.caminoneocatecumenal.comuapp.models

import android.os.Parcel
import android.os.Parcelable


data class PreparationData(val first: ReadingData?, val second: ReadingData?, val third: ReadingData?, val ev: ReadingData?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(ReadingData::class.java.classLoader),
        parcel.readParcelable(ReadingData::class.java.classLoader),
        parcel.readParcelable(ReadingData::class.java.classLoader),
        parcel.readParcelable(ReadingData::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(first, flags)
        parcel.writeParcelable(second, flags)
        parcel.writeParcelable(third, flags)
        parcel.writeParcelable(ev, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PreparationData> {
        override fun createFromParcel(parcel: Parcel): PreparationData {
            return PreparationData(parcel)
        }

        override fun newArray(size: Int): Array<PreparationData?> {
            return arrayOfNulls(size)
        }
    }


}