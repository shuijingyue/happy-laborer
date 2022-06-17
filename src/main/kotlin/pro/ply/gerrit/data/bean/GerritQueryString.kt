package pro.ply.gerrit.data.bean

class GerritQueryString {
    private val queryStrings: MutableMap<String, String> = mutableMapOf()

    fun put(key: String, value: String): GerritQueryString {
        queryStrings[key] = value
        return this
    }

    fun value(): String {
        val builder = StringBuilder()

        for (entry in queryStrings.entries) {
            builder.append(entry.key).append(':').append(entry.value).append("+")
        }

        return builder.substring(0, builder.length - 1)
    }
}
