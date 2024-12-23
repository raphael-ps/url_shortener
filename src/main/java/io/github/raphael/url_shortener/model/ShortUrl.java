package io.github.raphael.url_shortener.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.raphael.url_shortener.dto.RequestShortUrlPostDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "url_id", referencedColumnName = "id")
    @JsonManagedReference
    private OriginalUrl originalUrl;

    @Column(name = "user_id")
    private String userId;
    private String nickname;
    @Column(name = "clicks_count")
    private int clicksCount;
    private int accesses_count;
    private Instant creation_date;
    private Instant expiration_date;
    private String password;

    public ShortUrl(RequestShortUrlPostDTO url){
        this.nickname = url.nickname();
        this.creation_date = Instant.now();
        this.password = url.password() == null ? null : BCrypt.hashpw(url.password(), BCrypt.gensalt());
        this.userId = url.user_id();

        if (url.expirationDate() == null){
            this.expiration_date = LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.of("-3"));
        } else {
            this.expiration_date = url.expirationDate();
        }
    }
}
