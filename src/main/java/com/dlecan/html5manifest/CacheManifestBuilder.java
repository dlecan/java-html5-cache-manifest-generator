package com.dlecan.html5manifest;

import java.util.ArrayList;
import java.util.List;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * @author Damien Lecan
 */
public class CacheManifestBuilder {

	public interface Cache {
		public Cache add(String resource);
	}

	public interface Network {
		public Network add(String resource);

		public CacheManifestBuilder addAll();

	}

	public interface Fallback {
		public Fallback add(String resource, String fallbackResource);

	}

	private class SectionCache implements Cache {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.dlecan.html5manifest.CacheManifestBuilder.Cache#add(java.lang
		 * .String)
		 */
		@Override
		public Cache add(String resource) {
			assert resource != null : "Resource cannot be null";
			itemsCache.add(resource);
			return this;
		}

	}

	private class SectionFallback implements Fallback {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.dlecan.html5manifest.CacheManifestBuilder.Fallback#add(java.lang
		 * .String, java.lang.String)
		 */
		@Override
		public Fallback add(String resource, String fallbackResource) {
			assert resource != null : "Resource cannot be null";
			assert fallbackResource != null : "Fallback cannot be null";
			itemFallback.add(new String[] { resource, fallbackResource });
			return this;
		}

	}

	private class SectionNetwork implements Network {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.dlecan.html5manifest.CacheManifestBuilder.Network#addAll()
		 */
		@Override
		public CacheManifestBuilder addAll() {
			itemsNetwork.add("*");
			return CacheManifestBuilder.this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.dlecan.html5manifest.CacheManifestBuilder.Network#add(java.lang
		 * .String)
		 */
		@Override
		public Network add(String resource) {
			assert resource != null : "Resource cannot be null";
			itemsNetwork.add(resource);
			return this;
		}
	}

	private static final char EOL = '\n';

	private static final String HEADER = "CACHE MANIFEST";

	private static final String SECTION_HEADER_CACHE = "CACHE:";

	private static final String SECTION_HEADER_NETWORK = "NETWORK:";

	private static final String SECTION_HEADER_FALLBACK = "FALLBACK:";

	private String version = "";

	private SectionCache sectionCache = new SectionCache();

	private List<String> itemsCache = new ArrayList<String>();

	private SectionNetwork sectionNetwork = new SectionNetwork();

	private List<String> itemsNetwork = new ArrayList<String>();

	private SectionFallback sectionFallback = new SectionFallback();

	private List<String[]> itemFallback = new ArrayList<String[]>();

	private CacheManifestBuilder() {
	}

	public static CacheManifestBuilder newInstance() {
		return new CacheManifestBuilder();
	}

	public CacheManifestBuilder.Cache getCache() {
		return sectionCache;
	}

	public CacheManifestBuilder.Network getNetwork() {
		return sectionNetwork;
	}

	public CacheManifestBuilder.Fallback getFallback() {
		return sectionFallback;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public CacheManifestBuilder withVersion(String version) {
		this.version = version;
		return this;
	}

	public String toRawString() {
		StringBuilder sb = new StringBuilder(HEADER);

		sb.append(EOL);

		sb.append("# ");
		
		sb.append(version);

		sb.append(EOL);
		sb.append(EOL);

		sb.append(SECTION_HEADER_CACHE);
		sb.append(EOL);

		for (String cache : itemsCache) {
			sb.append(cache);
			sb.append(EOL);
		}
		
		sb.append(EOL);

		sb.append(SECTION_HEADER_NETWORK);
		sb.append(EOL);

		for (String network : itemsNetwork) {
			sb.append(network);
			sb.append(EOL);
		}

		sb.append(EOL);

		sb.append(SECTION_HEADER_FALLBACK);
		sb.append(EOL);

		for (String[] fallback : itemFallback) {
			sb.append(fallback[0]);
			sb.append(' ');
			sb.append(fallback[1]);
			sb.append(EOL);
		}

		return sb.toString();
	}

}
