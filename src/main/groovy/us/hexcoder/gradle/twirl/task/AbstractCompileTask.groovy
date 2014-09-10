package us.hexcoder.gradle.twirl.task
import org.apache.maven.artifact.ant.shaded.DirectoryScanner
import org.gradle.api.DefaultTask
import play.twirl.api.HtmlFormat
import play.twirl.api.JavaScriptFormat
import play.twirl.api.TxtFormat
import play.twirl.api.XmlFormat
import play.twirl.compiler.TwirlCompiler
import scala.io.Codec

import java.nio.charset.Charset
/**
 * @author 67726e
 */
abstract class AbstractCompileTask extends DefaultTask {
	protected static final Map<String, String> FORMATTERS;

	static {
		FORMATTERS = new HashMap<>()
		FORMATTERS.put("html", HtmlFormat.class.getCanonicalName())
		FORMATTERS.put("xml", XmlFormat.class.getCanonicalName())
		FORMATTERS.put("txt", TxtFormat.class.getCanonicalName())
		FORMATTERS.put("js", JavaScriptFormat.class.getCanonicalName())
	}

	def compile(String sourceDirectory, String targetDirectory) {
		File source = new File(sourceDirectory)
		File target = new File(targetDirectory)
		String imports = project.twirl.imports.join("\r\n").replaceAll("(.+)", "import \$1")
		Codec codec = new Codec(Charset.forName((String)project.twirl.charset))

		final List<String> templates = findTemplates(sourceDirectory)

		for (String templatePath : templates) {
			final File templateFile = new File(templatePath)
			final String formatter = FORMATTERS.get(extensionOf(templateFile))
			logger.debug("Compiling Twirl template: " + templatePath)
			TwirlCompiler.compile(templateFile, source, target, formatter, imports, codec, false, false)
		}
	}

	def findTemplates(String sourceDirectory) {
		DirectoryScanner scanner = new DirectoryScanner()

		scanner.setBasedir(getProject().projectDir)
		scanner.setIncludes(sourceDirectory + "/**/*.scala.*")
		// TODO: Set excluded files - scanner.setExcludes()
		scanner.addDefaultExcludes()
		scanner.scan()

		return Arrays.asList(scanner.getIncludedFiles())
	}

	private static String extensionOf(File file) {
		String[] parts = file.getName().split("\\.", -1);
		return parts[parts.length - 1];
	}
}
