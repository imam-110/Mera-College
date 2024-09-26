package kiet.imam.meracollege.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kiet.imam.meracollege.models.CollegeInfoModel
import kiet.imam.meracollege.utils.Constant.COLLEGE_INFO
import java.util.UUID

class CollegeInfoViewModel : ViewModel() {

    private val collegeInfoRef = Firebase.firestore.collection(COLLEGE_INFO)
    private val storageRef = Firebase.storage.reference

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted : LiveData<Boolean> = _isPosted


   private val _collegeInfoList = MutableLiveData<CollegeInfoModel>()
    val collegeInfoList : LiveData<CollegeInfoModel> = _collegeInfoList


    fun saveImage(uri: Uri, name : String, address : String, description: String, websiteLink : String){
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID().toString()

        val imageRef = storageRef.child("$COLLEGE_INFO/${randomUid}.jpg")

        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                uploadImage(it.toString(), name, address, description,websiteLink )
            }
        }



    }

     fun uploadImage(imageUrl: String,name : String, address : String, description: String, websiteLink : String){
        val map = mutableMapOf<String, Any>()
        map["imageUrl"] = imageUrl
        map["name"] = name
        map["address"] = address
        map["description"] = description
        map["websiteLink"] = websiteLink


        collegeInfoRef.document("imam").set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener{
                _isPosted.postValue(false)
            }

    }

    fun getCollegeInfo(){
        collegeInfoRef.document("imam").get().addOnSuccessListener {


            _collegeInfoList.postValue(
                CollegeInfoModel(
                    it.data!!["name"].toString(),
                    it.data!!["address"].toString(),
                    it.data!!["description"].toString(),
                    it.data!!["websiteLink"].toString(),
                    it.data!!["imageUrl"].toString()
                )
            )
        }
    }
    }


