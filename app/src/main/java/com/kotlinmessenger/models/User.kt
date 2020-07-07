package com.kotlinmessenger.models

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val profileImageUrl: String) : Parcelable {
    constructor() : this("", "", "")
}