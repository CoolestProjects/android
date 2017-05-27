package com.mttnow.coolestprojects.models

data class BeaconRegion(val regionId: String,
                        val beacons: List<BeaconDevice>)

data class BeaconDevice(val name: String,
                        val uuid: String,
                        val major: Int,
                        val minor: Int?)

data class BeaconRegionMessage(val regionId: String,
                               val version: String,
                               val title: String,
                               val message: String,
                               val url: String)