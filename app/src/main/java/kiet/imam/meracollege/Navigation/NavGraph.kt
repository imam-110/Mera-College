package kiet.imam.meracollege.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kiet.imam.meracollege.AdminScreens.AdminDashboard
import kiet.imam.meracollege.AdminScreens.FacultyDetailScreen
import kiet.imam.meracollege.AdminScreens.ManageBanner
import kiet.imam.meracollege.AdminScreens.ManageCollegeInfo
import kiet.imam.meracollege.AdminScreens.ManageFaculty
import kiet.imam.meracollege.AdminScreens.ManageGallery
import kiet.imam.meracollege.AdminScreens.ManageNotice
import kiet.imam.meracollege.Screens.AboutUs
import kiet.imam.meracollege.Screens.BottomNav
import kiet.imam.meracollege.Screens.Faculty
import kiet.imam.meracollege.Screens.Gallery
import kiet.imam.meracollege.Screens.Home
import kiet.imam.meracollege.utils.Constant.isAdmin

@Composable
fun NavGraph(navController: NavHostController){


   NavHost(
       navController= navController,
       startDestination = if (isAdmin) Routes.AdminDashboard.route
       else Routes.BottomNav.route
   ){
         composable(Routes.BottomNav.route){
             BottomNav(navController)
         }
       composable(Routes.Home.route){
             Home(navController)
         }
       composable(Routes.Faculty.route){
             Faculty(navController)
         }

       composable(Routes.Gallery.route){
             Gallery(navController)
         }
       composable(Routes.AboutUs.route){
             AboutUs(navController)
         }
       composable(Routes.AdminDashboard.route){
           AdminDashboard(navController)
       }
       composable(Routes.ManageBanner.route){
           ManageBanner(navController)
       }
       composable(Routes.ManageGallery.route){
           ManageGallery(navController)
       }
       composable(Routes.ManageFaculty.route){
           ManageFaculty(navController)
       }
       composable(Routes.ManageCollegeInfo.route){
           ManageCollegeInfo(navController)
       }
       composable(Routes.ManageNotice.route){
           ManageNotice(navController)
       }
       composable(Routes.FacultyDetail.route){
           val data = it.arguments!!.getString("catName")
           FacultyDetailScreen(navController, data!! )
       }



   }

}