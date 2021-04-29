package com.openking.oklog.printer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openking.oklog.R;
import com.openking.oklog.utils.OKSizeUtil;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/214:16 PM
 * des: 视图打印器显示的一个控制辅助类
 */
public class OKViewPrinterProvider {

    private final FrameLayout rootView;
    private View floatingView;
    private boolean isOpen;
    private FrameLayout logView;
    private final RecyclerView recyclerView;
    private final LogViewShowHideListener listener;

    public OKViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView, LogViewShowHideListener listener) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    /**
     * 显示悬浮窗
     */
    public void showFloatingView() {
        //如果已经显示就不做处理
        if (rootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        View floatingView = getFloatingView();
        floatingView.setTag(TAG_FLOATING_VIEW);
        floatingView.setBackgroundColor(Color.BLACK);
        floatingView.setAlpha(0.8f);
        params.bottomMargin = OKSizeUtil.dp2px(100);
        rootView.addView(getFloatingView(), params);
    }

    /**
     * 隐藏悬浮窗
     */
    public void closeFloatingView() {
        rootView.removeView(getFloatingView());
    }

    private View getFloatingView() {
        if (floatingView != null) {
            return floatingView;
        }
        TextView textView = new TextView(rootView.getContext());
        textView.setOnClickListener(view -> {
            if (!isOpen) {
                //如果悬浮窗没有展开,那么就打开LogView
                showLogView();
            }
        });
        textView.setTextColor(Color.WHITE);
        textView.setText(R.string.str_log_tag);
        return floatingView = textView;
    }

    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, OKSizeUtil.getScreenHeight(rootView.getContext()) / 2);
        params.gravity = Gravity.BOTTOM;
        View logView = getLogView();
        logView.setTag(TAG_LOG_VIEW);
        rootView.addView(getLogView(), params);
        isOpen = true;
        listener.show(true);
    }

    @SuppressLint("SetTextI18n")
    private View getLogView() {

        if (logView != null) {
            return logView;
        }
        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setBackgroundColor(Color.BLACK);
        logView.addView(recyclerView);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        TextView closeView = new TextView(rootView.getContext());
        closeView.setOnClickListener(view -> closeLogView());
        closeView.setText("Close");
        closeView.setTextColor(Color.WHITE);
        logView.addView(closeView, params);
        return this.logView = logView;
    }

    /**
     * 关闭LogView
     */
    private void closeLogView() {
        isOpen = false;
        listener.show(false);
        rootView.removeView(getLogView());
    }

    public interface LogViewShowHideListener {
        void show(boolean isShow);
    }
}
