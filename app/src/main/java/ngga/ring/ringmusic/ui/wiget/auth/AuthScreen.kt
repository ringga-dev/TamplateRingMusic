package ngga.ring.ringmusic.ui.wiget.auth

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ngga.ring.ringmusic.R
import ngga.ring.ringmusic.ui.activity.auth.state.AuthScreenState
import ngga.ring.ringmusic.ui.activity.auth.state.AuthUiState
import ngga.ring.ringmusic.ui.activity.auth.view_models.AuthViewModel
import ngga.ring.ringmusic.ui.activity.home.HomeActivity
import ngga.ring.ringmusic.ui.theme.font.FontFine
import ngga.ring.ringmusic.ui.theme.font.FontNunito
import ngga.ring.ringmusic.ui.wiget.loading.LoadingScreen

class AuthScreen {

    @Composable
    fun View(viewModel: AuthViewModel = viewModel()) {
        val screenState by viewModel.screenState

        when (screenState) {
            is AuthScreenState.LoginScreen -> {
                LoginPageScreen(viewModel)
            }

            is AuthScreenState.RegisterScreen -> {
                RegisterPageScreen(viewModel)
            }

            is AuthScreenState.ForgotPasswordScreen -> {
                ForgotPasswordPageScreen(viewModel)
            }

            is AuthScreenState.VerifyEmailScreen -> {
                VerifyEmailPageScreen(viewModel)
            }

            is AuthScreenState.ResetPasswordScreen -> {
                ResetPasswordPageScreen(viewModel)
            }

            is AuthScreenState.GoogleLoginScreen -> {
                GoogleLoginPageScreen(viewModel)
            }

            else -> {}
        }
    }


