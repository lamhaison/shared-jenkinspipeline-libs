// Using deploy_k8s_app_by_helm(helm_ns: "${}", helm_env: "${}", helm_chart_source: "${}", helm_chart_name: "${}", image_tag: "${}")
def call (HashMap params){
    action = "deploy"
    helm_timeout = env.HELM_TIMEOUT
    helm_path = "./"
    // action = params["action"]
    helm_ns = params["helm_ns"]
    helm_env = params["helm_env"]
    helm_chart_source = params["helm_chart_source"]
    helm_chart_name = params["helm_chart_name"]
    image_tag = params["image_tag"]


    switch(action) {
    case "deploy":
        dir("$helm_path"){
            echo "Deploy for chart ${helm_chart_name} on namespace ${helm_ns} on env ${helm_env}"


            sh """#!/bin/bash
                set -e
                set -x

                export KUBECONFIG=${helm_chart_source}-${helm_env}
                echo "Generate kubeconf file"
                eval "${KUBECONF_GENERATE_COMMAND}"
                
                echo "Deploy by helm"
                helm upgrade -n ${helm_ns} --wait --timeout ${helm_timeout} --install \
                    -f ${helm_chart_source}/${helm_env}-values.yaml \
                    ${helm_chart_name} \
                    --set image.tag=${image_tag} --debug ${helm_chart_source}
            """
        }

        break
    case "delete":
        dir("$helm_path"){
            sh """#!/bin/bash
                set -e
                set -x
                echo "Delete helm chart"
                helm delete -n ${helm_ns} ${helm_chart_name}
            """
        }

        break

    default:
        echo 'Do nothing'
        break
    }
}