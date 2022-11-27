package BookManager.project.utils;

import java.util.UUID;

/**
 * 管理用户的UUID，用于与Coolie对应
 */
public class UuidUtils {

    public static String next(){
        return UUID.randomUUID().toString().replace("-","a");
    }

}
