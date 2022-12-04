def call(String approval_flag) {
    if(approval_flag == "Yes"){
        userInput = input(id: 'confirm', message: "Should we continue?",  ok: "Yes, we should.",  submitter: "devops")
    }else{
        sleep 3
    }
}