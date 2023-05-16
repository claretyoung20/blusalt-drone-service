pipeline {
    agent {
        kubernetes {
            yaml '''
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            app: test
        spec:
          containers:
          - name: maven
            image: maven:3.6-jdk-11-slim
            command:
            - cat
            tty: true
            volumeMounts:
            - mountPath: "/root/.m2/repository"
              name: m2cache
          - name: git
            image: bitnami/git:latest
            command:
            - cat
            tty: true
          - name: dockercli
            image: docker:latest
            command:
            - cat
            tty: true
            volumeMounts:
            - mountPath: "/var/run/docker.sock"
              name: docker-sock
          - name: sonarcli
            image: sonarsource/sonar-scanner-cli:latest
            command:
            - cat
            tty: true
          - name: kubectl-helm-cli
            image: kunchalavikram/kubectl_helm_cli:latest
            command:
            - cat
            tty: true
          volumes:
          - name: m2cache
            persistentVolumeClaim:
              claimName: maven-cache
          - name: docker-sock
            hostPath:
               path: /var/run/docker.sock
        '''
        }
    }

    environment {
        NEXUS_VERSION = 'nexus3'
        NEXUS_PROTOCOL = 'http'
        NEXUS_URL = '167.99.87.35:31516'
        NEXUS_REPOSITORY = 'maven-hosted'
        NEXUS_CREDENTIAL_ID = 'NEXUS_CRED'
        DOCKERHUB_USER = 'claretyoung'
        APP_NAME = 'drone-app'
        IMAGE_NAME = "${DOCKERHUB_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    triggers {
        GenericTrigger causeString: 'Generic Cause', regexpFilterExpression: '', regexpFilterText: '', token: 'drone123', tokenCredentialId: ''
    }

    stages {
        stage('Checkout SCM') {
            steps {
                container('git') {
                    git credentialsId:
                    'GITHUB_CREDENTIALS',
                    branch: 'master',
                    poll: false,
                    url: 'https://github.com/claretyoung20/blusalt-drone-service.git'
                }
            }
        }
        stage('Build maven project') {
            steps {
                container('maven') {
                    sh 'mvn clean install'
                }
            }
            post {
                success {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Sonar scan') {
            steps {
                container('sonarcli') {
                    /* groovylint-disable-next-line NestedBlockDepth */
                    withSonarQubeEnv(credentialsId: 'SONARQUBE_USER_TOKEN', installationName: 'SONARSERVER') {
                        sh '''/opt/sonar-scanner/bin/sonar-scanner \
                        -Dsonar.projectKey=drone-service \
                        -Dsonar.projectName=drone-service \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src/main \
                        -Dsonar.tests=src/test \
                        -Dsonar.java.binaries=target/classes  \
                        -Dsonar.language=java \
                        -Dsonar.sourceEncoding=UTF-8 \
                        -Dsonar.java.libraries=target/classes
                        '''
                    }
                }
            }
        }
        stage('Quality gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true, credentialsId: 'SONARQUBE_USER_TOKEN'
                }
            }
        }

        stage('Push to nexus') {
            steps {
                container('jnlp') {
                   script {
                        pom = readMavenPom file: 'pom.xml'
                        filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                        artifactPath = filesByGlob[0].path;
                        artifactExists = fileExists artifactPath;
                        if(artifactExists) {
                            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version + '.' + BUILD_NUMBER,
                                repository: NEXUS_REPOSITORY,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: artifactPath,
                                    type: pom.packaging],

                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: 'pom.xml',
                                    type: 'pom']
                                ]
                            )
                        } else {
                            error "*** File: ${artifactPath}, could not be found"
                        }
                   }
                }
            }
        }

        stage('Build and push docker image') {
            steps {
                container('dockercli') {
                    sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
                    // sh "docker tag $IMAGE_NAME:$IMAGE_TAG $IMAGE_NAME:latest"
                    withCredentials([usernamePassword(credentialsId: 'DOCKERHUB_CRED',
                                    passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh "docker login -u $USER -p $PASS"
                        sh "docker push $IMAGE_NAME:$IMAGE_TAG"
                        // sh "docker push $IMAGE_NAME:latest"
                    }
                }
            }
        }

        stage('Deploy to kubernetes') {
            steps {
                container('kubectl-helm-cli') {
                    withKubeConfig(credentialsId: 'DO_K8S') {
                        sh 'kubectl apply -f deployment/,deployment/pg/,deployment/drone'
                    }
                }
            }
        }

        stage('Deploy to cluster with helm') {
            steps {
                container('kubectl-helm-cli') {
                    withKubeConfig(credentialsId: 'DO_K8S') {  
                        sh 'kubectl apply -f charts/ns.yaml' 
                        sh 'helm dependency build charts/drone-service'
                        withCredentials([string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASS'), string(credentialsId: 'DB_HOST', variable: 'DB_HOST')]) {
                            sh "helm upgrade --install drone-app charts/drone-service/ -n helm-drone --set configmap.db_name=drone_db,configmap.db_port=25060,image.tag=$IMAGE_TAG,secret.dbUser=drone,secret.dbHost=$DB_HOST,secret.dbPassword=$DB_PASS"
                        }
                        
                    }
                }
            }
        }
    }

    post {
        failure {
            mail body: "Check build logs at ${env.BUILD_URL}", from: 'jenkins-admin@gmail.com', subject: "Jenkins pipeline has failed for job ${env.JOB_NAME}", to: "dev@drone.service"
        }
        success {
            mail body: "Check build logs at ${env.BUILD_URL}", from: 'jenkins-admin@gmail.com', subject: "Jenkins pipeline for job ${env.JOB_NAME} is completed successfully", to: "dev@drone.service"
        }
    }
}
