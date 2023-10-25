package com.example.cotucatdpd.map;

import android.graphics.Canvas
import android.graphics.Rect
import com.example.cotucatdpd.graphics.SpriteSheet


abstract class Tile(mapLocationRect: Rect) {

    protected val mapLocationRect: Rect = mapLocationRect

    enum class TileType {
        GROUND_TILE
    }

    companion object {
        fun getTile(idxTileType: Int, spriteSheet: SpriteSheet?, mapLocationRect: Rect?): Tile? {
            return when (TileType.values()[idxTileType]) {
                TileType.GROUND_TILE -> GroundTile(spriteSheet!!, mapLocationRect!!)
                else -> null
            }
        }
    }

    abstract fun draw(canvas: Canvas?)

}