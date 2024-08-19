package ngga.ring.ringmusic.ui.activity.auth.state

sealed class AuthScreenState {
    data object LoginScreen : AuthScreenState()
    data object RegisterScreen : AuthScreenState()
    data object ForgotPasswordScreen : AuthScreenState()
    data object VerifyEmailScreen : AuthScreenState()
    data object ResetPasswordScreen : AuthScreenState()
    data object GoogleLoginScreen : AuthScreenState()
}

sealed class AuthUiState<out T> {
    object Loading : AuthUiState<Nothing>()
    data class Success<T>(val data: T) : AuthUiState<T>()
    data class Error(val message: String) : AuthUiState<Nothing>()
}