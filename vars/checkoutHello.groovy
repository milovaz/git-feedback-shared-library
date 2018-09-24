#!/usr/bin/env groovy

def call() {
  def scmVars = checkout scm
  echo "Hello, ${scmVars.GIT_COMMIT}."
}