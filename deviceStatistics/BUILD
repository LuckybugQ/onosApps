COMPILE_DEPS = CORE_DEPS + JACKSON + METRICS + KRYO + REST + [
    "@mysql2//jar",
    "@rrd4j//jar",
    "//apps/cpman/api:onos-apps-cpman-api",
    "//apps/cpman/app:onos-apps-cpman-app",
]

BUNDLES = [
    "//apps/deviceStatistics:onos-apps-deviceStatistics",
    "@mysql2//jar",
    "@rrd4j//jar",
]

osgi_jar_with_tests(
    api_description = "REST API for net Statistics",
    api_package = "org.onosproject.deviceStatistics.rest",
    api_title = "Statistics API",
    api_version = "1.0",
    suppress_checkstyle = True,
    web_context = "/onos/wqt",
    deps = COMPILE_DEPS,
)

onos_app(
    category = "Traffic Engineering",
    description = "Provisions traffic between end-stations using hop-by-hop flow programming by " +
                  "intercepting packets for which there are currently no matching flow objectives on the " +
                  "data plane. The paths paved in this manner are short-lived, i.e. they expire a few " +
                  "seconds after the flow on whose behalf they were programmed stops.\n\n" +
                  "The application relies on the ONOS path service to compute the shortest paths. " +
                  "In the event of negative topology events (link loss, device disconnect, etc.), " +
                  "the application will proactively invalidate any paths that it had programmed to lead " +
                  "through the resources that are no longer available.",
    included_bundles = BUNDLES,
    required_apps = ["org.onosproject.cpman"],
    title = "Device Statistics",
    url = "http://onosproject.org",
)
