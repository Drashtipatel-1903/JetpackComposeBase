package com.example.jetpackcomposebase.ui.login.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class LoginResponse {
    @JsonProperty("token")
    var token: String? = null
}