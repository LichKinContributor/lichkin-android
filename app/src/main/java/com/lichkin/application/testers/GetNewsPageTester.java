package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.GetNewsPageIn;
import com.lichkin.application.beans.impl.out.GetNewsPageOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.defines.beans.LKPageBean;
import com.lichkin.framework.utils.LKRandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 */
public class GetNewsPageTester {

    public static void test(LKRetrofit<GetNewsPageIn, LKPageBean<GetNewsPageOut>> retrofit, GetNewsPageIn in) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        retrofit.addTestResponseBeans(new LKPageBean<>(getSubList(in), in.getPageNumber(), in.getPageSize(), total));
    }

    private static List<GetNewsPageOut> getSubList(GetNewsPageIn in) {
        List<GetNewsPageOut> resultList = new ArrayList<>(total);
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

    private static List<GetNewsPageOut> list = new ArrayList<>(total);

    private static String[] titleArr = new String[]{
            "上海合作组织青岛峰会举行 习近平主持会议并发表重要讲话 强调要进一步弘扬“上海精神” 构建上海合作组织命运共同体",
            "40城、50次！5月楼市调控次数创纪录",
            "华北黄淮阴雨持续云南雨势渐强，下周北方再遭高温天",
            "深圳出台20年最强房产新政 本科毕业生可6折买房",
            "湖南邵阳公安局原纪检组长被双开：省监委执法时号召他人拒捕",
            "台茶农下跪陈情遭大陆退货 吴敦义：找民进党",
            "A级通缉令校长归案细节：国内落网 被抓时很平静",
            "六周前VS六周后，特朗普与马克龙的“塑料兄弟情”",
            "朝鲜领导人金正恩抵达新加坡",
            "新加坡总理李显龙今明两天分别会见朝美领导人",
            "美国放行中资并购险企 系特朗普上台来最大一笔",
            "G7峰会不欢而散：同一场景，各国“斗图”争抢C位！",
            "英女王庆92岁官方寿辰 哈利王子携妻子梅根亮相",
            "500万可以在深圳买套房 但在这里却只能买个车位",
            "外卖小哥为救护车引路网友怒赞 回应：举手之劳",
            "广州学生暴雨遇难：医院称遭电击伤 官方称无触电痕迹",
            "高三班主任被家长带人暴打 打人副局长被拘15天",
            "张家口杀6人嫌犯仍在逃 悬赏金升至40万800余人搜捕",
            "这些高校拼了！“最强”的招生广告来袭…最后一个笑岔",
            "又双叒叕有逃犯在张学友演唱会现场被抓获",
            "广西柳州两姐妹遇害前监控曝光 其父多次回望摄像头",
            "外卖平台招聘拒录“病毒性肝炎”骑手，被质疑歧视乙肝携带者",
            "鱼塘被倒不明液体后15吨鱼死亡？警方：监控没插电",
            "充话费送的？男子酒驾被查扔下儿子抱起女儿就跑",
            "云南陨石价格炒高几十倍 数百村民连夜寻石盼暴富"
    };

    private static String[] briefArr = new String[]{
            "10日，上海合作组织成员国元首理事会第十八次会议在青岛国际会议中心举行。中国国家主席习近平主持会议并发表重要讲话。上海合作组织成员国领导人、常设机构负责人、观察员国领导人及联合国等国际组织负责人出席会议。与会各方共同回顾上海合作组织发展历程，就本组织发展现状、任务、前景深入交换意见，就重大国际和地区问题协调立场，达成了广泛共识。",
            "5月全国各地楼市调控政策发布次数刷新单月纪录。为何政策发布如此频繁？这一波调控有哪些新变化？房价未来会怎么走？这都是民众颇为关注的问题。",
            "东北、华北、黄淮等地阴雨天持续，南方局部雨势强，华南东部、江南东部、云南等地有中到大雨，局地暴雨。下周，大范围暑热天将反扑，河北南部、河南大部、山东西部一带甚至超过35℃，需注意防暑降温。此外，云南雨势渐强，需防范地质灾害和次生灾害。",
            "6月5日上午，深圳市住房和建设局正式对外发布《关于深化住房制度改革加快建立多主体供给多渠道保障租购并举的住房供应与保障体系的意见(征求意见稿)》。",
            "湖南省纪委官方微信公号“三湘风纪”发布消息称：湖南邵阳市公安局原党委委员、市纪委驻市公安局纪检组原组长朱甲云被开除党籍和公职。",
            "前台湾地区副领导人、国民党主席吴敦义当天到南投县参加冻顶乌龙茶春茶展售会，在离开时前台中县茶商公会理事长、茶农陈再福突然下跪大喊“求‘副总统’帮忙。”",
            "蒋兆岗涉案潜逃被通缉。云南省公安厅发布A级通缉令，配合云南省监察委员会对涉案人员蒋兆岗进行通缉。",
            "当6周前，法国总统马克龙访美时与美国总统在白宫和媒体前面“大秀恩爱”时，估计谁也不会预料到在短短的6周时间之内，两人的关系就从“卿卿我我”跌入“冷若冰霜”，原因就在特朗普不顾盟友的反对，执意决定对包括法国在内的欧盟国家挥舞起“关税大棒”。在8日举行的G7峰会上，特朗普与马克龙在会面时一改之前的亲密，显示出两国之间所处的紧张状态。",
            "新华社华盛顿6月1日电 美国总统特朗普1日表示，他与朝鲜最高领导人金正恩的会晤将如期于6月12日在新加坡举行。",
            "新加坡外交部今早（10日）称，新加坡李显龙总理将分别在6月10日（今天）和11日（明天）会见朝鲜领导人金正恩、美国总统特朗普。朝美领导人都将在今天抵达新加坡，准备出席12日举行的朝美领导人会晤。",
            "中国泛海控股集团以27亿美元的价格收购美国Genworth的并购案，于6月8日通过美国外国投资委员会（CFIUS）的交易审查。收购成功后，Genworth公司将成为中国泛海控股集团旗下的子公司。",
            "在加拿大召开的本届G7峰会被认为是一场气氛紧张和尴尬的会面，特朗普提前离场，“聚会”不欢而散。",
            "英国举行女王伊丽莎白二世的92岁官方寿辰庆典，大批民众到场向英女王送上祝福。新加入的王室成员梅根，也跟着丈夫哈利王子同乘马车出场，梅根身着一袭粉红色洋装向民众挥手致意。",
            "据观察者网报道，近日有港媒称，新鸿基地产旗下位于香港九龙何文田片区的“天铸”公寓2楼单号车位，以约600万港元（约合人民币489.6万元）成交，荣登香港“车位王”。",
            "法制晚报·看法新闻 日前，一段外卖小哥给救护车带路的视频在朋友圈刷屏，引发网友点赞。视频中，一位外卖小哥骑着电动车奔波在路上，他不是去送外卖，而是在给救护车引路。",
            "受强雷雨云团影响，广州遭受连续数十小时的大暴雨，造成多地出现积水、内涝。6月8日，数名网友发微博称，一名17岁的学生在广州机场路上疑似“触电”后昏倒在水中，被送往医院抢救。随后，学生父亲表示，儿子抢救无效已不幸离世。",
            "有网贴称，因在校期间管教了上课耍手机、在校谈恋爱的违纪学生，资阳市乐至中学一名班主任在高考结束当晚，接到同是教师的学生家长邀约后，被学生家长带来的人暴打。",
            "河北省张家口市张北县大河镇公沟村发生一起故意杀人案，一名男子遇害，经侦查，警方确认一名叫做王力辉的男子有重大作案嫌疑。北京青年报记者10日从张家口张北县公安局专案组民警处了解到，此前，对提供线索抓获王力辉的举报人，公安机关的悬赏奖金已经从最初的5万元提高至20万元，现在，金额再次提高至40万元，专案组民警表示，目前他们已组织800余人展开搜捕，并向多地警方发出协查通报。本月8日，曾有人发布视频称王力辉已经被捕，对此专案组民警辟谣称，王力辉目前仍然在逃。北青报记者了解，从2008年起，犯罪嫌疑人王力辉在河南、河北、山西等地共杀害6人重伤1人，为公安部A级通缉犯。",
            "高考刚刚结束，国内多所高校发布2018年本科招生简章，选在这个时间，你们都是商量好的吗？",
            "在张学友金华演唱会现场，金华警方抓获一名逃犯(马某，49岁)，其因涉嫌盗窃被上海警方上网追逃。被抓获时，马某正在兜售演唱会门票，自称“歌神”歌迷，如门票卖不掉准备自己进场观看。同日，在演唱会开始前，金华警方还抓获一名涉嫌伪造国家机关公文罪被福建警方上网追逃的逃犯郑某。 ",
            "疑犯作案前的监控视频首度曝光，嫌疑人带着两个女儿在小卖部买酸奶时，曾多次回头看摄像头：",
            "乙肝公益组织“亿友公益”向澎湃新闻表示，美团骑手注册、接单用客户端“美团众包”涉嫌歧视乙肝人群，相比《有碍食品安全的疾病目录》中单列的病毒性甲肝和戊肝，美团的协议以“病毒性肝炎”代替，而实际上病毒性乙肝并不在有碍食品安全的疾病之列。",
            "辽宁省大连市普兰店区大刘家街道小山村村支部书记韩永福告诉澎湃新闻他的家人于5月31日看到，有人往自家承包的村里作为养殖、田地灌溉和牲畜饮用水备用水源的鱼塘里，倾倒两桶不明液体。一天后，鱼大批死亡，鱼塘水质明显受到污染。澎湃新闻从大连市水务局、普兰店区公安局了解到，目前当地相关部门已介入调查。",
            "原标题：酒驾被查，男子扔下儿子，抱起女儿就跑！求儿子心理阴影面积",
            "在甘蔗地水稻田里的土马路上，村民骑着摩托或机动三轮来来往往。"
    };

    static {
        for (int i = 1; i <= total; i++) {
            GetNewsPageOut news = new GetNewsPageOut();
            news.setUrl("file:///android_asset/test/test.html");
            int x = LKRandomUtils.randomInRange(0, titleArr.length - 1);
            news.setTitle(titleArr[x]);
            news.setBrief(briefArr[x]);
            List<String> imageUrls = new ArrayList<>();
            int random = LKRandomUtils.randomInRange(0, 9);
            for (int j = 1; j <= random; j++) {
                imageUrls.add("file:///android_asset/test/image/" + LKRandomUtils.randomInRange(11, 60) + ".jpg");
            }
            news.setImageUrls(imageUrls);
            list.add(news);
        }
    }

}