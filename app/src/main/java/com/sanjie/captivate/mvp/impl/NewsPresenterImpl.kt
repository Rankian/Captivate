package com.sanjie.captivate.mvp.impl

import com.sanjie.captivate.mvp.model.News
import com.sanjie.captivate.mvp.presenter.NewsPresenter

/**
 * Created by LangSanJie on 2017/4/18.
 */
class NewsPresenterImpl(val newsView: NewsPresenter.View) : NewsPresenter {


    override fun load() {
        val newsList = ArrayList<News>()

        for (i in 0..3) {

            newsList.add(News(100 + newsList.size, "今生有你，所以我的爱才如此浓烈，其实，我早就读懂了你离去时最后的回眸，那是尘世间最无奈的决绝，你的不舍是默默无言", "http://s7.sinaimg.cn/mw690/0035blA0gy6ENAnxlT8d6&690", "2017-04-18"))
            newsList.add(News(100 + newsList.size, "等待太久得来的东西，多半已经不是当初自己想要的样子了。世上最珍贵的不是永远得不到或已经得到的，而是你已经得到并且随时都有可能失去的东西！", "http://www.feizl.com/upload2007/2015_07/1507201245222423.jpg", "2017-04-18"))
            newsList.add(News(100 + newsList.size, "如果，爱一个人，守一份爱情，可以在早春一起去踏青，可以在盛夏一起去赏荷，可以在浅秋一起去观月，可以在深冬一起去寻梅，不厌倦，却欢乐，不平凡，却平淡。那么，此生便无憾了。", "http://www.feizl.com/upload2007/2015_07/1507201245222421.jpg", "2017-04-18"))
            newsList.add(News(100 + newsList.size, "你是一首百转千回的情歌，是一首心灵的词章，棹一方船桨，摆一叶方舟，着一张风帆，追你隐约的背影，远远观去，你是我一生无法如期盛开的花会，让我不忍放下手中笔的情诗，独恋红尘，生死相许，难描心中言，唯君身遥遥云外天。", "http://www.itouxiang.net/uploads/allimg/140423/1_140423083632_1.jpg", "2017-04-18"))
            newsList.add(News(100 + newsList.size, "一个钱币最美丽的状态，不是静止，而是当它像陀螺一样转动的时候，没人知道，即将转出来的那一面，是快乐或痛苦，是爱还是恨。快乐和痛苦，爱和恨，总是不停纠缠。", "http://www.itouxiang.net/uploads/allimg/140423/1_140423083643_1.jpg", "2017-04-18"))
            newsList.add(News(100 + newsList.size, "因为你，无论我走到了哪里，我看到的都是美丽风景，我听到都是悠扬的红尘恋歌。寒冬里，我用你的文字取暖；炎夏里，我用你的歌声纳凉；春天里，我在我的心田里播种温柔和浪漫；秋天里，我在你的心田里收获真爱和真情。", "http://www.qqpk.cn/Article/UploadFiles/201202/20120212114846610.jpg", "2017-04-18"))

        }

        newsView.onResult(newsList)
    }
}