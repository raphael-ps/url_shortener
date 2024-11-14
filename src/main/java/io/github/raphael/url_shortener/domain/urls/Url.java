package io.github.raphael.url_shortener.domain.urls;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.Instant;

@Table(name = "urls")
@Entity(name = "urls")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Url {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String url_original;
    private String url_nickname;
    private int clicks_count;
    private int accesses_count;
    private Instant created_date;
    private Instant expiration_date;
    private String url_password;
    private String created_by;

    public Url(ResquestUrlPostDTO url){
        this.url_original = url.url_original();
        this.url_nickname = url.url_nickname();
        this.created_date = Instant.now();

        if (url.expiration_date() == null){
            this.expiration_date = Instant.now().plus(Duration.ofDays(3));
        } else {
            this.expiration_date = url.expiration_date();
        }
    }
}
