package com.vitta.smvp.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vitta.smvp.R;
import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.model.MineFansBeen;

import java.net.URLDecoder;
import java.util.List;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 12:01
 * 描述：MineFansAdapter
 */

public class MineFansAdapter extends BaseQuickAdapter<MineFansBeen,BaseViewHolder> {

    public MineFansAdapter(@Nullable List<MineFansBeen> data) {
        super(R.layout.mine_fans_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineFansBeen item) {
        helper.setText(R.id.text, URLDecoder.decode(item.getUser_nickname()));
    }
}
