package com.example.cotucatdpd.map;

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.graphics.SpriteSheet

import com.example.cotucatdpd.map.MapLayout.NUMBER_OF_COLUMN_TILES
import com.example.cotucatdpd.map.MapLayout.NUMBER_OF_ROW_TILES
import com.example.cotucatdpd.map.MapLayout.TILE_HEIGHT_PIXELS
import com.example.cotucatdpd.map.MapLayout.TILE_WIDTH_PIXELS

class Tilemap(private val spriteSheet: SpriteSheet) {

    private val mapLayout = MapLayout()
    private val tilemap: Array<Array<Tile>>

    private var mapBitmap: Bitmap

    init {
        initializeTilemap()
    }

    private fun initializeTilemap() {
        val layout = mapLayout.layout
        tilemap = Array(NUMBER_OF_ROW_TILES) { Array(NUMBER_OF_COLUMN_TILES) { Tile() } }

        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol] = Tile.getTile(
                        layout[iRow][iCol],
                        spriteSheet,
                        getRectByIndex(iRow, iCol)
                )
            }
        }

        val config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
                NUMBER_OF_COLUMN_TILES * TILE_WIDTH_PIXELS,
                NUMBER_OF_ROW_TILES * TILE_HEIGHT_PIXELS,
                config
        )

        val mapCanvas = Canvas(mapBitmap)

        for (iRow in 0 until NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol].draw(mapCanvas)
            }
        }
    }

    private fun getRectByIndex(idxRow: Int, idxCol: Int): Rect {
        return Rect(
                idxCol * TILE_WIDTH_PIXELS,
                idxRow * TILE_HEIGHT_PIXELS,
                (idxCol + 1) * TILE_WIDTH_PIXELS,
                (idxRow + 1) * TILE_HEIGHT_PIXELS
        )
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        canvas.drawBitmap(
                mapBitmap,
                gameDisplay.gameRect,
                gameDisplay.DISPLAY_RECT,
                null
        )
    }
}