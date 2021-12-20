pipeline {
    agent any
    environment {
        CREDENTIALS_ID = 'eks'
    }
    stages {
        
        stage("Checkout code") {
            steps {
                checkout scm
            }
        }
    
        stage("Build image") {
            steps {
                script {
                    myapp = docker.build("public.ecr.aws/r2m7p7n2/eksdemos-dabberu:${env.BUILD_ID}")
                }
            }
        }
        stage("Push image") {
            steps {
                script {
                    docker.withRegistry('https://public.ecr.aws/r2m7p7n2', 'awsecr') {
                            myapp.push("latest")
                            myapp.push("${env.BUILD_ID}")
                    }
                }
            }
        }
        stage('Deploy to EKS') {
            steps {
                echo 'Hello World'
                // sh 'helm install flaskhw dabberu-repos/flaskhw-chart --namespace apps --create-namespace -f values.yaml --set image.tag="latest" --dry-run'
                sh 'helm upgrade flaskhw dabberu-repos/flaskhw-chart --namespace apps --create-namespace -f values.yaml '
            }
        } 
    }    
}