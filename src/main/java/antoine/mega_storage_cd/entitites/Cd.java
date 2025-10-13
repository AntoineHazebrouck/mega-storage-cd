package antoine.mega_storage_cd.entitites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Cd {

    @JsonProperty("position")
    private Integer position;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("album")
    private String album;
}
