package com.example.blue_lan.soppingcart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements Button.OnClickListener, AdapterView.OnItemClickListener {

    private List<product> datas; //數據源
    private ShopAdapter adapter; //自定義調變器
    private ListView listView;   //ListView控件
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        // 模擬數據
        datas = new ArrayList<product>();
        product product = null;
        for (int i = 0; i < 30; i++) {
            product = new product();
            product.setName("商品："+ i +":單價:"+ i);
            product.setNum(1);
            product.setPrice(i);
            datas.add(product);
        }
        adapter = new ShopAdapter(datas,this);
        listView.setAdapter(adapter);

        //以上就是我们常用的自定義調變器ListView展示數據的方法了

        //解決問題：在哪裡處理按鈕的點擊響應事件，是調變器還是 Activity或者Fragment，這裡是在Activity本身處理接口
        //執行添加商品數量，减少商品數量的按鈕點擊事件接口回調
        adapter.setOnAddNum(this);
        adapter.setOnSubNum(this);
        listView.setOnItemClickListener(this);

    }

    //3、點擊某個按鈕的時候，如果列表項所需的數據改變了，如何更新UI

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        switch (view.getId()){
            case R.id.item_btn_add: //點擊添加數量按鈕，執行相應的處理
                // 獲取 Adapter 中設置的 Tag
                if (tag != null && tag instanceof Integer) { //解決問題：如何知道你點擊的按鈕是哪一個列表項中的，通過Tag的position
                    int position = (Integer) tag;
                    //更改集合的數據
                    int num = datas.get(position).getNum();
                    num++;
                    datas.get(position).setNum(num); //修改集合中商品数量
                    datas.get(position).setPrice(position*num); //修改集合中该商品总价 数量*单价
                    //解决问题：点击某个按钮的时候，如果列表项所需的数据改变了，如何更新UI
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.item_btn_sub: //点击减少数量按钮 ，执行相应的处理
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    //更改集合的数据
                    int num = datas.get(position).getNum();
                    if (num > 0) {
                        num--;
                        datas.get(position).setNum(num); //修改集合中商品数量
                        datas.get(position).setPrice(position * num); //修改集合中该商品总价 数量*单价
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(MainActivity.this,"點擊了第"+i+"個列表項",Toast.LENGTH_SHORT).show();
    }
}
