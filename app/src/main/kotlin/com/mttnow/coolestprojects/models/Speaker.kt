package com.mttnow.coolestprojects.models

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class Speaker(val name: String = "",
                   val description: String = "",
                   val photoUrl: String? = null,
                   val summit: String = "",
                   val summitTimes: SummitTime? = null)

data class SummitTime(@SerializedName("string") val formattedTime: String = "", val timestamp: Int = 0) {

  fun dateTime() = DateTime(timestamp)

}