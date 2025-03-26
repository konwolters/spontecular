pipeline {
    agent any

    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip unit tests')
        booleanParam(name: 'SKIP_SONARQUBE', defaultValue: false, description: 'Skip SonarQube')
        booleanParam(name: 'SKIP_DEPLOY', defaultValue: false, description: 'Skip deployment')
    }

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
            when {
                expression { return !params.SKIP_TESTS }
            }
            steps {
                sh 'mvn test jacoco:report'
            }
        }

        stage('SonarQube') {
            when {
                expression { return !params.SKIP_SONARQUBE }
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            when {
                expression { return !params.SKIP_DEPLOY }
            }
            steps {
                sh """
            docker rm -f ${CONTAINER_NAME} || true
            docker run -d --name ${CONTAINER_NAME} -p ${APP_PORT}:8080 ${IMAGE_NAME}:latest
        """
            }
        }
    }

    post {
        success {
            echo '🎉 Build successful!'
            archiveArtifacts artifacts: 'target/**/*.jar,target/site/jacoco/*.xml,target/surefire-reports/*.xml'
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}
