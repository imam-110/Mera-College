package kiet.imam.meracollege.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kiet.imam.meracollege.itemview.NoticeItemView
import kiet.imam.meracollege.ui.theme.SkyBlue
import kiet.imam.meracollege.viewmodel.BannerViewModel
import kiet.imam.meracollege.viewmodel.CollegeInfoViewModel
import kiet.imam.meracollege.viewmodel.NoticeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(navController : NavHostController){
    val bannerViewModel : BannerViewModel = viewModel()
    val bannerList by bannerViewModel.bannerList.observeAsState(null)
    bannerViewModel.getBanner()

    val collegeInfoViewModel : CollegeInfoViewModel = viewModel()
    val collegeInfoList by collegeInfoViewModel.collegeInfoList.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

    val noticeViewModel : NoticeViewModel = viewModel()
    val noticeList by noticeViewModel.bannerList.observeAsState(null)
    noticeViewModel.getNotice()
    val imageSlider = ArrayList<AsyncImagePainter>()
    if(bannerList != null) {
        bannerList!!.forEach {
            imageSlider.add(rememberAsyncImagePainter(model = it.url))
        }
    }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { imageSlider.size })


    LaunchedEffect(Unit) {
        try {
            while (true) {
                yield()
                delay(2600)
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % pagerState.pageCount)
            }
        }catch (e : Exception){
            
        }

    }



    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            HorizontalPager(state = pagerState) {pager->
                Card(Modifier.height(250.dp)) {
                    Image(painter = imageSlider[pager],
                        contentDescription = "banner",
                        contentScale = ContentScale.Crop,
                      modifier = Modifier
                          .height(250.dp)
                          .fillMaxWidth())
                }
                
            }

        }
        item {
             if(collegeInfoList!= null){
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
                 Spacer(modifier = Modifier.height(25.dp))
                 Text(
                     text = "Notices",
                     fontWeight = FontWeight.Bold,
                     fontSize = 18.sp
                 )
                 Spacer(modifier = Modifier.height(20.dp))


             }
        }

        items(noticeList ?: emptyList()){
            NoticeItemView(it, delete = {docId->
                noticeViewModel.deleteNotice(docId)

            })
        }
    }


}