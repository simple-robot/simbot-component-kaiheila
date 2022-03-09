rootProject.name = "simbot-component-kaiheila"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.10"
        kotlin("plugin.serialization") version "1.6.10"
        id("org.jetbrains.dokka") version "1.6.10"

        // see https://github.com/gradle-nexus/publish-plugin
        id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    }
}

include("simbot-component-kaiheila-api")
include("simbot-component-kaiheila-stdlib")
include("simbot-component-kaiheila-core")
// include("simbot-component-kaiheila-component-boot")