package tutorial;

import com.alibaba.fastjson.JSON;
import com.sample.cep.watcher.watcher;
import org.junit.Test;

public class tutorial {

    @Test
    public void Tutorial() {

        // First thing first injection event module(a pojo) and give a query
        watcher w = new watcher(event.class,
                "@name('my-statement') " +
                        "select log,service,val " +
                        "from event " +
                        "where service= 'a' and val> 1.000000");

        // we add a listener when event triggered
        w.addListener((newData, oldData, statement, runtime) -> {
            System.out.println("new data ->>>>> " + getObjectSpecificProp(newData[0]));
        });

        // we send data to CEP runtime
        w.sendEventBean(new event("", "a", 0.00), "event");
        sleep(1000);
        w.sendEventBean(new event("", "a", 2.00), "event");
        sleep(1000);
        w.sendEventBean(new event("", "a", 3.00), "event");

    }

    @Test
    public void LogMetric() {
        // Doc: http://esper.espertech.com/release-5.2.0/esper-reference/html/epl-operator.html
        // Section: 9.10. The 'regexp' Keyword

        // First thing first injection event module(a pojo) and give a query
        watcher w = new watcher(event.class,
                "@name('my-statement') " +
                        "select count(*) " +
                        "from event#time(3 sec) " +
                        "where service= 'a' and log regexp '.*Error.*'");

        // we add a listener when event triggered
        w.addListener((newData, oldData, statement, runtime) -> {
            System.out.println("new data ->>>>> " + getObjectSpecificProp(newData[0]));
        });

        // we send data to CEP runtime
        w.sendEventBean(new event("abc Error a", "a", 0.00), "event");
        sleep(1000);
        w.sendEventBean(new event("abc Error b", "a", 2.00), "event");
        sleep(1000);
        w.sendEventBean(new event("abc Error c", "a", 3.00), "event");
        sleep(1000);
        w.sendEventBean(new event("abc Error d", "a", 0.00), "event");
        sleep(1000);
        w.sendEventBean(new event("abc Error e", "a", 2.00), "event");
        sleep(1000);
        w.sendEventBean(new event("abc Error f", "a", 3.00), "event");
        sleep(6000);
    }

    private String getObjectSpecificProp(Object jsonObject) {
        var json = JSON.toJSONString(jsonObject);
        var prop = JSON.parseObject(json).get("properties");
        return JSON.toJSONString(prop, true);
    }

    private void sleep(long length) {
        try {
            Thread.sleep(length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
