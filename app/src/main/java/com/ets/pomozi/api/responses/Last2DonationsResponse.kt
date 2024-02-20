package com.ets.pomozi.api.responses

import com.ets.pomozi.models.DonationModel

data class Last2DonationsResponse(
    val first: DonationModel?,
    val second: DonationModel?,
)
