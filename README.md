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
- Sample output available in [`#10-Pipeline-Console-Output.txt`](#10-Pipeline-Console-Output.txt)
