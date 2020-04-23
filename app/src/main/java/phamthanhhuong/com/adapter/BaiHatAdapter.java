package phamthanhhuong.com.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import phamthanhhuong.com.baitapkaraoke.MainActivity;
import phamthanhhuong.com.baitapkaraoke.R;
import phamthanhhuong.com.model.BaiHat;

public class BaiHatAdapter extends ArrayAdapter<BaiHat> {
    Activity context;
    int resource;
    public BaiHatAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view=this.context.getLayoutInflater().inflate(this.resource,null);
        TextView txtMa=view.findViewById(R.id.txtMa);
        TextView txtTen=view.findViewById(R.id.txtTen);
        TextView txtCaSi=view.findViewById(R.id.txtCaSi);
        final ImageView imgLike=view.findViewById(R.id.imgLike);
        final ImageView imgDisLike=view.findViewById(R.id.imgDislike);
        final BaiHat bh=getItem(position);
        txtMa.setText(bh.getMa());
        txtTen.setText(bh.getTen());
        txtCaSi.setText(bh.getCasi());
        if(bh.getThich()==1)
        {
            imgLike.setVisibility(View.INVISIBLE);
            imgDisLike.setVisibility(View.VISIBLE);

        }
        else
        {
            imgDisLike.setVisibility(View.INVISIBLE);
            imgLike.setVisibility(View.VISIBLE);
        }
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgLike.setVisibility(View.INVISIBLE);
                imgDisLike.setVisibility(View.VISIBLE);
             xuLyLike(bh);
            }
        });
        imgDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgDisLike.setVisibility(View.INVISIBLE);
                imgLike.setVisibility(View.VISIBLE);
                xuLyDislike(bh);
            }
        });
        return view;
    }

    private void xuLyDislike(BaiHat bh) {
        ContentValues  values=new ContentValues();
        values.put("YEUTHICH",0);
        int kq;
        kq = MainActivity.database.update(MainActivity.TableName,values,"MABH=?",new String []{bh.getMa()});
        if(kq>0) {
            Toast.makeText(context,"Đã gỡ bỏ bài hát ["+bh.getTen()+"] khỏi danh sách yêu thích thành công",
                    Toast.LENGTH_LONG).show();
            if(MainActivity.selectedTab==1)
            {
                remove(bh);
            }
        }
    }

    private void xuLyLike(BaiHat bh) {
        ContentValues  values=new ContentValues();
        values.put("YEUTHICH",1);
        int kq;
        kq = MainActivity.database.update(MainActivity.TableName,values,"MABH=?",new String []{bh.getMa()});
        if(kq>0)
            Toast.makeText(context,"Đã thêm bài hát ["+bh.getTen()+"] vào danh sách yêu thích thành công",
                    Toast.LENGTH_LONG).show();
    }
}
