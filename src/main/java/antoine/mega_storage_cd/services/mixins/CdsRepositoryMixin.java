package antoine.mega_storage_cd.services.mixins;

import antoine.mega_storage_cd.entitites.Cd;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.val;
import org.springframework.core.io.FileSystemResource;

public interface CdsRepositoryMixin {
    default void saveAllCds(@NonNull List<Cd> cds) {
        val file = new FileSystemResource("./cds.json");

        try {
            file
                .getOutputStream()
                .write(new ObjectMapper().writeValueAsBytes(cds));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    default List<Cd> findAllCds() {
        val file = new FileSystemResource("./cds.json");

        try {
            val content = file.getContentAsString(Charset.defaultCharset());

            val typed = new ObjectMapper()
                .readValue(content, new TypeReference<List<Cd>>() {});

            return typed;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    default Optional<Cd> findCdById(@NonNull Integer id) {
        return findAllCds()
            .stream()
            .filter(cd -> cd.getPosition() == id)
            .findFirst();
    }

    default void deleteAllCds() {
        saveAllCds(List.of());
    }

    default void deleteCdById(@NonNull Integer id) {
        val cds = findAllCds();
        val filtered = cds
            .stream()
            .filter(cd -> cd.getPosition() != id)
            .toList();
        saveAllCds(filtered);
    }

    default void saveCd(@NonNull Cd cd) {
        val cds = findAllCds();
        cds.add(cd);
        saveAllCds(cds);
    }
}
