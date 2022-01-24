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
        VERSION = "5.1.3-SEAL.${env.BUILD_NUMBER}"
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

def printMessage(message) {
    def mVersion = getVersion()
    println(message + " version $mVersion")
}

def setupVersion() {
    def mVersion = getVersion()
    sh("mvn -B versions:set -DnewVersion=$mVersion -DgenerateBackupPoms=false")
}

def deploy() {
    def mProfile = getDeployProfile()
    sh("mvn -B -P$mProfile -e clean deploy -Dmaven.test.skip=true")
}

def getVersion() {
    return isMaster() ? VERSION : BUILDVERSION
}

def getDeployProfile() {
    return isMasterOrRelease() ? "release" : "builds"
}

def isMasterOrRelease() {
    return isMaster() || env.BRANCH_NAME ==~ /^releases\/.*/ || env.BRANCH_NAME ==~ /^....Q.$/
}

def isMaster() {
    return env.BRANCH_NAME == 'master'
}
