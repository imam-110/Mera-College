package kiet.imam.meracollege.AdminScreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kiet.imam.meracollege.R
import kiet.imam.meracollege.itemview.BannerItemView
import kiet.imam.meracollege.itemview.NoticeItemView
import kiet.imam.meracollege.ui.theme.Purple40
import kiet.imam.meracollege.utils.Constant.BANNER
import kiet.imam.meracollege.viewmodel.BannerViewModel
import kiet.imam.meracollege.viewmodel.CollegeInfoViewModel
import kiet.imam.meracollege.viewmodel.NoticeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCollegeInfo(navController : NavHostController){
    val context = LocalContext.current
    val collegeInfoViewModel : CollegeInfoViewModel = viewModel()
    val isUploaded by collegeInfoViewModel.isPosted.observeAsState(false)
    val collegeInfoList by collegeInfoViewModel.collegeInfoList.observeAsState(null)


    collegeInfoViewModel.getCollegeInfo()


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var title by remember {
        mutableStateOf("")
    }
    var imageUrl by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var link by remember {
        mutableStateOf("")
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect( isUploaded) {
        if(isUploaded){
            Toast.makeText(context, "CollegeInfo Updated", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }
    
    LaunchedEffect(collegeInfoList) {
        if(collegeInfoList != null) {
            title = collegeInfoList!!.name!!
            address = collegeInfoList!!.address!!
            description = collegeInfoList!!.description!!
            link = collegeInfoList!!.websiteLink!!
            imageUrl = collegeInfoList!!.imageUrl!!
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage College Info")
            },

                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()} ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        },

    ) {padding->
        Column (modifier = Modifier.padding(padding)){

            ElevatedCard( modifier = Modifier.padding(8.dp)) {

                OutlinedTextField(value = title, onValueChange ={
                    title = it
                }, placeholder = { Text(text = "College Name..." )},
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(value = address, onValueChange ={
                    address = it
                }, placeholder = { Text(text = "College Address..." )},
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(value = description, onValueChange ={
                    description = it
                }, placeholder = { Text(text = "College Description..." )},
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(value = link, onValueChange ={
                    link = it
                }, placeholder = { Text(text = " Website link..." )},
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )



                Image( painter = if(imageUrl != "") rememberAsyncImagePainter(
                    model = imageUrl)
                else if(imageUri == null) painterResource(id = R.drawable.picker )
                    else rememberAsyncImagePainter(model = imageUri),
                    contentDescription = BANNER ,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth()
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )
                Row {
                    Button(onClick = {
                        if( title == "" || link == "" || address== "" || description ==""){
                            Toast.makeText(context, "Please provide all fields", Toast.LENGTH_SHORT)
                        }
                        else if (imageUri == null){
                            Toast.makeText(
                                context,
                                "Please Provide Image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else if (imageUrl != ""){
                            collegeInfoViewModel.uploadImage(imageUrl,title, address, description, link)

                        }
                        else
                            collegeInfoViewModel.saveImage(imageUri!!,title, address, description, link)

                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)) {
                        Text(text = "Update College Info")
                    }

                }

            }
        }

    }
}
