//import com.github.retronym.SbtOneJar.oneJarSettings
//import sbtassembly.Plugin._
//import AssemblyKeys._

name := "VisualNeo"

organization := "org.visualNeo"

version := "0.1-SNAPSHOT"

scalaVersion := "2.9.2"

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies ++= Seq (
        "org.scala-lang" % "scala-swing" % "2.9.2",
        "commons-lang" % "commons-lang" % "2.6")

//assemblySettings

//mainClass in assembly := Some("org.visualNeo.Main") //Some("main.main.java.main.java.org.jnativehook.example.NativeHookDemo") //

initialCommands in console := """
    import org.visualNeo._
"""