/*
 *  Copyright (c) 2022-2022 ForteScarlet <ForteScarlet@163.com>
 *
 *  本文件是 simbot-component-kook 的一部分。
 *
 *  simbot-component-kook 是自由软件：你可以再分发之和/或依照由自由软件基金会发布的 GNU 通用公共许可证修改之，无论是版本 3 许可证，还是（按你的决定）任何以后版都可以。
 *
 *  发布 simbot-component-kook 是希望它能有用，但是并无保障;甚至连可销售和符合某个特定的目的都不保证。请参看 GNU 通用公共许可证，了解详情。
 *
 *  你应该随程序获得一份 GNU 通用公共许可证的复本。如果没有，请看:
 *  https://www.gnu.org/licenses
 *  https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *  https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 *
 *
 */

import util.systemProp
import java.time.Duration

/*
 *  Copyright (c) 2022 ForteScarlet <ForteScarlet@163.com>
 *
 *  本文件是 simbot-component-kaiheila 的一部分。
 *
 *  simbot-component-kaiheila 是自由软件：你可以再分发之和/或依照由自由软件基金会发布的 GNU 通用公共许可证修改之，无论是版本 3 许可证，还是（按你的决定）任何以后版都可以。
 *
 *  发布 simbot-component-kaiheila 是希望它能有用，但是并无保障;甚至连可销售和符合某个特定的目的都不保证。请参看 GNU 通用公共许可证，了解详情。
 *
 *  你应该随程序获得一份 GNU 通用公共许可证的复本。如果没有，请看:
 *  https://www.gnu.org/licenses
 *  https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *  https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 *
 *
 */

plugins {
    id("signing")
    id("maven-publish")
}

val isSnapshotOnly = (System.getProperty("snapshotOnly") ?: System.getenv("simbot.snapshotOnly")) != null
val isReleaseOnly = (System.getProperty("releaseOnly") ?: System.getenv("simbot.releaseOnly")) != null

val isPublishConfigurable = when {
    isSnapshotOnly -> P.ComponentKook.isSnapshot
    isReleaseOnly -> !P.ComponentKook.isSnapshot
    else -> true
}

println("isSnapshotOnly: $isSnapshotOnly")
println("isReleaseOnly: $isReleaseOnly")
println("isPublishConfigurable: $isPublishConfigurable")


if (isPublishConfigurable) {
    val sonatypeUsername: String? = systemProp("OSSRH_USER")
    val sonatypePassword: String? = systemProp("OSSRH_PASSWORD")
    
    if (sonatypeUsername == null || sonatypePassword == null) {
        println("[WARN] - sonatype.username or sonatype.password is null, cannot config nexus publishing.")
    }
    
    val jarSources by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
    
    val jarJavadoc by tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
    }
    
    publishing {
        publications {
            create<MavenPublication>("dist") {
                from(components["java"])
                artifact(jarSources)
                artifact(jarJavadoc)
                
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
                
                pom {
                    name.set("${project.group}:${project.name}")
                    description.set("Simple Robot框架下针对开黑啦(Kook)平台的组件实现")
                    url.set("https://github.com/simple-robot/simbot-component-kook")
                    licenses {
                        license {
                            name.set("GNU GENERAL PUBLIC LICENSE, Version 3")
                            url.set("https://www.gnu.org/licenses/gpl-3.0-standalone.html")
                        }
                        license {
                            name.set("GNU LESSER GENERAL PUBLIC LICENSE, Version 3")
                            url.set("https://www.gnu.org/licenses/lgpl-3.0-standalone.html")
                        }
                    }
                    scm {
                        url.set("https://github.com/simple-robot/simbot-component-kook")
                        connection.set("scm:git:https://github.com/simple-robot/simbot-component-kook.git")
                        developerConnection.set("scm:git:ssh://git@github.com/simple-robot/simbot-component-kook.git")
                    }
                    developers {
                        developer {
                            id.set("forte")
                            name.set("ForteScarlet")
                            email.set("ForteScarlet@163.com")
                        }
                        developer {
                            id.set("forliy")
                            name.set("ForliyScarlet")
                            email.set("ForliyScarlet@163.com")
                        }
                    }
                }
            }
            
            repositories {
                maven {
                    name = Sonatype.oss.NAME
                    url = uri(Sonatype.oss.URL)
                    credentials {
                        username = sonatypeUsername
                        password = sonatypePassword
                    }
                }
                maven {
                    name = Sonatype.`snapshot-oss`.NAME
                    url = uri(Sonatype.`snapshot-oss`.URL)
                    credentials {
                        username = sonatypeUsername
                        password = sonatypePassword
                    }
                }
            }
        }
    }
    
    signing {
        val keyId = System.getenv("GPG_KEY_ID")
        val secretKey = System.getenv("GPG_SECRET_KEY")
        val password = System.getenv("GPG_PASSWORD")
        
        setRequired {
            !project.version.toString().endsWith("SNAPSHOT")
        }
        
        useInMemoryPgpKeys(keyId, secretKey, password)
        
        
        val publishing = extensions.getByName<PublishingExtension>("publishing")
        sign(publishing.publications["dist"])
    }
    
    
    println("[publishing-configure] - [$name] configured.")
}




