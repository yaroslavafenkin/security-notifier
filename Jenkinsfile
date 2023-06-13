node {
    stage('Checkout') {
        // Checkout code from SCM
        checkout scm
    }

    stage('Build') {
        // Run Maven command
        sh 'mvn install -DskipTests'
    }
}

