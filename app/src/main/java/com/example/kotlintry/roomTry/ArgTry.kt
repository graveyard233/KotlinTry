package com.example.kotlintry.roomTry

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
sealed class ArgTry : Parcelable
@Parcelize
data class ArgOwner(val owner: Owner):ArgTry(), Parcelable
@Parcelize
data class ArgPerson(val person: Person):ArgTry(), Parcelable


