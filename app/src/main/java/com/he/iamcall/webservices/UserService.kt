package com.he.iamcall.webservices

import com.he.iamcall.data.Contact
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface UserService {

    @GET("index.php")
    fun getContacts(): Deferred<List<Contact>>

}