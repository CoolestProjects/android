package com.mttnow.coolestprojects.models


data class Summit(val summit: String = "",
                  val speaker: List<SummitSpeaker> = listOf())

data class SummitSpeaker(val name: String = "",
                         val description: String = "",
                         val photoUrl: String? = null,
                         val summitEndTime: String? = null,
                         val talkTitle: String? = null,
                         val summitTimes: String? = null,
                         val summitStartTime: String? = null)