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
import kiet.imam.meracollege.viewmodel.NoticeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNotice(navController : NavHostController){
    val context = LocalContext.current
    val noticeViewModel : NoticeViewModel = viewModel()
    val isUploaded by noticeViewModel.isPosted.observeAsState(false)
    val isDeleted by noticeViewModel.isDeleted.observeAsState(false)
    val bannerList by noticeViewModel.bannerList.observeAsState(null)


    noticeViewModel.getNotice()


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var isNotice by remember {
        mutableStateOf<Boolean>(false)
    }
    var title by remember {
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
            Toast.makeText(context, "Notice Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isNotice = false
        }
    }

    LaunchedEffect( isDeleted) {
        if(isDeleted){
            Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage Notice")
            },

                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()} ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
               isNotice = true
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription =null)
            }
        }
    ) {padding->
        Column (modifier = Modifier.padding(padding)){

            if(isNotice) ElevatedCard( modifier = Modifier.padding(8.dp)) {

               OutlinedTextField(value = title, onValueChange ={
                   title = it
               }, placeholder = { Text(text = "Notice title..." )},
                   modifier = Modifier.padding(4.dp).fillMaxWidth()
               )
                OutlinedTextField(value = link, onValueChange ={
                   link = it
               }, placeholder = { Text(text = " Notice link..." )},
                   modifier = Modifier.padding(4.dp).fillMaxWidth()
               )
                


                Image( painter = if(imageUri == null) painterResource(id = R.drawable.picker )

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
                        if(imageUri == null || title == "" || link == ""){
                            Toast.makeText(context, "Please provide all fields", Toast.LENGTH_SHORT)
                        } else
                        noticeViewModel.saveNotice(imageUri!!, title, link)

                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)) {
                        Text(text = "Add Notice")
                    }
                    OutlinedButton(onClick = { imageUri = null
                                             isNotice = false},
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)) {
                        Text(text = "Cancel")
                    }
                }

            }
            LazyColumn {
                items(bannerList?: emptyList()){
                    NoticeItemView(noticeModel = it, delete = {docId->
                        noticeViewModel.deleteNotice(docId)

                    })
                }
            }
        }

    }
}
