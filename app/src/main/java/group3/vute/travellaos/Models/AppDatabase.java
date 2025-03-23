package group3.vute.travellaos.Models;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import group3.vute.travellaos.Dao.ContactDao;
import group3.vute.travellaos.Dao.FavoriteDao;
import group3.vute.travellaos.Dao.FoodCategoryDao;
import group3.vute.travellaos.Dao.FoodDao;
import group3.vute.travellaos.Dao.ImageDao;
import group3.vute.travellaos.Dao.PlaceCategoryDao;
import group3.vute.travellaos.Dao.PlaceDao;
import group3.vute.travellaos.Dao.ReviewDao;
import group3.vute.travellaos.Dao.UserDao;

@Database(entities = {User.class, FoodCategory.class, Food.class, PlaceCategory.class, Place.class, Review.class, Image.class, Favorite.class, Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public abstract UserDao userDao();
    public abstract FoodCategoryDao foodCategoryDao();
    public abstract FoodDao foodDao();
    public abstract PlaceCategoryDao placeCategoryDao();
    public abstract PlaceDao placeDao();
    public abstract ReviewDao reviewDao();
    public abstract ImageDao imageDao();
    public abstract ContactDao contactDao();
    public abstract FavoriteDao favoriteDao();

//    static Migration migration = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
//            supportSQLiteDatabase.execSQL("ALTER TABLE users ADD COLUMN column_name TEXT");
//        }
//    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "LaosTravel.db").allowMainThreadQueries()
                            // .addMigrations(migration)
                            .fallbackToDestructiveMigration().addCallback(roomCallback).build();
                }
            }
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final PlaceCategoryDao placeCategoryDao;
        private final PlaceDao placeDao;
        private final FoodCategoryDao foodCategoryDao;
        private final FoodDao foodDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            userDao = db.userDao();
            placeCategoryDao = db.placeCategoryDao();
            foodCategoryDao = db.foodCategoryDao();
            placeDao = db.placeDao();
            foodDao = db.foodDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.insertUser(new User("Admin", "admin@gmail.com", "0987878787",null, 1, "123"));
            userDao.insertUser(new User("user1", "user1@gmail.com", "0866666888", null,  0, "123"));
            userDao.insertUser(new User("user2", "user2@gmail.com", "0987654321", null,  0, "123"));

            placeCategoryDao.insert(new PlaceCategory("Du lịch Văn hóa"));
            placeCategoryDao.insert(new PlaceCategory("Du lịch Sinh thái"));
            placeCategoryDao.insert(new PlaceCategory("Du lịch Mạo hiểm"));
            placeCategoryDao.insert(new PlaceCategory("Du lịch Đô thị"));
            placeCategoryDao.insert(new PlaceCategory("Du lịch Làng quê"));
            placeCategoryDao.insert(new PlaceCategory("Du lịch Giải trí"));

            foodCategoryDao.insert(new FoodCategory("Ẩm thực Đặc sản"));
            foodCategoryDao.insert(new FoodCategory("Ẩm thực Đường phố"));
            foodCategoryDao.insert(new FoodCategory("Ẩm thực Chay"));
            foodCategoryDao.insert(new FoodCategory("Ẩm thực Truyền thống"));

            placeDao.insert(new Place(
                    "Thủ đô Vientiane",
                    1,
                    "Khác với những quốc gia khác, thủ đô Vientiane của Lào không quá ồn ào, náo nhiệt. Mọi thứ nơi đây diễn ra một cách bình yên và chậm rãi, đến đây du khách sẽ cảm nhận được một sự bình yên khác lạ và cảm giác thân thiện của những con người nơi đây.\n" +
                            "\n" +
                            "Thủ đô Vientiane - địa điểm du lịch Lào với lịch sử lâu đời nổi bật với những công trình kiến trúc cổ kính, mang dấu ấn Phật Giáo Nguyên Thủy qua những bức tượng Phật và phù điêu được điêu khắc tinh xảo, với lòng thành kính vô hạn.\n" +
                            "\n" +
                            "Một vài gợi ý cho bạn về những địa điểm, công trình kiến trúc không nên bỏ qua khi đến với thủ đô xinh đẹp của Lào:\n" +
                            "\n" +
                            "Khải hoàn môn - Tượng đài Patuxay\n" +
                            "Biểu tượng quốc gia Pha That Luang\n" +
                            "Chùa Phra Keo dát vàng, dát ngọc\n" +
                            "Vườn Tượng Phật\n" +
                            "Chợ Talat Sao",
                    "Thủ đô Vientiane",
                    null)
            );
            placeDao.insert(new Place(
                    "Cố đô Luang Prabang",
                    4,
                    "Nếu Campuchia có Siem Reap, Việt Nam có Huế thì ở Lào có Cố đô Luang Prabang, nằm dọc theo con sông Mê Kông hiền hoà, nơi đây sở hữu những công trình kiến trúc cổ xưa, nhuốm màu thời gian gắn liền với cuộc sống Lào thời xưa cũ.\n" +
                            "\n" +
                            "Trong khung cảnh thiên nhiên đẹp dịu dàng các tòa cung điện xưa mang vẻ đẹp thâm nghiêm, huyền bí và lịch sử thăng trầm từ thời xa xưa của đất nước Lào. Những địa điểm check-in Lào tại cố đô Luang Prabang lý tưởng cho chuyến khám phá vùng đất nhỏ bé này mà bạn nên chinh phục.\n" +
                            "\n" +
                            "Tham quan cung điện Hoàng Gia\n" +
                            "Ngắm trọn thành phố trên Núi Phousi\n" +
                            "Viếng thăm chùa Xieng Thong\n" +
                            "Xem những buổi lễ khất thực của nhà sư\n" +
                            "Khám phá động Pak Ou, thác nước Tad Sae",
                    "Luang Prabang",
                    null)
            );
            placeDao.insert(new Place(
                    "Thị trấn Vang Vieng",
                    4,
                    "Thị trấn Vang Vieng một địa điểm du lịch Lào nổi tiếng với vẻ đẹp hoang sơ, bình yên phá chút dịu dàng của thiên nhiên hùng vĩ. Cách thủ đô tầm 150km thị trấn mang một màu xanh của núi rừng, đồng lúa của một vùng quê yên bình, khí hậu trong lành.\n" +
                            "\n" +
                            "Với vị thế địa hình “lưng tựa núi, mặt nhìn sông” đến đây bạn sẽ như hoà mình vào thiên nhiên thanh bình bên dòng sông Nam Song, những cánh đồng xanh mướt chạy dài, hoà quyện cùng những ngọn núi nhấp nhô tạo nên một khung cảnh quyến rũ ẩn chứa nét hoang sơ, và quá đỗi yên bình.",
                    "Cách thủ đô tầm 150km",
                    null)
            );
            placeDao.insert(new Place(
                    "Thác Kuang Si",
                    2,
                    "Địa điểm tham quan ở Lào nổi tiếng với sự hùng vĩ, hoang sơ mang nét đẹp quyến rũ của núi rừng mà bất cứ ai khi đến Lào đều không muốn bỏ qua là ngọn thác Kuang Si vĩ đại. Toạ lạc ở phía nam cách thành phố Luang Prabang 29km, ngọn tháp được bao bọc bởi rừng Kuang Si hùng vĩ.\n" +
                            "\n" +
                            "Là một trong những địa điểm được giới trẻ và những con người yêu thích thiên nhiên “săn lùng”, nơi đây tập trung nhiều con thác lớn nhỏ tạo nên một bức tranh thiên nhiên thuỷ mặc hùng vĩ, nhưng mang một nét thơ mộng, quyến rũ." +
                            "\n" +
                            "Bạn sẽ như lạc vào tiên cảnh, tận hưởng chuyến đi một cách thư giãn ngắm dòng suối chảy, nghe tiếng suối chảy róc rách, hoà quyện với thiên nhiên. Nhiều du khách tìm đến đây để có những khung ảnh ấn tượng và được ngâm mình trong làn nước trong xanh mát rượi.",
                    "Toạ lạc ở phía nam cách thành phố Luang Prabang 29km",
                    null)
            );
            placeDao.insert(new Place(
                    "Wat Phou",
                    1,
                    "Ngôi đền thiêng Wat Phou một di sản văn hoá thế giới là công trình văn hoá tiêu biểu của Champasak. Chạy dọc theo bờ sông Mekong, công trình Wat Phou cách thành phố Pakse tầm 40km, là một quần thể bằng đá có kiến trúc độc đáo được xây dựng từ thế kỷ thứ 9 đến thế kỷ thứ 13 mới hoàn thành.\n" +
                            "\n" +
                            "Trải qua bao nhiêu thăng trầm của lịch sử nhiều khu vực của ngôi đền bị đổ nát, nhưng bạn vẫn có thể chiêm ngưỡng được những điêu khắc, chạm trổ ấn tượng của những bức phù điêu. Bạn sẽ băng qua các bậc cấp lát đá để đến được khu đền thượng, hai bên là những bức tượng hình Linga và cột đá tròn dựng đứng.\n" +
                            "\n" +
                            "Đền Wat Phou là một địa điểm du lịch Lào lý tưởng cho chuyến du lịch khám phá, tìm hiểu những công trình cổ, nơi đây nhuốm màu huyền bí và bạn sẽ cảm nhận được sự bí ẩn, linh thiêng tại đây.",
                    "Wat Phou cách thành phố Pakse tầm 40km",
                    null)
            );
            placeDao.insert(new Place(
                    "Hang động Tham Khoun Xe",
                    2,
                    "Tham Khoun Xe là một trong những hang động sông lớn nhất thế giới nằm ở tỉnh Khammouan thuộc miền trung Lào. Nơi đây có dòng sông Xe Bang Fai chảy xuyên suốt. Là một hang động kỳ vĩ nhưng không kém phần thư mộng, trữ tình với những bí ẩn từ thiên nhiên chờ đợi bạn khám phá",
                    "Hang động Tham Khoun Xe",
                    null)
            );
            placeDao.insert(new Place(
                    "Hồ Khoun Kong Leng",
                    2,
                    "Thêm một địa điểm du lịch Lào có vẻ đẹp hoang sơ Hồ Khoun Kong Leng toạ lạc tại phía bắc tỉnh Khammouan cách thị xã Thakhek khoảng 30km về, nơi đây có màu nước xanh và trong màu ngọc bích như pha lê.\n" +
                            "\n" +
                            "Nước hồ chảy qua những lớp đá vôi nằm quanh ngọn núi, một số khu vực gần cầu cạn bạn có thể thỏa bơi lội và ngâm mình trong dòng nước trong xanh. Ngoài chụp ảnh check-in, với khung cảnh thơ mộng này hãy tản bộ thư giãn trên con đường mòn dẫn quanh rừng.",
                    "Toạ lạc tại phía bắc tỉnh Khammouan cách thị xã Thakhek khoảng 30km về",
                    null)
            );
            placeDao.insert(new Place(
                    "Pha That Luang",
                    4,
                    "Nằm tại thủ đô Viêng Chăn, Pha That Luang một bảo tháp Phật giáo lớn với niên đại từ thế kỷ thứ 3. Qua nhiều lần trùng tu, kiến thiết Pha That Luang trở thành một di vật và là biểu tượng nổi bật của quốc gia.",
                    "Thủ đô Viêng Chăn",
                    null)
            );
            placeDao.insert(new Place(
                    "Cánh đồng Chum",
                    5,
                    "Một địa điểm tham quan ở Lào khá độc đáo, đây là một cảnh quan khảo cổ cự thạch nổi tiếng tại Lào. Đến đây bạn sẽ cực bất ngờ bởi hàng ngàn chiếc chum niên đại cổ nằm rải rác trên thung lũng cao nguyên Xieng Khuang.\n" +
                            "\n" +
                            "Với niên đại từ 1.500 - 2.000 năm những chiếc chum này được sử dụng trong việc đựng di cốt hoặc có thể là vật chứa thực phẩm của cộng đồng người Môn - Khmer.",
                    "Thung lũng cao nguyên Xieng Khuang",
                    null)
            );
            placeDao.insert(new Place(
                    "Buddha Park",
                    1,
                    "Giống như tên gọi Buddha Park nơi đây là một công viên khá đặc biệt của Phật Giáo nằm cách thủ đô khoảng 25km, với nhiều bức phù điêu bí ẩn mà người Lào điêu khắc về Phật Giáo, tỏ lòng tôn kính với nền văn hoá đặc trưng này, đây là địa điểm tham quan khám phá Phật Giáo cực thú vị mà bạn nên ghé đến.",
                    "Cách thủ đô khoảng 25km",
                    null)
            );
            placeDao.insert(new Place(
                    "Si Phan Don",
                    2,
                    "Một quốc gia không có những tài nguyên biển quý giá, nhưng tại Lào cũng có một nơi có đảo. Si Phan Don là một quần đảo lớn nằm ven sông Mê Kông thơ mộng, theo tên nơi này có nghĩa là “4000 hòn đảo” với các đảo chính là Don Khong, Don Som, Don Khon và Don Det.\n" +
                            "\n" +
                            "Si Phan Don nằm sát với biên giới Campuchia nên nơi đây có sự giao thoa văn hoá rất đặc biệt, là một trong những địa danh nằm trong khu vực Đông Dương thu hút nhiều du khách.",
                    "Sát với biên giới Campuchia",
                    null)
            );
            placeDao.insert(new Place(
                    "Động Tham Kong Lo",
                    3,
                    "Địa điểm du lịch Lào nổi tiếng kỳ bí và rùng rợn thu hút nhiều du khách mê mạo hiểm, động Tham Kong Lo là nơi có dòng sông Nam hun Bun chảy qua, với các vách động cao hơn 90m và những cột thạch nhũ lâu đời.",
                    "Động Tham Kong Lo",
                    null)
            );
            placeDao.insert(new Place(
                    "Muang Ngoi Neua",
                    4,
                    "Thị trấn nhỏ Muang Ngoi Neua nằm cách Luang Prabang 170km về hướng Đông Bắc – nơi đây có rất ít người sinh sống và nằm bên dòng sông Nam Ou. Đến đây bạn hoàn toàn được thả mình vào thiên nhiên, tận hưởng những giây phút thư thái nhất trong chuyến du lịch Lào.",
                    "Cách Luang Prabang 170km về hướng Đông Bắc",
                    null)
            );
            placeDao.insert(new Place(
                    "Đồi Phousi",
                    3,
                    "Phousi tiếng Lào là “ngọn đồi thiêng”, ngọn đồi chỉ cao khoảng 100m. Du khách muốn khám phá đồi thiêng nhất định phải vượt qua 328 bậc thang để có thể di chuyển từ dưới chân đồi lên đến đỉnh. Từ đỉnh đồi, bạn được ngắm toàn cảnh cố đô Luang Prabang, sông Mekong và sông Nam Khan cũng như những ngọn đồi và cánh rừng bao bọc xung quanh.",
                    "Đồi Phousi",
                    null)
            );

            /* ====== FOOD ====== */
            foodDao.insert(new Food(
                    1,
                    "Cá hấp Mok Pa",
                    "Cá hấp là món phổ biến ở nhiều quốc gia, nhưng cá hấp ở Lào được chế biến theo cách thật đặc biệt. Người ta lọc phile cá tươi, thái miếng nhỏ rồi trộn cùng các loại rau thơm, hành, tỏi ớt. Sau đó hỗn hợp cá được bọc trong lá chuối mang hấp cách thủy. Hầu như quán ăn hay nhà hàng nào cũng có món ăn này.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    4,
                    "Laap",
                    "Du khách có thể thưởng thức món đặc sản Lào này trong bất cứ nhà hàng nào tại nước triệu voi. Nguyên liệu dùng chế biến món ăn là các loại thịt gà, heo, bò, vịt…và tim, gan của chúng. Một số nhà hàng còn dùng thịt hươu để làm món ăn này. Các loại thịt được trộn cùng thính, rau thơm, ớt và gia vị trước khi mang đi làm chín. Laap hợp để ăn cùng xôi và rau sống.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    3,
                    "Nộm đu đủ Tam maak hung",
                    "Nộm đu đủ Tam maak hung ở Lào đặc biệt ở chỗ mua đến đâu, làm đến đó chứ không làm sẵn nên hương vị luôn tươi ngon. Vẫn là những nguyên liệu và gia vị quen thuộc như món nộm đu đủ ở Việt Nam, nhưng người Lào cho đu đủ vào cối giã tay với lực vừa phải để sợi đu đủ mềm mà không nát lại ngấm đều gia vị. Nước mắm được dùng để trộn nộm là nước mắm cá có mùi hơi nặng nhưng đã ăn một lần đảm bảo bạn nhớ mãi không quên.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    1,
                    "Bò khô Sien Savanh",
                    "Nếu đang tìm kiếm đặc sản Lào để mua về làm quà, thì bò khô là gợi ý đầu tiên dành cho bạn. Bò khô của người Lào được ướp cùng xì dầu, tỏi, ớt, đường cọ, tiêu sọ, dầu hào… Khi thịt tươi ngấm gia vị, người ta mang đun chín rồi phơi nắng nhiều ngày để thịt khô lại.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    2,
                    "Xúc xích Sai Oua",
                    "Ngoài bò khô, xúc xích Lào cũng rất hợp để làm quà tặng. Món ăn này được chế biến từ thịt heo tươi, nhưng khác với xúc xích Việt Nam ở chỗ nguyên liệu có thêm sả, lá chanh, riềng và ớt. Xúc xích được phơi khô và bán nhiều trong các chợ hoặc cửa hàng đặc sản.\n" +
                            "\n",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    1,
                    "Socola thủ công",
                    "Chocolate Dream by Mikaël - một loại socola được sản xuất thủ công cũng là một đặc sản Lào được nhiều du khách lựa chọn. Nguyên liệu được sử dụng hoàn toàn là nguyên liệu hữu cơ. Bao bì đóng gói đơn giản nhưng đẹp mắt khiến ai nhìn thấy cũng muốn mua về.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    3,
                    "Rong biển chiên Kaipen",
                    "Không phải chỉ có người Nhật mà cả người Lào cũng có những món ăn vặt nổi tiếng làm từ rong biển. Rong biển chiên là một trong số đó. Người Lào làm món này bằng cách giã nát rong biển cùng một số loại gia vị. Sau đó dàn mỏng ra khay rồi rắc vừng lên trên. Rong biển mang phơi nắng đến khi khô là thu được thành phẩm. Mua rong biển về, bạn có thể chế biến bằng cách chiên giòn, ăn cực kỳ đưa miệng.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    1,
                    "Cà phê Lào",
                    "Tuy không nằm trong danh sách các nước xuất khẩu cà phê hàng đầu thế giới nhưng cà phê Lào được đánh giá có chất lượng tốt nhất. 95% sản lượng cà phê ở đây được trồng tại vùng cao nguyên Bolovan với khí hậu và thổ nhưỡng vô cùng lý tưởng.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    1,
                    "Sald Thịt Băm (Larb)",
                    "Đây là món nôm được chế biến từ thịt băm và các nguyên liệu rau thơm đi kèm, Sald Thịt Băm được coi là món ăn quốc gia của Lào. Nguyên liệu chính để làm ra món này thường sẽ là từ thịt bò, thịt gà, thịt vịt hoặc thịt lợn cùng với một số gia vị tryền thống như gạo xay, nước mắm, nước cốt chanh cùng các loại thảo mộc tươi. Bên cạnh đó, món nộm này thường được ăn kèm cùng với rau sống và cơm nếp để tăng vị giác của món ăn, giúp chúng ta không dễ bị ngán và đầy bụng.",
                    "Toàn quốc",
                    null));
            foodDao.insert(new Food(
                    1,
                    "Sindat - nướng trên lẩu dưới",
                    "Nếu bạn đi du lịch Lào cùng gia đình hay cùng hội bạn thân thì nhất định phải thử qua món nướng trên lẩu dưới cực kì nổi tiếng ở Lào này nhé. Món ăn này có thể làm thỏa mãn những tín đồ mê nướng lẫn tín đồ thích lẩu. Đồ ăn gồm có các loại thịt và hải sản giống như các món lẩu và nướng thông thường khác.",
                    "Toàn quốc",
                    null));

            return null;
        }
    }
}

