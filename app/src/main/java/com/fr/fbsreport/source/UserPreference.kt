package com.fr.fbsreport.source

import com.fr.fbsreport.model.TokenModel
import com.fr.fbsreport.utils.PreferenceUtils
import com.google.gson.Gson

class UserPreference private constructor() {

    private val PREF_TOKEN_MODEL = "PREF_TOKEN_MODEL"

    private object LazyHolder {
        val INSTANCE = UserPreference()
    }

    companion object {
        val instance: UserPreference by lazy {
            LazyHolder.INSTANCE
        }
    }

    private var preferenceUtils: PreferenceUtils = PreferenceUtils.instance

    fun signOut() {
        preferenceUtils.removeKey(PREF_TOKEN_MODEL)
    }

    fun storeTokenModel(tokenModel: TokenModel) {
        preferenceUtils.putString(PREF_TOKEN_MODEL, Gson().toJson(tokenModel))
    }

    fun getTokenModel(): TokenModel? {
        return try {
            val userJson = preferenceUtils.getString(PREF_TOKEN_MODEL, null)
            Gson().fromJson(userJson, TokenModel::class.java)
        } catch (e: ClassCastException) {
            null
        }

    }

    fun isSignedIn(): Boolean {
        return preferenceUtils.getString(PREF_TOKEN_MODEL, null) != null
    }
}