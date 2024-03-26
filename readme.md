## Dopple
A simple gradle plugin that allows you to use secrets from [doppler.com](https://doppler.com) in a gradle project.

This is useful for people with multiple GitHub repos, CI projects, or local usage to manage their ENV variables from a central place.

***

### Setup


<details open="open"><summary>Groovy DSL</summary>

To use this plugin inside your project, first you have to add our maven.

To do this, open up `settings.gradle` and add the following:

```groovy
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url "https://maven.firstdark.dev/releases"
        }
    }
}
```

![badge](https://maven.firstdarkdev.xyz/api/badge/latest/releases/dev/firstdark/dopple?color=40c14a&name=dopple)

Next, in your `build.gradle` add:

```groovy
plugins {
    id "dev.firstdark.dopple" version "VERSION"
}
```

Replace VERSION with the version above.

Finally, add the following to your `build.gradle`:

```groovy
dopple {
    serviceToken.set("TOKEN")
}
```

</details>

<details><summary>Kotlin DSL</summary>

To use this plugin inside your project, first you have to add our maven.

To do this, open up `settings.gradle.kts` and add the following:

```groovy
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://maven.firstdark.dev/releases")
        }
    }
}
```

Next, in your `build.gradle.kts` add:

![badge](https://maven.firstdarkdev.xyz/api/badge/latest/releases/dev/firstdark/dopple?color=40c14a&name=dopple)

```kotlin
plugins {
    id("dev.firstdark.dopple") version "VERSION"
}
```

Replace VERSION with the version above.

Finally, add the following to your `build.gradle.kts`:

```groovy
dopple {
    serviceToken.set("TOKEN")
}
```

</details>

You can retrieve your service token from your [doppler](https://doppler.com) account.

---

### Usage

To get the value of a SECRET from doppler, simply use the following:

```groovy
dopple.get("KEY")
```

For example:

```groovy
publishing {
    repositories {
        maven {
            url dopple.get('MAVEN_URL')
            credentials {
                username dopple.get('MAVEN_USER')
                password dopple.get('MAVEN_PASS')
            }
        }
    }
}
```

***

This plugin is licensed under MIT.

If you need any help, open an issue, or visit our [DISCORD](https://discord.firstdark.dev)