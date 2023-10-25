package com.example.cotucatdpd.map;

import android.graphics.Canvas
import android.graphics.Rect
import com.example.androidstudio2dgamedevelopment.graphics.Sprite
import com.example.androidstudio2dgamedevelopment.graphics.SpriteSheet

class GroundTile(spriteSheet: SpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    private val sprite: Sprite = spriteSheet.groundSprite

    override fun draw(canvas: Canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
    }
}