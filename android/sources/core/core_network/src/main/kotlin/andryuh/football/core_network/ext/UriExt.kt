package andryuh.football.core_network.ext

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Uri.toRequestBody(context: Context): RequestBody {
  return context.contentResolver.openInputStream(this).use { inputStream ->
    val bytes = inputStream?.readBytes()!!

    bytes.toRequestBody("image/*".toMediaTypeOrNull())
  }
}
