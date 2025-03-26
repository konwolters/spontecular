pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    triggers {
        githubPush()
    }

    environment {
            IMAGE_NAME = 'spontecular'
            CONTAINER_NAME = 'spontecular-app'
            APP_PORT = '8070'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    // Use verify here to rerun tests & generate reports (required by Sonar)
                    sh 'mvn verify sonar:sonar -Dsonar.projectKey=spontecular-sonarqube'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Deploy') {
            steps {
                sh """
            docker rm -f ${CONTAINER_NAME} || true
            docker run -d --name ${CONTAINER_NAME} -p ${APP_PORT}:8070 ${IMAGE_NAME}:latest
        """
            }
        }
    }

    post {
        success {
            echo '🎉 Build successful!'
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}
