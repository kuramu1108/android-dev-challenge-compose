/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.DogDetail
import com.example.androiddevchallenge.ui.DogList
import com.example.androiddevchallenge.ui.navigation.Action
import com.example.androiddevchallenge.ui.navigation.Destination.DOG_DETAIL
import com.example.androiddevchallenge.ui.navigation.Destination.DOG_LIST
import com.example.androiddevchallenge.ui.navigation.PathParam.DOG_ID
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(viewModel)
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    val actions = remember(navController) { Action(navController = navController) }
    Surface(color = MaterialTheme.colors.background) {
        NavHost(navController = navController, startDestination = DOG_LIST) {
            composable(DOG_LIST) {
                DogList(
                    viewModel = viewModel,
                    onItemClick = actions.toDetail
                )
            }
            composable(
                route = "$DOG_DETAIL/{$DOG_ID}",
                arguments = listOf(navArgument(DOG_ID) { type = NavType.LongType })
            ) {
                val dogId = it.arguments?.getLong(DOG_ID) ?: -1L
                val dog = viewModel.getDog(dogId)
                DogDetail(dog = dog)
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(viewModel = MainViewModel())
    }
}

// @Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(viewModel = MainViewModel())
    }
}
