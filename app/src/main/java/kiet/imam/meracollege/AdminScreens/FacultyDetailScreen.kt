package kiet.imam.meracollege.AdminScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kiet.imam.meracollege.itemview.TeacherItemView
import kiet.imam.meracollege.ui.theme.Purple40
import kiet.imam.meracollege.viewmodel.FacultyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyDetailScreen(navController : NavHostController, catName : String){

    val context = LocalContext.current
    val facultyViewModel : FacultyViewModel = viewModel()

    val facultyList by facultyViewModel.facultyList.observeAsState(null)


    facultyViewModel.getFaculty(catName)


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage Faculty")
            },

                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()} ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {padding->

        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier.padding(padding)) {
            items(facultyList?: emptyList()){
                TeacherItemView(facultyModel = it, delete = {facultyModel->
                    facultyViewModel.deleteFaculty(facultyModel)
                })
            }
        }

    }


}