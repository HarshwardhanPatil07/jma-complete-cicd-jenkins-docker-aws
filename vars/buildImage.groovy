def call() {
    echo "building the docker image..."
    
    // Check if Docker is available and daemon is running
    script {
        def dockerInstalled = sh(script: 'which docker || echo "not_found"', returnStdout: true).trim()
        if (dockerInstalled == "not_found") {
            error "Docker is not installed on this Jenkins agent. Please install Docker or use a Docker-enabled agent."
        }
        
        // Check if Docker daemon is running
        def dockerRunning = sh(script: 'docker version > /dev/null 2>&1 && echo "running" || echo "stopped"', returnStdout: true).trim()
        if (dockerRunning == "stopped") {
            echo "Docker daemon is not running. Attempting to start Docker service..."
            try {
                sh 'sudo systemctl start docker'
                sh 'sleep 5'  // Wait for daemon to start
                // Verify daemon started
                sh 'docker version'
                echo "Docker daemon started successfully!"
            } catch (Exception e) {
                error """
Docker daemon is not running and cannot be started automatically.
Please start Docker daemon manually on the Jenkins agent:
- sudo systemctl start docker
- sudo systemctl enable docker
- sudo usermod -aG docker jenkins (then restart Jenkins)

Error: ${e.getMessage()}
"""
            }
        }
    }
    
    withCredentials([usernamePassword(credentialsId: '73b703cb-6da5-46be-b448-5954f154defe', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t harshwardhan07/harshwardhan:jenkinsJMA-1.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push harshwardhan07/harshwardhan:jenkinsJMA-1.0'
    }
}
