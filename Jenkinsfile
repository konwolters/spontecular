pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/konwolters/spontecular.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        // You can add more stages like Deploy, SonarQube, Docker, etc.
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
