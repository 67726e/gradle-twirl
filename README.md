# Gradle Twirl Templates Plugin
A Gradle plugin to provide Twirl template compilation and integration for your projects.

## Install
To use the Gradle plugin, just add the following to your `build.gradle` file.

```groovy
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "us.hexcoder:gradle-twirl:1.0.0-SNAPSHOT"
  }
}

apply plugin: "twirl"
```

If you are using the Twirl templates with Java, you will need to enable Scala's joint-compilation by assigning all Java
source directories to the Scala `sourceSet` and setting the Java `sourceSet` to be empty. Here is a default configuration:

```groovy
apply plugin: "scala"

sourceSets {
	// See: http://forums.gradle.org/gradle/topics/how_to_compile_a_java_class_that_depends_on_a_scala_class_in_gradle
	main {
		scala {
			srcDir "src/main/java"
		}

		java {
			srcDirs = []
		}
	}
}
```

## Customization

The plugin should work out-of-the-box for most users. All you need are your source templates under `src/main/twirl` for
the templates to be picked up and compiled. If the defaults are not sufficient for you, you can use the plugin 
configuration to tweak things to your liking.

```groovy
twirl {
	sourceDirectory = "src/main/twirl"
	testSourceDirectory = "src/test/twirl"
	targetDirectory = "build/generated-sources/main/twirl"
	testTargetDirectory = "build/generated-sources/test/twirl"
	imports = ["java.lang._", "java.util._", "scala.collection.JavaConversions._", "scala.collection.JavaConverters._"]
	charset = "UTF8"
}
```

## Tasks
The plugin makes two tasks available, `compileTwirl` and `compileTestTwirl`. These commands work in the same way that
commands like `compileJava` or `compileTestGroovy` would work. They compile from the source or test directories
respectively and output them into the appropriate directory. These tasks will be run before the `compileScala` or
`compileTestScala` tasks as available and as needed.
