package com.example.kaspe.app_shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private ArrayList<Product> data;

    private Context context;

    public ItemAdapter(Context context, ArrayList<Product> list) {
        super();
        this.data = list;

        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).
                inflate(R.layout.product, parent, false);
        Product p = data.get(position);
        TextView text1 = (TextView) rowView.findViewById(R.id.item_name);
        TextView text2 = (TextView) rowView.findViewById(R.id.plist_price_text);
        TextView text3 = (TextView) rowView.findViewById(R.id.plist_weight_text);
        final TextView amount = (TextView) rowView.findViewById(R.id.cart_product_quantity_tv);
        ImageView plus = (ImageView) rowView.findViewById(R.id.cart_plus_img);
        ImageView minus = (ImageView) rowView.findViewById(R.id.cart_minus_img);

        text1.setText(p.name);
        text2.setText(p.price);
        text3.setText(p.description);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(amount.getText().toString());
                int result = i + 1;
                amount.setText(String.valueOf(result));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(amount.getText().toString());
                int result = i - 1;
                if (result < 0) {
                    result = 0;
                }
                amount.setText(String.valueOf(result));
            }
        });

        return rowView;
    }

}