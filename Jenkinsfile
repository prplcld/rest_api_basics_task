pipeline {
    agent any

    triggers {
        pollSCM('*/5 * * * *')
    }

    stages {
        stage('Compile') {
            steps {
                gradlew('clean', 'classes')
            }
        }
        stage('Unit Tests') {
            steps {
                gradlew('test')
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }
        stage('Sonar Qube') {
            steps {
                withSonarQubeEnv(installationName : 'sq') {
                    gradlew('clean', 'sonar')
                }
            }
        }
    }
}

def gradlew(String... args) {
    bat "gradlew ${args.join(' ')} -s"
}