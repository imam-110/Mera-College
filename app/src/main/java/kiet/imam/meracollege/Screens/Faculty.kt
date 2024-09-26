package kiet.imam.meracollege.Screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kiet.imam.meracollege.Navigation.Routes
import kiet.imam.meracollege.itemview.FacultyItemView
import kiet.imam.meracollege.viewmodel.FacultyViewModel

@Composable
fun Faculty(navController : NavHostController){
    val facultyViewModel : FacultyViewModel = viewModel()
    val categoryList by facultyViewModel.categoryList.observeAsState(null)
    facultyViewModel.getCategory()


    LazyColumn {
        items(categoryList?: emptyList()){
            FacultyItemView(it, delete = {docId->
                facultyViewModel.deleteCategory(docId)

            }, onClick = {categoryName->
                val routes = Routes.FacultyDetail.route.replace("{catName}", categoryName)

                navController.navigate(routes)
            })
        }
    }

}