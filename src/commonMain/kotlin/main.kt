import com.soywiz.kds.Stack
import com.soywiz.klock.infiniteTimes
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.*
import com.soywiz.korge.input.onClick
import com.soywiz.korge.time.delay
import com.soywiz.korge.tween.*
import com.soywiz.korge.ui.iconButton
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing



suspend fun main() = Korge(width = 1200, height = 512, bgcolor = Colors["#2b2b2b"]) {

	val numberOfDiscs = 30

	val links = Tower(name = "links", x = 150, height = 300)
	val mitte = Tower(name = "mitte", x = 550, height = 300)
	val rechts = Tower(name = "rechts", x = 950, height = 300)

	addChild(links.view)
	addChild(mitte.view)
	addChild(rechts.view)

	repeat(numberOfDiscs){
		val maxWidth = 150
		val reductionStep = (maxWidth - 10) / numberOfDiscs
		links.discs.add(Disc(
				width = maxWidth - reductionStep * it,
				height = 10
		).apply { this@Korge.addChild(this.view) })
	}


	addUpdater { links.refresh()
		mitte.refresh()
		rechts.refresh() }


	textButton {}.onClick {
			solveHanoi(numberOfDiscs, links, mitte, rechts)
			}
}




fun moveDisk(source: Tower, destination: Tower) {
	if (source.discs.size > 0) {
		val lastDiscSource = source.discs.last()
		source.discs.remove(lastDiscSource)
		destination.discs.add(lastDiscSource)
	}
	//println("Bewege Scheibe von ${source.name} nach ${destination.name}")


}

class Disc(width : Int, height: Int){
	val colors = listOf(Colors.ORANGE, Colors.AQUA, Colors.CORAL, Colors.YELLOWGREEN, Colors.BEIGE, Colors.FIREBRICK)
	var view = SolidRect(width, height, colors.random())
}

class Tower(val name: String, val x : Int = 0, val height : Int = 300){
	val view = SolidRect(20, height, Colors.WHITE).xy(x, 512 - height - 20)
	var discs = listOf<Disc>().toMutableList()

	fun refresh(){
		discs.forEachIndexed { index, disc ->
			disc.view.x = this.x - disc.view.width/2 + this.view.width/2
			disc.view.y = this.view.y+this.view.height - disc.view.height * index
		}
	}
}
