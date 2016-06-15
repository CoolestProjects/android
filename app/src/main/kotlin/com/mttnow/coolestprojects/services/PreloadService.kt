package com.mttnow.coolestprojects.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.mttnow.coolestprojects.app.CoolestProjectsApp
import com.mttnow.coolestprojects.network.CoolestProjectsService
import rx.functions.Action1
import javax.inject.Inject

private val DATE_KEY = "date_key"
private val PREFS_FILE = "projects"

class PreloadService : IntentService(PreloadService::class.java.simpleName) {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startService(Intent(context, PreloadService::class.java))
        }
    }

    @Inject
    lateinit var coolestProjectsService: CoolestProjectsService
    lateinit var sharedPreferences: SharedPreferences

    private val ERROR_LOG_FUNCTION = Action1<kotlin.Throwable> { throwable : Throwable ->
        Log.w("PreloadService", "error", throwable)
    }

    private val EMPTY_FUNCTION = Action1<Any> { Log.w("PreloadService", "success $it") }

    override fun onCreate() {
        super.onCreate()
        DaggerPreloadServiceComponent.builder()
                .appComponent(CoolestProjectsApp.get(this).appComponent)
                .build().inject(this)
    }

    override fun onHandleIntent(intent: Intent) {
        return

//        sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
//        val lastSyncMillis = sharedPreferences.getLong(DATE_KEY, 0)
//        val lastSync = DateTime(lastSyncMillis).plusHours(1)
//        if (lastSync.isAfterNow) {
//            Log.i("PreloadService", "Synced an hour ago")
//            return
//        }
//        Log.i("PreloadService", "Syncing")
//        sharedPreferences.edit().putLong(DATE_KEY, DateTime.now().millis).commit()
//
//        coolestProjectsService.speakers().subscribe(EMPTY_FUNCTION, ERROR_LOG_FUNCTION)
//        coolestProjectsService.sponsors().subscribe(EMPTY_FUNCTION, ERROR_LOG_FUNCTION)
//        coolestProjectsService.summaries("").subscribe(EMPTY_FUNCTION, ERROR_LOG_FUNCTION)
//        coolestProjectsService.summits().subscribe(EMPTY_FUNCTION, ERROR_LOG_FUNCTION)
//        Log.i("PreloadService", "Done Syncing")
    }
}