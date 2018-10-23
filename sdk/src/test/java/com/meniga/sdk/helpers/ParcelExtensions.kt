@file:JvmName("ParcelExtensions")

package com.meniga.sdk.helpers

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

fun <R : Parcelable> R.testParcel(): R? {
    val bytes = marshallParcelable(this)
    return unmarshallParcelable(bytes)
}

fun <R : Parcelable> marshallParcelable(parcelable: R): ByteArray {
    val bundle = Bundle().apply { putParcelable("key", parcelable) }
    return marshall(bundle)
}

private fun marshall(bundle: Bundle): ByteArray =
        Parcel.obtain().use {
            it.writeBundle(bundle)
            it.marshall()
        }

private fun <R : Parcelable> unmarshallParcelable(bytes: ByteArray): R? = unmarshall(bytes)
        .readBundle()
        ?.run {
            classLoader = classLoader
            getParcelable("key")
        }

fun unmarshall(bytes: ByteArray): Parcel =
        Parcel.obtain().apply {
            unmarshall(bytes, 0, bytes.size)
            setDataPosition(0)
        }

private fun <T> Parcel.use(block: (Parcel) -> T): T =
        try {
            block(this)
        } finally {
            this.recycle()
        }
