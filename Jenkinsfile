pipeline {

    agent any

    tools {
        jdk 'jdk'
	maven 'maven'
    }

    stages {
        stage('CHECKOUT') {
            steps {
                git branch: 'deployment', url: 'https://github.com/crupiers/ecommerce-backend.git'
                echo "Commit: ${env.GIT_COMMIT}"
            }
        }

        stage('BUILD') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        //stage('UNIT TEST') {
            //steps {
		//sh 'mvn test'
		//junit '**/target/surefire-reports/*.xml'
            //}
        //}

	//stage('INTEGRATION TEST') {
            //steps {
		//sh 'mvn verify'
		//junit '**/target/failsafe-reports/*.xml'
            //}
        //}

	//stage('RUN') {
            //steps {
                //sh 'nohup java -jar target/ecommerce-crupiers-1.0.0-jar-with-dependencies.jar > app.log 2>&1 &'
            //}
        //}

        stage('DOCKER') {
            steps {
                script {
                    sh "docker build -t ec_bk ."
                }
            }
        }
    }

    post {
        always {
            cleanWs()
            echo "Pipeline finalizada con commit: ${env.GIT_COMMIT}"
        }
        success {
            echo 'Pipeline exitosa'
        }
        failure {
            echo 'Pipeline fallida'
        }
    }
}