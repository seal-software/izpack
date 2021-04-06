pipeline {

    options {
        timestamps()
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(
                logRotator(
                        artifactDaysToKeepStr: '10',
                        artifactNumToKeepStr: '5',
                        daysToKeepStr: '10',
                        numToKeepStr: '5'
                )
        )
    }

    agent {
        docker {
            image 'docker.seal-software.net/build-agent-java8'
            args dockerArgs()
            label 'medium'
        }
    }

    environment {
        BUILDVERSION = buildVersion()
        PROFILE = getDeployProfile()
    }

    stages {

        stage('setup') {
            steps {
                sh label: "Setup version $BUILDVERSION",
                        script: "mvn -V versions:set -DnewVersion=$BUILDVERSION -DgenerateBackupPoms=false"
            }
        }

        stage('build') {
            steps {
                sh label: "Building version $BUILDVERSION",
                        script: "mvn -T1C -U clean install"
            }
            post {
                always {
                    // Show test results in jenkins ui
                    junit '**/target/surefire-reports/**/*.xml'
                }
            }
        }

        stage('deploy') {
            when {
                expression {
                    return !isPrBuild()
                }
            }
            steps {
                sh label: "Deploying version $BUILDVERSION",
                        script: "mvn -T1C -P$PROFILE -e clean deploy -Dmaven.test.skip=true"
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }

}

def getDeployProfile() {
    return isRelease() ? "release" : "builds"
}
