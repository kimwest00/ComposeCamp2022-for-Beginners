package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptime.ui.theme.TipTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreiview()
        }
    }
}

@Composable
fun Caculate(modifier: Modifier) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Text(text = stringResource(R.string.title), style = TextStyle(
            fontSize = 20.sp
        ))
        TextField(value = TextFieldValue(stringResource(R.string.hint_text)), onValueChange = {})
        Text(text = stringResource(R.string.result), style = TextStyle(
            fontSize = 18.sp, fontWeight = FontWeight.Bold
        ))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreiview() {
    TipTimeTheme {
        Caculate(
            Modifier.fillMaxSize())
    }
}