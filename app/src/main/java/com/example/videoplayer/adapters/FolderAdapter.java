package com.example.videoplayer.adapters;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.videoplayer.R;
import com.example.videoplayer.models.FolderDetails;
import com.example.videoplayer.models.VideoDetails;
import com.example.videoplayer.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class FolderAdapter  extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    private List<FolderDetails> list;

    public FolderAdapter(List<FolderDetails> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public FolderAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_folder, viewGroup, false);
        return new FolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FolderAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        FolderDetails data = list.get(position);

        holder.tv_folder_name.setText(data.getName());
        holder.tv_video_size.setText(String.format(Locale.US,"%d Videos", data.getSize()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_video_size, tv_folder_name;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);


            tv_video_size = itemView.findViewById(R.id.tv_video_size);
            tv_folder_name = itemView.findViewById(R.id.tv_folder_name);

        }
    }
}
