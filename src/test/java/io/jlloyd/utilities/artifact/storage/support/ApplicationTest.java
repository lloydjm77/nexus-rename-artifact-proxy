package io.jlloyd.utilities.artifact.storage.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.jlloyd.utilities.artifact.storage.support.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ApplicationTest {

	private static final String FILENAME = "spring-test.jar";
	private static final String ARTIFACT_PATH = "https://repo1.maven.org/maven2/org/springframework/spring-test/5.1.1.RELEASE/spring-test-5.1.1.RELEASE.jar";
	private static final String ARTIFACT_TEST_URL = "/artifact?filename=" + FILENAME + "&artifactPath=" + ARTIFACT_PATH;
	private static final String DOWNLOAD_TEST_URL = "/download?filename=" + FILENAME + "&artifactPath=" + ARTIFACT_PATH;

	@LocalServerPort
	private int port;

	@Inject
	private TestRestTemplate restTemplate;

	@Test
	public void testArtifact() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		ResponseEntity<String> entity = restTemplate.exchange(ARTIFACT_TEST_URL, HttpMethod.GET,
				new HttpEntity<Void>(headers), String.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("<meta http-equiv=\"refresh\" content=\"0; url=download?filename="
				+ FILENAME + "&artifactPath=" + ARTIFACT_PATH + "\" />");
	}

	@Test
	public void testDownload() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		ResponseEntity<byte[]> entity = restTemplate.exchange(DOWNLOAD_TEST_URL, HttpMethod.GET,
				new HttpEntity<Void>(headers), byte[].class);

		HttpHeaders responseHeaders = entity.getHeaders();

		List<String> contentDispositionHeaders = responseHeaders.get(HttpHeaders.CONTENT_DISPOSITION);

		assertCachePreventionHeaders(responseHeaders);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(contentDispositionHeaders).hasSize(1);
		assertThat(contentDispositionHeaders.get(0)).isEqualTo(Application.ATTACHMENT_FILENAME + FILENAME);
		assertThat(entity.hasBody());
	}

	private void assertCachePreventionHeaders(HttpHeaders responseHeaders) {
		assertThat(responseHeaders.getCacheControl()).isEqualTo(Application.NO_CACHE_NO_STORE_MUST_REVALIDATE);
		assertThat(responseHeaders.getPragma()).isEqualTo(Application.NO_CACHE);
		assertThat(responseHeaders.getExpires()).isEqualTo(-1);
	}
}