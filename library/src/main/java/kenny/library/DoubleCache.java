package kenny.library;

import android.graphics.Bitmap;

/**
 * @author Junyao
 * @package kenny.library
 */

public class DoubleCache {
    private ImageCache mMemoryCache = new ImageCache();
    private DiskCache mDiskCache = new DiskCache();

    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }

}
