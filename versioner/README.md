#Versioner
Gradle плагин для автоматического формирования версии, основанной на git tag, в числовом и строковом форматах.

## Usage

```kotlin
plugins {
    // ...
    id("com.ivanprok.versioner")
}

versioner {
    namePattern = "%tag%%.count%%-branch_name%"
    codePattern = "MNNPPP"

    forBranches("master", "release/.*") {
        namePattern = "%tag%"
        codePattern = "MNNPPP"
    }

    forBranches("feature/.*", "fix/.*") {
        pattern = "%tag%%-branch_name%"
        codePattern = "MNNPPP"
    }
}
```

> Inspired by:
> - [AndroidGitVersion](https://github.com/gladed/gradle-android-git-version)
> - [How to write Gradle Plugin in Kotlin](https://medium.com/friday-insurance/how-to-write-a-gradle-plugin-in-kotlin-68d7a3534e71)
> - [Elasticmq-gradle-plugin](https://github.com/FRI-DAY/elasticmq-gradle-plugin)
