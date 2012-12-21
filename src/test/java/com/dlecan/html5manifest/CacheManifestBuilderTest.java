package com.dlecan.html5manifest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import com.dlecan.html5manifest.CacheManifestBuilder.Cache;
import com.dlecan.html5manifest.CacheManifestBuilder.Fallback;
import com.dlecan.html5manifest.CacheManifestBuilder.Network;

public class CacheManifestBuilderTest {

	private CacheManifestBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = CacheManifestBuilder.newInstance();
	}

	@Test
	public void test() throws Exception {

		String expectedManifest = readFile("/file1.manifest");

		builder.withVersion("2010-06-18:v2");
		Cache cache = builder.getCache();
		cache.add("/favicon.ico").add("index.html").add("images/logo.png");

		Network network = builder.getNetwork();
		network.add("login.php").add("/myapi").add("http://api.twitter.com");

		Fallback fallback = builder.getFallback();
		fallback.add("/main.py", "/static.html")
				.add("images/large/", "images/offline.jpg")
				.add("*.html", "/offline.html");

		String manifest = builder.toRawString();

		assertThat(manifest, equalTo(expectedManifest));
	}

	private String readFile(String resourcePath) throws Exception {

		URI uri = getClass().getResource(resourcePath).toURI();

		FileInputStream stream = new FileInputStream(new File(uri));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			return Charset.forName("UTF-8").decode(bb).toString();
		} finally {
			stream.close();
		}
	}
}
