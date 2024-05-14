package com.w36495.senty.data.domain

import com.w36495.senty.util.DateUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class FriendEntity(
    val name: String,
    val groupId: String,
    val birthday: String,
    val memo: String,
    @JsonNames("create_at")
    val createAt: String = DateUtil.toTimeStamp(System.currentTimeMillis()),
    @JsonNames("update_at")
    val updateAt: String = DateUtil.toTimeStamp(System.currentTimeMillis())
) {
    var id: String = ""
        private set

    fun setId(id: String) {
        this.id = id
    }
}