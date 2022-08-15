package com.app.babydogecloudminingapp.model

data class User(val id: String, val email: String, val fullName: String) {

    constructor(id: String, email: String, fullName: String, walletAddress: String) : this(
        id,
        email,
        fullName
    ) {

    }

}