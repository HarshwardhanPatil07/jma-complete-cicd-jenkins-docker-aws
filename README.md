# Java Maven Application - Jenkins Docker AWS Pipeline 🚀

A complete CI/CD pipeline implementation using Jenkins, Docker, and AWS EC2 for a Spring Boot application.

## 🌿 Branch Structure

This repository maintains two main branches with distinct functionalities:

### `main` Branch
- **Purpose**: Production-ready code and documentation
- **Contains**: 
  - Latest stable version of the application
  - Complete project documentation
  - Asset files and screenshots
  - Final configuration files

### `jenkins-jobs` Branch  
- **Purpose**: Active CI/CD pipeline integration
- **Contains**:
  - Jenkins pipeline execution code
  - Automated version updates from pipeline
  - Build artifacts and deployment configurations
  - Continuous integration workflows

### Branch Workflow
```
main branch (stable) ←→ jenkins-jobs branch (CI/CD active)
     ↓                           ↓
Documentation &            Pipeline execution &
Stable releases           Automated commits
```

The [Jenkinsfile](Jenkinsfile) is configured to push version updates to the `jenkins-jobs` branch:
```groovy
sh 'git push origin HEAD:jenkins-jobs'
```

This ensures that:
- ✅ Pipeline automation doesn't interfere with main branch stability
- ✅ Version increments are tracked in the CI/CD branch
- ✅ Main branch remains clean for releases and documentation
- ✅ Both branches maintain full project functionality

## 🎯 Pipeline Success

![happiness of execution](asset/Successful-Pipeline-10.png)
![Pipeline Stages](asset/Stages-10.png)
![EC2](AWS-EC2.png)
![Docker-Container](Docker-Container.png)

## 📋 Project Overview

This project demonstrates a fully automated CI/CD pipeline that:
- Builds a Java Maven Spring Boot application
- Packages it as a Docker container
- Deploys it to AWS EC2 using Jenkins Pipeline

## 🏗️ Architecture

```
GitHub Repository → Jenkins Pipeline → Docker Build → AWS EC2 Deployment
```

## 🛠️ Technologies Used

- **Backend**: Java 8, Spring Boot 2.3.5
- **Build Tool**: Maven
- **CI/CD**: Jenkins Pipeline (Declarative)
- **Containerization**: Docker
- **Cloud**: AWS EC2
- **Version Control**: Git/GitHub


## 🚀 Getting Started

### Prerequisites

- Java 8+
- Maven 3.6+
- Jenkins with required plugins
- Docker
- AWS EC2 instance
- GitHub repository access

1. **Clone the repository**
   ```bash
   git clone https://github.com/HarshwardhanPatil07/JMA-jenkins-docker-AWS.git
   cd JMA-jenkins-docker-AWS
   ```

## 🔧 Complete Setup Guide

### 1. Initial Project Setup

#### Branch Management
```bash
# Clone and create branches
git clone https://github.com/HarshwardhanPatil07/JMA-jenkins-docker-AWS.git
cd JMA-jenkins-docker-AWS

# Create jenkins-jobs branch
git checkout -b jenkins-jobs
git push origin jenkins-jobs

# Create jenkins-shared-library branch  
git checkout -b jenkins-shared-library
git push origin jenkins-shared-library

# Switch between branches
git checkout main           # Switch to main
git checkout jenkins-jobs   # Switch to CI/CD branch
git checkout -             # Switch to previous branch
```

#### Shared Library Setup
```bash
# Switch to shared library branch
git checkout jenkins-shared-library

# Create shared library structure
mkdir vars
# Create function files:
# - vars/buildJar.groovy
# - vars/buildImage.groovy  
# - vars/deployApp.groovy

git add vars/
git commit -m "Create Jenkins shared library structure"
git push origin jenkins-shared-library
```

### 2. Jenkins Configuration

#### Install Required Tools
In Jenkins **Manage Jenkins** → **Global Tool Configuration**:

1. **Maven Installation**
   - Name: `maven-3.9` 
   - Installation: Automatic from Apache

2. **JDK Installation**
   - Name: `jdk17`
   - Installation: Automatic from Eclipse Adoptium

3. **SonarQube Scanner** (if using)
   - Name: `sonar-scanner`
   - Installation: Automatic

#### Configure Global Pipeline Library
**Manage Jenkins** → **Configure System** → **Global Pipeline Libraries**:

