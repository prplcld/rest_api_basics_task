pipeline {
    agent any

    triggers {
        pollSCM('*/5 * * * *')
    }

    stages {
        // stage('Compile') {
        //     steps {
        //         gradlew('clean', 'classes')
        //     }
        // }
        // stage('Unit Tests') {
        //     steps {
        //         gradlew('test')
        //     }
        //     post {
        //         always {
        //             junit '**/build/test-results/test/TEST-*.xml'
        //         }
        //     }
        // }
        // stage('Sonar Qube') {
        //     steps {
        //         withSonarQubeEnv(installationName : 'sq') {
        //             gradlew('clean', 'test', 'jacocoTestReport', 'sonar')
        //         }
        //     }
        // }
        // stage('Quality gate') {
        //     steps {
        //         waitForQualityGate abortPipeline: true
        //     }
        // }
        stage('Deploy') {
            steps {
                gradlew('bootWar')
                deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://localhost:8080')], contextPath: 'api', war: '**/*.war'
            }
        }
    }
}

def gradlew(String... args) {
    bat "gradlew ${args.join(' ')} -s"
}