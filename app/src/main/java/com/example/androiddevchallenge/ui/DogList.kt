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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.MainViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Dog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DogList(
    viewModel: MainViewModel,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val grouped = viewModel.dogs.groupBy { it.species }
        grouped.forEach { (species, dogs) ->
            stickyHeader {
                DogListGroupHeader(title = species, Modifier.fillMaxWidth())
            }
            items(viewModel.dogs) { dog ->
                DogListItem(
                    dog = dog,
                    onItemClick = onItemClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DogListItem(
    dog: Dog,
    onItemClick: (String) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(
                onClick = {
                    onItemClick(dog.id.toString())
                }
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = dog.name.capitalize())
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_right_24),
            contentDescription = null,
        )
    }
}

@Composable
fun DogListGroupHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        title.capitalize(),
        Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        fontWeight = FontWeight.Bold,
    )
}

@Preview("Test", widthDp = 360, heightDp = 640)
@Composable
fun Preview() {
    DogList(viewModel = MainViewModel(), onItemClick = { }, modifier = Modifier.fillMaxWidth())
}

@Preview("item", widthDp = 360)
@Composable
fun PreviewItem() {
    val dog = Dog(1, "xxxx", "dogggggy", 3)
    DogListItem(dog = dog, onItemClick = { }, modifier = Modifier.fillMaxWidth())
}

@Preview("header", widthDp = 360)
@Composable
fun PreviewHeader() {
    DogListGroupHeader("test", modifier = Modifier.fillMaxWidth())
}
