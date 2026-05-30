pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'your-dockerhub-username/health-check-app'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        SONAR_HOST_URL = 'http://your-server-ip:9000'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build with Maven') {
            steps {
                sh './mvnw clean compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                sh './mvnw test'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh './mvnw sonar:sonar'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                sh "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
            }
        }
        
        stage('Push to DockerHub') {
            steps {
                withDockerRegistry([credentialsId: 'dockerhub-creds', url: '']) {
                    sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    sh "docker push ${DOCKER_IMAGE}:latest"
                }
            }
        }
        
        stage('Deploy to Server') {
            steps {
                sshagent(['deploy-server-cred']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no user@your-server-ip "
                            docker pull ${DOCKER_IMAGE}:${DOCKER_TAG} &&
                            docker stop health-check-app || true &&
                            docker rm health-check-app || true &&
                            docker run -d --name health-check-app -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG}
                        "
                    """
                }
            }
        }
    }
    
    post {
        success {
            emailext (
                subject: "Pipeline Success - ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Pipeline executed successfully. Image: ${DOCKER_IMAGE}:${DOCKER_TAG}",
                to: 'team@example.com'
            )
        }
        failure {
            emailext (
                subject: "Pipeline Failed - ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Pipeline failed. Check console output: ${env.BUILD_URL}",
                to: 'team@example.com'
            )
        }
    }
}