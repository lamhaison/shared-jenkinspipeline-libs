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

## docs
```
https://www.tutorialworks.com/jenkins-shared-library/
https://www.eficode.com/blog/jenkins-groovy-tutorial
```
