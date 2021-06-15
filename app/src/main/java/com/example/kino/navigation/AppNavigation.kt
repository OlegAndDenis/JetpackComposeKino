
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kino.navigation.RootScreen
import com.example.kino.navigation.Screen
import com.example.ui_movie.movie.TabHost
import com.example.ui_profile.Profile
import timber.log.Timber

// Todo: It would probably be better not to throw navController to composable,
//  but replace that with a lambda

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

private fun NavGraphBuilder.addProfileRoot(navController: NavController) {
    navigation(
        route = RootScreen.Profile.route,
        startDestination = Screen.Profile.route
    ) {
        addProfile(navController)
    }
}


private fun NavGraphBuilder.addMain(navController: NavController) {
    composable(Screen.Main.route) {
        TabHost()
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