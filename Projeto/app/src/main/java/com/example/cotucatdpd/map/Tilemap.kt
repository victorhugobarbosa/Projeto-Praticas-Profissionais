package com.example.cotucatdpd.map;

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.graphics.SpriteSheet


class Tilemap(spriteSheet: SpriteSheet) {

    private val mapLayout = MapLayout()
    private lateinit var tilemap: Array<Array<Tile?>>
    private val spriteSheet = spriteSheet
    private var mapBitmap: Bitmap? = null

    init {
        initializeTilemap()
    }

    private fun initializeTilemap() {
        val layout = mapLayout.getLayout()
        tilemap = Array<Array<Tile?>>(mapLayout.NUMBER_OF_ROW_TILES) {
            arrayOfNulls<Tile>(mapLayout.NUMBER_OF_COLUMN_TILES)
        }

        for (iRow in 0 until mapLayout.NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until mapLayout.NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol] = Tile.getTile(
                    layout[iRow][iCol],
                    spriteSheet,
                    getRectByIndex(iRow, iCol)
                )!!
            }
        }

        val config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
                mapLayout.NUMBER_OF_COLUMN_TILES * mapLayout.TILE_WIDTH_PIXELS,
                mapLayout.NUMBER_OF_ROW_TILES * mapLayout.TILE_HEIGHT_PIXELS,
                config
        )

        val mapCanvas = Canvas(mapBitmap!!)

        for (iRow in 0 until mapLayout.NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until mapLayout.NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol]!!.draw(mapCanvas)
            }
        }
    }

    private fun getRectByIndex(idxRow: Int, idxCol: Int): Rect {
        return Rect(
                idxCol * mapLayout.TILE_WIDTH_PIXELS,
                idxRow * mapLayout.TILE_HEIGHT_PIXELS,
                (idxCol + 1) * mapLayout.TILE_WIDTH_PIXELS,
                (idxRow + 1) * mapLayout.TILE_HEIGHT_PIXELS
        )
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        canvas.drawBitmap(
                mapBitmap!!,
                gameDisplay.gameRect(),
                gameDisplay.DISPLAY_RECT,
                null
        )
    }
}