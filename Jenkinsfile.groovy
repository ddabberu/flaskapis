pipeline {
    agent any
    environment {
        PROJECT_ID = 'PROJECT-ID'
        CLUSTER_NAME = 'CLUSTER-NAME'
        LOCATION = 'CLUSTER-LOCATION'
        CREDENTIALS_ID = 'eks'
    }
    stages {
        
        stage("Checkout code") {
            steps {
                checkout scm
            }
        }
        /*
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
        */
        /*       
        stage('Deploy to AWS EKS') {
            steps{
                sh "sed -i 's/hello:latest/hello:${env.BUILD_ID}/g' deployment.yaml"
                step([$class: 'KubernetesEngineBuilder', projectId: env.PROJECT_ID, clusterName: env.CLUSTER_NAME, location: env.LOCATION, manifestPattern: 'deployment.yaml', credentialsId: env.CREDENTIALS_ID, verifyDeployments: true])
            }
        }
        */
        state('Test user acces'){
            sh "kubectl auth can-i create pods"
        }
    }    
}