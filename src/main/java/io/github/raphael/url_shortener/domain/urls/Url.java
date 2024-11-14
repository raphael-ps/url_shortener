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
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class Url {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String url_original;
    @Column(name = "url_nickname")
    private String urlNickname;
    private int clicks_count;
    private int accesses_count;
    private Instant created_date;
    private Instant expiration_date;
    private String url_password;
    private String created_by;

    public Url(ResquestUrlPostDTO url){
        this.url_original = url.urlOriginal();
        this.urlNickname = url.urlNickname();
        this.created_date = Instant.now();

        if (url.expirationDate() == null){
            this.expiration_date = Instant.now().plus(Duration.ofDays(3));
        } else {
            this.expiration_date = url.expirationDate();
        }
    }
}
