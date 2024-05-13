package com.w36495.senty.view.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.w36495.senty.view.entity.FriendGroup
import com.w36495.senty.view.screen.FriendAddScreen
import com.w36495.senty.view.screen.FriendGroupDialogScreen
import com.w36495.senty.view.screen.home.FriendScreen

fun NavGraphBuilder.nestedFriendGraph(navController: NavController) {
    navigation(
        startDestination = BottomNavigationItem.Friend.route,
        route = "FriendNavigationGraph"
    ) {
        composable(BottomNavigationItem.Friend.route) {
            FriendScreen(onClickAddFriend = {
                navController.navigate(FriendNavigationItem.FRIEND_ADD.name)
            },
                onClickGroupSetting = {
                    navController.navigate(FriendNavigationItem.FRIEND_GROUP.name)
                })
        }
        composable(
            FriendNavigationItem.FRIEND_ADD.name,
            arguments = listOf(navArgument("group") {
                type = NavType.ParcelableType(FriendGroup::class.java)
                nullable = false
            })
        ) { backStackEntry ->
            val group = (backStackEntry.arguments?.getParcelable("group") as? FriendGroup) ?: FriendGroup()

            FriendAddScreen(
                group = group,
                onBackPressed = { navController.navigateUp() },
                onFriendGroupClick = {
                    navController.navigate(FriendNavigationItem.FRIEND_GROUP.name)
                },
                onSavedClick = { _, _, _, _ ->

                }
            )
        }
        dialog(FriendNavigationItem.FRIEND_GROUP.name) {
            FriendGroupDialogScreen(
                onDismiss = {
                    navController.navigateUp()
                },
                onGroupSelected = { group ->
                    navController.currentBackStackEntry?.arguments?.putParcelable("group", group)
                    navController.navigate(FriendNavigationItem.FRIEND_ADD.name)
                },
                onEditClick = {

                }
            )
        }
    }
}

enum class FriendNavigationItem {
    FRIEND_GROUP, FRIEND_ADD,
}