package ngga.ring.ringmusic.base

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ngga.ring.ringmusic.ui.theme.RingMusicTheme

//@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {

//    @Inject
//    lateinit var appExecutors: AppExecutors

    @Composable
    abstract fun setupViews()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RingMusicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                                Image(
                                    painter = painterResource(id = handleOrientation(application.resources.configuration.orientation)),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .blur(
                                            radiusX = 10.dp,
                                            radiusY = 10.dp,
                                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        imageBlurDrawable(
                                            drawable = resources.getDrawable(handleOrientation(application.resources.configuration.orientation)),
                                            blurRadius = 16F
                                        )
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            setupViews()
                        }

                    }
                }
            }


        }

    }


}
