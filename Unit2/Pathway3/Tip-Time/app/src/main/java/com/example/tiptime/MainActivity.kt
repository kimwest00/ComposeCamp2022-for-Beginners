package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

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
    var number by remember { mutableStateOf("") }
    val numberDoubleType = number.toDoubleOrNull() ?:0.0
    val tip = calculateTip(numberDoubleType)
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = stringResource(R.string.title), style = TextStyle(
            fontSize = 20.sp
        ))
        EditNumberField(
            value = number,
            onValueChange = {number = it}
        )
        Text(text = stringResource(R.string.result,tip), style = TextStyle(
            fontSize = 18.sp, fontWeight = FontWeight.Bold
        ))
    }
}
@Composable
fun EditNumberField(
    value: String,
    onValueChange:(String)->Unit,
){
    TextField(
        // Other parameters
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = value,
        label = { Text(stringResource(R.string.hint_text)) },
        onValueChange = onValueChange,)
}
private fun calculateTip(
    amount:Double,
    tipPercent:Double = 15.0
):String{
    val tip = amount / 100*amount
    //통화 형식으로 지정
    return NumberFormat.getCurrencyInstance().format(tip)
}
@Preview(showBackground = true)
@Composable
fun DefaultPreiview() {
    TipTimeTheme {
        Caculate(
            Modifier.fillMaxSize())
    }
}