import java.util.zip.ZipEntry
import java.util.zip.ZipFile

plugins {
    id("net.fabricmc.fabric-loom") version "1.16-SNAPSHOT"
    `maven-publish`
}

group = property("maven_group") as String
version = "${property("mod_version")}+fabric-mc${property("minecraft_version")}"

base {
    archivesName.set(property("archives_base_name") as String)
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://api.modrinth.com/maven") {
        content { includeGroup("maven.modrinth") }
    }
    flatDir {
        dirs("libs", "../../create-rei/CreateReiViewer-Fly/build/libs")
    }
}

val recipeViewer = ":CreateReiViewer:${property("createreiviewer_version")}+fabric-mc${property("minecraft_version")}"

loom {
    mods {
        create("bellsandwhistles") {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    implementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")
    implementation("maven.modrinth:create-fly:${property("create_fabric_version")}")

    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    include(recipeViewer)
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
}

tasks.processResources {
    val modMetadata = mapOf(
        "version" to project.version.toString(),
        "minecraft_dependency_version" to project.property("minecraft_dependency_version") as String,
        "fabric_loader_version" to project.property("fabric_loader_version") as String,
        "create_fabric_version_range" to project.property("create_fabric_version_range") as String,
    )
    inputs.properties(modMetadata)
    filesMatching("fabric.mod.json") {
        expand(modMetadata)
    }
}

tasks.jar {
    from("LICENSE")
    from("NOTICE")
}

tasks.named<Jar>("sourcesJar") {
    from("LICENSE")
    from("NOTICE")
}

val allowedJarPrefixes = listOf(
    "systems/alexander/bellsandwhistles/",
    "assets/bellsandwhistles/",
    "data/bellsandwhistles/",
    "data/minecraft/tags/",
    "META-INF/",
    "LICENSE",
    "NOTICE",
    "fabric.mod.json",
)

afterEvaluate {
    val checkForeignNamespaces = tasks.register("checkForeignNamespaces") {
        val jarNames = listOf("remapJar", "remapSourcesJar", "jar", "sourcesJar")
                .filter { tasks.names.contains(it) }
                .let { names -> if (names.contains("remapJar")) names.filter { it.startsWith("remap") } else names }
        require(jarNames.isNotEmpty()) { "checkForeignNamespaces found no jar task to inspect" }
        val jarTasks = jarNames.map { tasks.named<Jar>(it).get() }
        dependsOn(jarTasks)
        val archives: List<Provider<RegularFile>> = jarTasks.map { it.archiveFile }
        val prefixes = allowedJarPrefixes
        doLast {
            val bad = mutableListOf<String>()
            var checked = 0
            for (provider in archives) {
                val jar: File = provider.get().asFile
                if (!jar.exists()) continue
                checked++
                val zip = ZipFile(jar)
                try {
                    val entries = zip.entries()
                    while (entries.hasMoreElements()) {
                        val entry: ZipEntry = entries.nextElement()
                        if (entry.isDirectory) continue
                        val name: String = entry.name
                        if (prefixes.none { p -> name.startsWith(p) }) {
                            bad.add(jar.name + "!" + name)
                        }
                    }
                } finally {
                    zip.close()
                }
            }
            if (checked == 0) {
                throw GradleException("checkForeignNamespaces inspected no archives")
            }
            if (bad.isNotEmpty()) {
                throw GradleException("Foreign namespace entries in published artifacts:\n" + bad.joinToString("\n"))
            }
        }
    }
    tasks.named("check") { dependsOn(checkForeignNamespaces) }
}
