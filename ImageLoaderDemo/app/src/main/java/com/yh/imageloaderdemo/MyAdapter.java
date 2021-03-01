package com.yh.imageloaderdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yh.imageloaderdemo.MyAdapter.MyViewHolder;
import com.yh.imageloaderdemo.MyImageLoader.MyImageLoaderBuilder;
import java.util.List;

public class MyAdapter extends Adapter<MyViewHolder> {

  ImageLoader imageLoader = ImageLoader.getInstance();
  private final List<ListCell> listCells;

  private MyImageLoader myImageLoader;

  public MyAdapter(List<ListCell> listCells, Context context) {
    this.listCells = listCells;
    imageLoader.init(ImageLoaderConfiguration.createDefault(context));

    myImageLoader = new MyImageLoaderBuilder()
        .setDiskCache(context, "img")
        .setDiskCacheSize_MB(20)
        .setMemoryCacheSize_B(20 * 1024 * 1024)
        .build();
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_cell_layout, parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    ListCell listCell = listCells.get(position);
    //holder.imageView.setImageURI(Uri.fromFile(new File("/sdcard/test.png")));
//    try {
//      FileInputStream fis = new FileInputStream("/sdcard/DCIM/Camera/IMG_20210122_150605.jpg");
//      holder.imageView.setImageBitmap(BitmapFactory.decodeStream(fis));
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }

//    holder.imageView.setImageResource(0);
//    new DownloadImgTask(holder.imageView).execute(listCell.getImageUrl());

//    ImageLoader imageLoader = ImageLoader.getInstance();
//    imageLoader.cancelDisplayTask(holder.imageView);
//    imageLoader.displayImage(listCell.getImageUrl(), holder.imageView);

    // Glide.with(holder.itemView.getContext()).load(listCell.getImageUrl()).into(holder.imageView);

    myImageLoader.loadImage(listCell.getImageUrl(), holder.imageView);

    holder.name.setText(listCell.getName());
    holder.comment.setText(listCell.getComment());

    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
              case R.id.next:
                Toast.makeText(v.getContext(), "next", Toast.LENGTH_SHORT).show();
                break;
              case R.id.detail:
                Toast.makeText(v.getContext(), "detail", Toast.LENGTH_SHORT).show();
                break;
              case R.id.add:
                Toast.makeText(v.getContext(), "add", Toast.LENGTH_SHORT).show();
                break;
              case R.id.del:
                listCells.remove(position);
                notifyItemRemoved(position);

                popupMenu.getMenu().findItem(R.id.next).setVisible(false);
                Toast.makeText(v.getContext(), "del", Toast.LENGTH_SHORT).show();
                break;
            }

            return true;
          }
        });

        popupMenu.setOnDismissListener(new OnDismissListener() {
          @Override
          public void onDismiss(PopupMenu menu) {
            Toast.makeText(v.getContext(), "dismiss", Toast.LENGTH_SHORT).show();
          }
        });
      }
    });
  }

  @Override
  public int getItemCount() {
    return listCells.size();
  }

  static class MyViewHolder extends ViewHolder {

    ImageView imageView;
    TextView name;
    TextView comment;
    View itemView;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      this.itemView = itemView;
      imageView = itemView.findViewById(R.id.imgView);
      name = itemView.findViewById(R.id.name);
      comment = itemView.findViewById(R.id.comment);
    }
  }

}
