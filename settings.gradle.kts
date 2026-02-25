pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RickAndMortyApp"
include(":app")
include(":core:network")
include(":core:domain")
include(":core")
include(":core:model")
include(":core:data")
include(":core:ui")
include(":core:designsystem")
include(":feature:character")
include(":feature:episodes")
include(":core:common")
