package me.tylerbwong.gradle.metalava.extension

import me.tylerbwong.gradle.metalava.Documentation
import me.tylerbwong.gradle.metalava.Format
import me.tylerbwong.gradle.metalava.Signature
import org.gradle.api.JavaVersion
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import javax.inject.Inject

open class MetalavaExtension @Inject constructor(
    objectFactory: ObjectFactory,
) {
    /**
     * The version of Metalava to use.
     */
    val version: Property<String> = objectFactory.property { set("1.0.0-alpha06") }

    /**
     * A custom Metalava JAR location path to use instead of the embedded dependency.
     */
    val metalavaJarPath: Property<String> = objectFactory.property { set("") }

    /**
     * Sets the source level for Java source files; default is 11.
     */
    val javaSourceLevel: Property<JavaVersion> = objectFactory.property {
        set(JavaVersion.VERSION_11)
    }

    /**
     * @see Format
     */
    val format: Property<Format> = objectFactory.property { set(Format.V4) }

    /**
     * @see Signature
     */
    val signature: Property<Signature> = objectFactory.property { set(Signature.API) }

    /**
     * The final descriptor file output name.
     */
    val filename: Property<String> = objectFactory.property { set("api.txt") }

    /**
     * @see Documentation
     */
    val documentation: Property<Documentation> = objectFactory.property {
        set(Documentation.PROTECTED)
    }

    /**
     *  Type is one of 'api' and 'removed', which checks either the public api or the removed api.
     */
    val apiType: Property<String> = objectFactory.property { set("api") }

    /**
     * Controls whether nullness annotations should be formatted as in Kotlin (with "?" for nullable
     * types, "" for non-nullable types, and "!" for unknown. The default is true.
     */
    val outputKotlinNulls: Property<Boolean> = objectFactory.property { set(true) }

    /**
     * Controls whether default values should be included in signature files. The default is true.
     */
    val outputDefaultValues: Property<Boolean> = objectFactory.property { set(true) }

    /**
     * Whether the signature files should include a comment listing the format version of the
     * signature file. The default is true.
     */
    val includeSignatureVersion: Property<Boolean> = objectFactory.property { set(true) }

    /**
     * Remove the given packages from the API even if they have not been marked with @hide.
     */
    val hiddenPackages: SetProperty<String> = objectFactory.setProperty()

    /**
     * Treat any elements annotated with the given annotation as hidden.
     */
    val hiddenAnnotations: SetProperty<String> = objectFactory.setProperty()

    /**
     * Whether the signature file being read should be interpreted as having encoded its types using
     * Kotlin style types: a suffix of "?" for nullable types, no suffix for non nullable types, and
     * "!" for unknown. The default is false.
     */
    val inputKotlinNulls: Property<Boolean> = objectFactory.property { set(false) }

    /**
     * Promote all warnings to errors.
     */
    val reportWarningsAsErrors: Property<Boolean> = objectFactory.property { set(false) }

    /**
     * Promote all API lint warnings to errors.
     */
    val reportLintsAsErrors: Property<Boolean> = objectFactory.property { set(false) }

    /**
     * The directories to search for source files. An exception will be thrown if the named
     * directories are not direct children of the project root. The default is "src".
     *
     */
    val sourcePaths: ConfigurableFileCollection = objectFactory.fileCollection()
        .apply { setFrom("src") }

    /**
     * If the tasks should run as part of Gradle's `check` task. The default is true.
     */
    val enforceCheck: Property<Boolean> = objectFactory.property { set(true) }

    /**
     * Generate a file with keep rules at the specified location. The default is `null` which will
     * cause no keep file to be generated.
     */
    val keepFilename: Property<String?> = objectFactory.property { set(null) }

    private inline fun <reified T> ObjectFactory.property(
        configuration: Property<T>.() -> Unit = {}
    ) = property(T::class.java).apply { configuration() }

    private inline fun <reified T> ObjectFactory.setProperty(
        configuration: SetProperty<T>.() -> Unit = {}
    ) = setProperty(T::class.java).apply { configuration() }
}
