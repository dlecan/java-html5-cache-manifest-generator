package com.dlecan.html5manifest;

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
		public CacheManifestBuilder add(String resource);
	}

	public interface Network {
		public CacheManifestBuilder add(String resource);

		public CacheManifestBuilder addAll();

	}

	public interface Fallback {

		public CacheManifestBuilder add(String resource, String fallbackResource);

	}

	private class Section implements Cache, Network, Fallback {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.dlecan.html5manifest.CacheManifestBuilder.Fallback#add(java.lang
		 * .String, java.lang.String)
		 */
		@Override
		public CacheManifestBuilder add(String resource, String fallbackResource) {
			// TODO Auto-generated method stub
			return CacheManifestBuilder.this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.dlecan.html5manifest.CacheManifestBuilder.Network#addAll()
		 */
		@Override
		public CacheManifestBuilder addAll() {
			// TODO Auto-generated method stub
			return CacheManifestBuilder.this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.dlecan.html5manifest.CacheManifestBuilder.Cache#add(java.lang
		 * .String)
		 */
		@Override
		public CacheManifestBuilder add(String resource) {
			// TODO Auto-generated method stub
			return CacheManifestBuilder.this;
		}

	}

	private static final String HEADER = "CACHE MANIFEST";

	private static final String SECTION_HEADER_CACHE = "CACHE:";

	private static final String SECTION_HEADER_NETWORK = "NETWORK:";

	private static final String SECTION_HEADER_FALLBACK = "FALLBACK:";

	private String version = "";

	private Section section = new Section();

	private CacheManifestBuilder() {
	}

	public static CacheManifestBuilder newInstance() {
		return new CacheManifestBuilder();
	}

	public CacheManifestBuilder.Cache getCache() {
		return section;
	}

	public CacheManifestBuilder.Network getNetwork() {
		return section;
	}

	public CacheManifestBuilder.Fallback getFallback() {
		return section;
	}

	public String toRawString() {

		return null;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public CacheManifestBuilder withVersion(String version) {
		this.version = version;
		return this;
	}

}