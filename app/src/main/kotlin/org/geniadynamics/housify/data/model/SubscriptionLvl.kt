package org.geniadynamics.housify.data.model

data class SubscriptionLvl(
    val api_key_limit: Int,
    val description: String,
    val its: Int,
    val price: Int,
    val requests_hour: Int,
    val storage_limit: Int,
    val upload_size_limit: Int,
    val watermark: Boolean
)