#!groovy

import groovy.transform.Field

@Field String ENVIRONMENT
@Field String USER
@Field String USER_EMAIL

def call(String branch) {
    if (params.DEPLOY_ENVIRONMENT) {
        ENVIRONMENT = params.DEPLOY_ENVIRONMENT
    } else if (env.BUILD_ENVIRONMENT) {
        ENVIRONMENT = env.BUILD_ENVIRONMENT
    } else {
        ENVIRONMENT = ""
    }
    if (!branch?.trim()) {
        buildName "$BUILD_NUMBER-$GIT_COMMIT_SHORT"
    } else {
        buildName "$BUILD_NUMBER-$ENVIRONMENT-$branch-$GIT_COMMIT_SHORT"
    }
    wrap([$class: 'BuildUser']){
        USER = env.BUILD_USER ?: "Jenkins"
        USER_EMAIL = env.BUILD_USER_EMAIL
        buildDescription "Executed @ ${NODE_NAME}. Build started by ${USER}"
    }
}