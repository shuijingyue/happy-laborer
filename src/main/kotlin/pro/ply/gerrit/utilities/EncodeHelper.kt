package pro.ply.gerrit.utilities

import java.nio.charset.Charset
import java.util.Base64

object EncodeHelper {
    @JvmStatic
    fun base64(message: String) =
        Base64.getEncoder().encode(message.toByteArray()).toString(Charset.defaultCharset())
}
