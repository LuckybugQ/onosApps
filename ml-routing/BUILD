COMPILE_DEPS = CORE_DEPS + JACKSON + REST + [
    "//core/common:onos-core-common",
]

osgi_jar_with_tests(
    api_description = "ML Routing",
    api_package = "org.onosproject.mlrouting",
    api_title = "ML REST API v1.0",
    api_version = "/onos/v1/routing",
    web_context = "/onos/routing",
    deps = COMPILE_DEPS,
)

REQUIRE_APPS = [
    "org.onosproject.proxyarp",
]

onos_app(
    category = "Traffic Steering",
    description = "",
    required_apps = REQUIRE_APPS,
    title = "ML Routing",
    url = "http://onosproject.org",
)
