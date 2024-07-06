val scala3Version = "3.4.1"

lazy val root = project
  .in(file("."))
  .aggregate(core, app)
  .settings(
    name := "ScalaProject",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0",
      "dev.zio" %% "zio-json" % "0.4.2",
      "dev.zio" %% "zio-nio" % "2.0.2",
      "dev.zio" %% "zio-test" % "2.1.5" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.5" % Test,
      "org.scalatest" %% "scalatest" % "3.2.12" % Test
    )
  )

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0",
      "dev.zio" %% "zio-json" % "0.4.2",
      "org.scalatest" %% "scalatest" % "3.2.12" % Test
    )
  )

lazy val app = (project in file("app"))
  .dependsOn(core)
  .settings(
    name := "app",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0",
      "dev.zio" %% "zio-json" % "0.4.2",
      "dev.zio" %% "zio-nio" % "2.0.2",
      "dev.zio" %% "zio-test" % "2.1.5" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.5" % Test,
      "org.scalatest" %% "scalatest" % "3.2.12" % Test
    )
  )
