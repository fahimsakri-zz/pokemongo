package co.go.pokemon.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fahim on 7/16/16.
 */

public class BrandCollectionDecoration extends RecyclerView.ItemDecoration {
    private int topSpace;
    private int bottomSpace;
    private int leftSpace;
    private int rightSpace;

    public BrandCollectionDecoration(int space) {
        this.leftSpace = space;
        this.rightSpace = space;
        this.topSpace = space;
        this.bottomSpace = space;
    }

    public BrandCollectionDecoration(int leftSpace, int topSpace, int rightSpace, int bottomSpace) {
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = topSpace;
        outRect.left = leftSpace;
        outRect.right = rightSpace;
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
            outRect.bottom = 0;
        else outRect.bottom = topSpace;
    }
}