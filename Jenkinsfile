node{
    
    stage("Git Clone"){

        git branch: 'main', url: 'https://github.com/harishdasari1348/springboot-microservices'
    }
    
    stage('Build Project'){
        def mvnHome = tool name: 'maven', type: 'maven'
          sh "${mvnHome}/bin/mvn package"
          echo "Executed Successfully Project1"
    }
    
    stage("Docker build"){
        sh 'docker version'
        /*sh 'docker build -t java-img .'
        sh 'docker image list'
        sh 'docker tag java-img jyothsna1899/java-img:1.0' */
        sh 'ls && cd Jwt-Authentication'
        sh 'ls && cd App-2'
        sh 'docker build -t jyothsna1899/app-2-img:latest -f Dockerfile .'
        sh 'cd .. && cd MSS-User-Auth'
        sh 'docker build -t jyothsna1899/mss-user-auth-img:latest -f Dockerfile .'
        sh 'cd .. && cd frontend'
        sh 'docker build -t jyothsna1899/frontend-img:latest -f Dockerfile .'
        sh 'docker image ls'
    }
    
    withCredentials([string(credentialsId: 'jyothsna1899', variable: 'PASSWORD')]) {
        sh 'docker login -u jyothsna1899 -p $PASSWORD'
    }

    stage("Pushing Image to Docker Hub"){
       /* sh 'docker push jyothsna1899/java-img:1.0' */
       sh 'docker push jyothsna1899/app-2-img:latest'
       sh 'docker push jyothsna1899/mss-user-auth-img:latest'
       sh 'docker push jyothsna1899/frontend-img:latest'
    }
    
    stage("SSH Into k8s Server") {
        def remote = [:]
        remote.name = 'K8S master'
        remote.host = '40.121.137.58'
        remote.user = 'azuredevops'
        remote.password = 'mssdevops@123'
        remote.allowAnyHosts = true
    
        stage('Put app.yaml onto k8smaster') {
            
            //sshPut remote: remote, from: 'springboot-app.yaml', into: '/home/azuredevops'
            //sshCommand remote: remote, command: "sudo chmod 777 springboot-app.yaml "
            //sshCommand remote: remote, command: " sudo chown root:root springboot-app.yaml "
            //sh 'docker push jyothsna1899/java-img:1.0'
            sh 'docker push jyothsna1899/app-2-img:latest'
            sh 'docker push jyothsna1899/mss-user-auth-img:latest'
            sh 'docker push jyothsna1899/frontend-img:latest'
            sh 'echo $USER'
        }
        
        stage('Deploy Spring Boot Application') {
            // sshCommand remote: remote, command: "sudo kubectl apply -f springboot-app.yaml "
            //sh 'sudo kubectl apply -f springboot-app.yaml' 
          sh 'sudo kubectl apply -f app.yaml'
          sh 'sudo kubectl create configmap mysql-initdb-config --from-file=users.sql'
        }
    }
    
}
    
    



    


