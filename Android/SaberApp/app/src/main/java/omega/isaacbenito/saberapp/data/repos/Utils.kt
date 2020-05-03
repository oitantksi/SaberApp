package omega.isaacbenito.saberapp.data.repos

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omega.isaacbenito.saberapp.data.AppLocalDataSource
import omega.isaacbenito.saberapp.data.entities.ProfilePicture
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest

class Utils {

    companion object {

        suspend fun saveRemoteImage(
            userId: Long,
            bitmap: Bitmap,
            filesDir: File,
            dataSource: AppLocalDataSource
        ) {
            val imgUri = withContext(Dispatchers.IO) {
                //Convert bitmap to byte array
                val outStream = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream)
                val bitmapdata = outStream.toByteArray()

                outStream.close()

                val digest = MessageDigest.getInstance("MD5")
                val encodedHash = digest.digest(bitmapdata)
                val sb = StringBuilder()
                for (b in encodedHash) {
                    sb.append(String.format("%02x", b))
                }
                val md5 = sb.toString()

                val imgFile = File(filesDir, "$md5.jpg")

                val fileOutStream = FileOutputStream(imgFile)

                //write the bytes in file
                val fos = FileOutputStream(imgFile)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()

                return@withContext Uri.fromFile(imgFile)
            }

            dataSource.savePicture(ProfilePicture(userId, imgUri))
        }

    }
}
