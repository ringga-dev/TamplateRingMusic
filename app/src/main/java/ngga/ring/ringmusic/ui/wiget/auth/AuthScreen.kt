package ngga.ring.ringmusic.ui.wiget.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import ngga.ring.ringmusic.ui.activity.auth.state.AuthScreenState
import ngga.ring.ringmusic.ui.activity.auth.state.AuthUiState
import ngga.ring.ringmusic.ui.activity.auth.view_models.AuthViewModel

class AuthScreen {

    @Composable
    fun View(viewModel: AuthViewModel = viewModel()) {
        val screenState by viewModel.screenState
        val authUiState by viewModel.authUiState.collectAsState()

        Column {
            AuthScreen(authScreenState = screenState)
            AuthStatusScreen(authUiState = authUiState)
        }
    }


    @Composable
    fun AuthScreen(authScreenState: AuthScreenState) {
        when (authScreenState) {
            is AuthScreenState.LoginScreen -> {
                LoginPageScreen()
            }
            is AuthScreenState.RegisterScreen -> {
                RegisterPageScreen()
            }
            is AuthScreenState.ForgotPasswordScreen -> {
                ForgotPasswordPageScreen()
            }
            is AuthScreenState.VerifyEmailScreen -> {
                VerifyEmailPageScreen()
            }
            is AuthScreenState.ResetPasswordScreen -> {
//                ResetPasswordScreen()
            }
            is AuthScreenState.GoogleLoginScreen -> {
//                GoogleLoginScreen()
            }
        }
    }

    @Composable
    fun AuthStatusScreen(authUiState: AuthUiState<Any>) {
        when (authUiState) {
            is AuthUiState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthUiState.Success -> {
                Text(text = "Success: ${authUiState.data}")
            }
            is AuthUiState.Error -> {
                Text(text = "Error: ${authUiState.message}", color = Color.Red)
            }
        }
    }

    @Composable
    fun LoginPageScreen() {
        Text(text = "Login Page")
    }

    @Composable
    fun RegisterPageScreen() {
        Text(text = "Register Page")
    }

    @Composable
    fun ForgotPasswordPageScreen() {
        Text(text = "Forgot Password Page")
    }

    @Composable
    fun VerifyEmailPageScreen() {
        Text(text = "Verify Email Page")
    }




}