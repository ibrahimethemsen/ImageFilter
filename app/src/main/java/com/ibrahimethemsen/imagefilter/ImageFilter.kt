package com.ibrahimethemsen.imagefilter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.S)
class ImageFilter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private val bitmap = BitmapFactory.decodeResource(resources, R.drawable.wolf)
    private val colorMatrix = ColorMatrix()
    private val paint = Paint().apply {
        colorFilter = ColorMatrixColorFilter(colorMatrix)
    }
    private lateinit var blurEffect: RenderEffect
    private val radius = 16f
    private val path = Path()

    init {
        setBlurEffect()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val scaledBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        path.addRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            radius,
            radius,
            Path.Direction.CW
        )

        canvas?.apply {
            clipPath(path)
            drawBitmap(scaledBitmap, 0f, 0f, paint)
        }
    }

    fun setSaturation(saturation: Boolean) {
        if (saturation) colorMatrix.setSaturation(0f) else colorMatrix.setSaturation(1f)
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        invalidate()
    }

    fun setBlur(blur: Boolean) {
        val renderEffect = if (blur) setBlurEffect(5f) else setBlurEffect(1f)
        this.setRenderEffect(renderEffect)
        invalidate()
    }

    private fun setBlurEffect(blur: Float = 1f): RenderEffect {
        blurEffect = RenderEffect.createBlurEffect(blur, blur, Shader.TileMode.CLAMP)
        return blurEffect
    }
}