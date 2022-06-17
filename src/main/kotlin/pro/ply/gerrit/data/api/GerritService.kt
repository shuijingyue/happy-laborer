package pro.ply.gerrit.data.api

import pro.ply.gerrit.data.bean.ChangeInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface GerritService {
    @GET("changes/")
    suspend fun queryChanges(
        @Query("q", encoded = true) query: String,
        @Query("start", encoded = true) start: Int,
        @Query("n", encoded = true) n: Int
    ): List<ChangeInfo>
}
