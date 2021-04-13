import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
	id("org.jetbrains.compose") version "0.4.0-build180"
}

group = "in.imoz"
version = "0.1-alpha"

repositories {
    mavenCentral()
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
	maven("https://kotlin.bintray.com/kotlinx")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
		jvmTarget = "1.8"
		useIR = true
	}
}

dependencies {
	implementation(compose.desktop.currentOs)
	implementation("org.jsoup:jsoup:1.13.1")
}

compose.desktop {
	application {
		mainClass = "MainKt"
	}
}
