def call() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: '73b703cb-6da5-46be-b448-5954f154defe', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t harshwardhan07/harshwardhan:jenkinsJMA-1.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push harshwardhan07/harshwardhan:jenkinsJMA-1.0'
    }
}
