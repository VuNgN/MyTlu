package com.aptech.vungn.mytlu.ui.destinations.login

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aptech.vungn.mytlu.R
import com.aptech.vungn.mytlu.ui.destinations.login.components.TextFields
import com.aptech.vungn.mytlu.ui.destinations.login.components.TopBar
import com.aptech.vungn.mytlu.ui.destinations.login.vm.LoginViewModel
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme

@Composable
fun LoginDestination(viewModel: LoginViewModel) {
    val isLoginIncorrect = viewModel.isLoginIncorrect.collectAsState()
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    MyTluTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                isLoginIncorrect = isLoginIncorrect,
                username = username,
                password = password
            ) { viewModel.login(username.value, password.value) }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isLoginIncorrect: State<Boolean>,
    username: MutableState<String>,
    password: MutableState<String>,
    login: () -> Unit
) {
    val kc = LocalSoftwareKeyboardController.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    ConstraintLayout(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            kc?.hide()
        }) {
        val (topLayout, centerLayout, divider, bottomLayout) = createRefs()
        TopBar(modifier = Modifier.constrainAs(topLayout) {
            top.linkTo(parent.top)
            bottom.linkTo(centerLayout.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        })
        StateLayer(
            modifier = Modifier.constrainAs(centerLayout) {
                top.linkTo(topLayout.bottom)
                bottom.linkTo(bottomLayout.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
            },
            username = username,
            password = password,
            kc = kc,
            isLoginIncorrect = isLoginIncorrect,
            login = login
        )
        Divider(
            modifier = Modifier.constrainAs(divider) {
                top.linkTo(centerLayout.bottom)
                bottom.linkTo(bottomLayout.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.matchParent
                height = Dimension.wrapContent
            },
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Actions(modifier = Modifier.constrainAs(bottomLayout) {
            top.linkTo(centerLayout.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        }, kc = kc, login = login)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StateLayer(
    modifier: Modifier = Modifier,
    username: MutableState<String>,
    password: MutableState<String>,
    kc: SoftwareKeyboardController?,
    isLoginIncorrect: State<Boolean>,
    login: () -> Unit
) {
    Surface(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            val (title, textFields) = createRefs()
            Title(modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                bottom.linkTo(textFields.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.matchParent
            }, isLoginIncorrect = isLoginIncorrect)
            TextFields(modifier = Modifier.constrainAs(textFields) {
                top.linkTo(title.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.matchParent
            }, username = username, password = password, keyboardController = kc, login = login)
        }
    }
}

@Composable
fun Title(modifier: Modifier = Modifier, isLoginIncorrect: State<Boolean>) {
    Surface(modifier = modifier.padding(10.dp)) {
        Crossfade(targetState = isLoginIncorrect.value) { isShowLoginIncorrect ->
            if (isShowLoginIncorrect) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Tải khoản không tồn tại",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Nếu bạn chưa từng có tài khoản, hãy liên hệ nhà trường thể được cấp tài khoản.",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                    Text(
                        text = "Xin chào \ncán bộ, sinh viên  của trường \nđại học Thủy Lợi",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Actions(modifier: Modifier = Modifier, kc: SoftwareKeyboardController?, login: () -> Unit) {
    Surface(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(onClick = {
                kc?.hide()
                login()
            }) {
                Text(text = stringResource(id = R.string.login_button))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PrevLoginScreen() {
    val isLoginIncorrect = remember {
        mutableStateOf(true)
    }
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    MyTluTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LoginScreen(
                isLoginIncorrect = isLoginIncorrect,
                username = username,
                password = password
            ) {

            }
        }
    }
}
