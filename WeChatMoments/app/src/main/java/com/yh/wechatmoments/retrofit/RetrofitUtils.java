package com.yh.wechatmoments.retrofit;

import com.yh.wechatmoments.model.Image;
import com.yh.wechatmoments.model.Tweet;
import com.yh.wechatmoments.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://thoughtworks-ios.herokuapp.com/user/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final GetRequest_interface request = retrofit.create(GetRequest_interface.class);

    public static User getUser() {
        User user = null;
        Call<User> call = request.getUser();
        try {
            Response<User> response = call.execute();
            if (response.isSuccessful()) {
                user = response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<Tweet> getTweets() {
        List<Tweet> tweets = null;
        Call<List<Tweet>> call = request.getTweetsCall();
        try {
            Response<List<Tweet>> response = call.execute();
            if (response.isSuccessful()) {
                tweets = response.body();
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        if(!(tweet.getContent() == null && tweet.getSender() == null && tweet.getComments() == null)){
                            tweet.setImgList(mockImages());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    private static List<Image> mockImages() {
        List<Image> imageList = new ArrayList<>();
        Random random = new Random();
        if (random.nextBoolean())
            imageList.add(new Image("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.article.pchome.net%2F00%2F28%2F02%2F47%2Fpic_lib%2Fs960x639%2FWebshot_08s960x639.jpg&refer=http%3A%2F%2Fimg.article.pchome.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616465580&t=e177b6af58b9d16823597732f7065cff"));
        if (random.nextBoolean())
            imageList.add(new Image("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.article.pchome.net%2F00%2F34%2F09%2F06%2Fpic_lib%2Fs960x639%2Fjmfj061s960x639.jpg&refer=http%3A%2F%2Fimg.article.pchome.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616465580&t=6d24ae853e61825c8cd9c68c107b6ed6"));
        if (random.nextBoolean())
            imageList.add(new Image("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.article.pchome.net%2F00%2F26%2F57%2F76%2Fpic_lib%2Fs960x639%2Fseaside007s960x639.jpg&refer=http%3A%2F%2Fimg.article.pchome.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616465580&t=4acd529848e546dc9fda68fac9410f4b"));
        if (random.nextBoolean())
            imageList.add(new Image("https://img.tukuppt.com/ad_preview/00/15/72/5e900b62c12ef.jpg!/fw/980"));
        if (random.nextBoolean())
            imageList.add(new Image("https://tse1-mm.cn.bing.net/th/id/OIP.GqnSoczQtNWNAsdVfRl1OQHaFj?pid=ImgDet&rs=1"));
        if (random.nextBoolean())
            imageList.add(new Image("https://th.bing.com/th/id/R1957f37e2530230007a638e541cf9dff?rik=4HBooMIQXsfjPw&riu=http%3a%2f%2fimg.pptjia.com%2fimage%2f20190412%2f07bcbef31974e33370e787858543ac09.jpg&ehk=BxnXvBGBdmayyWqqRb3UoAwHD0kV18mvE7ffU2ucHJg%3d&risl=&pid=ImgRaw"));
        if (random.nextBoolean())
            imageList.add(new Image("https://th.bing.com/th/id/Re6afe2789ad8a7eaf757feadeed47091?rik=re8f8mNS8CTRag&riu=http%3a%2f%2fpic26.nipic.com%2f20130115%2f9252150_190535673305_2.jpg&ehk=8jGJrRW700rhyAu4HnlVVeOOyIFEYb6lAt482Ec%2b%2fXU%3d&risl=&pid=ImgRaw"));
        if (random.nextBoolean())
            imageList.add(new Image("https://th.bing.com/th/id/R54071695334e222d9f553dcb3a5324e2?rik=brhqV2LKxfPheg&riu=http%3a%2f%2fpic304.nipic.com%2ffile%2f20200624%2f27782217_182048277030_2.jpg&ehk=MIDrlWKgohDSEF6LemRFJPmbo0UgupbNxGyoYcZYMtc%3d&risl=&pid=ImgRaw"));
        if (random.nextBoolean())
            imageList.add(new Image("https://th.bing.com/th/id/R3a332e11fc1895cf77d6250b6d2bd7b3?rik=xI7cb7bXdc2xyQ&riu=http%3a%2f%2fimg.51miz.com%2fElement%2f00%2f71%2f42%2f57%2f6331fa18_E714257_dfe90258.jpg&ehk=PALZG1gHb2QOWwr2P1w84t3zTrm5mX2%2f0TJvYnal7fM%3d&risl=&pid=ImgRaw"));
        return imageList;
    }

}
