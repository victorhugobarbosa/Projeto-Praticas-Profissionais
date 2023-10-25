package com.example.cotucatdpd.map;

import android.graphics.Canvas
import android.graphics.Rect
import com.example.cotucatdpd.graphics.Sprite
import com.example.cotucatdpd.graphics.SpriteSheet

class GroundTile(spriteSheet: SpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    private val sprite: Sprite = spriteSheet.getGroundSprite()

    override fun draw(canvas: Canvas?) {
        sprite.draw(canvas, mapLocationRect.left.toFloat(), mapLocationRect.top.toFloat())
    }
}