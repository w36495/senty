package com.w36495.senty.view.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.w36495.senty.util.getTextColorByBackgroundColor
import com.w36495.senty.view.entity.Friend
import com.w36495.senty.view.ui.component.buttons.SentyOutlinedButtonWithIcon
import com.w36495.senty.view.ui.component.chips.FriendGroupChip
import com.w36495.senty.viewModel.FriendViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendScreen(
    vm: FriendViewModel = hiltViewModel(),
    onClickGroupSetting: () -> Unit,
    onClickAddFriend: () -> Unit,
    onClickFriend: (String) -> Unit,
) {
    val friendList by vm.friends.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "친구목록") },
                actions = {
                    IconButton(onClick = { onClickGroupSetting() }) {
                        Icon(imageVector = Icons.Default.Group, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        FriendContents(
            modifier = Modifier.padding(innerPadding),
            friends = friendList,
            onClickFriend = { friend -> onClickFriend(friend.friendDetail.id) },
            onClickAddFriend = {
                onClickAddFriend()
            }
        )
    }
}

@Composable
private fun FriendContents(
    modifier: Modifier = Modifier,
    friends: List<Friend>,
    onClickFriend: (Friend) -> Unit,
    onClickAddFriend: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SentyOutlinedButtonWithIcon(
            modifier = Modifier.fillMaxWidth(),
            text = "친구 등록",
            icon = Icons.Default.Add,
            onClick = { onClickAddFriend() }
        )

        friends.forEachIndexed { index, friendEntity ->
            FriendItemContent(
                friend = friendEntity,
                onClickFriend = { onClickFriend(it) }
            )

            if (index < friends.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
fun FriendItemContent(
    modifier: Modifier = Modifier,
    friend: Friend,
    onClickFriend: (Friend) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .clickable { onClickFriend(friend) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FriendGroupChip(
                text = friend.friendDetail.friendGroup.name,
                chipColor = friend.friendDetail.friendGroup.color,
                textColor = friend.friendDetail.friendGroup.color.getTextColorByBackgroundColor()
            )
            Text(
                text = if (friend.friendDetail.birthday.isEmpty()) "" else friend.friendDetail.displayBirthdayOnlyDate(),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Text(
            text = friend.friendDetail.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = friend.friendDetail.memo,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = "💝 받은 선물",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = friend.receivedGiftCount.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = "\uD83C\uDF81 준 선물",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = friend.sentGiftCount.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}