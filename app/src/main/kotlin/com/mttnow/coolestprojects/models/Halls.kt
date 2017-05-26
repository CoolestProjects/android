package com.mttnow.coolestprojects.models

/**
 * Created by robbie on 26/05/17.
 */
data class Halls (val hall: String = "",
                  val hallId: String = "",
                  val hallPanels: List<HallPanels> = listOf())

data class HallPanels(val description: String = "",
           val endTime: String = "",
           val name: String = "",
           val panelSpeakers: List<PanelSpeakers> = listOf())

data class PanelSpeakers(val name: String = "",
                         val photoUrl: String = "",
                         val title: String = "")
