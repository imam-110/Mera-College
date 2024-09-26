package kiet.imam.meracollege.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kiet.imam.meracollege.models.GalleryModel
import kiet.imam.meracollege.utils.Constant.GALLERY

import java.util.UUID

class GalleryViewModel : ViewModel() {

    private val galleryRef = Firebase.firestore.collection(GALLERY)
    private val storageRef = Firebase.storage.reference

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted : LiveData<Boolean> = _isPosted

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted : LiveData<Boolean> = _isDeleted

   private val _galleryList = MutableLiveData<List<GalleryModel>>()
    val galleryList : LiveData<List<GalleryModel>> = _galleryList

    fun saveGallery(uri: Uri, category:String, isCategory : Boolean ){
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID().toString()

        val imageRef = storageRef.child("$GALLERY/${randomUid}.jpg")

        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                if (isCategory)
                uploadCategory(it.toString(), category)
                else updateImage(it.toString(), category)
            }
        }



    }

  private   fun uploadCategory(image : String, category: String){
        val map = mutableMapOf<String, Any>()
        map["images"] = FieldValue.arrayUnion(image)
        map["category"] = category



        galleryRef.document(category).set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener{
                _isPosted.postValue(false)
            }

    }
    private   fun updateImage(image : String, category: String){




        galleryRef.document(category).update("images", FieldValue.arrayUnion(image))
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener{
                _isPosted.postValue(false)
            }

    }

    fun getGallery(){
        galleryRef.get().addOnSuccessListener {
            val list = mutableListOf<GalleryModel>()

            for(doc in it){
                list.add(doc.toObject(GalleryModel::class.java))
            }

            _galleryList.postValue(list)
        }
    }

    fun deleteGallery(galleryModel: GalleryModel){
        galleryModel.images!!.forEach {
            Firebase.storage.getReferenceFromUrl(it).delete()
        }

        galleryRef.document(galleryModel.category!!).delete()
            .addOnSuccessListener {
                _isDeleted.postValue(true)
            }
            .addOnFailureListener{
                _isDeleted.postValue(false)
            }

    }
    fun deleteImage(category: String, image : String){
        galleryRef.document(category).update("images", FieldValue.arrayRemove(image))
            .addOnSuccessListener {
                Firebase.storage.getReferenceFromUrl(image).delete()
                _isDeleted.postValue(true)
            }
            .addOnFailureListener{
                _isDeleted.postValue(false)
            }

    }


}