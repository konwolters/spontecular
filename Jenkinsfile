pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    triggers {
        githubPush()
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
