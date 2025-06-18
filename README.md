# Java Maven Application - Jenkins Docker AWS Pipeline 🚀

A complete CI/CD pipeline implementation using Jenkins, Docker, and AWS EC2 for a Spring Boot application.


## 🎯 Pipeline Success

![Successful Pipeline](/asset/Successful-Pipeline-18.png)
![Stages](/asset/stages-18.png)
![EC2](/asset/AWS-EC2.png)
![Docker-Container](/asset/Docker-Container.png)


## 📋 Project Overview

This project demonstrates a fully automated CI/CD pipeline that:
- Builds a Java Maven Spring Boot application
- Packages it as a Docker container
- Deploys it to AWS EC2 using Jenkins Pipeline



## 🌿 Branch Structure & Functionality

This repository maintains three specialized branches with distinct purposes:

### `main` Branch
- **Purpose**: Production-ready code and stable documentation
- **Contains**: 
  - Latest stable version of the application source code
  - Project documentation and setup guides
  - Asset files and screenshots
  - Core configuration files

### `jenkins-jobs` Branch  
- **Purpose**: Active CI/CD pipeline execution and automation
- **Functionality**:
  - **Automated Version Management**: Increments Maven version numbers automatically (e.g., 1.1.0 → 1.1.1)
  - **Continuous Integration**: Builds Java Maven application and runs tests
  - **Docker Operations**: Builds Docker images and pushes to Docker Hub (`harshwardhan07/harshwardhan:jenkinsJMA-1.0`)
  - **AWS EC2 Deployment**: Deploys containerized application to EC2 instance via SSH
  - **Auto-commit**: Commits version bumps back to jenkins-jobs branch automatically
  - **Pipeline Monitoring**: Tracks build history and deployment status

### `jenkins-shared-library` Branch
- **Purpose**: Reusable Jenkins pipeline functions
- **Contains**:
  - `vars/buildJar.groovy` - Maven build function
  - `vars/buildImage.groovy` - Docker build and push function with error handling
  - `vars/deployApp.groovy` - Deployment function
  - Shared pipeline utilities and common functions

### Pipeline Workflow
```
jenkins-jobs branch → Jenkins Pipeline → Docker Hub → AWS EC2
       ↓                     ↓              ↓          ↓
Version increment    Build & Test    Push Image   Deploy App
Auto-commit         Use shared lib   Registry     Live service
```

**Key Features of jenkins-jobs branch**:
- ✅ Fully automated CI/CD pipeline with 5 stages
- ✅ Automatic version incrementation and Git commits
- ✅ Docker image building with permission handling
- ✅ AWS EC2 deployment with SSH automation
- ✅ Enhanced error handling and troubleshooting
- ✅ Integration with shared library functions


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

## 🔧 Jenkins Pipeline Configuration

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
- Sample output available in [`#18-Pipeline-Console-Output.txt`](#18-Pipeline-Console-Output.txt)