- **Name**: `jenkins-shared-library`
- **Default version**: `jenkins-shared-library`
- **Retrieval method**: Modern SCM
- **Source Code Management**: Git
- **Project Repository**: `https://github.com/HarshwardhanPatil07/JMA-jenkins-docker-AWS.git`
- **Credentials**: Select your GitHub credentials
- **Behaviors**: Add "Discover branches" → Pattern: `jenkins-shared-library`

#### Required Credentials Setup

**Manage Jenkins** → **Credentials** → **System** → **Global credentials**:

1. **GitHub Personal Access Token**
   - **ID**: `gitlab-credentials` (keep this name for compatibility)
   - **Type**: Username with password
   - **Username**: Your GitHub username
   - **Password**: GitHub Personal Access Token
   - **⚠️ Important**: PAT expires every 30-90 days - update regularly!

2. **Docker Hub Credentials**
   - **ID**: `73b703cb-6da5-46be-b448-5954f154defe`
   - **Type**: Username with password  
   - **Username**: Your Docker Hub username
   - **Password**: Docker Hub password/token

3. **EC2 SSH Key**
   - **ID**: `ec2-server-key`
   - **Type**: SSH Username with private key
   - **Username**: `ec2-user`
   - **Private Key**: Content of your EC2 .pem file

### 3. Jenkins Pipeline Configuration

#### Create Multibranch Pipeline
1. **New Item** → **Multibranch Pipeline**
2. **Branch Sources** → **Git**
   - **Project Repository**: `https://github.com/HarshwardhanPatil07/JMA-jenkins-docker-AWS.git`
   - **Credentials**: Select GitHub credentials
   - **Behaviors**: 
     - Discover branches: All branches
     - Discover pull requests from forks: None

#### Pipeline Execution
- Pipeline will automatically detect `Jenkinsfile` in `jenkins-jobs` branch
- Builds will run on the `jenkins-jobs` branch
- Version updates committed back to `jenkins-jobs` branch

### 4. Docker Setup

#### On Windows (Development)
```powershell
# Check Docker service
Get-Service -Name "*docker*"

# Start Docker Desktop (GUI application)
# Or start service (if Docker Desktop installed)
Start-Service -Name "com.docker.service"

# Verify Docker
docker --version
docker run hello-world
```

#### On Jenkins Server (Linux)
```bash
# Install Docker
sudo apt-get update
sudo apt-get install -y docker.io

# Add jenkins user to docker group
sudo usermod -aG docker jenkins

# Start Docker service
sudo systemctl start docker
sudo systemctl enable docker

# Verify installation
docker --version
```

#### Jenkins with Docker (Container setup)
```bash
# Run Jenkins with Docker socket mounted
docker run -p 8080:8080 -p 50000:50000 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts

# Or install Docker inside Jenkins container
docker exec -it -u root jenkins_container bash
apt-get update && apt-get install -y docker.io
usermod -aG docker jenkins
```

### 5. AWS EC2 Setup

#### EC2 Instance Configuration
1. **Launch EC2 instance** (Amazon Linux 2)
2. **Configure Security Group**:
   - SSH (22): Your IP
   - HTTP (80): 0.0.0.0/0
   - Custom (3080): 0.0.0.0/0 (for application)

#### Install Docker on EC2
```bash
# Connect to EC2
ssh -i your-key.pem ec2-user@your-ec2-ip

# Install Docker
sudo yum update -y
sudo yum install -y docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker ec2-user

# Logout and login again for group changes
exit
ssh -i your-key.pem ec2-user@your-ec2-ip

# Verify Docker
docker --version
```

### 6. Troubleshooting Common Issues

#### 🔴 Maven Tool Error
**Error**: `Tool type "maven" does not have an install of "Maven" configured`

**Solution**: Update Jenkinsfile tools section:
```groovy
tools {
    maven 'maven-3.9'  // Use exact name from Global Tool Configuration
    jdk 'jdk17'
}
```

#### 🔴 Docker Not Found Error  
**Error**: `docker: not found`

**Solutions**:
1. **Install Docker** on Jenkins agent
2. **Pipeline already handles this** - Docker build stage will be skipped if Docker unavailable
3. **Check Docker service**: `sudo systemctl status docker`

#### 🔴 GitHub Authentication Failed
**Error**: `Authentication failed for 'https://github.com/...'`

**Solutions**:
1. **Update Personal Access Token** in Jenkins credentials
2. **Check PAT permissions**: repo, workflow, admin:repo_hook
3. **Verify credentials ID** matches Jenkinsfile: `gitlab-credentials`

#### 🔴 Shared Library Not Found
**Error**: `Library jenkins-shared-library expected to contain at least one of src or vars directories`

