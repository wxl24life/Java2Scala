
About how-to create a scala project in Intellij Idea:

[1] [BUILDING A SCALA PROJECT WITH INTELLIJ AND SBT](https://docs.scala-lang.org/getting-started-intellij-track/building-a-scala-project-with-intellij-and-sbt.html)  
[2] [Your first Scala Hello World application](http://allaboutscala.com/tutorials/chapter-1-getting-familiar-intellij-ide/scala-tutorial-first-hello-world-application/)

Notes on 2019-07-17:

Right now, my concern is on code practicing, so I converted this project from sbt building tool to more familiar tool - maven.

The converting procedure is very simple:

1. Delete *build.sbt* and add *pom.xml*
2. Use File | Open..., select the pom.xml file, choose "Delete Existing Project and Import" in the message box that appears
3. Delete top level *project* directory in the root
4. Right click on the src/main/scala directory and *Mark Directory as* **Source Root**
5. Open some scala files and add scala SDK if needed