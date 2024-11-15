package io.github.raphael.url_shortener.domain.original_url;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "original_url")
@Entity(name = "original_url")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class OriginalUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String url;
    private int times_shortened;

    public OriginalUrl(String url){
        this.url = url;
    }
}
