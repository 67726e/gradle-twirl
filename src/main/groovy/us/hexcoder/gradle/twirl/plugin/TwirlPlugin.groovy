package us.hexcoder.gradle.twirl.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.scala.ScalaPlugin
import us.hexcoder.gradle.twirl.task.TwirlCompileTask
import us.hexcoder.gradle.twirl.task.TwirlCompileTestTask
/**
 * @author 67726e
 */
class TwirlPlugin implements Plugin<Project> {
	@Override
	void apply(Project project) {
		project.getPlugins().apply(ScalaPlugin.class)
		project.extensions.create("twirl", TwirlExtension.class)

		// Add the generated source directory for Twirl templates to the Scala source set
		project.sourceSets.main.scala.srcDirs project.twirl.targetDirectory
		project.sourceSets.test.scala.srcDirs project.twirl.testTargetDirectory

		// Compile the source templates before the source code is run
		applyDependency(project, "compileJava", "compileTwirl")
		applyDependency(project, "compileScala", "compileTwirl")

		// Compile the test templates before the source code is run
		applyDependency(project, "compileTestJava", "compileTestTwirl")
		applyDependency(project, "compileTestScala", "compileTestTwirl")

		// Add tasks to run individual commands for compiling templates
		project.task("compileTwirl", type: TwirlCompileTask)
		project.task("compileTestTwirl", type: TwirlCompileTestTask)
	}

	static def applyDependency(Project project, String taskName, String dependency) {
		for (Task task : project.getTasksByName(taskName, true)) {
			task.dependsOn(dependency)
		}
	}
}
