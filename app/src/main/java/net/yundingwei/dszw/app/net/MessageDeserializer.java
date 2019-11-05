package net.yundingwei.dszw.app.net;


import com.google.gson.reflect.TypeToken;

import net.yundingwei.dszw.app.home.model.GameEntity;
import net.yundingwei.dszw.app.home.model.MenuEntity;
import net.yundingwei.dszw.app.home.model.ResEntity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class MessageDeserializer {

    // 所有返回数据包类型
    public static HashMap<String, Type> typeMap = new HashMap<String, Type>();
    static {

        typeMap.put(MessageApi.game, new TypeToken<List<GameEntity>>(){}.getType());
        typeMap.put(MessageApi.menu, new TypeToken<List<MenuEntity>>(){}.getType());
        typeMap.put(MessageApi.auth, new TypeToken<ResEntity>(){}.getType());
    }
}
