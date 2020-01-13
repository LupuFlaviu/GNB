package com.example.gnb.api.model

import retrofit2.Response

/**
 * Helper class used to wrap an API response with all needed information like code, body, error
 */
sealed class ApiResponse<T> {
    companion object {

        private const val EMPTY_RESPONSE = 204

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == EMPTY_RESPONSE) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                ApiErrorResponse(
                    response.code(), response.errorBody()?.string()
                        ?: response.message()
                )
            }
        }

        fun <T> create(errorCode: Int, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(errorCode, error.message ?: "Unknown Error!")
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()
data class ApiErrorResponse<T>(val errorCode: Int, val errorMessage: String) : ApiResponse<T>()
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()