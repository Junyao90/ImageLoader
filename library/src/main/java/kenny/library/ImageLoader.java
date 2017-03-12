package kenny.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Junyao
 * @package kenny.library
 */

public class ImageLoader {

    /**
     * memory cache
     */
    private ImageCache mImageCache = new ImageCache();


    /**
     * sd card cache
     */
    private DiskCache mDiskCache = new DiskCache();

    /**
     * both memory cache and sd card cache
     */
    private DoubleCache mDoubleCache = new DoubleCache();


    private boolean isUseDiskCache = false;
    private boolean isUseDoubleCache = false;

    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader() {
    }


    public void displayImage(final String url, final ImageView imageView) {

        Bitmap bitmap;
        if (isUseDoubleCache) {
            bitmap = mDoubleCache.get(url);
        } else if (isUseDiskCache) {
            bitmap = mDiskCache.get(url);
        } else {
            bitmap = mImageCache.get(url);
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setTag(url);
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = downloadImage(url);
                    if (bitmap == null) {
                        return;
                    }
                    if (imageView.getTag().equals(url)) {
                        imageView.setImageBitmap(bitmap);
                    }
                    mImageCache.put(url, bitmap);
                }
            });
        }

    }

    public void useDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    public void useDoubleCache(boolean useDoubleCache) {
        isUseDoubleCache = useDoubleCache;
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
