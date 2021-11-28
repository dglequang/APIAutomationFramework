package utility;

import apitest.BaseFunctions;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Common.class);

    public static String getTime() throws Exception{
        logger.info("getTime is running");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss_SSS");
        String systime = sdf.format(new Date());
        systime = systime.replace(":", "");
        systime = systime.replace("-", "");
        return systime;
    }
}
