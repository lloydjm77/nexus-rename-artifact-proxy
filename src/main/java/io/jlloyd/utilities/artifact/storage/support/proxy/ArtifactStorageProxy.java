package io.jlloyd.utilities.artifact.storage.support.proxy;

import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Defines methods for resolving artifacts.
 * 
 * @author b14951
 */
public interface ArtifactStorageProxy {

	/**
	 * Returns an HTML view used for loading the artifact.
	 * 
	 * @param artifactPath
	 *            - The original path to the artifact.
	 * @param filename
	 *            - The renamed file, or the original name if none is specified.
	 * @param modelMap
	 *            - The {@link ModelMap} used to set parameters.
	 * @return The view name.
	 */
	@GetMapping(value = "/artifact")
	String loadArtifact(String artifactPath, Optional<String> filename, ModelMap modelMap);

	/**
	 * Downloads the artifact at the supplied artifactPath renamed to the supplied filename.
	 * 
	 * @param artifactPath
	 *            - The original path to the artifact.
	 * @param filename
	 *            - The renamed file, or the original name if none is specified.
	 * @return A {@link ResponseEntity} that will be interpreted as an application/octet-stream.
	 */
	@GetMapping(value = "/download")
	ResponseEntity<InputStreamResource> download(String artifactPath, String filename);
}