package com.mttnow.coolestprojects.models


data class Hall(val hall: String = "",
                val hallWorkshop1: List<HallWorkshop>? = null,
                val hallWorkshop2: List<HallWorkshop>? = null,
                val hallPanels: List<HallPanel>? = null)

data class HallWorkshop(val name: String = "",
                         val description: String = "",
                         val endTime: String? = null,
                         val startTime: String? = null)

data class HallPanel(val name: String = "",
                         val description: String = "",
                         val endTime: String? = null,
                         val panelSpeakers: List<HallSpeaker>? = null,
                         val startTime: String? = null)

data class HallSpeaker(val name: String = "",
                   val photoUrl: String? = "",
                   val title: String? = "")