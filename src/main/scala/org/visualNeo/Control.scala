package main.scala.org.visualNeo

import org.jnativehook.keyboard.{NativeKeyListener, NativeKeyEvent}
import org.jnativehook.GlobalScreen
import org.jnativehook.mouse.{NativeMouseEvent, NativeMouseListener}

class Control extends NativeKeyListener with NativeMouseListener {

  val view = inizializeView
  val model = new Model

  var k1 = false // key shift
  var k2 = false // key caps_lock
  var k3 = false // key backslash (on an english keyboard)

  def inizializeView = {
    val v = new View(this)
    GlobalScreen.getInstance.addNativeKeyListener(this)
    GlobalScreen.getInstance.addNativeMouseListener(this)
    GlobalScreen.registerNativeHook
    v.open
    v
  }

  def exit = {
    GlobalScreen.unregisterNativeHook()
    sys.exit(0)
  }

  def setLevel(level: KeyboardLevel) =
    if (level != model.currentLevel) {
      model.currentLevel = level
      view.setPicture(level)
      println(model.currentLevel)
    }

  def update = {
    val oldLevel = model.currentLevel
    val newLevel = (k1,k2,k3) match {
      case (false, false, false) => Level1
      case (true,  false, false) => Level2
      case (false, true,  false) => Level3
      case (false, false, true)  => Level4
      case (true,  true,  false) => Level5
      case (false, true,  true)  => Level6
      case (true,  false, true)  => LevelNone // non-existing combination
      case (true,  true,  true)  => LevelNone // non-existing combination
    }
    if (newLevel != oldLevel)
      setLevel(newLevel)
  }

  override def nativeKeyPressed(e: NativeKeyEvent) = {
    e.getRawCode match {
      case 160 => k1 = true
      case 161 => k1 = true
      case 226 => k2 = true
      case 223 => k3 = true
      case _ => ()
    }
    update
  }

  override def nativeKeyReleased(e: NativeKeyEvent) = {
    e.getRawCode match {
      case 160 => k1 = false
      case 161 => k1 = false
      case 226 => k2 = false
      case 223 => k3 = false
      case _ => ()
    }
    update
  }

  override def nativeKeyTyped(e: NativeKeyEvent) = {}

  def nativeMouseClicked(p1: NativeMouseEvent) = {}

  def nativeMousePressed(p1: NativeMouseEvent) = {}

  def nativeMouseReleased(p1: NativeMouseEvent) = view.updateResize
}
