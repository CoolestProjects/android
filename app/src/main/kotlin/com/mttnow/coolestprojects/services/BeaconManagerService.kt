package com.mttnow.coolestprojects.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.estimote.sdk.Beacon
import com.estimote.sdk.BeaconManager
import com.estimote.sdk.Region
import com.mttnow.coolestprojects.app.CoolestProjectsApp
import com.mttnow.coolestprojects.models.BeaconDevice
import com.mttnow.coolestprojects.models.BeaconRegion
import com.mttnow.coolestprojects.network.CoolestProjectsService
import com.mttnow.coolestprojects.screens.home.HomeActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.HashSet

import com.mttnow.coolestprojects.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import com.mttnow.coolestprojects.models.BeaconRegionMessage
import android.R.id.edit
import android.content.SharedPreferences
import android.net.Uri


class BeaconManagerService : IntentService(BeaconManagerService::class.java.simpleName) {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startService(Intent(context, BeaconManagerService::class.java))
        }
    }

    val DUMMY_MESSAGE = BeaconRegionMessage("", "", "", "", "")
    val MESSAGE_NOTIFICATION_ID = 1999
    val TAG: String = "BeaconManagerService"

    @Inject
    lateinit var coolestProjectsService: CoolestProjectsService
    lateinit var beaconManager: BeaconManager
    val beaconRegions: MutableMap<String, MutableSet<BeaconDevice>> = HashMap()
    val beaconMessages: MutableMap<String, BeaconRegionMessage> = HashMap()

    override fun onCreate() {
        super.onCreate()

        DaggerBeaconManagerServiceComponent.builder()
                .appComponent(CoolestProjectsApp.get(this).appComponent)
                .build()
                .inject(this)

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {

            Log.d(TAG, "Bluetooth Low Engery is available")

            beaconManager = BeaconManager(application)

            beaconManager.setMonitoringListener(
                    object : BeaconManager.MonitoringListener {
                        override fun onEnteredRegion(region: Region, list: List<Beacon>) {
                            Log.d(TAG, "Entered region $region")

                            val beaconMessage = beaconMessages[region.identifier]

                            if (beaconMessage != null && !alreadySeen(beaconMessage.regionId)) {

                                val mBuilder = NotificationCompat.Builder(this@BeaconManagerService)
                                        .setSmallIcon(R.drawable.beacon_icon)
                                        .setContentTitle(beaconMessage.title)
                                        .setContentText(beaconMessage.message)

                                val resultIntent = Intent(this@BeaconManagerService, HomeActivity::class.java) // TODO URL link?

                                val stackBuilder = TaskStackBuilder.create(this@BeaconManagerService)

                                stackBuilder.addParentStack(HomeActivity::class.java) // TODO URL link?

                                stackBuilder.addNextIntent(resultIntent)
                                val resultPendingIntent = stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                )
                                mBuilder.setContentIntent(resultPendingIntent)
                                val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                                mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build())

                                rememberMessageId(beaconMessage.regionId)

                            } else {
                                Log.d(TAG, "No message for region $region")
                            }
                        }

                        override fun onExitedRegion(region: Region) {
                            Log.d(TAG, "Exit region $region")
                        }
                    })

            downloadMessages()

            coolestProjectsService.regions()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        beaconManager.connect(
                                object : BeaconManager.ServiceReadyCallback {
                                    override fun onServiceReady() {
                                        startMonitoringRegions(it)
                                    }
                                }
                        )
                    }
        } else {
            Log.d(TAG, "Bluetooth Low Energy is not available")
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        coolestProjectsService.regions().subscribe {
            // TODO Stop monitoring beacons that have moved region or been removed
            // TODO Start monitoring new beacons or beacons that have moved region
        }
    }

    fun downloadMessages() {
        coolestProjectsService.messages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d(TAG, "Messages download - $it")
                    it.forEach { cacheMessage(it) }
                }
    }

    fun getMessages(): List<BeaconRegionMessage> = getRememberedMessageIds().map { beaconMessages[it] ?: DUMMY_MESSAGE }.toList()

    private fun cacheMessage(beaconMessage: BeaconRegionMessage) {
        val oldMessage = beaconMessages[beaconMessage.regionId]
        if (oldMessage == null || oldMessage.version != beaconMessage.version) {
            beaconMessages[beaconMessage.regionId] = beaconMessage
            forgetMessageId(beaconMessage.regionId)
        }
    }

    private fun alreadySeen(messageId: String) = getRememberedMessageIds().contains(messageId)

    private fun getRememberedMessageIds(): Set<String> {
        val sharedPref = getSharedPreferences("CoolestProjectsBeacons", Context.MODE_PRIVATE)
        return sharedPref.getStringSet("remembered_messages", HashSet());
    }

    private fun forgetMessageId(messageId: String) {
        Log.d(TAG, "Forget that ${messageId} has been seen")
        updateRememberedMessageIds({ messageIds -> messageIds.remove(messageId) })
    }

    private fun rememberMessageId(messageId: String) {
        Log.d(TAG, "Remember that ${messageId} has been seen")
        updateRememberedMessageIds({ messageIds -> messageIds.add(messageId) })
    }

    fun updateRememberedMessageIds(modifier: (messageIds: MutableSet<String>) -> Unit) {
        val sharedPref = getSharedPreferences("CoolestProjectsBeacons", Context.MODE_PRIVATE)
        val rememberedMessages:MutableSet<String> = sharedPref.getStringSet("remembered_messages", HashSet());
        modifier(rememberedMessages)
        val editor = sharedPref.edit()
        editor.putStringSet("remembered_messages", rememberedMessages)
        editor.apply()
    }

    private fun startMonitoringRegions(regions: List<BeaconRegion>) {
        Log.d(TAG, "Start monitoring regions")
        regions.forEach { startMonitoringRegion(it) }
    }

    private fun startMonitoringRegion(region: BeaconRegion) {
        Log.d(TAG, "Start monitoring region $region")
        region.beacons.forEach { startMonitoringBeacon(region.regionId, it) }
    }

    private fun startMonitoringBeacon(regionId: String, beacon: BeaconDevice) {
        Log.d(TAG, "Start monitoring beacon $regionId,$beacon")
        beaconManager.startMonitoring(asEstimoteRegion(regionId, beacon))
        var beacons = beaconRegions[regionId]
        if (beacons == null) {
            beacons = HashSet()
            beaconRegions[regionId] = beacons
        }
        beacons.add(beacon)
    }

    private fun stopMonitoring(regionId: String, beacon: BeaconDevice) {
        beaconManager.stopMonitoring(asEstimoteRegion(regionId, beacon))
        beaconRegions[regionId]?.remove(beacon)
        Log.d(TAG, "Stop monitoring beacon $regionId,$beacon")
    }

    private fun asEstimoteRegion(regionId: String, beacon: BeaconDevice) = Region(regionId, UUID.fromString(beacon.uuid), beacon.major, beacon.minor)
}