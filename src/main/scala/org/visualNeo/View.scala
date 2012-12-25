package main.scala.org.visualNeo

import java.awt.{Toolkit, Image}
import swing._
import swing.event._
import javax.swing.{UIManager, SwingUtilities}
import javax.swing.plaf.nimbus.NimbusLookAndFeel
import javax.swing.plaf.synth.SynthLookAndFeel

class View(control: Control) extends Frame {

  val dim = new Dimension(750, 165)
  val pictureComponent = new PictureComponent(Level1.image, true, true)
  def thisScale = size.getWidth / size.getHeight

  var resized = false

  initialize

  def initialize = {
    title = "VisualNeo"
    contents = pictureComponent

    setPreferredContentSize(dim)
    location = new Point(
      screenSize.width - taskBarSize.right - preferredSize.width,
      screenSize.height - taskBarSize.bottom - preferredSize.height)

    pack
    setPicture(Level1)
    peer.setAlwaysOnTop(true)

    pack
  }

  override def closeOperation = control.exit
  def horizontalInsets = peer.getInsets.left + peer.getInsets.right
  def verticalInsets = peer.getInsets.top + peer.getInsets.bottom
  def screenSize = Toolkit.getDefaultToolkit.getScreenSize
  def taskBarSize = Toolkit.getDefaultToolkit().getScreenInsets(peer.getGraphicsConfiguration());

  def setPicture(pic: KeyboardLevel) = {
    pictureComponent.setPicture(pic.image)
  }

  def setOpacity(f: Float) = if (peer.isUndecorated) peer.setOpacity(f)

  def setPreferredContentSize(d: Dimension) =
    preferredSize = new Dimension(d.width + horizontalInsets, d.height + verticalInsets)

  var currentSize = size
  def updateResize = if (currentSize != size) {
    println("resize")
    val picScale = pictureComponent.picScale
    setPreferredContentSize(
      if (thisScale > picScale)
        new Dimension((size.height * picScale).toInt, size.height)
      else
        new Dimension(size.width, (size.width / picScale).toInt))
    pack
    pictureComponent.preferredSize = new Dimension(size.getWidth.toInt - horizontalInsets, size.getHeight.toInt - verticalInsets)
    pictureComponent.update
    currentSize = size
  }
}


class PictureComponent(var pic: Image, scale: Boolean, center: Boolean) extends Component {

  var scaledPic = pic

  def thisScale = size.getWidth / size.getHeight
  def picWidth  = scaledPic.getWidth(null)
  def picHeight = scaledPic.getHeight(null)
  def picScale  = picWidth.asInstanceOf[Double] / picHeight

  def setPicture(newPic: Image) = {
    pic = newPic
    update
  }

  private def getScaledPic(pic: Image): Image =
    if (thisScale > picScale) // frame wider than picture
      pic.getScaledInstance(-1, this.size.height, Image.SCALE_SMOOTH)
    else // frame higher than picture (or equal)
      pic.getScaledInstance(this.size.width, -1, Image.SCALE_SMOOTH)

  def update {
    if (this.size.width > 0 && this.size.height > 0)
      scaledPic = getScaledPic(pic)
    repaint
  }

  override def paintComponent(g: Graphics2D) {
    if (!center) {
      if (!g.drawImage(scaledPic, 0, 0, null))
        ()//println("Failed to draw pic!!")
    }
    else {
      val x = (size.width - picWidth) / 2
      val y = (size.height - picHeight) / 2
      if (!g.drawImage(scaledPic, x, y, null))
        ()//println("Failed to draw pic!!")
    }
  }
}