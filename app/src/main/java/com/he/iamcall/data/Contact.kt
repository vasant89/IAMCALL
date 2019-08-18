package com.he.iamcall.data


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Contact @JvmOverloads constructor(
        @SerializedName("FID")
        @Expose
        var fId: String?="",
        @SerializedName("PART")
        @Expose
        var part: String? = "",
        @SerializedName("NAME")
        @Expose
        var name: String? = "",
        @SerializedName("ALPHA")
        @Expose
        var alpha: String? = "",
        @SerializedName("FIRST")
        @Expose
        var first: String? = "",
        @SerializedName("MIDDLE")
        @Expose
        var middle: String? = "",
        @SerializedName("LAST")
        @Expose
        var last: String? = "",
        @SerializedName("FORTH")
        @Expose
        var forth: String? = "",
        @SerializedName("MOBILE")
        @Expose
        var mobile: String? = ""
):Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}