package examples.file;

import org.springframework.core.io.Resource;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    void handleDif(final DeferredResult result);

    String deleteFile(String username, String filename);

    boolean saveImage(byte[] data, String imageName) throws IOException;

}
