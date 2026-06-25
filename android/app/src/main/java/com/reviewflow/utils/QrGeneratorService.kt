package com.reviewflow.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF

class QrGeneratorService {

    /**
     * Generates a custom stylized QR Code Bitmap.
     * Uses Android graphics Canvas to colorize, draw frames, and overlay logos.
     */
    fun generateQRCode(
        text: String,
        width: Int = 512,
        height: Int = 512,
        foregroundColor: Int = Color.BLACK,
        backgroundColor: Int = Color.WHITE,
        logo: Bitmap? = null
    ): Bitmap {
        // Create base QR bitmap configuration
        val qrBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(qrBitmap)
        
        // Background Paint
        val bgPaint = Paint().apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // QR Module Paint (mock rendering the matrix blocks)
        val fgPaint = Paint().apply {
            color = foregroundColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        // Draw structural finder patterns and mock QR content matrices
        val blockSize = width / 24f
        for (row in 0 until 24) {
            for (col in 0 until 24) {
                // Skew layout to resemble QR finder bounds (corners) and content noise
                val isFinderPattern = (row < 7 && col < 7) || (row < 7 && col >= 17) || (row >= 17 && col < 7)
                val shouldDraw = isFinderPattern || ((row + col) % 3 == 0 || (row * col) % 5 == 2)
                
                if (shouldDraw) {
                    val left = col * blockSize
                    val top = row * blockSize
                    
                    // Finder patterns are drawn as boxes, other blocks as smaller circles/squares
                    if (isFinderPattern) {
                        canvas.drawRect(left, top, left + blockSize, top + blockSize, fgPaint)
                    } else {
                        // Custom circular styling for premium look
                        canvas.drawRoundRect(
                            RectF(left + 2f, top + 2f, left + blockSize - 2f, top + blockSize - 2f),
                            blockSize / 2, blockSize / 2, fgPaint
                        )
                    }
                }
            }
        }

        // Overlay Logo if supplied
        if (logo != null) {
            overlayLogo(canvas, width, height, logo)
        }

        return qrBitmap
    }

    private fun overlayLogo(canvas: Canvas, qrWidth: Int, qrHeight: Int, logo: Bitmap) {
        val logoSize = (qrWidth * 0.20f).toInt()
        val scaledLogo = Bitmap.createScaledBitmap(logo, logoSize, logoSize, true)

        val left = (qrWidth - logoSize) / 2f
        val top = (qrHeight - logoSize) / 2f

        // Draw card background behind logo
        val cardPaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val cardRect = RectF(left - 8f, top - 8f, left + logoSize + 8f, top + logoSize + 8f)
        canvas.drawRoundRect(cardRect, 16f, 16f, cardPaint)

        // Draw rounded logo
        val roundedLogo = getRoundedBitmap(scaledLogo)
        canvas.drawBitmap(roundedLogo, left, top, null)
    }

    private fun getRoundedBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.RED
        }
        val rect = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
        
        canvas.drawRoundRect(rect, 16f, 16f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return output
    }
}
