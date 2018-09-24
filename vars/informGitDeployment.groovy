#!/usr/bin/env groovy

def call(String owner, String repo, String id) {
    // Record new Deployment Status based on output
    def result = 'success'
    def deployStatusBody = '{"state": "' + result + '","target_url": "http://github.com/deploymentlogs"}'
    def deployStatusURL = "https://api.github.com/repos/${owner}/${repo}/deployments/${id}/statuses"
    def deployStatusResponse = httpRequest authentication: 'github-user', httpMode: 'POST', requestBody: deployStatusBody , responseHandle: 'STRING', url: deployStatusURL
    if(deployStatusResponse.status != 201) {
      error("Deployment Status API Update Failed: " + deployStatusResponse.status + " - " + deployStatusResponse.message)
    }
}