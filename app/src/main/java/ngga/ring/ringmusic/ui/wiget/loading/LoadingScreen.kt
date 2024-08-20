package ngga.ring.ringmusic.ui.wiget.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ngga.ring.ringmusic.R

class LoadingScreen {
    @Composable
    fun View(){
        // Mengambil animasi Lottie dari resource
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Background semi transparan
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent), // Latar belakang transparan agar tidak bisa di klik
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier.size(300.dp) // Menentukan ukuran animasi
                )
            }
        }
    }
}