package main.scala.org.visualNeo

import javax.swing.ImageIcon
import javax.imageio.ImageIO
import java.io.File

class Model {

  var currentLevel: KeyboardLevel = Level1
  var currentOpacity: Float = 0.75f

}

sealed trait KeyboardLevel {
  val level: Int

  def image = {
    def getFile(s: String): Option[File] = Some(new File(s)).filter(_.isFile)
    val file = {
      val name = "neo_level" + level + ".png"
      getFile(name) orElse
        getFile("src/main/resources/" + name) orElse
        getFile("C:/Temp/VisualNeoData/" + name) getOrElse
        (throw new Exception("Could not find Images!"))
    }

    ImageIO.read(file)
  }

  override def toString = "KeyboardLevel:Level" + level
}

object Level1 extends KeyboardLevel {
  val level = 1
}

object Level2 extends KeyboardLevel {
  val level = 2
}

object Level3 extends KeyboardLevel {
  val level = 3
}

object Level4 extends KeyboardLevel {
  val level = 4
}

object Level5 extends KeyboardLevel {
  val level = 5
}

object Level6 extends KeyboardLevel {
  val level = 6
}

object LevelNone extends KeyboardLevel {
  val level = 0
}