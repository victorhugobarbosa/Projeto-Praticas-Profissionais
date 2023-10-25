package com.example.cotucatdpd.map;

class MapLayout {
    companion object {
        const val TILE_WIDTH_PIXELS = 64
        const val TILE_HEIGHT_PIXELS = 64
        const val NUMBER_OF_ROW_TILES = 60
        const val NUMBER_OF_COLUMN_TILES = 60
    }

    private var layout: Array<IntArray>

    init {
        initializeLayout()
    }

    fun getLayout(): Array<IntArray> {
        return layout
    }

    private fun initializeLayout() {
        layout = Array(NUMBER_OF_ROW_TILES) { IntArray(NUMBER_OF_COLUMN_TILES) }
        for (i in 0 until NUMBER_OF_ROW_TILES) {
            for (j in 0 until NUMBER_OF_COLUMN_TILES) {
                layout[i][j] = 0
            }
        }
    }
}