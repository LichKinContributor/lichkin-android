package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.GetCompNewsPageIn;
import com.lichkin.application.beans.out.impl.GetCompNewsPageOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.defines.beans.LKPageBean;
import com.lichkin.framework.utils.LKRandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 */
public class GetCompNewsPageTester {

    public static void test(LKRetrofit<GetCompNewsPageIn, LKPageBean<GetCompNewsPageOut>> retrofit, GetCompNewsPageIn in) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "GetCompNewsPageTester");
        retrofit.addTestResponseBeans(new LKPageBean<>(getSubList(in), in.getPageNumber(), in.getPageSize(), total));
    }

    private static List<GetCompNewsPageOut> getSubList(GetCompNewsPageIn in) {
        List<GetCompNewsPageOut> resultList = new ArrayList<>(total);
        int from = in.getPageNumber() * in.getPageSize();
        int end = from + in.getPageSize();
        if (end > total) {
            end = total;
        }
        for (int i = from; i < end; i++) {
            resultList.add(list.get(i));
        }
        return resultList;
    }

    private static int total = 100;

    private static List<GetCompNewsPageOut> list = new ArrayList<>(total);

    private static String[] titleArr = new String[]{
            "苏州父女先后捐献遗体角膜 共帮助5人重获光明",
            "6月17日苏州住宅成交71套 非住宅成交55套",
            "一分钟看懂6月18日苏州重要教育新闻",
            "2018年第24周苏州住宅成交1607套 环比下跌6%",
            "中国球迷偶遇迪巴拉亲友团 苏州女粉丝高呼：我爱你",
            "江苏苏州为解决中小微台企“融资难”搭建新平台",
            "“百岁”苏州码子牌 现身京张铁路河北段",
            "苏州将赴哈佛招高端医学人才50余人，与世界级医院探讨合作",
            "中国电子竞技嘉年华苏州开幕",
            "26位浙江中青年艺术家苏州展出40余幅粉画新作",
            "iCity·2018苏州外籍人士足球联赛隆重开赛",
            "新时代改革再出发 未来苏州产业升级科技创新要这样做",
            "极客”精神助力苏宁缔造苏州河畔生活美学目的地",
            "开业即爆满 苏州丰隆汇HLCC mall究竟有何魅力？",
            "长安汽车奔奔苏州最高降0.49万 价格浮动欲购从速",
            "6月17日苏州教育新闻，向您道晚安",
            "为爱奔跑 金牌苏马唤来浓浓夏日情 科勒•2018苏州国际10公里精英赛今日完美收官",
            "1985年的苏州 除了小桥流水还有家家户户必备的马桶",
            "苏州将建设高水平的黄金经济带、生态屏障带、文化旅游带",
            "端午小长假首日天气晴好 苏州景区活动吸引人气"
    };

    private static String[] briefArr = new String[]{
            "三名新疆姑娘古丽沙拉·沙布尔拜、陈婧和永吉芳，日前在苏州免费接受了角膜移植手术，重获光明。记者获悉，此次移植所用的角膜来自于苏州一位名叫刘鲁卫的捐献者。而五年前，刘鲁卫的独生女去世时，也捐出了遗体和角膜。",
            "新浪乐居讯 据新浪乐居数据监控中心显示，2018年6月17日苏州商品房成交126套，成交面积11169.46平方米。截止2018年6月17日24时，苏州可售房源套数122016套，可售房源面积13419810.96平方米。",
            "名城苏州网讯 今晚，大型创业投资节目《创赢未来》第二季将播出第五期，下面我们先睹为快。",
            "新浪乐居讯(编辑 建宏)据新浪乐居数据监控中心显示，2018年第24周(6.11-6.17)苏州楼市商品房成交（含吴江）共计2288套，成交面积252519.64平米，环比前一周减少179套，环比跌幅7.26%。截止2018年6月17日24时，苏州可售房源套数122016套，可售房源面积面积13419810.96平方米。【往周成交榜】",
            "6月16日，在阿根廷本届世界杯首场比赛，梅西领衔的阿根廷队鏖战90分钟，被冰岛队逼平，当家球星梅西罚丢点球，错失首场胜利，但是拥有梅西、迪巴拉、阿圭罗的阿根廷队，永远不会让喜爱他们的球迷失望，阿根廷球迷也不会因为一场比赛就对喜爱了那么多年的球队失去信心。",
            "新华社南京6月17日电（记者何磊静）记者17日从江苏苏州市台办获悉，以“两岸同心，共谋发展”为主题的台商金融服务推进会日前在苏州举行，致力于解决中小微台企的融资难题。",
            "法制晚报讯（记者 崔毅飞）作为中国早期民间的商用数字，“苏州码”现在极少出现在百姓生活中，但在京张铁路沿途却还能发现“苏州码”的身影。近日，王嵬和陈培阳两位铁路文化学者驱车170公里，专程去寻找一处废弃的苏州码子里志牌。",
            "原标题：苏州将赴哈佛招高端医学人才50余人，与世界级医院探讨合作",
            "本报讯 16日，CEC2018中国电子竞技嘉年华（简称CEC2018）在苏州开幕。CEC2018以“王者荣耀”为核心项目，开展各项精彩赛事，并结合动漫、娱乐、直播等形式，使赛事内容更为丰富、观赏性更强。",
            "扬子晚报网6月17日讯（记者 张毕荣）6月17日，由杭州师范大学美术学院、苏州市美术家协会、明·美术馆共同主办的“粉白黛绿——浙江粉画邀请展”在苏州金鸡湖畔李公堤文化创意街区举办。本次展览为促进艺术家之间的相互交流，进一步推动中国粉画的发展，主办方邀请了26位来自浙江的中青年艺术家参展，展出他们的粉画新作40余幅。",
            "东方网6月17日消息：2018俄罗斯世界杯激情战火已经点燃！6月16日，SuzhouWorldCup苏州外籍人士足球联赛在苏州工业园区隆重开赛，百余名外籍人士参与其中，共享世界杯激情。",
            "6月15日，大型系列访谈节目《学习新思想发展高质量新时代改革再出发——解读“十二项三年行动计划”》第三场在苏州广电总台录制。本期节目聚焦“迈向产业高质量、跑出创新加速度”这一主题。苏州市委常委、常务副市长王翔携市编办、市发改委等部门负责同志，与专家、市民等展开对话，详细解读苏州正在实施的产业升级、科技创新两项三年行动计划”。",
            "夏日的惬意傍晚,在北外滩漫步的人,双眼很容易被苏州河边的一座似“欧式宫殿”一般的美丽建筑所吸引。",
            "对于苏州的潮流时尚“玩”家来说，这个六月无疑是不平凡的，因为金鸡湖畔的丰隆汇HLCC mall终于在大家的期待中揭开了神秘的面纱。",
            "近期新浪汽车主推新车奔奔，全国30多个城市限时优惠。其中苏州地区，奔奔近一个月成交价最低可享8.9折，现金优惠最高可达0.49万元，新车优惠价3.67万元起。新浪汽车全国实时采集价格，实际成交价请以当地经销商折扣为准，以上价格信息自发布之日起一周内有效。猛戳：更有90多个品牌400多款新车降价热销，优惠低至3.9折。",
            "苏城初中将职业体验营引入校园",
            "6月17日上午8点半，众所期待的科勒•2018苏州国际10公里精英赛在苏州石湖燃情开跑。来自包括肯尼亚、埃塞俄比亚、日本、韩国、新加坡等多个国家和地区的数千名马拉松选手积极参与，一场10公里马拉松赛事完满收官。此次赛事也是中国田协“金牌赛事”苏州（太湖）马拉松的系列赛之一。比赛当日，正值一年一度的父亲节，多个家庭全家出动参与这场盛会，使整个赛事不仅昂扬着竞技热情，也四处弥漫着浓浓的爱之互动。最终，在众所瞩目下，来自肯尼亚的Soy solomom Kipsang取得了男子组10公里项目冠军，来自埃塞俄比亚的Sintayehu tilahun Getahun取得了女子组10公里项目冠军。",
            "上有天堂，下有苏杭。苏州，一座有太多故事的历史文化名城。一组80年代的老照片，带您领略那时候的老苏州。",
            "名城苏州网讯 昨天召开的全市长江经济带发展工作推进会提出，要把保护长江生态、推进长江经济带发展作为苏州的一项重要政治任务和一个重大发展机遇。市相关部门表示，要认真贯彻落实会议精神，建设高水平的黄金经济带、生态屏障带、文化旅游带。",
            "今天是端午小长假第一天，晴好天气适宜出游，各大景区游客众多。"
    };

    static {
        for (int i = 1; i <= total; i++) {
            GetCompNewsPageOut news = new GetCompNewsPageOut();
            news.setUrl("file:///android_asset/test/test.html");
            int x = LKRandomUtils.randomInRange(0, titleArr.length - 1);
            news.setTitle(titleArr[x]);
            news.setBrief(briefArr[x]);
            list.add(news);
        }
    }

}
