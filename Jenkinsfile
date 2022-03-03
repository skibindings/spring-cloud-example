def image
def hash
def serviceName = 'ai-api'

node(label: 'maven') {

    stage('Clone repository') {
        deleteDir()
        checkout scm
        hash = sh(script: 'git describe --always', returnStdout: true)
    }

    stage('Build application') {
        withMaven(jdk: 'jdk11', maven: 'maven', mavenSettingsConfig: 'nexus-maven') {
            sh 'mvn clean install -DskipTests'
        }
    }

    if (env.BRANCH_NAME == 'master') {

        stage('Build image') {
            image = docker.build(serviceName, "--build-arg REGISTRY=${NEXUS_PULL} .")
        }

        stage('Push image') {
            docker.withRegistry("http://${NEXUS_PUSH}", "docker-login") {
                image.push("${BRANCH_NAME}-${hash}")
                image.push("latest")
            }
            sh """
              docker rmi ${NEXUS_PUSH}/${image.id}:${BRANCH_NAME}-${hash}
              docker rmi ${NEXUS_PUSH}/${image.id}:latest
              docker rmi ${image.id}:latest
              docker image prune -f
            """
        }

    } else if (env.BRANCH_NAME.startsWith('PR')) {
        stage('Run PR tests') {
            withMaven(jdk: 'jdk11', maven: 'maven', mavenSettingsConfig: 'nexus-maven') {
                sh 'mvn test'
            }
        }
    } else {
        sh 'echo "No tasks defined for this branch!"'
    }

    stage('Cleanup') {
        deleteDir()
    }

}
