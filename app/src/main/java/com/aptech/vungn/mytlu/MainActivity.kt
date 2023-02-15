package com.aptech.vungn.mytlu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.aptech.vungn.mytlu.ui.activity.UserViewModel
import com.aptech.vungn.mytlu.ui.activity.impl.UserViewModelImpl
import com.aptech.vungn.mytlu.ui.destinations.home.HomeDestination
import com.aptech.vungn.mytlu.ui.destinations.home.vm.HomeViewModel
import com.aptech.vungn.mytlu.ui.destinations.home.vm.impl.HomeViewModelImpl
import com.aptech.vungn.mytlu.ui.destinations.login.LoginDestination
import com.aptech.vungn.mytlu.ui.destinations.login.vm.LoginViewModel
import com.aptech.vungn.mytlu.ui.destinations.login.vm.impl.LoginViewModelImpl
import com.aptech.vungn.mytlu.ui.theme.MyTluTheme
import com.aptech.vungn.mytlu.util.LoginState
import com.aptech.vungn.mytlu.util.consts.Const.LOGIN_SUCCESSFUL
import com.aptech.vungn.mytlu.util.consts.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels<UserViewModelImpl>()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.user.observe(this) {
            Log.d(TAG, "Stored user -> $it")
            if (it != null) {
                userViewModel.onLoggedIn()
            }
        }
        setContent {
            MyTluTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RootNavHost()
                }
            }
        }
    }

    @Composable
    private fun RootNavHost() {
        navController = rememberNavController()
        LaunchedEffect(key1 = true) {
            userViewModel.loginState.collect { state ->
                Log.d(TAG, "Login state -> $state")
                if (state == LoginState.NotLoggedIn) navController.navigate(Routes.LOGIN_GRAPH)
            }
        }
        NavHost(navController = navController, startDestination = Routes.MAIN_GRAPH) {
            mainGraph(navController)
            loginGraph(navController)
        }
    }

    private fun NavGraphBuilder.loginGraph(navController: NavHostController) {
        navigation(
            startDestination = Routes.LOGIN_DESTINATION, route = Routes.LOGIN_GRAPH
        ) {
            composable(route = Routes.LOGIN_DESTINATION) {
                val loginViewModel: LoginViewModel = hiltViewModel<LoginViewModelImpl>()
                LaunchedEffect(key1 = true) {
                    val savedStateHandle = navController.previousBackStackEntry!!.savedStateHandle
                    savedStateHandle[LOGIN_SUCCESSFUL] = false
                    loginViewModel.isLoggedIn.collect {
                        if (it) {
                            savedStateHandle[LOGIN_SUCCESSFUL] = true
                            navController.popBackStack()
                        }
                    }
                }
                LoginDestination(viewModel = loginViewModel)
            }
        }
    }

    private fun NavGraphBuilder.mainGraph(navController: NavHostController) {
        navigation(startDestination = Routes.HOME_DESTINATION, route = Routes.MAIN_GRAPH) {
            composable(route = Routes.HOME_DESTINATION) {
                LaunchedEffect(key1 = true) {
                    val currentBackStackEntry = navController.currentBackStackEntry!!
                    val savedStateHandle = currentBackStackEntry.savedStateHandle
                    savedStateHandle.getLiveData<Boolean>(LOGIN_SUCCESSFUL)
                        .observe(currentBackStackEntry) { success ->
                            if (!success) {
                                this@MainActivity.finish()
                            }
                        }
                }
                val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>()
                HomeDestination(viewModel = homeViewModel)
            }
            composable(route = Routes.ATTENDANCE_DESTINATION) {

            }
            composable(route = Routes.NEWS_DESTINATION) {

            }
            composable(route = Routes.PROFILE_DESTINATION) {

            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}