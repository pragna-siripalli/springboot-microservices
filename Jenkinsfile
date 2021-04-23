node{
    
    stage("Git Clone"){

        git branch: 'main', url: 'https://github.com/harishdasari1348/springboot-microservices'
    }
   
    stage("Docker build"){
        sh 'docker version'
        sh 'cd App-2 && docker build -t jyothsna1899/app-2-img:latest -f Dockerfile .'
        sh 'cd MSS-User-Auth && docker build -t jyothsna1899/mss-user-auth-img:latest -f Dockerfile .'
        sh 'cd frontend && docker build -t jyothsna1899/frontend-img:latest -f Dockerfile .'
        sh 'docker image ls'
    }

    withCredentials([string(credentialsId: 'jyothsna1899', variable: 'PASSWORD')]) {
        sh 'docker login -u jyothsna1899 -p $PASSWORD'
    }

    stage("Pushing Image to Docker Hub"){
     
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
            sh 'docker push jyothsna1899/app-2-img:latest'
            sh 'docker push jyothsna1899/mss-user-auth-img:latest'
            sh 'docker push jyothsna1899/frontend-img:latest'
            sh 'echo $USER'
          }
        
        stage('Deploy Spring Boot Application') {
          sh 'sudo kubectl create configmap mysql-initdb-config --from-file=users.sql'
          sh 'sudo kubectl apply -f app.yaml'
          
        }
       
    }
    
}
