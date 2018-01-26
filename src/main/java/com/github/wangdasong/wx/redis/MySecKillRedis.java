package com.github.wangdasong.wx.redis;

import com.github.wangdasong.wx.config.RedisBusiness;
import org.springframework.data.redis.connection.RedisConnection;

import java.util.List;

public class MySecKillRedis{
    String watchkeys = "watchkeys";// 监视keys
    RedisBusiness redisBusiness = null;
    String userinfo;
    String eventId;
    public MySecKillRedis(RedisBusiness redisBusiness, String wxCode, String eventId, String bonusId) {
        this.userinfo = wxCode;
        this.watchkeys = bonusId;
        this.eventId = eventId;
        this.redisBusiness = redisBusiness;
    }

    public int doSecKill() {
        RedisConnection redisConnection = redisBusiness.getConnection();
        try {
            //是否已经抢得到了
            if(redisBusiness.exists("succ_"+ userinfo + "_" + eventId)) {
                String failuserifo = "fail_"+ userinfo + "_" + eventId + "_" + watchkeys;
                String failinfo="用户：" + failuserifo + "商品争抢失败，抢购失败";
                System.out.println(failinfo);
                /* 抢购失败业务逻辑 */
                redisBusiness.setNX(failuserifo, failinfo);
                return -1;
            }

            redisBusiness.watch(redisConnection, watchkeys);// watchkeys

            String val = redisBusiness.get(redisConnection, watchkeys);
            int valint = Integer.valueOf(val);

            if (valint >= 1) {
                redisBusiness.multi(redisConnection);// 开启事务
                // tx.incr("watchkeys");
                redisBusiness.incrBy(redisConnection, watchkeys, -1l);

                List<Object> list = redisBusiness.exec(redisConnection);// 提交事务，如果此时watchkeys被改动了，则返回null

                if (list == null ||list.size()==0) {
                    String failuserifo = "fail_"+ userinfo + "_" + eventId + "_" + watchkeys;
                    String failinfo="用户：" + failuserifo + "商品争抢失败，抢购失败";
                    System.out.println(failinfo);
                    /* 抢购失败业务逻辑 */
                    redisBusiness.setNX(failuserifo, failinfo);
                    return -1;
                } else {
                    for(Object succ : list){
                        String succuserifo ="succ_" + userinfo + "_" + eventId;
                        String succinfo="用户：" + succuserifo + "抢购成功，当前剩余数量:"
                                + valint ;
                        System.out.println(succinfo);
                         /* 抢购成功业务逻辑 */
                        redisBusiness.setNX(succuserifo, succinfo);
                    }
                    return 1;

                }

            } else {
                String failuserifo ="kcfail_" +  userinfo + "_" + eventId + "_" + watchkeys;
                String failinfo1="用户：" + failuserifo + "商品被抢购完毕，抢购失败";
                System.out.println(failinfo1);
                redisBusiness.setNX(failuserifo, failinfo1);
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(redisConnection != null){
                redisConnection.close();
            }
        }
        return -1;
    }
}
