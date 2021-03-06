package com.github.fwidder.timeFinder2.api;

import com.github.fwidder.timeFinder2.model.EchoMessage;
import com.github.fwidder.timeFinder2.model.rest.ApplicationUserRequest;
import com.github.fwidder.timeFinder2.model.rest.EchoRequest;
import com.github.fwidder.timeFinder2.model.rest.EventRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {

    public static final String USER_PASS_MAIL = "test";
    static String jwt = null;
    final int randomServerPort = 12345;
    final String url_base = "http://localhost:" + randomServerPort;

    @Test
    @Order(1)
    public void createUserTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/users/sign-up";
        HttpEntity<ApplicationUserRequest> request = new HttpEntity<>(new ApplicationUserRequest(USER_PASS_MAIL, USER_PASS_MAIL + "@test.de", USER_PASS_MAIL));
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    @Order(2)
    public void getEchoTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/echo";
        ResponseEntity<EchoMessage> response = restTemplate.getForEntity(url, EchoMessage.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(EchoMessage.builder().message("Hello World").build()));
    }

    @Test
    @Order(3)
    public void postEchoErrorTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/echo";
        HttpEntity<EchoRequest> request = new HttpEntity<>(new EchoRequest("Hello Test"));
        Assertions.assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity(url, request, String.class));
    }

    @Test
    @Order(4)
    public void loginErrorTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/jwt-login";
        HttpEntity<String> request = new HttpEntity<>(createHeaders(USER_PASS_MAIL + "XX", USER_PASS_MAIL));
        Assertions.assertThrows(HttpClientErrorException.Unauthorized.class, () -> restTemplate.postForEntity(url, request, String.class));
    }

    @Test
    @Order(5)
    public void loginUsernameTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/jwt-login";
        HttpEntity<String> request = new HttpEntity<>(createHeaders(USER_PASS_MAIL, USER_PASS_MAIL));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        jwt = response.getHeaders().getFirst("Authorization");
    }

    @Test
    @Order(5)
    public void loginEmailTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/jwt-login";
        HttpEntity<String> request = new HttpEntity<>(createHeaders(USER_PASS_MAIL + "@test.de", USER_PASS_MAIL));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        jwt = response.getHeaders().getFirst("Authorization");
    }

    @Test
    @Order(6)
    public void postEchoTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/echo";
        HttpEntity<EchoRequest> request = new HttpEntity<>(new EchoRequest("Hello Test"), createHeaders(jwt));
        ResponseEntity<EchoMessage> response = restTemplate.exchange(url, HttpMethod.POST, request, EchoMessage.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(EchoMessage.builder().message("Hello Test").build()));
    }

    @Test
    @Order(7)
    public void postEventTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/event";
        HttpEntity<EventRequest> request = new HttpEntity<>(new EventRequest("Name", "Description", false, null, LocalDate.now(), LocalDate.now().plusDays(1)), createHeaders(jwt));
        ResponseEntity<EchoMessage> response = restTemplate.exchange(url, HttpMethod.POST, request, EchoMessage.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    @Order(8)
    public void postEventSecureTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/event";
        HttpEntity<EventRequest> request = new HttpEntity<>(new EventRequest("Name", "Description", true, "test", LocalDate.now(), LocalDate.now().plusDays(1)), createHeaders(jwt));
        ResponseEntity<EchoMessage> response = restTemplate.exchange(url, HttpMethod.POST, request, EchoMessage.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    @Order(9)
    public void postEventSecureErrorTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/event";
        HttpEntity<EventRequest> request = new HttpEntity<>(new EventRequest("Name", "Description", true, null, LocalDate.now(), LocalDate.now().plusDays(1)), createHeaders(jwt));
        Assertions.assertThrows(HttpServerErrorException.InternalServerError.class, () -> restTemplate.postForEntity(url, request, String.class));
    }

    @Test
    @Order(10)
    public void postEventNoLoginTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/event";
        HttpEntity<EventRequest> request = new HttpEntity<>(new EventRequest("Name", "Description", true, null, LocalDate.now(), LocalDate.now().plusDays(1)));
        Assertions.assertThrows(HttpClientErrorException.Forbidden.class, () -> restTemplate.postForEntity(url, request, String.class));
    }

    @Test
    @Order(11)
    public void postEventDeleteTest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_base + "/event/id/1";
        HttpEntity<EventRequest> request = new HttpEntity<>(createHeaders(jwt));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    HttpHeaders createHeaders(String jwt) {
        return new HttpHeaders() {{
            set("Authorization", jwt);
        }};
    }
}
