package io.gtrain.util.helper;

import io.gtrain.security.annotations.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
@Profile("authorization")
public class SecuredMethodHandler {

	@CanCreateEvents
	public Mono<ServerResponse> requiresCreateEvents(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanCreateMemberProfile
	public Mono<ServerResponse> requiresCreateMemberProfile(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanDeleteEvent
	public Mono<ServerResponse> requiresDeleteEvents(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanDeleteMemberProfile
	public Mono<ServerResponse> requiresDeleteMemberProfile(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanEditOwnProfile
	public Mono<ServerResponse> requiresEditProfile(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanListAssociateMemberProfiles
	public Mono<ServerResponse> requiresListAssociateMemberProfile(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanListEvents
	public Mono<ServerResponse> requiresListEvents(ServerRequest request) {
		return ServerResponse.ok().build();
	}

	@CanListMemberProfiles
	public Mono<ServerResponse> requiresListMemberProfiles(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanListMembers
	public Mono<ServerResponse> requiresListMembers(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanUpdateEvents
	public Mono<ServerResponse> requiresUpdateEvents(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanViewAssociateMemberProfile
	public Mono<ServerResponse> requiresViewAssociateMemberProfile(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanViewEvent
	public Mono<ServerResponse> requiresViewEvent(ServerRequest request) { return ServerResponse.ok().build(); }

	@CanViewMemberProfile
	public Mono<ServerResponse> requiresViewMemberProfile(ServerRequest request) { return ServerResponse.ok().build(); }
}
