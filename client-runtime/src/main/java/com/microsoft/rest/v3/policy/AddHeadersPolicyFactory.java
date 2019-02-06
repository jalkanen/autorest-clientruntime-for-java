/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.rest.v3.policy;

import com.microsoft.rest.v3.http.HttpHeader;
import com.microsoft.rest.v3.http.HttpHeaders;
import com.microsoft.rest.v3.http.HttpRequest;
import com.microsoft.rest.v3.http.HttpResponse;
import reactor.core.publisher.Mono;

/**
 * Creates a RequestPolicy which adds a particular set of headers to HTTP requests.
 */
public class AddHeadersPolicyFactory implements RequestPolicyFactory {
    private final HttpHeaders headers;

    /**
     * Creates a AddHeadersPolicyFactory which adds headers from this HttpHeaders object to the request.
     * @param headers The headers to add to outgoing requests.
     */
    public AddHeadersPolicyFactory(HttpHeaders headers) {
        this.headers = headers;
    }

    @Override
    public RequestPolicy create(RequestPolicy next, RequestPolicyOptions options) {
        return new AddHeadersPolicy(next);
    }

    private final class AddHeadersPolicy implements RequestPolicy {
        private final RequestPolicy next;
        private AddHeadersPolicy(RequestPolicy next) {
            this.next = next;
        }

        @Override
        public Mono<HttpResponse> sendAsync(HttpRequest request) {
            for (HttpHeader header : headers) {
                request.withHeader(header.name(), header.value());
            }

            return next.sendAsync(request);
        }
    }

}