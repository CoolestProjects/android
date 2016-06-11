package com.mttnow.coolestprojects.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mttnow.coolestprojects.models.Speaker
import com.mttnow.coolestprojects.models.SponsorTier
import com.mttnow.coolestprojects.models.Summit
import rx.Observable
import java.lang.reflect.Type


private val KEY_SPEAKERS = "speakers"
private val KEY_SUMMITS = "summits"
private val KEY_SPONSORS = "sponsors"

class CachedCoolestProjectsService(private val coolestProjectsService: CoolestProjectsService,
                                   private val jsonCache: JsonCache,
                                   private val gson: Gson) : CoolestProjectsService {

    override fun speakers(): Observable<List<Speaker>> {
        return fromCacheObservable<List<Speaker>>(KEY_SPEAKERS, object : TypeToken<List<Speaker>>() {}.type)
                .concatWith(toCacheObservable(KEY_SPEAKERS, coolestProjectsService.speakers()))

    }

    override fun summits(): Observable<List<Summit>> {
        return fromCacheObservable<List<Summit>>(KEY_SUMMITS, object : TypeToken<List<Speaker>>() {}.type)
                .concatWith(toCacheObservable(KEY_SPEAKERS, coolestProjectsService.summits()))
    }

    override fun sponsors(): Observable<List<SponsorTier>> {
        return fromCacheObservable<List<SponsorTier>>(KEY_SPONSORS, object : TypeToken<List<Speaker>>() {}.type)
                .concatWith(toCacheObservable(KEY_SPEAKERS, coolestProjectsService.sponsors()))
    }

    private fun <T> fromCacheObservable(key: String, type: Type): Observable<T> {
        return Observable.create { subscriber ->
            val json = jsonCache.get(key)
            if (json != null) {
                val data: T = gson.fromJson(json, type)
                subscriber.onNext(data)
            }
            subscriber.onCompleted()
        }
    }

    private fun<T> toCacheObservable(key: String, observable: Observable<T>): Observable<T> {
        return observable.doOnNext { jsonCache.put(key, gson.toJson(it)) }
                .onErrorResumeNext { Observable.empty() }
    }

}
