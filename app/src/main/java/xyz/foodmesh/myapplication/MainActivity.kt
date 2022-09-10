package xyz.foodmesh.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import xyz.foodmesh.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApplicationTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					OpenInWhatsApp()
				}
			}
		}
	}
}


@Composable
fun OpenInWhatsApp() {
	var countryCode = 91;
	var phoneNumber by rememberSaveable { mutableStateOf("") }
	val context = LocalContext.current
	val focusRequester = FocusRequester()

	LaunchedEffect(Unit) {
		focusRequester.requestFocus()
	}
	Column(modifier = Modifier.padding(16.dp)) {
		Text(
			text = "Hello WhatsApp",
			modifier = Modifier.padding(bottom = 8.dp),
			style = MaterialTheme.typography.h5
		)
		OutlinedTextField(
			value = phoneNumber,
			onValueChange = {
				if (it.length <= 10) phoneNumber = it
			},
			label = { Text("Phone number") },
			maxLines = 1,
			modifier = Modifier.focusRequester(focusRequester),
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Phone,
			)
		)
		Button(onClick = {
			val url = "http://wa.me/$countryCode$phoneNumber"
			Uri.parse(url).openInBrowser(context = context)
		}, enabled = phoneNumber.length == 10) {
			Text(text = "Send Whatsapp")
		}
	}
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	MyApplicationTheme {
		OpenInWhatsApp()
	}
}

fun Uri?.openInBrowser(context: Context) {
	this ?: return // Do nothing if uri is null

	val browserIntent = Intent(Intent.ACTION_VIEW, this)
	ContextCompat.startActivity(context, browserIntent, null)
}
