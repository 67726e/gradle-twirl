package us.hexcoder.gradle.twirl.task

import org.gradle.api.tasks.TaskAction

/**
 * @author 67726e
 */
class TwirlCompileTestTask extends AbstractCompileTask {
	public TwirlCompileTestTask() {
		setDescription("Compiles all Twirl templates in the ${project.twirl.testSourceDirectory}")
		inputs.dir project.twirl.testSourceDirectory
		outputs.dir project.twirl.testTargetDirectory
	}

	@TaskAction
	def compileTestTwirl() {
		logger.info("Compiling test Twirl templates")
		compile((String)project.twirl.testSourceDirectory, (String)project.twirl.testTargetDirectory)
	}
}
