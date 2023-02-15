package com.sidukov.kabar.data.settings

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CacheForImage(private val context: Context) {

    fun saveImageToCache(bitmap: Bitmap, stringName: String?): File {
        var cachePath: File? = null
        var fileName = Settings.TEMP_FILE_NAME
        if (!TextUtils.isEmpty(stringName)) {
            fileName = stringName!!
        }
        try {
            cachePath = File(context.applicationContext.cacheDir, Settings.CHILD_DIR)
            cachePath.mkdirs()

            val stream = FileOutputStream("$cachePath/$fileName${Settings.FILE_EXTENSION}")
            bitmap.compress(Bitmap.CompressFormat.PNG, Settings.COMPRESS_QUALITY, stream)
            stream.close()
        } catch (e: IOException) {
            Log.e(Settings.TAG, "saveImgToCache error: $bitmap", e)
        }
        return cachePath!!
    }

    fun saveToCacheAndGetUri(bitmap: Bitmap): Uri? {
        return saveToCacheAndGetUri(bitmap, null)
    }

    fun saveToCacheAndGetUri(bitmap: Bitmap, name: String?): Uri?{
        val file = saveImageToCache(bitmap, name)
        return getImageUri(file, name)
    }

    fun getUriByFileName(name: String): Uri? {
        val context = context.applicationContext
        var fileName = ""
        if (!TextUtils.isEmpty(name)){
            fileName = name
        } else return null

        val imagePath = File(context.cacheDir, Settings.CHILD_DIR)
        val newFile = File (imagePath, "$fileName${Settings.FILE_EXTENSION}")
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", newFile)
    }

    fun getImageUri (fileDir: File, name: String?) : Uri?{
        val context = context.applicationContext
        var fileName = Settings.TEMP_FILE_NAME
        if (!TextUtils.isEmpty(name)){
            fileName = name!!
        }
        val newFile = File (fileDir, "$fileName${Settings.FILE_EXTENSION}")
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", newFile)
    }

    fun getContentType(uri: Uri): String?{
        return context.applicationContext.contentResolver.getType(uri)
    }

}