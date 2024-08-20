package ngga.ring.ringmusic.base

import android.content.res.Configuration
import ngga.ring.ringmusic.R

fun handleOrientation(orientation: Int): Int {
    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            return R.drawable.background_tablet
        }

        Configuration.ORIENTATION_PORTRAIT -> {
            // Tindakan yang perlu diambil jika layar berorientasi portrait
            return R.drawable.background_phone
        }

        else -> {
            // Tindakan yang perlu diambil jika orientasi tidak dikenal
            return R.drawable.background_phone
        }
    }
}
