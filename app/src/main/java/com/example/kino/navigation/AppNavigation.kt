import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.kino.navigation.RootScreen
import com.example.kino.navigation.Screen
import com.example.ui_detail.Detail
import com.example.ui_login.Login
import com.example.ui_profile.Profile
import com.example.ui_tab_host.TabHost
import timber.log.Timber

// Todo: It would probably be better not to throw navController to composable,
//  but replace that with a lambda

@ExperimentalFoundationApi
@Composable
fun AppNavigation(navController: NavHostController, paddings: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = RootScreen.Main.route,
        modifier = Modifier.padding(paddings)
    ) {
        addMainRoot(navController)
        addFollowingRoot(navController)
        addSearchRoot(navController)
        addProfileRoot(navController)
        addDetail(navController)
    }
}

private fun NavGraphBuilder.addMainRoot(navController: NavController) {
    navigation(
        route = RootScreen.Main.route,
        startDestination = Screen.Main.route
    ) {
        addMain(navController)
    }
}

private fun NavGraphBuilder.addFollowingRoot(navController: NavController) {
    navigation(
        route = RootScreen.Following.route,
        startDestination = Screen.Following.route
    ) {
        addFollowing(navController)
    }
}

private fun NavGraphBuilder.addSearchRoot(navController: NavController) {
    navigation(
        route = RootScreen.Search.route,
        startDestination = Screen.Search.route
    ) {
        addSearch(navController)
    }
}

@ExperimentalFoundationApi
private fun NavGraphBuilder.addProfileRoot(navController: NavController) {
    navigation(
        route = RootScreen.Profile.route,
        startDestination = Screen.Profile.route
    ) {
        addProfile(navController)
        addLogin(navController)
    }
}


private fun NavGraphBuilder.addMain(navController: NavController) {
    composable(Screen.Main.route) {
        TabHost(
            openFilm = { id, type ->
                navController.navigate(Screen.Detail.createRoot(id, type.type))
            },
            openGenres = { id, type ->
                navController.navigate(Screen.AllFilm.createRoot(id, type.type))
            }
        )
    }
}

private fun NavGraphBuilder.addFollowing(navController: NavController) {
    composable(Screen.Following.route) {
        Timber.e("Add Following screen")
        // Todo("Add Following screen")
    }
}

private fun NavGraphBuilder.addSearch(navController: NavController) {
    composable(Screen.Search.route) {
        Timber.e("Add Search screen")
        // Todo("Add Search screen")
    }
}

private fun NavGraphBuilder.addProfile(navController: NavController) {
    composable(Screen.Profile.route) {
        Profile()
    }
}

@ExperimentalFoundationApi
private fun NavGraphBuilder.addLogin(navController: NavController) {
    composable(route = Screen.Login.route) {
        Login()
    }
}

private fun NavGraphBuilder.addAllFilm(navController: NavController) {
    composable(Screen.AllFilm.route,
        arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType }
        )) {

    }
}

private fun NavGraphBuilder.addDetail(navController: NavController) {
    composable(
        route = Screen.Detail.route,
        arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType }
        )
    ) {
        Detail()
    }
}