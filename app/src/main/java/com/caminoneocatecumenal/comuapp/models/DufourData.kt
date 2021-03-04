package com.caminoneocatecumenal.comuapp.models

import android.os.Parcel
import android.os.Parcelable


data class DufourData (val name : String, val htmlFile : String, val texts : String) : Parcelable {
    override fun toString(): String {
        return "DufourData -> (name=$name, htmlFile=$htmlFile, showType=$showType, texts=$texts)"
    }
    var showType : Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
        showType = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(htmlFile)
        parcel.writeString(texts)
        parcel.writeByte(if (showType) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DufourData> {
        override fun createFromParcel(parcel: Parcel): DufourData {
            return DufourData(parcel)
        }

        override fun newArray(size: Int): Array<DufourData?> {
            return arrayOfNulls(size)
        }
    }


}