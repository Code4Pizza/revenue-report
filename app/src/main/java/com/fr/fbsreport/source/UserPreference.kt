package com.fr.fbsreport.source

import com.fr.fbsreport.App
import com.fr.fbsreport.model.TokenModel
import com.fr.fbsreport.model.User
import com.fr.fbsreport.utils.PreferenceUtils
import com.google.gson.Gson
import javax.inject.Inject

class UserPreference(app: App) {

    private val PREF_TOKEN_MODEL = "PREF_TOKEN_MODEL"
    private val PREF_USER_INFO = "PREF_USER_INFO"

    private var preferenceUtils: PreferenceUtils = PreferenceUtils(app)

    fun signOut() {
        preferenceUtils.removeKey(PREF_TOKEN_MODEL)
    }

    fun storeTokenModel(tokenModel: TokenModel) {
        preferenceUtils.putString(PREF_TOKEN_MODEL, Gson().toJson(tokenModel))
    }

    fun storeUserInfo(user: User) {
        preferenceUtils.putString(PREF_USER_INFO, Gson().toJson(user))
    }

    fun getTokenModel(): TokenModel? {
        return try {
            val tokenModel = preferenceUtils.getString(PREF_TOKEN_MODEL, null)
            Gson().fromJson(tokenModel, TokenModel::class.java)
        } catch (e: ClassCastException) {
            null
        }
    }

    fun getUserInfo(): User? {
        return try {
            val userJson = preferenceUtils.getString(PREF_USER_INFO, null)
            Gson().fromJson(userJson, User::class.java)
        } catch (e: ClassCastException) {
            null
        }
    }

    fun isSignedIn(): Boolean {
        return preferenceUtils.getString(PREF_TOKEN_MODEL, null) != null
    }
}