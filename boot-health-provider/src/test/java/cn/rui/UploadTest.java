package cn.rui;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @author 徽州大都督
 * @date 2020/8/25
 */
public class UploadTest {

    //使用七牛云提供的SDK测试上传图片
    @Test
    public void test() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration (Zone.zone0 ());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager (cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "ff6t7oun0rGL45XaRR0moRqfCgdHoO3_iMEF9LZh";
        String secretKey = "pS_732bLiVdNnCWTx2E4k7Pt8K02pWbBCGHFp_lW";
        String bucket = "lidazhu";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\BaiduYunDownload\\传智健康\\(07)传智健康\\day04\\素材\\图片资源\\03a36073-a140-4942-9b9b-712cecb144901.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "abc.jpg";
        Auth auth = Auth.create (accessKey, secretKey);
        String upToken = auth.uploadToken (bucket);
        try {
            Response response = uploadManager.put (localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson ().fromJson (response.bodyString (), DefaultPutRet.class);
            System.out.println (putRet.key);
            System.out.println (putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println (r.toString ());
            try {
                System.err.println (r.bodyString ());
            } catch (QiniuException ex2) {
                //ignore
            }
        }


    }


    //删除七牛云服务器中的图片
    @Test
    public void test2(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        String accessKey = "ff6t7oun0rGL45XaRR0moRqfCgdHoO3_iMEF9LZh";
        String secretKey = "pS_732bLiVdNnCWTx2E4k7Pt8K02pWbBCGHFp_lW";
        String bucket = "lidazhu";
        String key = "abc.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
