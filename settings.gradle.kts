plugins {
    id("com.mooltiverse.oss.nyx") version "2.4.5"
}

rootProject.name = "nyx-sandbox"
include("app")

configure<com.mooltiverse.oss.nyx.gradle.NyxExtension> {
    scheme.set("SEMVER")
    changelog {
        path.set("CHANGELOG.md")
    }
    stateFile.set("state.txt")

    commitMessageConventions {
        enabled.set(listOf("conventionalCommits"))
        items {
            register("conventionalCommits") {
                expression.set("(?m)^(?<type>[a-zA-Z0-9_]+)(!)?(\\\\((?<scope>[a-z ]+)\\\\))?:( (?<title>.+))$(?s).*")
                bumpExpressions {
                    put("major", "(?s)(?m)^[a-zA-Z0-9_]+(!|.*^(BREAKING( |-)CHANGE: )).*")
                    put("minor", "(?s)(?m)^feat(?!!|.*^(BREAKING( |-)CHANGE: )).*")
                    put("patch", "(?s)(?m)^fix(?!!|.*^(BREAKING( |-)CHANGE: )).*")
                }
            }
        }
    }

    releaseTypes {
        enabled.set(listOf("release", "pre-release"))
        items {
            register("release") {
                gitTag.set("true")
                gitCommit.set("true")
                gitCommitMessage.set("chore: Release version {{version}}")
                gitPush.set("true")
                collapseVersions.set(true)
                collapsedVersionQualifier.set("dev")
            }
            register("pre-release") {
                matchBranches.set("^(?!main\$)\\S+\$")
                gitTag.set("true")
                gitCommit.set("true")
                gitCommitMessage.set("chore: Release version {{version}}")
                gitPush.set("true")
                collapseVersions.set(true)
                collapsedVersionQualifier.set("dev")
            }
        }
    }


    git {
        remotes {
            register("origin") {
                authenticationMethod.set("PUBLIC_KEY")
            }
        }
    }

}