    @Composable
    fun LoginPageScreen(viewModel: AuthViewModel) {
        val authUiState by viewModel.authUiState.collectAsState()
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        Box(modifier = Modifier.fillMaxSize()) {

            when (authUiState) {
                is AuthUiState.Initial -> {
                    var isNewopen by remember { mutableStateOf(true) }
                    if (isNewopen) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                                .align(Alignment.TopCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        ),
                                        text = buildAnnotatedString {
                                            append("Welcome to ")

                                            // Gaya untuk "Ring"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.primary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Ring")

                                            pop() // Mengakhiri gaya sebelumnya

                                            // Gaya untuk "Music"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.tertiary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Music")

                                            pop() // Mengakhiri gaya yang di-push
                                        },
                                        style = FontFine().textStyleLight18 // Gaya dasar untuk teks
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                isNewopen = false
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }


                    }
                }

                is AuthUiState.Loading -> {

                    Box(
                        modifier = Modifier
                            .clickable(enabled = false) {}
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingScreen().View()
                    }
                }

                is AuthUiState.Success -> {
                    val context = LocalContext.current
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }

                is AuthUiState.Error -> {
                    Text(
                        text = "Error: ${(authUiState as AuthUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Input Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.loginUser(email, password) // Memanggil aksi login di ViewModel
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sing in")
                }

                Button(
                    onClick = {
                        viewModel.navigateTo(AuthScreenState.RegisterScreen)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Sing Up", color = MaterialTheme.colorScheme.onPrimary)

                }

                Button(
                    onClick = {
                        viewModel.navigateTo(AuthScreenState.ForgotPasswordScreen)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Forgot Password", color = MaterialTheme.colorScheme.onPrimary)

                }

                Button(
                    onClick = {
                        viewModel.navigateTo(AuthScreenState.VerifyEmailScreen)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Verify Email", color = MaterialTheme.colorScheme.onPrimary)

                }
                Button(
                    onClick = {
                        viewModel.navigateTo(AuthScreenState.ResetPasswordScreen)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Reset Password", color = MaterialTheme.colorScheme.onPrimary)

                }
                Button(
                    onClick = {
                        viewModel.navigateTo(AuthScreenState.GoogleLoginScreen)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Google Login", color = MaterialTheme.colorScheme.onPrimary)

                }


            }
        }

    }

    @Composable
    fun RegisterPageScreen(viewModel: AuthViewModel) {
        val authUiState by viewModel.authUiState.collectAsState()
        var email by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        Box(modifier = Modifier.fillMaxSize()) {

            when (authUiState) {
                is AuthUiState.Initial -> {
                    var isNewopen by remember { mutableStateOf(true) }
                    if (isNewopen) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                                .align(Alignment.TopCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        ),
                                        text = buildAnnotatedString {
                                            append("Register to ")

                                            // Gaya untuk "Ring"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.primary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Ring")

                                            pop() // Mengakhiri gaya sebelumnya

                                            // Gaya untuk "Music"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.tertiary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Music")

                                            pop() // Mengakhiri gaya yang di-push
                                        },
                                        style = FontFine().textStyleLight18 // Gaya dasar untuk teks
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                isNewopen = false
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                    }
                }

                is AuthUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AuthUiState.Success -> {
                    val context = LocalContext.current
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }

                is AuthUiState.Error -> {
                    Text(
                        text = "Error: ${(authUiState as AuthUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Input Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Button Login
                Button(
                    onClick = {
                        viewModel.loginUser(email, password) // Memanggil aksi login di ViewModel
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sing Up")
                }

                Button(
                    onClick = {
                        viewModel.navigateTo(AuthScreenState.LoginScreen)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Sing In", color = MaterialTheme.colorScheme.onPrimary)

                }


            }
        }


    }

    @Composable
    fun ForgotPasswordPageScreen(viewModel: AuthViewModel) {
        val authUiState by viewModel.authUiState.collectAsState()
        var email by remember { mutableStateOf("") }


        Box(modifier = Modifier.fillMaxSize()) {

            when (authUiState) {
                is AuthUiState.Initial -> {
                    var isNewopen by remember { mutableStateOf(true) }
                    if (isNewopen) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                                .align(Alignment.TopCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        ),
                                        text = buildAnnotatedString {
                                            append("Welcome to ")

                                            // Gaya untuk "Ring"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.primary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Ring")

                                            pop() // Mengakhiri gaya sebelumnya

                                            // Gaya untuk "Music"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.tertiary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Music")

                                            pop() // Mengakhiri gaya yang di-push
                                        },
                                        style = FontFine().textStyleLight18 // Gaya dasar untuk teks
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                isNewopen = false
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }


                    }
                }

                is AuthUiState.Loading -> {

                    Box(
                        modifier = Modifier
                            .clickable(enabled = false) {}
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingScreen().View()
                    }
                }

                is AuthUiState.Success -> {
                    val context = LocalContext.current
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }

                is AuthUiState.Error -> {
                    Text(
                        text = "Error: ${(authUiState as AuthUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))



                Button(
                    onClick = {
                        viewModel.loginUser(email,"") // Memanggil aksi login di ViewModel
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sing in")
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text("Sing Up", modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        viewModel.navigateTo(AuthScreenState.RegisterScreen)
                    })
            }
        }
    }

    @Composable
    fun VerifyEmailPageScreen(viewModel: AuthViewModel) {
        val authUiState by viewModel.authUiState.collectAsState()
        var inputOTP by remember { mutableStateOf("") }


        Box(modifier = Modifier.fillMaxSize()) {

            when (authUiState) {
                is AuthUiState.Initial -> {
                    var isNewopen by remember { mutableStateOf(true) }
                    if (isNewopen) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                                .align(Alignment.TopCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        ),
                                        text = buildAnnotatedString {
                                            append("Welcome to ")

                                            // Gaya untuk "Ring"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.primary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Ring")

                                            pop() // Mengakhiri gaya sebelumnya

                                            // Gaya untuk "Music"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.tertiary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Music")

                                            pop() // Mengakhiri gaya yang di-push
                                        },
                                        style = FontFine().textStyleLight18 // Gaya dasar untuk teks
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                isNewopen = false
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }


                    }
                }

                is AuthUiState.Loading -> {

                    Box(
                        modifier = Modifier
                            .clickable(enabled = false) {}
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingScreen().View()
                    }
                }

                is AuthUiState.Success -> {
                    val context = LocalContext.current
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }

                is AuthUiState.Error -> {
                    Text(
                        text = "Error: ${(authUiState as AuthUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Input Email
                OutlinedTextField(
                    value = inputOTP,
                    onValueChange = { inputOTP = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text("Input OTP") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))



                Button(
                    onClick = {
                        viewModel.loginUser(inputOTP,"") // Memanggil aksi login di ViewModel
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sing in")
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text("Sing Up", modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        viewModel.navigateTo(AuthScreenState.RegisterScreen)
                    })
            }
        }
    }

    @Composable
    fun ResetPasswordPageScreen(viewModel: AuthViewModel) {
        val authUiState by viewModel.authUiState.collectAsState()
        var email by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize()) {

            when (authUiState) {
                is AuthUiState.Initial -> {
                    var isNewopen by remember { mutableStateOf(true) }
                    if (isNewopen) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                                .align(Alignment.TopCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        ),
                                        text = buildAnnotatedString {
                                            append("Welcome to ")

                                            // Gaya untuk "Ring"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.primary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Ring")

                                            pop() // Mengakhiri gaya sebelumnya

                                            // Gaya untuk "Music"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.tertiary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Music")

                                            pop() // Mengakhiri gaya yang di-push
                                        },
                                        style = FontFine().textStyleLight18 // Gaya dasar untuk teks
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                isNewopen = false
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }


                    }
                }

                is AuthUiState.Loading -> {

                    Box(
                        modifier = Modifier
                            .clickable(enabled = false) {}
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingScreen().View()
                    }
                }

                is AuthUiState.Success -> {
                    val context = LocalContext.current
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }

                is AuthUiState.Error -> {
                    Text(
                        text = "Error: ${(authUiState as AuthUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text("Input Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))



                Button(
                    onClick = {
                        viewModel.loginUser(email,"") // Memanggil aksi login di ViewModel
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sing in")
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text("Sing Up", modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        viewModel.navigateTo(AuthScreenState.RegisterScreen)
                    })
            }
        }
    }

    @Composable
    fun GoogleLoginPageScreen(viewModel: AuthViewModel) {
        val authUiState by viewModel.authUiState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {

            when (authUiState) {
                is AuthUiState.Initial -> {
                    var isNewopen by remember { mutableStateOf(true) }
                    if (isNewopen) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                                .align(Alignment.TopCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        ),
                                        text = buildAnnotatedString {
                                            append("Welcome to ")

                                            // Gaya untuk "Ring"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.primary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Ring")

                                            pop() // Mengakhiri gaya sebelumnya

                                            // Gaya untuk "Music"
                                            pushStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = MaterialTheme.colorScheme.tertiary, // Warna teks berbeda
                                                    fontFamily = FontNunito
                                                )
                                            )
                                            append("Music")

                                            pop() // Mengakhiri gaya yang di-push
                                        },
                                        style = FontFine().textStyleLight18 // Gaya dasar untuk teks
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                isNewopen = false
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }


                    }
                }

                is AuthUiState.Loading -> {

                    Box(
                        modifier = Modifier
                            .clickable(enabled = false) {}
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingScreen().View()
                    }
                }

                is AuthUiState.Success -> {
                    val context = LocalContext.current
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }

                is AuthUiState.Error -> {
                    Text(
                        text = "Error: ${(authUiState as AuthUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Input Email




                Button(
                    onClick = {
//                        viewModel.loginUser(inputOTP,"") // Memanggil aksi login di ViewModel
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sing in")
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text("Sing Up", modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        viewModel.navigateTo(AuthScreenState.RegisterScreen)
                    })
            }
        }
    }



}
