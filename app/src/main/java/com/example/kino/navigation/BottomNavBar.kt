package com.example.kino.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.coil.rememberCoilPainter

// https://developer.android.com/jetpack/compose/navigation#bottom-nav

/** Bottom navigation bar items */
private val items = listOf(
    RootScreen.Main,
    RootScreen.Following,
    RootScreen.Search,
    RootScreen.Profile,
)

/** Routes that don't need a bottom navigation bar  */
val blackList = listOf(
    Screen.Login.route,
    Screen.Detail.route,
    Screen.AllFilm.route,
)

@Composable
fun BottomNavBar(
    navController: NavController
) = BottomNavigation(
    backgroundColor = MaterialTheme.colors.background,
    elevation = 10.dp,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    items.forEach { screen ->
        BottomNavigationItem(
            icon = { Icon(screen) },
            label = { Text(screen.label) },
            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
            onClick = {
                navController.navigate(screen.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        // FixMe: Extract Methods
                        saveState =
                            currentDestination?.hierarchy?.any { it.route == screen.route } != true ||
                                    currentDestination.parent?.startDestinationRoute == currentDestination.route
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        )
    }
}

@Composable
fun Icon(screen: RootScreen) {
    Icon(
        painter = rememberCoilPainter(request = screen.iconId),
        contentDescription = null,
        modifier = Modifier.height(20.dp)
    )
}