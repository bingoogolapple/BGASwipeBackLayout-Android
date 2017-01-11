package cn.bingoogolapple.swipebacklayout.demo.util;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.demo.model.NormalModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/11 上午2:54
 * 描述:
 */
public class DataUtil {
    private DataUtil() {
    }

    public static List<NormalModel> loadNormalModelDatas() {
        List<NormalModel> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 4 == 0) {
                datas.add(new NormalModel("标题" + i, "我是短的描述" + i));
            } else {
                datas.add(new NormalModel("标题" + i, "我是很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的描述" + i));
            }
        }
        return datas;
    }
}
