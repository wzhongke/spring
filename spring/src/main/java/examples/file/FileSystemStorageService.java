package examples.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author wangzhongke
 */
@Service
public class FileSystemStorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(@Value("tmp/") String path) {
        Path rootLocation1;
        try {
            File directory = new File("");
            String courseFile = directory.getCanonicalPath();
            rootLocation1 = Paths.get(courseFile, path);
            if (!Files.exists(rootLocation1)){
                Files.createDirectories(rootLocation1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            rootLocation1 = Paths.get(path);
        }
        this.rootLocation = rootLocation1;
    }

    public Path getRootLocation() {
        return this.rootLocation;
    }

    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            file.transferTo(this.rootLocation.resolve(file.getOriginalFilename()).toFile());
            System.out.println("save success" + this.rootLocation.resolve(file.getOriginalFilename()).toFile().getAbsolutePath());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public String deleteFile (String username, String filename) {
        Path path = this.rootLocation.resolve(username).resolve(filename);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
                return "{message:failed, error: " + e.getMessage() + " }";
            }
        } else {
            return "{message:failed, error:there is not a file called + " + filename + "}";
        }
        return "{message:success}";
    }

    public boolean saveImage(byte[] data, String imageName) throws IOException {
        int len = data.length;
        FileOutputStream outputStream = new FileOutputStream(new File(rootLocation.toString(),imageName));

        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        System.out.println("save image:" + len);
        return true;
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void handleDif(final DeferredResult result) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("redirect:files");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
