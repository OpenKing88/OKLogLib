package com.openking.oklog.printer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.openking.oklog.OKLogConfig;
import com.openking.oklog.OKLogType;
import com.openking.oklog.R;
import com.openking.oklog.bean.OKLogBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/213:36 PM
 * des: 视图可视化打印器，将log日志显示到界面上
 */
public class OKViewPrinter implements OKLogPrinter {

    private final RecyclerView recyclerView;
    private final LogAdapter adapter;
    private final OKViewPrinterProvider provider;

    public OKViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        provider = new OKViewPrinterProvider(rootView, recyclerView, isShow -> {
            if (!isShow)
                adapter.clearLog();
        });
    }

    public OKViewPrinterProvider getProvider() {
        return provider;
    }

    @Override
    public void print(@NotNull OKLogConfig config, int level, String tag, @NotNull String printStr) {
        //将log展示添加到列表中
        adapter.addItem(new OKLogBean(System.currentTimeMillis(), level, tag, printStr));
        //滚动到最新的log位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private final LayoutInflater inflater;
        private final List<OKLogBean> logs = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        void addItem(OKLogBean bean) {
            logs.add(bean);
            notifyItemInserted(logs.size() - 1);
        }

        public void clearLog() {
            logs.clear();
            notifyItemRangeRemoved(0,getItemCount());
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.ok_log_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            OKLogBean bean = logs.get(position);
            int color = getHighlightColor(bean.level);
            holder.tagView.setTextColor(color);
            holder.msgView.setTextColor(color);
            holder.tagView.setText(bean.getFlattened());
            holder.msgView.setText(bean.log);
        }

        /**
         * 根据log的级别获取不同的显示颜色
         *
         * @param logLevel level
         * @return color
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case OKLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case OKLogType.D:
                    highlight = 0xffffffff;
                    break;
                case OKLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case OKLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case OKLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tagView, msgView;

        public LogViewHolder(@NotNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            msgView = itemView.findViewById(R.id.msg);
        }
    }
}
