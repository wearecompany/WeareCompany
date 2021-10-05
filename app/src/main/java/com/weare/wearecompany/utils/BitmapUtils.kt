package com.weare.wearecompany.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapUtils {


    fun resizeAndCompressUserProfileImage(context: Context, filePath: String?, fileName: String?): File? {
        val file = File(context.cacheDir, fileName)
        try {
            val bitMap = BitmapFactory.decodeFile(filePath)
            // we create an scaled bitmap so it reduces the image, not just trim it
            val dstBmp: Bitmap
            dstBmp = if (bitMap.width >= bitMap.height) {
                Bitmap.createBitmap(
                    bitMap,
                    bitMap.width / 2 - bitMap.height / 2,
                    0,
                    bitMap.height,
                    bitMap.height
                )
            } else {
                Bitmap.createBitmap(
                    bitMap,
                    0,
                    bitMap.height / 2 - bitMap.width / 2,
                    bitMap.height,
                    bitMap.height
                )
            }
            val resize = Bitmap.createScaledBitmap(dstBmp, 640, 640, false)
            val outStream = ByteArrayOutputStream()
            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
            val rotatedBitmap = rotateImage(resize, filePath)
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 70, outStream)
            // we save the file, at least until we have made use of it
            file.createNewFile()
            //write the bytes in file
            val fo = FileOutputStream(file)
            fo.write(outStream.toByteArray())
            // remember close de FileOutput
            fo.close()
        } catch (e: Exception) {
        }
        return file
    }

    fun glideToBitmap(
        context: Context,
        bitMap: Bitmap,
        fileName: String?
    ): File {
        val file = File(context.cacheDir, fileName)
        try {
            // we create an scaled bitmap so it reduces the image, not just trim it
            file.createNewFile()

            val resize = Bitmap.createScaledBitmap(bitMap, bitMap.width, bitMap.height, false)
            val outStream = ByteArrayOutputStream()

            resize.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
            //val rotatedBitmap: Bitmap = rotateImage(resize, filePath?)

            // we save the file, at least until we have made use of it

            //write the bytes in file
            val fo = FileOutputStream(file)
            // remember close de FileOutput
            fo.write(outStream.toByteArray())
            fo.close()
        } catch (e: Exception) {
        }
        return file
    }

    @JvmStatic
    @BindingAdapter("hotpickImage")
    fun setHotpickListImage(imageView: ImageView, url: String) {
        val context = imageView.context

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(24))
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }

    fun rotateImage(bitmap: Bitmap, path: String?): Bitmap {
        var rotate = 0
        val exif: ExifInterface
        val matrix = Matrix()
        try {
            exif = ExifInterface(path!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            }
            matrix.postRotate(rotate.toFloat())
        } catch (e: IOException) {
        }
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, true
        )
    }

    fun getCircleImage(context: Context?, image: Drawable): Bitmap? {
        val bitmap = (image as BitmapDrawable).bitmap
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(
            (bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat(), (
                    bitmap.width / 2).toFloat(), paint
        )
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output
    }

    fun resizeBitmap(context: Context, src: Bitmap?, fileName: String?): File {
        val file = File(context.cacheDir, fileName)
        try {
            val resizedBitmap = src
            val outStream = ByteArrayOutputStream()
            if (resizedBitmap != null) {
                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            }
            file.createNewFile()
            val fo = FileOutputStream(file)
            fo.write(outStream.toByteArray())
            // remember close de FileOutput
            fo.close()
        } catch (e:Exception) {
        }
        return file

    }
}