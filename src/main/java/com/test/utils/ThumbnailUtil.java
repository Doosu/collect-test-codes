package com.test.utils;

import java.awt.image.BufferedImage;
import org.imgscalr.Scalr;

public abstract class ThumbnailUtil {

  public final static int DEFAULT_THUMBNAIL_WIDTH = 150;
  public final static int DEFAULT_THUMBNAIL_HEIGHT = 150;

  private static int bigger(int size) {
    return (int) Math.ceil(size + (size * 0.05));
  }

  private static boolean isNeedReduced(
      final int width, final int height, final int resizeWidth, final int resizeHeight
  ) {
    return (width != bigger(resizeWidth)) || (height != bigger(resizeHeight));
  }

  private static Scalr.Mode direction(
      final int width, final int height, final int resizeWidth, final int resizeHeight
  ) {
    final var targetWidth = (width - resizeWidth) / (resizeWidth * 1F);
    final var targetHeight = (height - resizeHeight) / (resizeHeight * 1F);

    return targetWidth > targetHeight ? Scalr.Mode.FIT_TO_HEIGHT : Scalr.Mode.FIT_TO_WIDTH;
  }

  public static BufferedImage resize(
      final BufferedImage image, final int width, final int height
  ) {
    final var orgWidth = image.getWidth();
    final var orgHeight = image.getHeight();
    final var direction = direction(orgWidth, orgHeight, width, height);

    return Scalr.resize(image, Scalr.Method.AUTOMATIC, direction, width, height);
  }

  public static BufferedImage crop(BufferedImage image, final int width, final int height) {
    if (null != image && isNeedReduced(image.getWidth(), image.getHeight(), width, height)) {
      image = resize(image, bigger(width), bigger(height));

      return Scalr.crop(
          image,
          (image.getWidth() - width) / 2,
          (image.getHeight() - height) / 2,
          width, height
      );
    }

    return image;
  }

}
