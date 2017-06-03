import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the TableStat entity.
 */
class TableStatGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-XSRF-TOKEN" -> "${xsrf_token}"
    )

    val scn = scenario("Test the TableStat entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authentication")
        .headers(headers_http_authenticated)
        .formParam("j_username", "admin")
        .formParam("j_password", "admin")
        .formParam("remember-me", "true")
        .formParam("submit", "Login")
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all tableStats")
            .get("/api/table-stats")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new tableStat")
            .post("/api/table-stats")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "schemaname":"SAMPLE_TEXT", "relname":"SAMPLE_TEXT", "seqScan":null, "seqTupRead":null, "idxScan":null, "idxTupFetch":null, "nTupIns":null, "nTupUpd":null, "nTupDel":null, "nTupHotUpd":null, "nLiveTup":null, "nDeadTup":null, "lastVacuum":"2020-01-01T00:00:00.000Z", "lastAutovacuum":"2020-01-01T00:00:00.000Z", "lastAnalyze":"2020-01-01T00:00:00.000Z", "lastAutoanalyze":"2020-01-01T00:00:00.000Z", "vacuumCount":null, "autovacuumCount":null, "analyzeCount":null, "autoanalyzeCount":null}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_tableStat_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created tableStat")
                .get("${new_tableStat_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created tableStat")
            .delete("${new_tableStat_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
