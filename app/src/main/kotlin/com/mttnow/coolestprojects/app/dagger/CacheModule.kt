package com.mttnow.coolestprojects.app.dagger

import android.content.Context
import com.mttnow.coolestprojects.network.JsonCache
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Qualifier

@Qualifier
private annotation class CacheDir

@AppScope
@Module(includes = arrayOf(AppModule::class))
class CacheModule {

    @AppScope
    @Provides
    fun cache(@CacheDir cacheDir: File) = JsonCache(cacheDir)

    @CacheDir
    @AppScope
    @Provides
    fun cacheDir(context: Context): File {
        return File(context.cacheDir.absolutePath + File.separator + "json_cache")
    }
}