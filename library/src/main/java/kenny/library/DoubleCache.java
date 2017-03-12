package kenny.library;

import android.graphics.Bitmap;

/**
 * @author Junyao
 * @package kenny.library
 */

public class DoubleCache implements ImageCache {
    private ImageCache mMemoryCache = new MemoryCache();
    private DiskCache mDiskCache = new DiskCache();

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }

}
