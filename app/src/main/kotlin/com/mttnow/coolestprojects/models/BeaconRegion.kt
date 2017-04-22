package com.mttnow.coolestprojects.models

data class BeaconRegion(val regionId: String,
                        val beacons: List<BeaconDevice>)

data class BeaconDevice(val name: String,
                        val uuid: String,
                        val major: Int,
                        val minor: Int?)