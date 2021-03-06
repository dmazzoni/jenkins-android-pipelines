def call(Map config) {

  pipeline {
    def gitUri = config.gitUri
    def moduleName = config.moduleName
    def credentialsId = config.credentialsId
    def gitBranch = config.gitBranch ?: 'master'
    def lintReportPattern = config.lintReportPattern ?: '**/lint-results.xml'

    if (gitUri == null || moduleName == null || credentialsId == null) {
        throw new IllegalStateException('Missing configuration arguments')
    }

    androidBuild(config)

  	stage ('Unit Test') {
  	    sh "./gradlew :${moduleName}:jacocoTestReport"
  	}
  }
}
