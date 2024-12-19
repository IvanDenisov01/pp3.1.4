import model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Application {
    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();

    public static void main(String[] args) {

        getUsers();

        String part1 = addUser(new User(3L, "James", "Brown", (byte) 25));

        String part2 = updateUser(new User(3L, "Thomas", "Shelby", (byte) 25));

        String part3 = deleteUser(3L);

        System.out.println("Код: " + part1 + part2 + part3);
    }

    private static void getUsers() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, String.class);

        headers.set("Cookie", response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));

        System.out.println("Полученные пользователи: " + response.getBody());
    }

    private static String addUser(User user) {
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);

        System.out.println("Ответ при добавлении пользователя: " + response.getBody());
        return response.getBody();
    }

    private static String updateUser(User user) {
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestEntity, String.class);

        System.out.println("Ответ при обновлении пользователя: " + response.getBody());
        return response.getBody();
    }

    private static String deleteUser(Long id) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, requestEntity, String.class);

        System.out.println("Ответ при удалении пользователя: " + response.getBody());
        return response.getBody();
    }
}
