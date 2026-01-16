pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springboot-app:${env.BUILD_NUMBER}"
        // Workspace-local Gradle cache to avoid lock issues
        GRADLE_USER_HOME = "${WORKSPACE}/.gradle"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out code..."
                git branch: 'main',
                    url: 'https://github.com/akrembendhia-02/projetfederateur.git'
            }
        }

        stage('Prepare') {
            steps {
                echo "Making Gradle wrapper executable..."
                sh 'chmod +x gradlew'
            }
        }

        stage('Clean Gradle Cache') {
            steps {
                echo "Cleaning Gradle cache..."
                sh 'rm -rf $GRADLE_USER_HOME/caches/'
            }
        }

        stage('Build') {
            steps {
                echo "Building the project (skipping tests that require DB)..."
                // Skip tests so pipeline doesn't fail due to missing DB
                sh './gradlew clean build -x test --no-daemon'
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image..."
                script {
                    docker.build("${DOCKER_IMAGE}", ".")
                }
            }
        }

        stage('Docker Push') {
            steps {
                echo "Docker Push skipped (manual TP)"
            }
        }
    }

    post {
        always {
            echo "Archiving build artifacts..."
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
        }
        success {
            echo "Pipeline CI/CD terminé avec succès !"
        }
        failure {
            echo "Échec du pipeline"
        }
    }
}
