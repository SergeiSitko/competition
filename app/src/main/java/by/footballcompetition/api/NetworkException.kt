package by.footballcompetition.api

import retrofit2.HttpException

data class NetworkException(
      val reason: String,
      val code: Int
) : Exception(reason)

fun Exception.form() = when (this) {
    is HttpException -> {
        when (code()) {
            400 -> NetworkException("Bad request", code())
            403 -> NetworkException("The resource is only available to clients with a paid subscription.", code())
            404 -> NetworkException("Not found", code())
            429 -> NetworkException("To many requests", code())
            500 -> NetworkException("Server failed", code())
            else -> Exception(this.message)
        }
    }
    else -> Exception(message)
}


