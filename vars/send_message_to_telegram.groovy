#!/usr/bin/env groovy
// send_message_to_telegram("Passed") or send_message_to_telegram("Failed")
def call (String test_status) {

    if(env.ENVIRONMENT == "development" || env.ENVIRONMENT == "develop" ||env.ENVIRONMENT == "dev" || env.ENVIRONMENT == "stg" || env.ENVIRONMENT == "staging") {
        env.chat_id=sh(script: 'echo $CI_CD_CHAT_ID_DEV', returnStdout: true)
    }

    if(env.ENVIRONMENT == "production" || env.ENVIRONMENT == "prd" || env.ENVIRONMENT == "prod") {
        env.chat_id=sh(script: 'echo $CI_CD_CHAT_ID_PRD', returnStdout: true)
    }


    if(test_status == "Passed") {
        env.message=sh(
            script: "echo '<b>Project</b> : ${PROJECT_NAME} \
            <b>Git Branch Name</b>: ${BRANCH_NAME} \
            <b>Environment</b>: ${ENVIRONMENT} \
            <b>BUILD RESULT</b>:  Passed \
            <b>Jenkins job url</b>: ${BUILD_URL}'", 
            returnStdout: true)
        
    } else{
        env.message=sh(
            script: "echo '<b>Project</b> : ${PROJECT_NAME} \
            <b>Git Branch Name</b>: ${BRANCH_NAME} \
            <b>Environment</b>: ${ENVIRONMENT} \
            <b>BUILD RESULT</b>: <strong><strike>Failed</strike></strong> \
            <b>Jenkins job url</b>: ${BUILD_URL}'", 
            returnStdout: true)
    }


    withCredentials([string(credentialsId: 'telegram_token', variable: 'TOKEN')]) {
    
        sh '''#!/bin/bash -x
            curl -s -X POST https://api.telegram.org/bot${TOKEN}/sendMessage -d chat_id=${chat_id} -d parse_mode="HTML" -d text="${message}"
        '''
    }
}