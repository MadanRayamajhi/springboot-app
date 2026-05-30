pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'madanrayamajhi/springboot-app'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
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
        
        stage('Deploy to EC2 Instance') {
            steps {
                sshagent(['deploy-server-cred']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ec2-user@32.198.49.239 "
                            docker pull ${DOCKER_IMAGE}:${DOCKER_TAG} &&
                            docker stop springboot-app || true &&
                            docker rm springboot-app || true &&
                            docker run -d --name springboot-app -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG}
                        "
                    """
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
            echo "Deployed to http://32.198.49.239:8080"
        }
        failure {
            echo 'Pipeline failed! Check the logs above.'
        }
    }
}
