package io.github.raphael.url_shortener.domain.short_url;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.Duration;
import java.time.Instant;

@Table(name = "short_url")
@Entity(name = "short_url")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class ShortUrl {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String url_id;
    private String user_id;
    private String nickname;
    private int clicks_count;
    private int accesses_count;
    private Instant creation_date;
    private Instant expiration_date;
    private String password;

    public ShortUrl(RequestShortUrlPostDTO url){
        this.nickname = url.nickname();
        this.creation_date = Instant.now();
        this.password = BCrypt.hashpw(url.password(), BCrypt.gensalt());
        this.user_id = url.user_id();

        if (url.expirationDate() == null){
            this.expiration_date = Instant.now().plus(Duration.ofDays(3));
        } else {
            this.expiration_date = url.expirationDate();
        }
    }
}
