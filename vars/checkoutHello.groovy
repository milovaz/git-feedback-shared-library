#!/usr/bin/env groovy

def call() {
  def scmVars = checkout scm
  env.GIT_COMMIT = scmVars.GIT_COMMIT
}