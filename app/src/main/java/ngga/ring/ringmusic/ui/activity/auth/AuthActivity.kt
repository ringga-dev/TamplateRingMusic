package ngga.ring.ringmusic.ui.activity.auth


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ngga.ring.ringmusic.base.BaseActivity
import ngga.ring.ringmusic.ui.theme.RingMusicTheme
import ngga.ring.ringmusic.ui.wiget.auth.AuthScreen

class AuthActivity : BaseActivity() {
    @Composable
    override fun setupViews() {
        AuthScreen().View()
    }


}

