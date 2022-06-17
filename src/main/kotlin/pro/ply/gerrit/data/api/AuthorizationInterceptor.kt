package pro.ply.gerrit.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "Basic ${authorization()}")
            .build()

        return chain.proceed(request)
    }

    private fun authorization(): String {
        return AuthorizationHelper.passwordAuthorization()
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
    }
}
