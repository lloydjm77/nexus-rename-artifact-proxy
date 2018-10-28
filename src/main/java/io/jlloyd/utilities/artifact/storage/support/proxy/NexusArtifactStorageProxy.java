package io.jlloyd.utilities.artifact.storage.support.proxy;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import io.jlloyd.utilities.artifact.storage.support.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * Controller for resolving artifacts in Nexus.
 * 
 * @author b14951
 */
@Controller
public class NexusArtifactStorageProxy implements ArtifactStorageProxy {

	private static final Logger LOGGER = LoggerFactory.getLogger(NexusArtifactStorageProxy.class);

	@Inject
	private RestTemplate restTemplate;

	private HttpHeaders cachePreventionHeaders;

	@PostConstruct
	public void init() {
		cachePreventionHeaders = new HttpHeaders();
		cachePreventionHeaders.add(HttpHeaders.CACHE_CONTROL, Application.NO_CACHE_NO_STORE_MUST_REVALIDATE);
		cachePreventionHeaders.add(HttpHeaders.PRAGMA, Application.NO_CACHE);
		cachePreventionHeaders.add(HttpHeaders.EXPIRES, "0");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jlloyd.utilities.nexus.artifact.support.ws.controller.IArtifactResolverController#showPage(java.lang.String,
	 * java.util.Optional, org.springframework.ui.ModelMap)
	 */
	@Override
	public String loadArtifact(@RequestParam String artifactPath, @RequestParam Optional<String> filename,
			ModelMap modelMap) {

		String downloadFilename = filename.orElse(Paths.get(artifactPath).getFileName().toString());

		LOGGER.debug("Returning page.");

		modelMap.put(Application.FILENAME, downloadFilename);
		modelMap.put(Application.ARTIFACT_PATH, artifactPath);

		return "download";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jlloyd.utilities.nexus.artifact.support.ws.controller.IArtifactResolverController#download(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ResponseEntity<InputStreamResource> download(@RequestParam String artifactPath,
			@RequestParam String filename) {

		LOGGER.debug("Fetching: " + artifactPath);

		ResponseEntity<byte[]> artifactPathResponse = restTemplate.getForEntity(artifactPath, byte[].class);
		byte[] artifact = artifactPathResponse.getBody();

		// @formatter:off

		return ResponseEntity
				.ok()
				.headers(cachePreventionHeaders)
				.header(HttpHeaders.CONTENT_DISPOSITION, Application.ATTACHMENT_FILENAME + filename)
				.contentLength(artifact.length)
				.body(new InputStreamResource(new ByteArrayInputStream(artifact)));

		// @formatter:on
	}
}