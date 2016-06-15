package com.mttnow.coolestprojects.app.dagger

import android.content.Context
import com.google.gson.Gson
import com.mttnow.coolestprojects.BuildConfig
import com.mttnow.coolestprojects.network.CoolestProjectsService
import com.mttnow.coolestprojects.rx.RxSchedulers
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Qualifier

@AppScope
@Module(includes = arrayOf(AppModule::class, GsonModule::class))
class NetworkModule {


    /** Dangerous interceptor that rewrites the server's cache-control header.  */
    private val REWRITE_CACHE_CONTROL_INTERCEPTOR = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        originalResponse.newBuilder().header("Cache-Control", "max-age=3600").build()
    }

    @Provides
    @AppScope
    fun okhttp(cache: Cache, httpLoggingInterceptor: Interceptor)
            = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .build()

    @Provides
    @AppScope
    fun loggingIntercetor(): Interceptor {
        if (!BuildConfig.DEBUG) {
            return Interceptor { it.proceed(it.request()) }
        }
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }


    @Provides
    @AppScope
    fun cache(cacheDir: File) = Cache(cacheDir, 10 * 1024 * 1024)

    @Provides
    @AppScope
    fun cacheDir(context: Context): File {
        val dir = File("${context.cacheDir.absolutePath}${File.separator}http_cache")
        dir.mkdirs()
        return dir
    }

    @Provides
    @AppScope
    fun apiBaseService(retrofit: Retrofit) = retrofit.create(CoolestProjectsService::class.java)

    @Provides
    @AppScope
    fun converterFactory(gson: Gson) = GsonConverterFactory.create(gson)

    @Provides
    @AppScope
    fun callAdapter(rxSchedulers: RxSchedulers) = RxJavaCallAdapterFactory.create()

    @Provides
    @AppScope
    fun retrofit(@BaseServiceUrl baseServiceUrl: String, okHttpClient: OkHttpClient,
                 converterFactory: GsonConverterFactory, rxJavaCallAdapterFactory: RxJavaCallAdapterFactory): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
                .baseUrl(baseServiceUrl)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(converterFactory)
                .build()
    }

}