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

public class CacheManifestBuilderTest {

	private CacheManifestBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = CacheManifestBuilder.newInstance();
	}

	@Test
	public void test() throws Exception {

		String expectedManifest = readFile("/file1.manifest");

		String manifest = builder.withVersion("myversion").getCache()
				.add("/truc").getCache().add("muche").getNetwork().addAll()
				.getFallback().add("/bidule", "offline.html").toRawString();

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
