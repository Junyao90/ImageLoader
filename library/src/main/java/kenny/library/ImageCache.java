package kenny.library;

import android.graphics.Bitmap;

/**
 * @author Junyao
 * @package kenny.library
 */

public interface ImageCache {
    public Bitmap get(String url);

    public void put(String url, Bitmap bitmap);
}
