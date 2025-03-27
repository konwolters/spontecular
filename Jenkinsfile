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
            MAVEN_CACHE = '/hdd/portainer/cache/.m2'
            MAVEN_IMAGE = 'maven:3.9.9-eclipse-temurin-17'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    docker.image(env.MAVEN_IMAGE).inside("-v ${env.MAVEN_CACHE}:/root/.m2") {
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Debug Maven Mount') {
            steps {
                script {
                    docker.image(env.MAVEN_IMAGE).inside("-v ${env.MAVEN_CACHE}:/root/.m2") {
                        sh '''
                    echo "👉 Inside container as user: $(id)"
                    mkdir -p /root/.m2/test123
                    touch /root/.m2/test123/from-container.txt
                    echo "✅ Created test file in /root/.m2/test123"
                    ls -la /root/.m2/test123
                '''
                    }
                }
            }
        }

        stage('Test') {
            when {
                expression { return !params.SKIP_TESTS }
            }
            steps {
                script {
                    docker.image(env.MAVEN_IMAGE).inside("-v ${env.MAVEN_CACHE}:/root/.m2") {
                        sh 'mvn test jacoco:report'
                    }
                }
            }
        }

        stage('SonarQube') {
            when {
                expression { return !params.SKIP_SONARQUBE }
            }
            steps {
                script {
                    docker.image(env.MAVEN_IMAGE).inside("-v ${env.MAVEN_CACHE}:/root/.m2") {
                        withSonarQubeEnv('SonarQube') {
                            sh 'mvn sonar:sonar'
                        }
                    }
                }
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Deploy') {
            when {
                    allOf {
                        branch 'main'
                        expression { return !params.SKIP_DEPLOY }
                    }
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
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}
