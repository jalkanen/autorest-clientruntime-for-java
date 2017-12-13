/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.rest.v2.http;

/**
 * A builder class that is used to create URLs.
 */
public class UrlBuilder {
    private String scheme;
    private String host;
    private String port;
    private String path;
    private String query;

    /**
     * Set the scheme/protocol that will be used to build the final URL.
     * @param scheme The scheme/protocol that will be used to build the final URL.
     * @return This UrlBuilder so that multiple setters can be chained together.
     */
    public UrlBuilder withScheme(String scheme) {
        return with(scheme, UrlTokenizerState.SCHEME);
    }

    /**
     * Get the scheme/protocol that has been assigned to this UrlBuilder.
     * @return the scheme/protocol that has been assigned to this UrlBuilder.
     */
    public String scheme() {
        return scheme;
    }

    /**
     * Set the host that will be used to build the final URL.
     * @param host The host that will be used to build the final URL.
     * @return This UrlBuilder so that multiple setters can be chained together.
     */
    public UrlBuilder withHost(String host) {
        return with(host, UrlTokenizerState.SCHEME_OR_HOST);
    }

    /**
     * Get the host that has been assigned to this UrlBuilder.
     * @return the host that has been assigned to this UrlBuilder.
     */
    public String host() {
        return host;
    }

    /**
     * Set the port that will be used to build the final URL.
     * @param port The port that will be used to build the final URL.
     * @return This UrlBuilder so that multiple setters can be chained together.
     */
    public UrlBuilder withPort(String port) {
        return with(port, UrlTokenizerState.PORT);
    }

    /**
     * Set the port that will be used to build the final URL.
     * @param port The port that will be used to build the final URL.
     * @return This UrlBuilder so that multiple setters can be chained together.
     */
    public UrlBuilder withPort(int port) {
        return withPort(Integer.toString(port));
    }

    /**
     * Get the port that has been assigned to this UrlBuilder.
     * @return the port that has been assigned to this UrlBuilder.
     */
    public Integer port() {
        return port == null ? null : Integer.valueOf(port);
    }

    /**
     * Set the path that will be used to build the final URL.
     * @param path The path that will be used to build the final URL.
     * @return This UrlBuilder so that multiple setters can be chained together.
     */
    public UrlBuilder withPath(String path) {
        return with(path, UrlTokenizerState.PATH);
    }

    /**
     * Get the path that has been assigned to this UrlBuilder.
     * @return the path that has been assigned to this UrlBuilder.
     */
    public String path() {
        return path;
    }

    /**
     * Add the provided query parameter name and encoded value to query string for the final URL.
     * @param queryParameterName The name of the query parameter.
     * @param queryParameterEncodedValue The encoded value of the query parameter.
     * @return The provided query parameter name and encoded value to query string for the final
     * URL.
     */
    public UrlBuilder addQueryParameter(String queryParameterName, String queryParameterEncodedValue) {
        if (query == null) {
            query = "";
        }
        else {
            query += "&";
        }
        query += queryParameterName + "=" + queryParameterEncodedValue;
        return this;
    }

    /**
     * Set the query that will be used to build the final URL.
     * @param query The query that will be used to build the final URL.
     * @return This UrlBuilder so that multiple setters can be chained together.
     */
    public UrlBuilder withQuery(String query) {
        return with(query, UrlTokenizerState.QUERY);
    }

    /**
     * Get the query that has been assigned to this UrlBuilder.
     * @return the query that has been assigned to this UrlBuilder.
     */
    public String query() {
        return query;
    }

    private UrlBuilder with(String text, UrlTokenizerState startState) {
        final UrlTokenizer tokenizer = new UrlTokenizer(text, startState);
        while (tokenizer.next()) {
            final UrlToken token = tokenizer.current();
            final String tokenText = token.text();
            final UrlTokenType tokenType = token.type();
            switch (tokenType) {
                case SCHEME:
                    scheme = emptyToNull(tokenText);
                    break;

                case HOST:
                    host = emptyToNull(tokenText);
                    break;

                case PORT:
                    port = emptyToNull(tokenText);
                    break;

                case PATH:
                    path = emptyToNull(tokenText);
                    break;

                case QUERY:
                    query = emptyToNull(tokenText);
                    break;

                default:
                    break;
            }
        }
        return this;
    }

    /**
     * Get the string representation of the URL that is being built.
     * @return The string representation of the URL that is being built.
     */
    public String toString() {
        final StringBuilder result = new StringBuilder();

        final boolean isAbsolutePath = path != null && (path.startsWith("http://") || path.startsWith("https://"));
        if (!isAbsolutePath) {
            if (scheme != null) {
                result.append(scheme);

                if (!scheme.endsWith("://")) {
                    result.append("://");
                }
            }

            if (host != null) {
                result.append(host);
            }
        }

        if (port != null) {
            result.append(":");
            result.append(port);
        }

        if (path != null) {
            if (result.length() != 0 && !path.startsWith("/")) {
                result.append('/');
            }
            result.append(path);
        }

        if (query != null) {
            if (!query.startsWith("?")) {
                result.append('?');
            }
            result.append(query);
        }

        return result.toString();
    }

    /**
     * Parse a UrlBuilder from the provided URL string.
     * @param url The string to parse.
     * @return The UrlBuilder that was parsed from the string.
     */
    public static UrlBuilder parse(String url) {
        final UrlBuilder result = new UrlBuilder();
        result.with(url, UrlTokenizerState.SCHEME_OR_HOST);
        return result;
    }

    private static String emptyToNull(String value) {
        return value == null || value.isEmpty() ? null : value;
    }
}