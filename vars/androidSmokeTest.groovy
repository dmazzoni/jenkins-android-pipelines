def call(Map config) {

  pipeline {
    def gitUri = config.gitUri
    def moduleName = config.moduleName
    def credentialsId = config.credentialsId

    if (gitUri == null || moduleName == null || credentialsId == null) {
        throw new IllegalStateException('Missing configuration arguments')
    }

    stage ('Checkout') {
        git credentialsId: credentialsId, url: gitUri
    }

  	stage ('Analyze') {
  	    sh "./gradlew :${moduleName}:lint"
  	    androidLint()
  	}

  	stage ('Build') {
  	   sh "./gradlew :${moduleName}:clean :${moduleName}:assemble"
  	}

  	stage ('Unit Test') {
  	    sh "./gradlew :${moduleName}:jacocoTestReport"
  	}
  }
}