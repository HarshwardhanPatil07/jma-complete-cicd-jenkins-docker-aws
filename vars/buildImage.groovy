def call() {
    echo "building the docker image..."
    
    // Check if Docker is available and daemon is running
    script {
        def dockerInstalled = sh(script: 'which docker || echo "not_found"', returnStdout: true).trim()
        if (dockerInstalled == "not_found") {
            error "Docker is not installed on this Jenkins agent. Please install Docker or use a Docker-enabled agent."
        }
        
        // Check if Docker daemon is running and accessible
        def dockerAccessible = sh(script: 'docker version > /dev/null 2>&1 && echo "accessible" || echo "denied"', returnStdout: true).trim()
        if (dockerAccessible == "denied") {
            echo "Docker daemon permission denied. Attempting to fix permissions..."
            try {
                // Try to fix Docker socket permissions
                sh 'sudo chmod 666 /var/run/docker.sock || true'
                sh 'sleep 2'
                
                // Verify Docker is now accessible
                def retryAccessible = sh(script: 'docker version > /dev/null 2>&1 && echo "accessible" || echo "denied"', returnStdout: true).trim()
                if (retryAccessible == "denied") {
                    error """
Docker daemon is running but not accessible to jenkins user.
Please fix Docker permissions on the Jenkins agent:
- sudo usermod -aG docker jenkins
- sudo systemctl restart jenkins
- Or run: sudo chmod 666 /var/run/docker.sock
"""
                }
                echo "Docker permissions fixed successfully!"
            } catch (Exception e) {
                error """
Docker daemon permission denied and cannot be fixed automatically.
Please fix Docker permissions manually on the Jenkins agent:
- sudo usermod -aG docker jenkins (then restart Jenkins)
- Or run: sudo chmod 666 /var/run/docker.sock

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
