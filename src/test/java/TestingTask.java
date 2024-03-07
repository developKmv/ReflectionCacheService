import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.develop.entity.Fraction;
import ru.develop.entity.Fractionable;
import ru.develop.utils.CacheService;

import java.lang.reflect.Proxy;

@Slf4j
public class TestingTask {


    @Test
    public void testCache(){

        Fraction f3 = new Fraction(4,2);
        Fractionable proxyF5 = CacheService.cache(f3);
        proxyF5.doubleValue();

        Assertions.assertEquals(f3.doubleValue(),proxyF5.doubleValue());

        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));

        proxyF5.setNum(6);
        proxyF5.doubleValue();

        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));
        log.info(String.valueOf(Double.valueOf(proxyF5.doubleValue())));

    }
}
