#!/usr/bin/env groovy

import io.jlloyd.pipeline.config.ExternalConfig

// Pull the shared yaml files from Bitbucket.
def externalPipelineConfig = new ExternalConfig("PAU", "liferay-pipeline-config", "pipeline.yml", "heads/master")

// Execute the pipeline.
standardMavenPipeline([ externalConfigs: [ externalPipelineConfig ]])
