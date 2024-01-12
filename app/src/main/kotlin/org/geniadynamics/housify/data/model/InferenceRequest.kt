package org.geniadynamics.housify.data.model

data class InferenceRequest(
    var user: String,
    var input: String,
    var img_input: String? = null,
    var img_output: String = "",
    var output_description: String = "",
    var output_classification: String = "",
    var request_classification: Double = 0.0,
    var is_public: Boolean = false
)