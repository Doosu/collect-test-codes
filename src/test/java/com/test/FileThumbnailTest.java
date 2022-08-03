package com.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.test.utils.ThumbnailUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FileThumbnailTest {

  @Test
  public void fileCropAndCopy() throws IOException {
    final var imagePath = "/Users/nil/Downloads/yoongs.jpeg";
    final var targetPath = "/Users/nil/Downloads/cutOff/yoongs.jpeg";

    final var target = new File(targetPath);
    final var is = new File(imagePath);
    final var image = ThumbnailUtil.crop(
        ImageIO.read(is),
        ThumbnailUtil.DEFAULT_THUMBNAIL_WIDTH, ThumbnailUtil.DEFAULT_THUMBNAIL_HEIGHT
    );
    ImageIO.write(image, "jpeg", target);

    log.warn("SAVE FILE PATH :: {}", target.getAbsolutePath());

    assertTrue(target.exists());
  }

  @Test
  public void normalizeURL() throws Exception {
    final var targetUrl = "http:/localhost/assets/images/image.png";
    final var unNormalizedUrl = "http://localhost/assets/images//image.png";
    final var replacedUrl = unNormalizedUrl.replaceAll("\\/\\/", "/");
    final var url = new URL(unNormalizedUrl);
    final var urlString = url.toString();

    log.warn("URL TO URI :: {}", url.toURI());
    log.warn("URL STRING :: {}", urlString);
    log.warn("REPLACE URL :: {}", replacedUrl);

    assertFalse(targetUrl.equals(urlString));
    assertTrue(targetUrl.equals(replacedUrl));
  }

}
