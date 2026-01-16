pipeline {
    agent any

    environment {
        // Set your Gradle wrapper path
        GRADLEW = './gradlew'
    }

    stages {

        stage('Checkout SCM') {
            steps {
                echo 'Checking out code from Git...'
                checkout scm
            }
        }

        stage('Prepare') {
            steps {
                echo 'Making Gradle wrapper executable...'
                sh 'chmod +x gradlew || true' // || true to ignore errors on Windows
            }
        }

        stage('Clean Gradle Cache') {
            steps {
                echo 'Cleaning Gradle cache...'
                sh 'rm -rf .gradle/caches || true'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project (skipping tests that require DB)...'
                sh "${GRADLEW} clean build -x test --no-daemon"
            }
        }

        // ===== Docker stages are commented for Windows =====
        /*
        stage('Docker Build') {
            steps {
                script {
                    echo 'Building Docker image...'
                    docker.build("${DOCKER_IMAGE}", ".")
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    echo 'Pushing Docker image...'
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        docker.image("${DOCKER_IMAGE}").push()
                    }
                }
            }
        }
        */

    }

    post {
        always {
            echo 'Archiving build artifacts...'
            archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: true
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
