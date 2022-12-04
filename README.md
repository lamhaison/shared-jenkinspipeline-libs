# Library Jenkins

## example to declare method
- put methods in floder vars
- put class in src
- name of method like name of file groovy.
example: `get_approval_from_user.groovy` and then call method `get_approval_from_user`
- in `vars/get_approval_from_user.groovy` only declare function call
- example: 
```
def call(String approval_flag) {
    if(approval_flag == "Yes"){
        userInput = input(id: 'confirm', message: "Should we continue?",  ok: "Yes, we should.",  submitter: "devops")
    }else{
        sleep 10
    }
}
```

## example to use
- `@Library('pipeline-library-demo') _` : pipeline-library-demo is name of library in jenkins
```
@Library('pipeline-library-demo') _

import com.cleverbuilder.GlobalVars
import com.cleverbuilder.SampleClass

pipeline {
    agent any
    parameters {
        choice(name: "NODE_LABEL", choices: ["YOUR_PROJECT-jenkins-1"])
        string(name: 'PROJECT_NAME', defaultValue: 'YOUR_PROJECT-wordpress', description: 'Enter service name?')
        choice(name: 'ENVIRONMENT', choices: ['dev'], description: 'Enter short environment name?')
        choice(name: 'GET_APPROVAL', choices: ['No', 'Yes'], description: 'Do you want to confirm before going to the next or not?')
    }
    stages {
        stage('Demo') {
            steps {
                echo 'Hello, world'
                sayHello 'Dave'
                echo "Sum 8888 and 9999 : ${sum(8888,9999)}"
                echo 'The value of foo is : ' + GlobalVars.foo
                get_approval_from_user("${params.GET_APPROVAL}")
                script {
                    def person = new SampleClass()
                    person.age = 21
                    person.increaseAge(10)
                    echo 'Incremented age, is now : ' + person.age
                }
            }
        }
    }
}
```

## Send notification to telegram
### How to get the channel ID
```
Step 1: Create a private channel
Step 2: Add the bot(@alert_prd_bot) to the channel
Step 3: Use the API below to get the ID of the channel. It has format like tat -100xxx
step 4: check id by
curl -i https://api.telegram.org/bot${YOUR_TOKEN}/getUpdates
```
### Set envs
* CI_CD_CHAT_ID_DEV: Your channel ID or group Telegram ID
* CI_CD_CHAT_ID_PRD: Your channel ID or group Telegram ID
### Set credential
* ID: telegram_token
* Secret: Your bot token (don't add prefix bot in the secret token)
* Description: Without bot prefix
* Kind: Secret text

### Jenkinsfile
* Please check jenkinsfile-examples/sending_telegram_example.groovy

## docs
```
https://www.tutorialworks.com/jenkins-shared-library/
https://www.eficode.com/blog/jenkins-groovy-tutorial
```
