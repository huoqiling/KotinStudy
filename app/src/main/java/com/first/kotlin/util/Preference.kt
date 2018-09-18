package com.first.kotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.first.kotlin.MyApp
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreference封装
 */

class Preference<T>(val name: String?, private val default: T) : ReadWriteProperty<Any?, T> {

    companion object {
        val prefs: SharedPreferences by lazy {
            MyApp.instance.applicationContext.getSharedPreferences("default", Context.MODE_PRIVATE)
        }
    }

    override  fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharePreferences(name!!, default)
    }

    override  fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name!!, value)
    }

    /**
     * 删除全部数据
     */
    @SuppressLint("ApplySharedPref")
    fun clearPreference() {
        prefs.edit().clear().commit()
    }

    /**
     * 根据key删除存储数据
     */
    @SuppressLint("ApplySharedPref")
    fun clearPreference(key: String) {
        prefs.edit().remove(key).commit()
    }

    @SuppressLint("CommitPrefEdits")
    private fun putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSharePreferences(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }
        return res as T
    }
}
