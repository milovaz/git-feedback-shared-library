#!/usr/bin/env groovy

def call() {
    def scmVars = checkout scm
    def environment = scmVars.GIT_BRANCH == 'master' ? 'production' : 'development'
    def description = "Deploying branch" 
    def ref = scmVars.GIT_COMMIT
    def splittedURL = scmVars.GIT_URL.split('/')
    def repo = splittedURL.last().split('\\.').first()
    def owner = splittedURL[-2]
    //def owner = "milovaz"
    //def repo = "node-js-sample"
    def deployURL = "https://api.github.com/repos/${owner}/${repo}/deployments"
    def deployBody = '{"ref": "' + ref +'","environment": "' + environment  +'","description": "' + description + '", "auto_merge": false, "required_contexts": []}'

    // Create new Deployment using the GitHub Deployment API
    def response = httpRequest authentication: 'github-user', httpMode: 'POST', requestBody: deployBody, responseHandle: 'STRING', url: deployURL
    if(response.status != 201) {
        error("Deployment API Create Failed: " + response.status)
    }

    // Get the ID of the GitHub Deployment just created
    def responseJson = readJSON text: response.content
    def id = responseJson.id
    if(id == "") {
        error("Could not extract id from Deployment response")
    }

    env.GIT_COMMIT = scmVars.GIT_COMMIT
    env.GIT_BRANCH = scmVars.GIT_BRANCH
    env.REPO_OWNER = owner
    env.REPO_NAME = repo
    env.DEPLOYMENT_ID = id
}