package yandex.metrics;

import com.yandex.metrics.ClientResultGetter;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientResultGetterTest {
    private ClientResultGetter clientResultGetter;

    @Test
    public void testHighFailure(){
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("app-context-yandex-metrics.xml");
        ctx.refresh();
        clientResultGetter = (ClientResultGetter) ctx.getBean("clientResultGetter");

        List<String> res = clientResultGetter.getResult("highFailure");

        assertThat(res).hasSize(5);
    }
}
