package com.w36495.senty.viewModel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.senty.domain.repository.GiftImgRepository
import com.w36495.senty.domain.repository.GiftRepository
import com.w36495.senty.util.toByteArray
import com.w36495.senty.view.entity.gift.GiftEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

@HiltViewModel
class GiftAddViewModel @Inject constructor(
    private val giftRepository: GiftRepository,
    private val giftImageRepository: GiftImgRepository
) : ViewModel() {
    fun saveGift(gift: GiftEntity, giftImg: Any?) {
        viewModelScope.launch {
            val result = giftRepository.insertGift(gift.toDataEntity())

            if (result.isSuccessful) {
                result.body()?.let {
                    val jsonObject = Json.decodeFromString<JsonObject>(it.string())
                    val key = jsonObject["name"].toString().replace("\"", "")

                    saveGiftImg(key, giftImg)
                    val giftKeyResult = giftRepository.patchGiftKey(key)
                    if (giftKeyResult.isSuccessful) {
                        if (giftKeyResult.body()?.string() == it.string()) { }
                    }
                }
            } else {
                Log.d("GiftAddVM", "saveGift(Failed) : ${result.errorBody().toString()}")
            }
        }
    }

    private fun saveGiftImg(giftId: String, giftImg: Any?) {
        when (giftImg) {
            is Bitmap -> {
                val byteGiftImg = giftImg.toByteArray()
                giftImageRepository.insertGiftImgByBitmap(giftId, byteGiftImg)
            }
            is Uri -> {
                giftImageRepository.insertGiftImgByUri(giftId, giftImg)
            }
        }
    }
}