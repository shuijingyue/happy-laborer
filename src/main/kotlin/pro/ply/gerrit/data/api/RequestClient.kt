package pro.ply.gerrit.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestClient private constructor() {

    companion object {
        @Volatile
        private var client: Retrofit? = null

        fun getInstance(): Retrofit {
            if (client == null) {

                synchronized(RequestClient::class.java) {
                    if (client == null) {
                        val loggingInterceptor = HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }

                        val okHttpClient =  OkHttpClient.Builder()
                            .addInterceptor(AuthorizationInterceptor())
                            .addInterceptor(loggingInterceptor)
                            .addInterceptor(GerritMagicPrefixInterceptor())
                            .build()

                        // http://gerrit.pt.mioffice.cn/a/changes/?q=after:2021-10-01+status:merged+project:platform/packages/apps/PersonalAssistant+branch:widget-alpha
                        client = Retrofit.Builder()
                            .baseUrl("http://gerrit.pt.mioffice.cn/a/")
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }
                }
            }

            return client!!
        }
    }
}
