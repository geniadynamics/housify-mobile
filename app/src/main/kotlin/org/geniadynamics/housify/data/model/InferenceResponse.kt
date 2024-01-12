package org.geniadynamics.housify.data.model

data class InferenceResponse(
    var input: String,
    var img_input: String,
    var img_output: String,
    var output_description: String,
    var output_classification: String,
    var request_classification: Int,
    var is_public: Boolean
)