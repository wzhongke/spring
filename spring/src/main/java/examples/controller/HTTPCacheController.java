package examples.controller;

import examples.instantiating.ExampleBean;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/10/28.
 */
@Controller
@RequestMapping("/cache")
public class HTTPCacheController {

        /* ETag 是HTTP响应头中标记请求内容是否改变的标记位
    * setCachePeriod(int seconds): -1 不会生成"Cache-Control'的响应头
    *                               0 不能使用"Cache-Control: no-store:
    *                               n>0 返回"Cache-Control:max-age=n"的响应头
    * // Cache for an hour - "Cache-Control: max-age=3600"
        CacheControl ccCacheOneHour = CacheControl.maxAge(1, TimeUnit.HOURS);
        // Prevent caching - "Cache-Control: no-store"
        CacheControl ccNoStore = CacheControl.noStore();
        // Cache for ten days in public and private caches,
        // public caches should not transform the response
        // "Cache-Control: max-age=864000, public, no-transform"
        CacheControl ccCustom = CacheControl.maxAge(10, TimeUnit.DAYS)
        .noTransform().cachePublic();
    *                               */

    /**
     * ETags 通过Servlet filter 设置ShallowEtagHeaderFilter来启用
     * */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ExampleBean> showPerson(@ModelAttribute ExampleBean bean) {
        String hash = bean.hashCode() + "";
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .eTag(hash)
                .body(bean);
    }
}
