pipeline {
    agent any
    environment{
        app = ''
    }
    stages{
        stage('Clone repository')
        {
            /* Cloning the Repository to our Workspace */
            steps
            {
                checkout scm
            }
        }
        stage('Build')
        {
            steps
            {
                sh 'mvn clean package'
            }
        }
        stage('Build image')
        {
            /* This builds the actual image */
            steps
            {
                script
                {
                    app = docker.build("rtgrdh/backend-file-master")
                }
            }
        }
        stage('Push image')
        {
            /*
            You would need to first register with DockerHub before you can push images to your account
            */
            steps
            {
                script
                {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub')
                    {
                        app.push("${env.BUILD_NUMBER}")
                    }
                    echo "Pushing to docker hub"
                }
            }
        }
    }
}