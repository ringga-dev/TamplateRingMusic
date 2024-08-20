package ngga.ring.ringmusic.base

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun Context.imageBlurDrawble(image: Int, BLUR_RADIUS: Float = 16F): Bitmap {

    // Dekode sumber daya gambar ke dalam objek Bitmap
    val originalBitmap = BitmapFactory.decodeResource(this.resources, image)

    // Buat salinan bitmap untuk menghindari perubahan pada bitmap asli
    val mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

    // Buat objek Canvas untuk menggambar pada mutableBitmap
    val canvas = Canvas(mutableBitmap)

    try {
        // Buat objek paint dengan BlurMaskFilter
        val paint = Paint().apply {
            maskFilter = BlurMaskFilter(BLUR_RADIUS, BlurMaskFilter.Blur.NORMAL)
        }

        // Gambar salinan bitmap dengan filter pengaburan
        canvas.drawBitmap(mutableBitmap, 0f, 0f, paint)

        // Kembalikan mutableBitmap yang sudah diolah
        return mutableBitmap
    } finally {
        // Pastikan untuk membebaskan sumber daya asli
        originalBitmap.recycle()
    }
}

fun Context.uriToBitmap(uri: Uri): Bitmap? {
    return try {
        val contentResolver: ContentResolver = contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    }
}

fun Context.saveImageToExternalStorage(bitmap: Bitmap): Uri? {
    val filename = "${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    var imageUri: Uri? = null
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
            imageUri = Uri.fromFile(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return imageUri
}


fun Context.imageBlurDrawable(drawable: Drawable, blurRadius: Float = 16F): Bitmap {
    // Mendapatkan ukuran gambar dari Drawable
    val width = drawable.intrinsicWidth
    val height = drawable.intrinsicHeight

    // Membuat objek Bitmap untuk gambar
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    // Menggambar Drawable ke dalam Bitmap
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)

    // Memanggil fungsi pengaburan sesuai dengan SDK versi
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        applyBlurUsingRenderScript(this, bitmap, blurRadius)
    } else {
        applyBlurUsingMaskFilter(bitmap, blurRadius)
    }
}

private fun applyBlurUsingMaskFilter(inputBitmap: Bitmap, blurRadius: Float): Bitmap {
    val outputBitmap =
        Bitmap.createBitmap(inputBitmap.width, inputBitmap.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(outputBitmap)

    // Buat objek paint dengan BlurMaskFilter
    val paint = Paint().apply {
        maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
    }

    // Gambar salinan bitmap dengan filter pengaburan
    canvas.drawBitmap(inputBitmap, 0f, 0f, paint)

    return outputBitmap
}

private fun applyBlurUsingRenderScript(
    context: Context,
    inputBitmap: Bitmap,
    blurRadius: Float
): Bitmap {
    val outputBitmap = Bitmap.createBitmap(inputBitmap)

    val rs = RenderScript.create(context)

    // Inisialisasi Allocations
    val inputAllocation = Allocation.createFromBitmap(rs, inputBitmap)
    val outputAllocation = Allocation.createFromBitmap(rs, outputBitmap)

    // Buat objek ScriptIntrinsicBlur
    val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    script.setInput(inputAllocation)

    // Terapkan efek pengaburan
    script.setRadius(blurRadius)
    script.forEach(outputAllocation)

    // Salin hasil kembali ke output bitmap
    outputAllocation.copyTo(outputBitmap)

    // Bebaskan sumber daya RenderScript
    rs.destroy()

    return outputBitmap
}