**Solution**:
1. Ensure `vars/` directory exists in `jenkins-shared-library` branch
2. Check Global Pipeline Library configuration
3. Verify branch name: `jenkins-shared-library`

#### 🔴 EC2 Connection Issues
**Error**: SSH connection failed

**Solutions**:
1. **Check Security Group**: Allow SSH (port 22) from Jenkins IP
2. **Verify EC2 IP**: Update IP in Jenkinsfile if changed
3. **Test SSH manually**: `ssh -i key.pem ec2-user@ip`
4. **Check credentials**: Ensure `ec2-server-key` credential exists

#### 🔴 Version Increment Conflicts
**Error**: Git conflicts during version commit

**Solution**:
```bash
# Manual resolution on jenkins-jobs branch
git checkout jenkins-jobs
git pull origin jenkins-jobs
git merge main --strategy-option=theirs
git push origin jenkins-jobs
```

#### 🔴 Docker Daemon Not Running
**Error**: `Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?`

**Solutions**:
1. **Check Docker daemon status**:
   ```bash
   sudo systemctl status docker
   ```

2. **Start Docker daemon**:
   ```bash
   sudo systemctl start docker
   sudo systemctl enable docker  # Auto-start on boot
   ```

3. **Add Jenkins user to Docker group**:
   ```bash
   sudo usermod -aG docker jenkins
   # Restart Jenkins service after this
   sudo systemctl restart jenkins
   ```

4. **Manual Docker socket permissions** (if needed):
   ```bash
   sudo chmod 666 /var/run/docker.sock
   ```

5. **Verify Docker is working**:
   ```bash
   docker version
   docker run hello-world
   ```

**Note**: The shared library now automatically attempts to start Docker daemon if it's stopped.

### 7. Branch Maintenance

#### Syncing Branches
```bash
# Keep jenkins-jobs updated with main
git checkout jenkins-jobs
git merge main
git push origin jenkins-jobs

# Update shared library independently
git checkout jenkins-shared-library
# Make changes to vars/ functions
git add vars/
git commit -m "Update shared library functions"
git push origin jenkins-shared-library
```

#### Regular Maintenance Tasks
- **Monthly**: Update GitHub Personal Access Token
- **Weekly**: Merge main branch changes to jenkins-jobs
- **As needed**: Update EC2 IP addresses in Jenkinsfile
- **As needed**: Update Docker image tags and versions

### 8. Pipeline Monitoring

#### Success Indicators
- ✅ All pipeline stages pass
- ✅ Version number incremented in pom.xml
- ✅ Docker image pushed to registry
- ✅ Application running on EC2:3080
- ✅ Git commit pushed to jenkins-jobs branch

#### Monitoring Commands
```bash
# Check application on EC2
curl http://your-ec2-ip:3080

# Check Docker containers on EC2  
ssh ec2-user@your-ec2-ip "docker ps"

# Check latest Docker image
docker pull harshwardhan07/harshwardhan:jenkinsJMA-1.0
```

### Required Credentials

Configure these credentials in Jenkins:

1. **GitHub Credentials** (`github-credentials`)
   - Type: Username with password
   - Username: Your GitHub username
   - Password: Personal Access Token (PAT)
   - ⚠️ **Note**: PAT expires every 30 days - update regularly

2. **EC2 Server Key** (`ec2-server-key`)
   - Type: SSH Username with private key
   - ID: `ec2-server-key`
   - Username: `ec2-user`
   - Private Key: Your EC2 instance .pem file content

### Pipeline Stages

The [Jenkinsfile](Jenkinsfile) defines three stages:

1. **Test Stage** - Runs application tests
2. **Build Stage** - Builds the Maven application
3. **Deploy Stage** - Deploys Docker container to EC2

## 🐳 Docker Deployment

The pipeline deploys the application using:
```bash
docker run -p 3080:3080 -d harshwardhan07/my-app:1.0
```

- Update in AWS
![alt text](InboundRules.png)


## 🔍 Troubleshooting

### Common Issues

1. **Credential Expiry**
   - GitHub PAT expires every 30 days
   - Update credentials in Jenkins: `Dashboard → Manage Jenkins → Credentials`

2. **EC2 Connection Issues**
   - Verify security group allows SSH (port 22)
   - Check EC2 instance public IP
   - Ensure .pem file permissions are correct

3. **Docker Pull Issues**
   - Verify Docker image exists in registry
   - Check network connectivity from EC2 to Docker Hub

### Logs
Check the pipeline console output for detailed logs:
- Available in Jenkins job → Build History → Console Output
- Sample output available in [`#10-Pipeline-Console-Output.txt`](#10-Pipeline-Console-Output.txt)
