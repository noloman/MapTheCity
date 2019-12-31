package me.manulorenzo.citymaps.about

import android.util.Log
import me.manulorenzo.citymaps.about.About.Model
import me.manulorenzo.citymaps.about.About.Presenter
import me.manulorenzo.citymaps.city.data.source.Repository
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

class AboutModelImpl(
    private val presenter: Presenter,
    private val repository: Repository
) :
    Model {
    override val aboutInfo: Unit
        get() {
            val aboutInfoJson = aboutInfoFromAssets
            if (aboutInfoJson != null && aboutInfoJson.isNotEmpty()) {
                parseAboutInfo(aboutInfoJson)?.let {
                    presenter.onSuccess(it)
                } ?: run {
                    presenter.onFail()
                }
            }
        }

    private fun parseAboutInfo(aboutInfoJson: String): AboutInfo? {
        try {
            val jsonObject = JSONObject(aboutInfoJson)
            return AboutInfo(
                companyName = jsonObject.getString("companyName"),
                companyAddress = jsonObject.getString("companyAddress"),
                companyCity = jsonObject.getString("city"),
                companyPostal = jsonObject.getString("postalCode"),
                aboutInfo = jsonObject.getString("details")
            )
        } catch (e: JSONException) {
            Log.e(TAG, e.localizedMessage, e)
        }
        return null
    }

    private val aboutInfoFromAssets: String?
        get() {
            return repository.getAbout()
        }

    companion object {
        private val TAG = AboutModelImpl::class.java.simpleName
    }
}