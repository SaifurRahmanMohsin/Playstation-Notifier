import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import contract.Store
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

var storeStatusGlobal = mutableStateOf(Store.availableStores.map { it to false }.toMutableList())

fun main() = Window(title = "Playstation Notifier", size = IntSize(800, 200 + Store.availableStores.count() * 50)) {

	val lastUpdatedAt = remember { mutableStateOf(Calendar.getInstance()) }
	val storeStatus = remember { mutableStateOf(Store.availableStores.map { it to false }.toMutableList()) }
	storeStatusGlobal = storeStatus

	Timer().scheduleAtFixedRate(object : TimerTask() {
		override fun run() {
			lastUpdatedAt.value = Calendar.getInstance()
			updateAll()
		}
	}, 60, 60000)
	MaterialTheme {
		Column(Modifier.fillMaxWidth(1.0f).fillMaxHeight(8f).padding(0.dp, 50.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
			for (store in Store.availableStores) {
				Row {
					Text(store.key, modifier = Modifier.fillMaxWidth(0.3f))
					if ((storeStatus.value.first { it.first.key == store.key }).second) {
						Text("Available!!!", modifier = Modifier.fillMaxWidth(0.3f), color = Color.Red)

						val audioInputStream = AudioSystem.getAudioInputStream(Thread.currentThread().contextClassLoader.getResource("starwars.wav"))
						val clip = AudioSystem.getClip()
						clip.open(audioInputStream)
						clip.loop(Clip.LOOP_CONTINUOUSLY)
					} else {
						Text("Unavailable", modifier = Modifier.fillMaxWidth(0.3f))
					}
				}
			}
		}
		Column(Modifier.fillMaxWidth(1.0f).fillMaxHeight(2f), Arrangement.Bottom) {
			Button(modifier = Modifier.align(Alignment.CenterHorizontally),
				onClick = {
					// Recheck
					updateAll()
				}) {
				Text("Recheck all")
			}
			Text(String.format("Last checked on: %tR %1\$tp", lastUpdatedAt.value.time), modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp, 20.dp))
		}
	}
}


fun updateAll()
{
	for (store in Store.availableStores) {
		runBlocking {
			if (Store.create(store.value)!!.check()) {
				storeStatusGlobal.value = storeStatusGlobal.value.map { if (it.first.key == store.key) store to true else it }.toMutableList()
			}
		}
	}
}
