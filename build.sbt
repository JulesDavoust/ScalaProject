
val scala3Version = "3.4.1"

lazy val root = project
  .in(file("."))
  .aggregate(core)
  .settings(
    name := "ScalaProject",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
    //   "dev.zio" %% "zio" % "2.0.5",
    //   "dev.zio" %% "zio-json" % "0.3.0-RC10",
      "org.scalatest" %% "scalatest" % "3.2.10" % Test
    )
    // Compile / mainClass := Some("graph.Main")
  )

lazy val app = (project in file("app"))
  .dependsOn(core)
  .settings(
    name := "app",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.5"
    )
  )
