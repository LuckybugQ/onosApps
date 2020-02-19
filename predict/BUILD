COMPILE_DEPS = CORE_DEPS + JACKSON + METRICS + KRYO + REST + [
    "@mysql2//jar",
    "@rrd4j//jar",
    "//apps/cpman/api:onos-apps-cpman-api",
    "//apps/cpman/app:onos-apps-cpman-app",
]

BUNDLES = [
    "//apps/predict:onos-apps-predict",
    "@mysql2//jar",
    "@rrd4j//jar",
]

osgi_jar_with_tests(
    api_description = "REST API for prediction",
    api_package = "org.onosproject.predict.rest",
    api_title = "Prediction API",
    api_version = "1.0",
    suppress_checkstyle = True,
    web_context = "/onos/wqt",
    deps = COMPILE_DEPS,
)

onos_app(
    category = "Traffic Engineering",
    description = "",
    included_bundles = BUNDLES,
    required_apps = ["org.onosproject.cpman"],
    title = "predict",
    url = "http://onosproject.org",
)
