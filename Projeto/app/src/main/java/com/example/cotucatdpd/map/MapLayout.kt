package com.example.cotucatdpd.map;

class MapLayout {
    val TILE_WIDTH_PIXELS = 64
    val TILE_HEIGHT_PIXELS = 64
    val NUMBER_OF_ROW_TILES = 120
    val NUMBER_OF_COLUMN_TILES = 120


    private var layout: Array<IntArray>

    init {
        layout = Array(NUMBER_OF_ROW_TILES) { IntArray(NUMBER_OF_COLUMN_TILES) }
        for (i in 0 until NUMBER_OF_ROW_TILES) {
            for (j in 0 until NUMBER_OF_COLUMN_TILES) {
                layout[i][j] = 0
            }
        }
    }

    fun getLayout(): Array<IntArray> {
        return layout
    }

}