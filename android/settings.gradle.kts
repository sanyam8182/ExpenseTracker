pluginManagement {
    repositories {
        google()
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

rootProject.name = "ExpenseTracker"

include(":app")
include(":spikes")

// Pocket, the budget character library. Included by path; its licence is undecided, so it is not in the repository yet.
include(":pocket")
project(":pocket").projectDir = file("../assets/pocket/android/pocket")
