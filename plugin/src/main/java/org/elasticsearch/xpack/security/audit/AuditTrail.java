/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.security.audit;

import org.elasticsearch.common.Nullable;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.transport.TransportMessage;
import org.elasticsearch.xpack.security.authc.AuthenticationToken;
import org.elasticsearch.xpack.security.transport.filter.SecurityIpFilterRule;
import org.elasticsearch.xpack.security.user.User;

import java.net.InetAddress;
import java.util.Set;

public interface AuditTrail {

    String name();

    void authenticationSuccess(String realm, User user, RestRequest request);

    void authenticationSuccess(String realm, User user, String action, TransportMessage message);

    void anonymousAccessDenied(String action, TransportMessage message);

    void anonymousAccessDenied(RestRequest request);

    void authenticationFailed(RestRequest request);

    void authenticationFailed(String action, TransportMessage message);

    void authenticationFailed(AuthenticationToken token, String action, TransportMessage message);

    void authenticationFailed(AuthenticationToken token, RestRequest request);

    void authenticationFailed(String realm, AuthenticationToken token, String action, TransportMessage message);

    void authenticationFailed(String realm, AuthenticationToken token, RestRequest request);

    /**
     * Access was granted for some request.
     * @param specificIndices if non-null then the action was authorized
     *      for all indices in this particular set of indices, otherwise
     *      the action was authorized for all indices to which it is
     *      related, if any
     */
    void accessGranted(User user, String action, TransportMessage message, @Nullable Set<String> specificIndices);

    /**
     * Access was denied for some request.
     * @param specificIndices if non-null then the action was denied
     *      for at least one index in this particular set of indices,
     *      otherwise the action was denied for at least one index
     *      to which the request is related. If the request isn't
     *      related to any particular index then the request itself
     *      was denied.
     */
    void accessDenied(User user, String action, TransportMessage message, @Nullable Set<String> specificIndices);

    void tamperedRequest(RestRequest request);

    void tamperedRequest(String action, TransportMessage message);

    void tamperedRequest(User user, String action, TransportMessage request);

    void connectionGranted(InetAddress inetAddress, String profile, SecurityIpFilterRule rule);

    void connectionDenied(InetAddress inetAddress, String profile, SecurityIpFilterRule rule);

    void runAsGranted(User user, String action, TransportMessage message);

    void runAsDenied(User user, String action, TransportMessage message);

    void runAsDenied(User user, RestRequest request);
}
