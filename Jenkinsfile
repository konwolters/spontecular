pipeline {
    agent any

    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip unit tests')
        booleanParam(name: 'SKIP_SONARQUBE', defaultValue: false, description: 'Skip SonarQube')
        booleanParam(name: 'SKIP_DEPLOY', defaultValue: false, description: 'Skip deployment')
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
        MAVEN_REPO_LOCAL = '/maven/.m2'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    docker.image(env.MAVEN_IMAGE).inside("-u 1000:1000 -v ${env.MAVEN_CACHE}:${env.MAVEN_REPO_LOCAL}") {
                        sh "mvn clean install -DskipTests -Dmaven.repo.local=${env.MAVEN_REPO_LOCAL}"
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
                    docker.image(env.MAVEN_IMAGE).inside("-u 1000:1000 -v ${env.MAVEN_CACHE}:${env.MAVEN_REPO_LOCAL}") {
                        sh "mvn test jacoco:report -Dmaven.repo.local=${env.MAVEN_REPO_LOCAL}"
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
                    docker.image(env.MAVEN_IMAGE).inside("-u 1000:1000 -v ${env.MAVEN_CACHE}:${env.MAVEN_REPO_LOCAL}") {
                        withSonarQubeEnv('SonarQube') {
                            sh "mvn sonar:sonar -Dmaven.repo.local=${env.MAVEN_REPO_LOCAL}"
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
                    docker build -t ${IMAGE_NAME}:latest .
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
