package com.mttnow.coolestprojects.app.dagger

import android.content.Context
import com.google.gson.Gson
import com.mttnow.coolestprojects.network.CachedCoolestProjectsService
import com.mttnow.coolestprojects.network.CoolestProjectsService
import com.mttnow.coolestprojects.network.JsonCache
import com.mttnow.coolestprojects.rx.RxSchedulers
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Qualifier

@Qualifier
annotation class BaseNetworkService

@AppScope
@Module(includes = arrayOf(AppModule::class, GsonModule::class, CacheModule::class))
class NetworkModule {

    @Provides
    @AppScope
    fun okhttp(cache: Cache) = OkHttpClient.Builder().cache(cache).build()

    @Provides
    @AppScope
    fun cache(cacheDir: File) = Cache(cacheDir, 10 * 1024 * 1024)

    @Provides
    @AppScope
    fun cacheDir(context: Context): File {
        val dir = File("${context.cacheDir.absolutePath}${File.separator}http_cache")
        dir.mkdirs();
        return dir;
    }

    @Provides
    @AppScope
    @BaseNetworkService
    fun apiBaseService(retrofit: Retrofit) = retrofit.create(CoolestProjectsService::class.java)

    @Provides
    @AppScope
    fun converterFactory(gson: Gson) = GsonConverterFactory.create(gson)

    @Provides
    @AppScope
    fun callAdapter(rxSchedulers: RxSchedulers) = RxJavaCallAdapterFactory.createWithScheduler(rxSchedulers.network())

    @Provides
    @AppScope
    fun retrofit(@BaseServiceUrl baseServiceUrl: String, okHttpClient: OkHttpClient,
                 converterFactory: GsonConverterFactory,
                 rxJavaCallAdapterFactory: RxJavaCallAdapterFactory): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
                .baseUrl(baseServiceUrl)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    @AppScope
    fun apiService(@BaseNetworkService coolestProjectsService: CoolestProjectsService,
                   jsonCache: JsonCache, gson: Gson): CoolestProjectsService
            = CachedCoolestProjectsService(coolestProjectsService, jsonCache, gson)
}