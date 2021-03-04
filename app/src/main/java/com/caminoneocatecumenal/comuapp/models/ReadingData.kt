package com.caminoneocatecumenal.comuapp.models

import android.os.Parcel
import android.os.Parcelable


data class ReadingData (val name : String, val reader : String, val monition : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun toString(): String {
        return "ReadingData -> (name=$name, reader=$reader, showType=$monition)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(reader)
        parcel.writeString(monition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReadingData> {
        override fun createFromParcel(parcel: Parcel): ReadingData {
            return ReadingData(parcel)
        }

        override fun newArray(size: Int): Array<ReadingData?> {
            return arrayOfNulls(size)
        }
    }


}