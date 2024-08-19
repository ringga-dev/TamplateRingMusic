package ngga.ring.ringmusic.ui.activity.auth.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ngga.ring.ringmusic.ui.activity.auth.state.AuthScreenState
import ngga.ring.ringmusic.ui.activity.auth.state.AuthUiState

class AuthViewModel :ViewModel() {

    private val _screenState = mutableStateOf<AuthScreenState>(AuthScreenState.LoginScreen)
    val screenState: State<AuthScreenState> = _screenState

    private val _authUiState = MutableStateFlow<AuthUiState<Any>>(AuthUiState.Loading)
    val authUiState: StateFlow<AuthUiState<Any>> = _authUiState

    fun navigateTo(screenState: AuthScreenState) {
        _screenState.value = screenState
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _authUiState.value = AuthUiState.Loading
            try {
                // Simulasi proses login
                delay(2000)
                // Jika login sukses
                _authUiState.value = AuthUiState.Success("User logged in successfully")
                navigateTo(AuthScreenState.VerifyEmailScreen)
            } catch (e: Exception) {
                _authUiState.value = AuthUiState.Error("Login failed: ${e.message}")
            }
        }
    }
}