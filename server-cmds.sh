#!/usr/bin/env bash

export IMAGE=$1

# Check if docker-compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "docker-compose not found. Installing..."
    
    # Install docker-compose
    sudo curl -L "https://github.com/docker/compose/releases/download/v2.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    
    # Create symlink for backward compatibility
    sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
    
    echo "docker-compose installed successfully"
    docker-compose --version
fi

# Run docker-compose
docker-compose -f docker-compose.yaml up --detach
echo "success"