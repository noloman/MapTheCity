package me.manulorenzo.mapthecity.ui.about

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 * DTO representing aboutInfo object
 */

data class AboutInfo(
    val companyName: String,
    val companyAddress: String,
    val companyPostal: String,
    val companyCity: String,
    val aboutInfo: String
)