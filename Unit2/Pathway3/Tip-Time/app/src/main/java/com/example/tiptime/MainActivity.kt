package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptime.ui.theme.DarkGrey
import com.example.tiptime.ui.theme.MainBackGround
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
fun Caculate(modifier: Modifier=Modifier) {
    val focusManager = LocalFocusManager.current

    var billAmount by remember { mutableStateOf("") }
    var tipAmount by remember { mutableStateOf("") }
    var isRound by remember {
        mutableStateOf(false)
    }

    val billDoubleType = billAmount.toDoubleOrNull() ?:0.00
    val tipDoubleType = tipAmount.toDoubleOrNull() ?:0.00
    val tip = calculateTip(billDoubleType,tipDoubleType,isRound)


    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = stringResource(R.string.title), style = TextStyle(
            fontSize = 20.sp
        ))
        EditNumberField(
            value = billAmount,
            onValueChange = {billAmount = it},
            label = R.string.bill_amount_hint_text,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions (
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),


        )
        EditNumberField(
            value = tipAmount,
            onValueChange = {tipAmount = it},
            label = R.string.tip_text,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions (
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )
        RoundTipRow(isRound = isRound, onRoundedCheck = {
            isRound = it
        })
        Text(text = stringResource(R.string.result,tip), style = TextStyle(
            fontSize = 18.sp, fontWeight = FontWeight.Bold
        ))
    }
}
@Composable
fun EditNumberField(
    value: String,
    onValueChange:(String)->Unit,
    @StringRes label: Int,
    modifier: Modifier = Modifier.fillMaxWidth(),
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
){
    TextField(
        // Other parameters
        modifier = modifier,
        colors =  TextFieldDefaults.textFieldColors(
            backgroundColor = MainBackGround
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        value = value,
        label = { Text(stringResource(label)) },
        onValueChange = onValueChange,)
}

@Composable
fun RoundTipRow(
    modifier: Modifier=Modifier,
    isRound:Boolean = false,
    onRoundedCheck : (Boolean) -> Unit,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ){
        Text(text = stringResource(id = R.string.round_up_tip))
        Switch(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End)
            ,checked = isRound, onCheckedChange = onRoundedCheck
            , colors = SwitchDefaults.colors(
                uncheckedThumbColor = DarkGrey
            )
        )
    }
}

@VisibleForTesting
internal fun calculateTip(
    amount:Double,
    tipPercent:Double = 15.0,
    isRound: Boolean
):String{
    var tip = tipPercent / 100 * amount
    if(isRound){
        tip = kotlin.math.ceil(tip)
    }
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