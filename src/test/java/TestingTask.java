import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.develop.entity.Fraction;
import ru.develop.entity.Fractionable;
import ru.develop.utils.CacheService;

@Slf4j
public class TestingTask {


    @Test
    public void testCache(){
        Fraction f =new Fraction(4,2);
        Fractionable f2 = CacheService.cache(f);
        Assertions.assertEquals(f2.doubleValue(),f2.doubleValue());

    }
}
