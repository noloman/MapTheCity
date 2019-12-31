package me.manulorenzo.citymaps.about

import android.os.Handler
import me.manulorenzo.citymaps.about.About.Presenter
import me.manulorenzo.citymaps.about.About.View
import me.manulorenzo.citymaps.city.data.source.Repository
import java.lang.ref.WeakReference

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

class AboutPresenterImpl(view: View, repository: Repository) :
    Presenter {
    private val aboutView: WeakReference<View> = WeakReference(view)
    private val aboutModel: AboutModelImpl = AboutModelImpl(this, repository)
    override val aboutInfo: Unit
        get() {
            val aboutViewImpl = aboutView.get()
            aboutViewImpl?.showProgress()
            Handler().post { aboutModel.aboutInfo }
        }

    override fun onSuccess(aboutInfo: AboutInfo?) {
        val aboutViewImpl = aboutView.get()
        if (aboutViewImpl != null) {
            aboutViewImpl.hideProgress()
            aboutViewImpl.setCompanyName(aboutInfo!!.companyName)
            aboutViewImpl.setCompanyAddress(aboutInfo.companyAddress)
            aboutViewImpl.setCompanyPostalCode(aboutInfo.companyPostal)
            aboutViewImpl.setCompanyCity(aboutInfo.companyCity)
            aboutViewImpl.setAboutInfo(aboutInfo.aboutInfo)
        }
    }

    override fun onFail() {
        val aboutViewImpl = aboutView.get()
        if (aboutViewImpl != null) {
            aboutViewImpl.hideProgress()
            aboutViewImpl.showError()
        }
    }
}