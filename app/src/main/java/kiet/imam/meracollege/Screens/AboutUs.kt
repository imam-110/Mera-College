package kiet.imam.meracollege.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kiet.imam.meracollege.ui.theme.SkyBlue
import kiet.imam.meracollege.viewmodel.CollegeInfoViewModel

@Composable
fun AboutUs(navController : NavHostController){
    val collegeInfoViewModel : CollegeInfoViewModel = viewModel()
    val collegeInfoList by collegeInfoViewModel.collegeInfoList.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

   Column(modifier = Modifier.padding(8.dp)) {
       if(collegeInfoList!= null){
           
           
           Image(painter = rememberAsyncImagePainter(model = collegeInfoList!!.imageUrl),
               contentDescription ="College Image",
               modifier = Modifier
                   .height(220.dp)
                   .fillMaxWidth(),
               contentScale = ContentScale.Crop
               )
           Spacer(modifier = Modifier.height(25.dp))
           Text(
               text = collegeInfoList!!.name!!,
               fontWeight = FontWeight.Bold,
               fontSize = 18.sp
           )
           Spacer(modifier = Modifier.height(20.dp))
           Text(
               text = collegeInfoList!!.address!!,
               fontWeight = FontWeight.Normal,
               fontSize = 16.sp
           )
           Spacer(modifier = Modifier.height(20.dp))
           Text(
               text = collegeInfoList!!.description!!,
               fontWeight = FontWeight.Thin,
               fontSize = 14.sp
           )
           Spacer(modifier = Modifier.height(20.dp))


           Text(
               text = collegeInfoList!!.websiteLink!!,
               fontWeight = FontWeight.Light,
               fontSize = 18.sp,
               color = SkyBlue
           )
          


       }

   }

}