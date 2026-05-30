pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'madanrayamajhi/springboot-app'
        EC2_HOST = '54.205.205.48'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-credentials',
                    url: 'https://github.com/MadanRayamajhi/springboot-app.git'
            }
        }
        
        stage('Build & Test') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean compile'
                sh './mvnw test'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './mvnw sonar:sonar'
                }
            }
        }
        
        stage('Package') {
            steps {
                sh './mvnw package -DskipTests'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', 'docker-hub-credentials') {
                        docker.image("${DOCKER_IMAGE}:latest").push()
                    }
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                sshagent(['ec2-ssh-key']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} "
                            docker pull ${DOCKER_IMAGE}:latest &&
                            docker stop springboot-app || true &&
                            docker rm springboot-app || true &&
                            docker run -d --name springboot-app -p 8081:8080 ${DOCKER_IMAGE}:latest
                        "
                    """
                }
            }
        }
    }
    
    post {
        success {
            echo '🎉 Pipeline completed successfully!'
            echo "App available at: http://${EC2_HOST}:8081"
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
