package pro.ply.gerrit.data.api

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class GerritMagicPrefixInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val contentType = response.header("Content-Type", "")!!
        if (contentType.contains("application/json")) {
            val src = response.body?.bytes()
            if (src != null && src.size > MAGIC_PREFIX_LENGTH && hasMagicPrefix(src)) {
                val dst = ByteArray(src.size - MAGIC_PREFIX_LENGTH)
                System.arraycopy(src, MAGIC_PREFIX_LENGTH, dst, 0, dst.size)
                return response.newBuilder()
                    .body(dst.toResponseBody("application/json".toMediaType()))
                    .build()
            }
        }

        return response
    }

    private fun hasMagicPrefix(byteArray: ByteArray): Boolean {
        for (i in MAGIC_PREFIX.indices) {
            if (byteArray[i] != MAGIC_PREFIX[i]) {
                return false
            }
        }
        return true
    }

    companion object {
        private val MAGIC_PREFIX: ByteArray = byteArrayOf(41/* ) */, 93/* ] */, 125/* } */, 39/* ' */, 10/* \n */)

        private val MAGIC_PREFIX_LENGTH = MAGIC_PREFIX.size
    }
}
