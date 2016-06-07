package com.mttnow.coolestprojects.models

data class SponsorTier(val tier: String = "",
                       val sponsorList: List<Sponsor> = listOf())

data class Sponsor(val name: String = "",
                   val description: String = "",
                   val logoUrl: String? = null)