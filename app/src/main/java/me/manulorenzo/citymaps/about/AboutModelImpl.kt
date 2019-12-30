package me.manulorenzo.citymaps.about

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import me.manulorenzo.citymaps.about.About.Model
import me.manulorenzo.citymaps.about.About.Presenter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

class AboutModelImpl(private val presenter: Presenter, context: Context) :
    Model {
    private val context: WeakReference<Context?> = WeakReference(context)
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
            if (context.get() != null) {
                try {
                    val manager: AssetManager? = context.get()?.assets
                    val file = manager?.open(FILE_NAME)
                    file?.available()?.let {
                        val formArray = ByteArray(it)
                        with(file) {
                            read(formArray)
                            close()
                        }
                        return String(formArray)
                    }
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