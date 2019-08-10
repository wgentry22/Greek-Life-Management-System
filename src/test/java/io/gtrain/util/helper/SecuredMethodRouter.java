package io.gtrain.util.helper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author William Gentry
 */
@Configuration
@Profile("authorization")
public class SecuredMethodRouter {

	private final SecuredMethodHandler securedMethodHandler;

	public SecuredMethodRouter(SecuredMethodHandler securedMethodHandler) {
		this.securedMethodHandler = securedMethodHandler;
	}

	@Bean
	public RouterFunction<ServerResponse> securedMethodRouterBean() {
		return RouterFunctions
				.route()
					.add(getRequiresListEventsRoute())
					.add(getRequiresCreateEventRoute())
					.add(getCreateMemberProfileRoute())
					.add(getRequiresDeleteEventsRoute())
					.add(getRequiresDeleteMemberProfileRoute())
					.add(getRequiresEditProfileRoute())
					.add(getRequiresListAssociateMemberProfileRoute())
					.add(getRequiresListMemberProfilesRoute())
					.add(getRequiresListMembersRoute())
					.add(getRequiresUpdateEventsRoute())
					.add(getRequiresViewAssociateMemberProfile())
					.add(getRequiresViewEvent())
					.add(getRequiresViewMemberProfileRoute())
				.build();
	}

	/*

			EVENT ENDPOINTS

	 */

	private RouterFunction<ServerResponse> getRequiresCreateEventRoute() {
		return RouterFunctions
				.route()
					.POST(TestRoutes.EVENTS, securedMethodHandler::requiresCreateEvents)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresDeleteEventsRoute() {
		return RouterFunctions
				.route()
				.DELETE(TestRoutes.EVENTS, securedMethodHandler::requiresDeleteEvents)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresListEventsRoute() {
		return RouterFunctions
				.route()
				.GET(TestRoutes.EVENTS, securedMethodHandler::requiresListEvents)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresUpdateEventsRoute() {
		return RouterFunctions
				.route()
				.PUT(TestRoutes.EVENTS, securedMethodHandler::requiresUpdateEvents)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresViewEvent() {
		return RouterFunctions
				.route()
				.GET(TestRoutes.EVENTS + "/{id}", securedMethodHandler::requiresViewEvent)
				.build();
	}


	/*

			PROFILE ENDPOINTS

	 */

	private RouterFunction<ServerResponse> getCreateMemberProfileRoute() {
		return RouterFunctions
				.route()
					.POST(TestRoutes.PROFILES, securedMethodHandler::requiresCreateMemberProfile)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresDeleteMemberProfileRoute() {
		return RouterFunctions
				.route()
					.DELETE(TestRoutes.PROFILES, securedMethodHandler::requiresDeleteMemberProfile)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresEditProfileRoute() {
		return RouterFunctions
				.route()
					.PUT(TestRoutes.PROFILES, securedMethodHandler::requiresEditProfile)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresListAssociateMemberProfileRoute() {
		return RouterFunctions
				.route()
					.GET(TestRoutes.PROFILES + "/am", securedMethodHandler::requiresListAssociateMemberProfile)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresListMemberProfilesRoute() {
		return RouterFunctions
				.route()
					.GET(TestRoutes.PROFILES + "/mem", securedMethodHandler::requiresListMemberProfiles)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresViewAssociateMemberProfile() {
		return RouterFunctions
				.route()
				.GET(TestRoutes.PROFILES + "/am/{id}", securedMethodHandler::requiresViewAssociateMemberProfile)
				.build();
	}

	private RouterFunction<ServerResponse> getRequiresViewMemberProfileRoute() {
		return RouterFunctions
				.route()
				.GET(TestRoutes.PROFILES + "/mem/{id}", securedMethodHandler::requiresViewMemberProfile)
				.build();
	}

	/*


			MEMBER ENDPOINTS


	 */

	private RouterFunction<ServerResponse> getRequiresListMembersRoute() {
		return RouterFunctions
				.route()
					.GET(TestRoutes.MEMBERS, securedMethodHandler::requiresListMembers)
				.build();
	}

}
