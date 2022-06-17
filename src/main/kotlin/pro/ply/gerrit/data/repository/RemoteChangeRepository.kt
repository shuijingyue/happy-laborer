package pro.ply.gerrit.data.repository

import pro.ply.gerrit.data.api.GerritService
import pro.ply.gerrit.data.bean.ChangeInfo
import pro.ply.gerrit.data.bean.GerritQueryString
import java.net.SocketException

object RemoteChangeRepository: RemoteRepository<GerritService>() {
    suspend fun queryChanges(queryString: GerritQueryString, start: Int, limit: Int): List<ChangeInfo> {
        return try {
            service.queryChanges(queryString.value(), start, limit)
        } catch (e: SocketException) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun serviceClass(): Class<GerritService> {
        return GerritService::class.java
    }
}
