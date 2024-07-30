package com.example.jetpackcomposebase.ui.login.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.jetpackcomposebase.R
import com.example.jetpackcomposebase.base.ToolBarData
import com.example.jetpackcomposebase.navigation.Destination
import com.example.jetpackcomposebase.navigation.NAV_HOME
import com.example.jetpackcomposebase.navigation.navigateTo
import com.example.jetpackcomposebase.network.ResponseData
import com.example.jetpackcomposebase.network.ResponseHandler
import com.example.jetpackcomposebase.ui.custom_compose.CustomButton
import com.example.jetpackcomposebase.ui.custom_compose.CustomTextField
import com.example.jetpackcomposebase.ui.login.model.LoginResponse
import com.example.jetpackcomposebase.ui.login.viewmodel.LoginViewModel
import com.example.jetpackcomposebase.ui.theme.bodyBold
import com.example.jetpackcomposebase.utils.DebugLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreenView(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    bottomBarVisibility: (Boolean) -> Unit,
    topBar: (ToolBarData) -> Unit,
    circularProgress: (Boolean) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        topBar(
            ToolBarData(
                title = "Login",
                isVisible = false,
                isDrawerIcon = false,
                isBackIconVisible = false
            )
        )
        bottomBarVisibility(false)
        circularProgress(false)
        observeData(
            viewModel = loginViewModel,
            context = context,
            navController = navController,
            circularProgress = { circularProgress(it) })
        delay(2500)
        navController.popBackStack()
    }
    LoginUI(navController = navController, loginViewModel = loginViewModel)
}


@Composable
fun LoginUI(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (loginText, customTextFieldUserName, customTextFieldPassword, customButton) = createRefs()
        val centerGuideline = createGuidelineFromTop(0.3f)

        val userName by loginViewModel.email.collectAsState()
        val password by loginViewModel.password.collectAsState()
        var showPassword by rememberSaveable { mutableStateOf(false) }

        Text(
            text = stringResource(id = R.string.lbl_login_here),
            style = MaterialTheme.typography.bodyBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(loginText) {
                top.linkTo(centerGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
        )

        // Username TextField
        CustomTextField(
            value = userName,
            onValueChange = { loginViewModel.setUserName(it) },
            label = stringResource(id = R.string.lbl_username),
            modifier = Modifier.constrainAs(customTextFieldUserName) {
                top.linkTo(loginText.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            indicatorColor = MaterialTheme.colorScheme.primary,
            indicatorWidth = 2.dp,
            isPasswordField = false,
            showPassword = false,
            setShowPassword = {}
        )

        // Password TextField
        CustomTextField(
            value = password,
            onValueChange = { loginViewModel.setPassword(it) },
            label = stringResource(id = R.string.lbl_username),
            modifier = Modifier.constrainAs(customTextFieldPassword) {
                top.linkTo(customTextFieldUserName.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            indicatorColor = MaterialTheme.colorScheme.primary,
            indicatorWidth = 2.dp,
            isPasswordField = true,
            showPassword = showPassword,
            setShowPassword = { showPassword = it }
        )

        CustomButton(
            text = stringResource(id = R.string.lbl_login),
            backgroundColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(customButton) {
                top.linkTo(customTextFieldPassword.bottom, margin = 50.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            onCLick = {
                if (userName.isNotEmpty() && password.isNotEmpty()) {
                    navController.navigate(NAV_HOME)
                    // loginViewModel.callLoginAPI()
                } else {
                    DebugLog.d("Login failed")

                }
            }
        )
    }
}


private fun observeData(
    viewModel: LoginViewModel,
    context: Context,
    navController: NavController,
    circularProgress: (Boolean) -> Unit
) {

    viewModel.viewModelScope.launch {
        viewModel.loginResponse.collect {
            when (it) {
                is ResponseHandler.Loading -> {
                    circularProgress(true)
                }

                is ResponseHandler.OnFailed -> {
                    circularProgress(false)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is ResponseHandler.OnSuccessResponse<ResponseData<LoginResponse>?> -> {
                    when {
                        it.response?.data?.token?.isNotEmpty() == true -> {
                            DebugLog.e("response : email:${it.response.data?.token}")
                            Toast.makeText(
                                context,
                                "Email:${it.response.data?.token}",
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateTo(
                                navHostController = navController,
                                route = Destination.HomeScreen.fullRoute,
                                popUpToRoute = Destination.LoginScreen.fullRoute,
                                isInclusive = true
                            )
                        }

                        else -> {
                        }
                    }
                }

                else -> {
                    circularProgress(false)
                }

            }
        }
    }
}
