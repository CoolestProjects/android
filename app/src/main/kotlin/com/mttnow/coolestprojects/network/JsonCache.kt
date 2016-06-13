package com.mttnow.coolestprojects.network

import android.support.v4.util.LruCache
import android.util.Log
import java.io.File

/**
 * Hybrid disk/memory cache
 */
class JsonCache(private val fileDir: File) {

    companion object {
        private val CHARSET = Charsets.UTF_8
        private val CACHE_SIZE = 500 * 1024; //500KB
    }

    private val memCache = object : LruCache<String, String>(CACHE_SIZE)  {
        override fun sizeOf(key: String?, value: String?): Int {
            return value?.length ?: 0 * 6;
        }
    }

    @Synchronized
    fun put(key: String, json: String) {
        memCache.put(key, json)

        val file = getFileForKey(key)
        if (file.exists()) {
            file.delete()
        }
        file.writeText(json, CHARSET)
        Log.i("JsonCache", "Write $key to cache")
    }


    @Synchronized
    fun get(key: String): String? {
        if(memCache.get(key) != null) {
            return memCache.get(key)
        }

        val file = getFileForKey(key)
        if (!file.exists()) {
            return null
        }
        Log.i("JsonCache", "Read $key to cache")
        return file.readText(CHARSET)
    }

    private fun getFileForKey(key: String) = File(fileDir, key)
}

