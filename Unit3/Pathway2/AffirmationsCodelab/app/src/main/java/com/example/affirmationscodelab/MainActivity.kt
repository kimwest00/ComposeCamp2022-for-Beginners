/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.affirmationscodelab
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.example.affirmationscodelab.data.Datasource
import com.example.affirmationscodelab.data.GridDataSource
import com.example.affirmationscodelab.model.Affirmation
import com.example.affirmationscodelab.model.Topic
import com.example.affirmationscodelab.ui.theme.AffirmationsTheme
import androidx.compose.foundation.lazy.grid.items
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GridApp()
        }
    }
}
@Preview
@Composable
fun GridApp(){
    AffirmationsTheme {
        GridView(GridDataSource.topics)
    }
}
@Composable
fun GridView(itemList:List<Topic>,modifier: Modifier=Modifier){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(itemList){item->
            GridCard(item)
        }
    }
}
@Composable
fun GridCard(topic: Topic){
    Card(
        elevation = 10.dp
    ){
        Row {
           Image(
               painter = painterResource(id = topic.imageResourceId),
               contentDescription = stringResource(id= topic.stringResourceId),
               modifier = Modifier
                   .width(68.dp)
                   .height(68.dp),
               contentScale = ContentScale.Fit
           )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = topic.stringResourceId),
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                   Text("✨")
                    Spacer(modifier = Modifier.width(8.dp))
                   Text(
                       topic.view.toString(),
                       style = MaterialTheme.typography.caption
                   )
                }

            }
        }
    }
}


@Composable
fun AffirmationApp() {
    AffirmationsTheme {
        // card UI test
        val dataSource = Datasource().loadAffirmations()
        AffirmationList(dataSource)
    }
}
@Composable
private fun AffirmationList(affirmationList:List<Affirmation>,modifier: Modifier=Modifier){
    LazyColumn {
        items(affirmationList){ affirmation ->
            AffirmationsCard(affirmation)
        }
    }
}
@Composable
fun AffirmationsCard(affirmation: Affirmation,modifier: Modifier = Modifier){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = modifier.padding(8.dp), elevation = 4.dp
        ){
            Column {
                Image(
                    painter = painterResource(affirmation.imageResourceId)
                    ,contentDescription = stringResource(affirmation.stringResourceId),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    contentScale = ContentScale.Crop

                )
                Text(
                    text =stringResource(affirmation.stringResourceId),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6
                )

            }
        }
    }

}