package me.manulorenzo.citymaps.about

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.annotation.NonNull
import me.manulorenzo.citymaps.about.About.Model
import me.manulorenzo.citymaps.about.About.Presenter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

class AboutModelImpl(@param:NonNull private val presenter: Presenter, @NonNull context: Context?) :
    Model {
    private val context: WeakReference<Context?> = WeakReference(context)
    override val aboutInfo: Unit
        get() {
            val aboutInfoJson = aboutInfoFromAssets
            if (aboutInfoJson != null && aboutInfoJson.isNotEmpty()) {
                val aboutInfo = parseAboutInfo(aboutInfoJson)
                if (aboutInfo != null) {
                    presenter.onSuccess(aboutInfo)
                    return
                }
            }
            presenter.onFail()
        }

    private fun parseAboutInfo(aboutInfoJson: String): AboutInfo? {
        var aboutInfo: AboutInfo? = null
        try {
            val jsonObject = JSONObject(aboutInfoJson)
            aboutInfo = AboutInfo()
            aboutInfo.companyName = jsonObject.getString("companyName")
            aboutInfo.companyAddress = jsonObject.getString("companyAddress")
            aboutInfo.companyCity = jsonObject.getString("city")
            aboutInfo.companyPostal = jsonObject.getString("postalCode")
            aboutInfo.aboutInfo = jsonObject.getString("details")
        } catch (e: JSONException) {
            Log.e(TAG, e.localizedMessage, e)
        }
        return aboutInfo
    }

    private val aboutInfoFromAssets: String?
        get() {
            if (context.get() != null) {
                try {
                    val manager: AssetManager = context.get()!!.assets
                    val file = manager.open(FILE_NAME)
                    val formArray = ByteArray(file.available())
                    file.read(formArray)
                    file.close()
                    return String(formArray)
                } catch (ex: IOException) {
                    Log.e(TAG, ex.localizedMessage, ex)
                }
            }
            return null
        }

    companion object {
        private val TAG = AboutModelImpl::class.java.simpleName
        private const val FILE_NAME = "aboutInfo.json"
    }

}