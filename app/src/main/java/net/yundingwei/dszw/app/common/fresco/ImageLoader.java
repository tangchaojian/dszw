package net.yundingwei.dszw.app.common.fresco;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


/**
 * 图片加载
 */
public class ImageLoader {


    @BindingAdapter({"imgUrl", "width", "height"})
    public static void loadImage(SimpleDraweeView mDraweeView, String imgUrl, float width, float height){

        if(!TextUtils.isEmpty(imgUrl)) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imgUrl))
                    .setResizeOptions(new ResizeOptions((int)width, (int)height))
                    .build();

            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(mDraweeView.getController())
                    .setImageRequest(request)
                    .build();

            mDraweeView.setController(controller);
        }
    }

    @BindingAdapter("imgUrl")
    public static void loadImage(SimpleDraweeView mDraweeView, String imgUrl){
        if(!TextUtils.isEmpty(imgUrl)){
            mDraweeView.setImageURI(imgUrl);
        }
    }

    @BindingAdapter({"imgUrl", "error"})
    public static void loadImage(SimpleDraweeView mDraweeView, String imgUrl, Drawable error){

        GenericDraweeHierarchy hierarchy = mDraweeView.getHierarchy();
        hierarchy.setFadeDuration(300);
        hierarchy.setPlaceholderImage(error);

        mDraweeView.setHierarchy(hierarchy);

        if(!TextUtils.isEmpty(imgUrl)){
            mDraweeView.setImageURI(imgUrl);
        }
    }


    @BindingAdapter("imgSrc")
    public static void setImageSrc(ImageView mIvIcon, int resId){
        if(resId > 0){
            mIvIcon.setImageDrawable(ContextCompat.getDrawable(mIvIcon.getContext(), resId));
        }
    }

}
