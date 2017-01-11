package cn.bingoogolapple.swipebacklayout.demo.widget;

import java.util.Comparator;

import cn.bingoogolapple.swipebacklayout.demo.model.IndexModel;


public class PinyinComparator implements Comparator<IndexModel> {

    public int compare(IndexModel o1, IndexModel o2) {
        if (o1.topc.equals("@") || o2.topc.equals("#")) {
            return -1;
        } else if (o1.topc.equals("#") || o2.topc.equals("@")) {
            return 1;
        } else {
            return o1.topc.compareTo(o2.topc);
        }
    }

}