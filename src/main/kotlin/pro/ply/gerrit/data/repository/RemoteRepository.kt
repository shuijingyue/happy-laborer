package pro.ply.gerrit.data.repository

import pro.ply.gerrit.data.api.RequestClient

abstract class RemoteRepository<Service> {
    protected val service: Service by lazy { RequestClient.getInstance().create(serviceClass()) }

    protected abstract fun serviceClass(): Class<Service>
}
