#!/usr/bin/env groovy

def call(String channel) {
    def COLOR_MAP = ['SUCCESS': 'good', 'FAILURE': 'danger', 'UNSTABLE': 'danger', 'ABORTED': 'danger']
    def resultColor = COLOR_MAP[currentBuild.currentResult]

    slackSend channel: "${channel}", color: "${resultColor}", message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}"
}