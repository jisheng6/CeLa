package com.jish.cela;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ListView;
import com.jish.cela.adapter.HaoHanAdapter;
import com.jish.cela.bean.HaoHan;
import com.jish.cela.ui.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 侧拉索引:音乐APP,即时通讯,电商选择城市,短信验证选择城市都有这个类型自定义控件
 * 实现步骤:
 *  1.绘制A-Z的字母列表 (自绘式自定义控件)
 *  2.响应触摸事件
 *  3.提供监听回调
 *  4.获取汉字的拼音,首字母    (pinying4J通过汉字得到他的拼音,只能一个字符一个字符去转换成拼音)
 *  作业:提高自学能力,要求使用TingPinying:http://promeg.io/2017/03/18/tinypinyin-part-1/
 *  5.根据拼音排序
 *  6.根据首字母分组
 *  7.把监听回调和ListVIew结合起来
 *
 *  掌握解决问题的思路:把复杂的东西简单话,把复杂的东西分成尽可能小的模块,把我模块关键点,一步一个脚印的去做,最终就可以实现复杂的效果
 *  */
public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<HaoHan> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuickIndexBar bar = (QuickIndexBar) findViewById(R.id.bar);

/*      //C.打印按下的吐司
        bar.setOnLetterUpdateListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                ToastUtil.showToast(MainActivity.this,letter);
            }
        });
*/
        /* //测试有没有获取到拼音
        System.out.println(PinyinUtil.getPinyin("易宸锋"));
        System.out.println(PinyinUtil.getPinyin("易宸锋$%^$^D"));
        System.out.println(PinyinUtil.getPinyin("易    宸  锋  "));*/

        //D.View层
        lv = (ListView) findViewById(R.id.lv);

        //D.model层,创建集合
        persons = new ArrayList<>();
        //D.填充并排列数据
        fillAndSortData(persons);
        //D.Controller层,设置适配器
        lv.setAdapter(new HaoHanAdapter(persons,this));

        //根据用户按住的字符,自动跳到对应的ListView条目上
        bar.setOnLetterUpdateListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                for(int x=0; x<persons.size(); x++){
                    String l = persons.get(x).getPinyin().charAt(0) + "";
                    if (TextUtils.equals(letter,l)){
                        //找到第一个首字母是letter条目
                        lv.setSelection(x);
                        break;
                    }
                }
            }
        });
    }

    /**
     * 填充数据,并进行排序
     * @param persons
     */
    private void fillAndSortData(ArrayList<HaoHan> persons) {
        //填充
        for(int x=0; x<Cheeses.NAMES.length; x++){
            String name = Cheeses.NAMES[x];
            persons.add(new HaoHan(name));
        }
        //排序
        Collections.sort(persons);
    }
}
