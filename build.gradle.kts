plugins {
    id("fabric-loom") version "1.14-SNAPSHOT"
    id("maven-publish")
}

version = "${project.property("mod_version")}+mc${project.property("minecraft_version")}"
group = project.property("maven_group") as String

base {
    archivesName = project.property("archives_base_name") as String
}

loom {
    mixin {
        defaultRefmapName.set("whoslow-refmap.json")
        useLegacyMixinAp.set(true)
    }
}

repositories {
    maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }
    maven { url = uri("https://maven.shedaniel.me/") }
    maven { url = uri("https://maven.terraformersmc.com/releases") }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    modApi("me.shedaniel.cloth:cloth-config-fabric:${project.property("cloth_config_version")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    modCompileOnly("com.terraformersmc:modmenu:${project.property("modmenu_version")}")
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.processResources {
    inputs.property("version", project.version)
    filteringCharset = "UTF-8"
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = 21
}

tasks.jar {
    from("LICENSE")
}
