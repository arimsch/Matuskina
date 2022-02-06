package com.aimatushkina.matuskinalab.room

import android.content.Context
import android.content.SharedPreferences
import com.aimatushkina.matuskinalab.models.ImgModel
import org.json.JSONObject

class DataBasePreferences(var context: Context) {

    private var prefs: SharedPreferences
    private val prefsSetting = context.getSharedPreferences("databaseInfo", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor
    private val editorSettings = prefsSetting.edit()
    private var indexNow = 0

    init {
        prefs = context.getSharedPreferences(getSizeInt().toString(), Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun getSizeInt(): Int {
        return prefsSetting.getInt("size", 0)
    }

    private fun sizeUp() {
        editorSettings.putInt("size", getSizeInt() + 1).apply()
    }

    private fun sizeDown() {
        editorSettings.putInt("size", getSizeInt() - 1).apply()
    }

    private fun updatePrefs(index: Int) {
        if (indexNow != index) {
            indexNow = index
            prefs = context.getSharedPreferences(index.toString(), Context.MODE_PRIVATE)
            editor = prefs.edit()
        }
    }

    fun addJSONObject(_input: ImgModel) {
        sizeUp()
        updatePrefs(getSizeInt()+1)
        setUrl(_input.previewURL)
        setDescr(_input.description)
    }

    fun setDescr(_input: String, _id: Int? = -1) {
        _id?.let {
            if (_id != -1)
                updatePrefs(_id)
            editor.putString("DESCR", _input).apply()
        }
    }

    fun getDescr(_id: Int? = -1): String? {
        _id?.let {
            if (_id != -1)
                updatePrefs(_id)
            return prefs.getString("DESCR", "").toString()
        }
        return null
    }

    fun setUrl(_input: String, _id: Int? = -1) {
        _id?.let {
            if (_id != -1)
                updatePrefs(_id)
            editor.putString("URL", _input).apply()
        }
    }

    fun getURL(_id: Int? = -1): String? {
        _id?.let {
            if (_id != -1)
                updatePrefs(_id)
            return prefs.getString("URL", "").toString()
        }
        return null
    }


}