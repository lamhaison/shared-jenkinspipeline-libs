@Library('YOUR_PROJECT-jenkins-pipeline-library-dev') _

pipeline {
    agent {
        node {
            label params.NODE_LABEL
        }
    }
    
    parameters {
        choice(name: "NODE_LABEL", choices: ["master"])
    }
    environment {
        ENVIRONMENT = "production"
        BRANCH_NAME = "production"
        PROJECT_NAME = "packer"

    }

    stages{

        stage('Do nothing') {
        	steps {
        		echo "Testing"
        	}
        }


    }

    post {
        success {
            send_message_to_telegram("Passed")
        }
        unstable {
            echo "The build is unstable"
        }
        failure {
            echo "The build is failed"
             send_message_to_telegram("Failed")
        }
        changed {
            echo 'Things were different before...'
        }
    }
}