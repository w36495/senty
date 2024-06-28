package com.w36495.senty.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.w36495.senty.domain.repository.GiftImgRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GiftImgRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth,
) : GiftImgRepository {
    private var userId: String = firebaseAuth.currentUser!!.uid
    override suspend fun getGiftImages(giftId: String): List<String> {
        var giftImages = mutableListOf<String>()
        val imgPath = "images/gifts/$userId/$giftId"

        coroutineScope {
            firebaseStorage.reference.child(imgPath).listAll()
                .addOnFailureListener {
                    Log.d("GiftImgRepositoryImpl", "Exception: (${it.message.toString()})")
                }
                .addOnSuccessListener {
                    it.items.forEach { storageReference ->
                        launch {
                            val downloadUrl = storageReference.downloadUrl.await()
                            giftImages.add(downloadUrl.toString())
                        }
                    }
                }.await()
        }

        return giftImages.toList()
    }

    override suspend fun insertGiftImgByBitmap(
        giftId: String,
        giftImage: ByteArray
    ) {
        val imgName = generateImageName()
        val giftImagePath = "images/gifts/$userId/$giftId/$imgName.jpg"

        firebaseStorage.reference.child(giftImagePath).putBytes(giftImage)
            .addOnFailureListener {
                Log.d("GiftImgRepositoryImpl", "Exception: (${it.message.toString()})")
            }
    }

    override suspend fun deleteGiftImg(giftId: String, imgPath: String): Boolean {
        var result = false
        val imagePath = "images/gifts/$userId/$giftId/$imgPath"

        firebaseStorage.reference.child(imagePath).delete()
            .addOnFailureListener {
                throw IllegalArgumentException("Gift image delete Failed")
            }
            .addOnSuccessListener {
                result = true
            }.await()

        return result
    }

    private fun generateImageName() = System.currentTimeMillis().toString()
}