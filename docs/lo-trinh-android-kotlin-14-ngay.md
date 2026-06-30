# Lộ trình học Android (Kotlin) — 15 ngày chi tiết

> **Dự án xuyên suốt:** "Movie Explorer" — app native gọi API phim thật (TMDB), có danh sách + chi tiết, favorite offline, đa ngôn ngữ VI/EN, push notification, xử lý lifecycle.
> Mọi kiến thức đều "rơi" vào app này, không học rời rạc.

## Cách dùng tài liệu này
- **Giả định:** bạn đã biết lập trình cơ bản (biến, hàm, vòng lặp) và dành ~4–6h/ngày. Nếu mới học lập trình lần đầu, giãn Day 1–2 thành 4 ngày.
- **Nhịp mỗi ngày:** Sáng học lý thuyết → Chiều làm trên app → Cuối ngày làm bài tập drill + viết báo cáo.
- **Quy ước:** mỗi ngày có **DoD (Definition of Done)** — chỉ qua ngày mới khi tick hết. Commit code mỗi ngày (commit message gợi ý ở cuối mỗi ngày).
- **Stack 2026:** Kotlin 2.2 (K2), Jetpack Compose + Material 3, ViewModel/StateFlow (MVVM), Retrofit + Coroutines, kotlinx.serialization, Coil, Room/DataStore, Hilt, Firebase Cloud Messaging.
- **Debug & hiệu năng xuyên suốt:** debug không gom vào một ngày cuối. Kỹ năng nền (Logcat, breakpoint, đọc stack trace) học ngay Day 1; mỗi công cụ chuyên biệt xuất hiện đúng lúc tính năng tương ứng ra đời — recomposition count + `key` (Day 4), Network Inspector (Day 9), Database Inspector (Day 11) — và Day 14 dành để đào sâu profiling khi app đã đủ phức tạp.

## Chuẩn bị trước Day 1
- [ ] Cài Android Studio (bản mới nhất) + Android SDK.
- [ ] Tạo emulator (AVD) hoặc bật USB debugging trên điện thoại thật.
- [ ] Đăng ký tài khoản TMDB và lấy API key (themoviedb.org → Settings → API). Miễn phí.
- [ ] Tài liệu nền song song: khóa miễn phí **"Android Basics with Compose"** của Google.

---

# TUẦN 1 — Nền tảng Kotlin & UI

## Day 1 — Setup môi trường + Kotlin cơ bản

**Mục tiêu:** chạy được app rỗng + nắm syntax Kotlin và null safety.

**Học (~2h)**
- Kotlin: `val`/`var`, kiểu dữ liệu, string template (`"$x"`), `if`/`when`, `for`/`while`, hàm + tham số mặc định.
- Null safety: kiểu nullable `?`, safe call `?.`, elvis `?:`, `!!`, smart cast.

**Làm trên app**
- Tạo project mới tên `MovieExplorer`, template **Empty Activity (Compose)**, Minimum SDK 24, package `com.<tên_bạn>.movieexplorer`.
- Chạy lên emulator, thấy màn hình "Hello Android" mặc định.
- `git init` + commit đầu tiên.

**Bài tập drill** (tạo file `Playground.kt`, chạy bằng hàm `main()`)
1. `fun grade(score: Int): String` trả về A/B/C/D/F theo thang điểm, dùng `when`.
2. `fun safeLength(s: String?): Int` trả độ dài chuỗi, nếu null trả 0 (dùng elvis).
3. `fun fizzbuzz()` in 1..30: bội 3 → "Fizz", bội 5 → "Buzz", cả hai → "FizzBuzz".

**🔧 Debug nền (học NGAY — dùng mỗi ngày từ giờ trở đi)**
Đây là kỹ năng cốt lõi, không để dành đến cuối. Học ngay khi bắt đầu có code có thể lỗi:
- **Logcat:** in log bằng `Log.d("TAG", "msg")`, lọc theo tag và mức (Debug/Error). Đây là cửa sổ bạn nhìn nhiều nhất.
- **Breakpoint + Debug mode:** đặt breakpoint (click lề trái), chạy nút **Debug** (con bọ), dùng step over/into và **"Evaluate Expression"** để xem giá trị biến lúc đang chạy.
- **Đọc stack trace:** khi crash, đọc từ trên xuống, tìm dòng đầu tiên thuộc code của bạn (`com.<tên_bạn>...`) — thường là nơi gây lỗi.
- Mini-drill: thêm `Log.d` vào hàm `grade()`, đặt breakpoint trong đó, chạy Debug và xem giá trị `score`.

**DoD**
- [ ] App rỗng chạy được trên emulator/điện thoại.
- [ ] 3 bài drill chạy ra kết quả đúng.
- [ ] Biết in Logcat, đặt breakpoint, dùng Evaluate Expression và đọc stack trace.
- [ ] Commit: `chore: init project + kotlin basics drills`

---

## Day 2 — Kotlin OOP + Collections + Coroutine (khái niệm)

**Mục tiêu:** viết được class/data class/sealed class và xử lý list bằng hàm bậc cao.

**Học (~2.5h)**
- `class`, constructor, property; `data class` (equals/copy/destructuring); `object`, `companion object`; `interface`; `enum`; **`sealed class/interface`** (cực quan trọng cho quản lý state sau này).
- Collections: `List`/`MutableList`/`Map`/`Set`; `map`, `filter`, `forEach`, `sortedBy`, `maxByOrNull`, `groupBy`; lambda & trailing lambda.
- Coroutine (chỉ khái niệm): `suspend`, `delay`, ý tưởng "không block main thread". Test bằng `runBlocking`.

**Làm trên app**
- Tạo package `model`, file `Movie.kt`:
  `data class Movie(val id: Int, val title: String, val overview: String, val posterPath: String?, val voteAverage: Double, val releaseDate: String)`
- Tạo `sealed interface MovieListUiState`: `Loading` (object), `Success(val movies: List<Movie>)`, `Error(val message: String)`.

**Bài tập drill**
1. `data class Student(val name: String, val score: Double)`. Tạo list 5 student → in tên student điểm cao nhất, lọc student >= 8.0, tính điểm trung bình (dùng `maxByOrNull`/`filter`/`map`+`average`).
2. `enum class Genre` (ACTION, COMEDY, DRAMA...) + hàm trả mô tả bằng `when` (exhaustive, không cần `else`).
3. `suspend fun loadFakeData(): List<String>` — `delay(1000)` rồi trả 3 chuỗi; gọi trong `runBlocking` và in.

**DoD**
- [ ] `Movie.kt` + `MovieListUiState` biên dịch không lỗi.
- [ ] 3 bài drill chạy đúng.
- [ ] Commit: `feat: add Movie model + UiState + kotlin oop drills`

---

## Day 3 — Jetpack Compose cơ bản + State

**Mục tiêu:** hiểu tư duy declarative và state làm UI tự cập nhật.

**Học (~2.5h)**
- `@Composable`, recomposition, `@Preview`.
- Widget: `Text`, `Button`, `Image`, `Spacer`; layout `Column`/`Row`/`Box` (+ arrangement/alignment); `Modifier` (`padding`, `fillMaxWidth`, `size`, `background`, `clickable`).
- State: `remember`, `mutableStateOf`, `by`; **state hoisting** (đưa state lên composable cha, truyền sự kiện xuống).

**Làm trên app**
- Tạo composable `MovieCard(movie: Movie)` hiển thị title + overview (rút gọn) + điểm. Viết `@Preview` với 1 `Movie` mẫu hardcode.

**Bài tập drill**
1. **Counter:** Text hiện số + nút "+", "–", "Reset" (dùng `remember`/`mutableStateOf`).
2. **Toggle:** một nút đổi text "ON"/"OFF" và đổi màu nền theo trạng thái.
3. **Greeting realtime:** `TextField` nhập tên → `Text` hiển thị "Xin chào, <tên>" cập nhật ngay khi gõ.

**DoD**
- [ ] `MovieCard` preview hiển thị đúng trong Android Studio.
- [ ] 3 bài drill chạy trên emulator.
- [ ] Commit: `feat: MovieCard composable + compose state drills`

---

## Day 4 — Layout danh sách + Material 3 + load ảnh

**Mục tiêu:** dựng danh sách cuộn theo Material 3, load ảnh từ URL.

**Học (~2.5h)**
- `LazyColumn`/`LazyRow` + `items()` + `key`.
- Material 3: `Scaffold`, `TopAppBar`, `Card`, `Surface`, `MaterialTheme.colorScheme`/`typography`; dark/light theme (KHÔNG hardcode màu).
- Load ảnh URL bằng **Coil** (`AsyncImage`) — thêm dependency.
- Mở **Material 3 Figma kit** để đối chiếu component với code.

**Làm trên app**
- Màn hình `MovieListScreen`: `Scaffold` + `TopAppBar` "Movie Explorer" + `LazyColumn` render ~8 `Movie` hardcode bằng `MovieCard`. Poster load qua Coil (dùng URL TMDB mẫu `https://image.tmdb.org/t/p/w500<posterPath>` hoặc placeholder).

**Bài tập drill**
1. Danh sách 10 món ăn + giá trong `LazyColumn`, mỗi item là `Card`.
2. `LazyRow` các "chip" danh mục cuộn ngang.
3. Bật dark mode ở hệ thống → app đổi màu đúng nhờ dùng `colorScheme` (không hardcode).

**🔧 Hiệu năng (bắt đầu xây thói quen từ đây)**
- Mở **Layout Inspector**, bật **recomposition count**, cuộn danh sách và để ý composable nào đếm tăng vọt.
- Luôn truyền `key` cho `items()` trong `LazyColumn` (vd `items(movies, key = { it.id })`) — hiểu vì sao nó giúp Compose tái dùng item thay vì dựng lại.

**DoD**
- [ ] Danh sách phim cuộn mượt, có poster, theme đúng cả light/dark.
- [ ] `items()` có dùng `key`; biết mở recomposition count.
- [ ] 3 bài drill xong.
- [ ] Commit: `feat: MovieListScreen with LazyColumn + Material3 + Coil`

---

## Day 5 — Navigation nhiều màn hình

**Mục tiêu:** điều hướng list → detail và truyền tham số.

**Học (~2h)**
- Navigation Compose: `NavController`, `NavHost`, `composable("route")`, truyền argument (`detail/{movieId}`), `navigate()`, `popBackStack()`.

**Làm trên app**
- Thêm 2 route: `list` và `detail/{movieId}`.
- Bấm `MovieCard` → mở `MovieDetailScreen` hiển thị chi tiết phim (tìm theo `id` trong list). Có nút Back.

**Bài tập drill**
1. App 3 màn hình Home → Profile → Settings (điều hướng bằng nút hoặc bottom bar).
2. Truyền một `String` argument giữa 2 màn hình và hiển thị nó ở màn đích.

**DoD**
- [ ] Bấm phim mở đúng màn chi tiết, back hoạt động.
- [ ] 2 bài drill xong.
- [ ] Commit: `feat: navigation list -> detail with movieId arg`

---

## Day 6 — Kiến trúc MVVM: ViewModel + StateFlow

**Mục tiêu:** tách logic khỏi UI theo luồng dữ liệu một chiều (UDF).

**Học (~2.5h)**
- `ViewModel` (vì sao tồn tại, sống sót qua config change), `viewModelScope`.
- `StateFlow` (vs LiveData), `collectAsStateWithLifecycle`.
- Unidirectional Data Flow: state đi xuống, event đi lên.
- Thêm dependency: `lifecycle-viewmodel-compose`, `lifecycle-runtime-compose`.

**Làm trên app**
- Tạo `MovieListViewModel` giữ `StateFlow<MovieListUiState>`, khởi tạo bằng list hardcode (giả lập). `MovieListScreen` quan sát state qua `collectAsStateWithLifecycle` và render theo `Loading`/`Success`/`Error`.

**Bài tập drill**
1. Chuyển Counter (Day 3) vào `ViewModel` (`StateFlow<Int>`), screen chỉ render + gọi event.
2. Thêm `fun refresh()` trong VM: set `Loading` → `delay(1500)` → set `Success`. Quan sát spinner xuất hiện rồi list hiện.

**DoD**
- [ ] List hiển thị từ VM (không còn state cứng trong Composable).
- [ ] 2 bài drill xong.
- [ ] Commit: `refactor: introduce MovieListViewModel + StateFlow`

---

## Day 7 — MILESTONE tuần 1: ráp skeleton + refactor

**Mục tiêu:** app chạy đủ luồng UI với data giả, cấu trúc gọn gàng.

**Làm trên app**
- Tổ chức package: `ui` (screens, components, theme), `model`, `viewmodel`.
- List → Detail dùng chung nguồn data từ VM.
- Hiển thị đúng 3 trạng thái `Loading`/`Success`/`Error` (tạm dùng nút giả lập để chuyển trạng thái).
- Thêm màn hình/ô **search giả**: `TextField` lọc list theo title (client-side).

**Bài tập tổng hợp**
- Quay video 30s demo toàn bộ luồng: mở app → loading → list → search → mở detail → back.

**DoD**
- [ ] App chạy đủ luồng UI, không crash.
- [ ] Code chia package rõ ràng.
- [ ] Viết **retro tuần 1** (5 dòng: hiểu gì / chưa hiểu gì).
- [ ] Commit + tag: `git tag v0.1-ui` — message `milestone: week1 UI skeleton complete`

---

# TUẦN 2 — Data, logic & tính năng nâng cao

## Day 8 — App Lifecycle

**Mục tiêu:** hiểu vòng đời và vì sao state mất khi xoay màn hình.

**Học (~2.5h)**
- Activity lifecycle: `onCreate`/`onStart`/`onResume`/`onPause`/`onStop`/`onDestroy`; configuration change (xoay màn hình); khái niệm process death.
- Compose side-effects: `LaunchedEffect`, `DisposableEffect`, `rememberCoroutineScope`, `rememberSaveable`.
- Vì sao `remember` mất khi xoay, còn `ViewModel`/`rememberSaveable` thì giữ.

**Làm trên app**
- Ghi log lifecycle (qua `DefaultLifecycleObserver` hoặc override trong Activity) → xem Logcat khi mở/back/đa nhiệm.
- Nhập text vào ô search rồi xoay màn hình: chuyển từ `remember` → `rememberSaveable` để giữ giá trị; list vẫn còn nhờ ViewModel.

**Bài tập drill**
1. In log đủ các callback lifecycle khi mở app, nhấn Home, quay lại, back ra.
2. Demo mất state với `remember` khi xoay → fix bằng `rememberSaveable`.
3. `LaunchedEffect(Unit)` log "screen entered" 1 lần; `DisposableEffect` log "screen left" khi rời màn.

**DoD**
- [ ] Hiểu và demo được khác biệt remember vs rememberSaveable vs ViewModel.
- [ ] Logcat thể hiện đúng chuỗi lifecycle.
- [ ] Commit: `feat: lifecycle logging + rememberSaveable for search`

---

## Day 9 — Kết nối API thật (Retrofit + Coroutines)

**Mục tiêu:** app hiển thị phim thật từ TMDB.

**Học (~3h)**
- REST/HTTP & JSON cơ bản.
- Retrofit: interface, `@GET`, `@Query`, `@Path`, hàm `suspend`.
- kotlinx.serialization: `@Serializable`, `@SerialName`.
- OkHttp `HttpLoggingInterceptor` để xem request/response.
- Thêm permission `INTERNET` + các dependency.

**Làm trên app**
- `MovieApi` interface: `getPopularMovies(page)` gọi `/movie/popular`.
- DTO: `MovieDto`, `MovieResponseDto` (dùng `@SerialName` map field JSON), viết mapper `MovieDto -> Movie`.
- Tạo Retrofit instance. ViewModel gọi API trong `viewModelScope`, đổ vào `StateFlow`.
- App hiển thị phim thật + poster thật (Coil + base url ảnh).

**Bài tập drill**
1. Bật logging interceptor, in raw JSON ra Logcat trước khi parse.
2. Thêm hàm `searchMovies(query)` gọi `/search/movie` và gọi thử (chưa cần nối UI).

**🔧 Debug mạng**
- Mở **Network Inspector** (App Inspection) → xem trực quan request TMDB: URL, query param, header, status code, body JSON. Dùng nó để soi lỗi 401 (sai API key) / 404 / parse fail thay vì đoán mò.

**DoD**
- [ ] Mở app thấy danh sách phim phổ biến thật từ TMDB.
- [ ] Logcat in được JSON trả về.
- [ ] Soi được request bằng Network Inspector.
- [ ] Commit: `feat: integrate TMDB API with Retrofit + serialization`

---

## Day 10 — Repository + Hilt + xử lý Loading/Error

**Mục tiêu:** kiến trúc sạch và app xử lý lỗi mạng đàng hoàng.

**Học (~3h)**
- Repository pattern: tách data source khỏi ViewModel.
- Hilt: `@HiltAndroidApp`, `@AndroidEntryPoint`, `@Module`/`@Provides`, `@Inject`, `@HiltViewModel`.
- Xử lý lỗi: `try/catch` hoặc `runCatching`, `Result`.

**Làm trên app**
- `MovieRepository` (interface + impl) gọi `MovieApi`.
- Cấu hình Hilt cung cấp `Retrofit`/`MovieApi`/`MovieRepository`. `@HiltViewModel` inject repository vào VM.
- VM bọc gọi mạng trong try/catch → set `Loading`/`Success`/`Error`. UI hiện spinner khi tải, hiện thông báo lỗi + nút "Thử lại" khi lỗi.

**Bài tập drill**
1. Tắt wifi/mạng → app hiện trạng thái Error + nút Retry hoạt động (bật mạng lại thì load được).
2. Thêm nút refresh (hoặc pull-to-refresh) gọi lại repository.

**DoD**
- [ ] App không crash khi mất mạng, hiện Error đúng.
- [ ] Hilt inject chạy được (không tạo thủ công Retrofit trong VM nữa).
- [ ] Commit: `refactor: add Repository + Hilt DI + error/loading handling`

---

## Day 11 — Lưu trữ cục bộ (Room + DataStore)

**Mục tiêu:** favorite lưu offline, đọc lại sau khi tắt app.

**Học (~3h)**
- Room: `@Entity`, `@Dao` (insert/delete/query trả `Flow`), `@Database`.
- DataStore Preferences: lưu cấu hình key-value (theme/ngôn ngữ).

**Làm trên app**
- Room lưu phim yêu thích: entity `FavoriteMovie`, DAO (`insert`, `delete`, `getAll(): Flow`). Nút trái tim ở màn Detail → toggle favorite.
- Màn hình "Yêu thích" đọc từ Room (xem được cả khi offline).
- DataStore: lưu lựa chọn theme hoặc ngôn ngữ (chuẩn bị cho Day 12).

**Bài tập drill**
1. Thêm favorite → kill app → mở lại vẫn còn.
2. Hiển thị badge số lượng phim đã yêu thích trên TopAppBar.

**🔧 Debug dữ liệu**
- Mở **Database Inspector** (App Inspection) → xem bảng `FavoriteMovie` cập nhật **realtime** khi bấm tim; chạy thử truy vấn SQL ngay trong đó để kiểm tra dữ liệu.

**DoD**
- [ ] Favorite persistent qua các lần mở app.
- [ ] Đọc favorite offline được.
- [ ] Xem được bảng Room bằng Database Inspector.
- [ ] Commit: `feat: favorites with Room + settings with DataStore`

---

## Day 12 — Localization (đa ngôn ngữ VI/EN)

**Mục tiêu:** app chuyển đổi tiếng Việt ↔ tiếng Anh.

**Học (~2.5h)**
- `strings.xml` + thư mục `values-vi/strings.xml`; lấy chuỗi qua `stringResource()`.
- Per-app language (LocaleManager / AppCompat locale).
- Format số/ngày theo `Locale`; `plurals` (quantity strings).

**Làm trên app**
- Tách TOÀN BỘ text cứng → `strings.xml` (EN mặc định) + bản dịch VI ở `values-vi/`.
- Thêm setting đổi ngôn ngữ (lưu vào DataStore), áp dụng ngay.
- Format ngày phát hành + điểm số theo locale hiện tại.

**Bài tập drill**
1. Đổi ngôn ngữ trong app VI ↔ EN, toàn bộ text đổi theo.
2. Format cùng một ngày theo 2 locale khác nhau và in ra.
3. Dùng `plurals`: "1 phim" / "5 phim" (số ít/số nhiều đúng).

**DoD**
- [ ] Không còn chuỗi hardcode trong UI.
- [ ] Chuyển ngôn ngữ hoạt động đầy đủ.
- [ ] Commit: `feat: localization VI/EN + locale-aware formatting`

---

## Day 13 — Push Notification (Firebase Cloud Messaging)

**Mục tiêu:** nhận và hiển thị push notification.

**Học (~3h)**
- Notification cơ bản: `NotificationChannel` (bắt buộc Android 8+), quyền `POST_NOTIFICATIONS` (xin runtime trên Android 13+), `NotificationCompat.Builder`, `PendingIntent`.
- FCM: tạo Firebase project, thêm `google-services.json`, `FirebaseMessagingService`, lấy device token.

**Làm trên app**
- Tạo notification channel; xin quyền `POST_NOTIFICATIONS` lúc runtime.
- Tích hợp FCM: in token ra Logcat; service hiển thị notification khi nhận message; bấm notification mở app (mở thẳng tới màn Detail = điểm cộng, dùng deep link).

**Bài tập drill**
1. Tạo **local notification** bằng một nút bấm (chưa cần FCM) để chắc channel + permission đúng.
2. Gửi **test message** từ Firebase Console (Cloud Messaging) → nhận được khi app foreground và khi app background.

**DoD**
- [ ] Nhận được notification từ Firebase Console.
- [ ] Bấm vào notification mở đúng app/màn hình.
- [ ] Commit: `feat: push notifications with FCM + notification channel`

---

## Day 14 — Performance deep-dive & tổng kiểm tra

**Mục tiêu:** đào sâu hiệu năng trên app đã trưởng thành và tổng hợp các kỹ năng debug đã rải suốt 2 tuần.

> Lưu ý: kỹ năng debug nền (breakpoint, Logcat, stack trace) đã học từ Day 1; Network Inspector (Day 9), Database Inspector (Day 11), recomposition count + `key` (Day 4) cũng đã làm. Hôm nay tập trung vào phần **chỉ làm được khi app đã đủ phức tạp**: profiling sâu, săn leak, và tối ưu recomposition nâng cao.

**Học (~3h)**
- **Memory Profiler:** heap dump, nhận diện rò rỉ bộ nhớ (object không được giải phóng), giữ tham chiếu sai.
- **CPU Profiler:** tìm hàm tốn CPU, thao tác nặng chạy nhầm trên main thread.
- **Jank / khung hình rớt:** đo độ mượt khi cuộn, hiểu ngưỡng 16ms/frame.
- **Recomposition nâng cao:** `derivedStateOf` (tránh tính lại thừa), tham số ổn định `@Stable`/`@Immutable`, hoisting state hợp lý, tránh truyền lambda tạo mới mỗi lần.
- **Cold start & Baseline Profiles:** đo thời gian khởi động và (đọc hiểu) cách Baseline Profiles rút ngắn nó.

**Làm trên app**
- Chạy **Memory Profiler:** mở/đóng màn Detail ~10 lần, chụp heap, kiểm tra bộ nhớ có tăng không hồi (dấu hiệu leak) → sửa nếu có.
- Audit recomposition toàn app bằng Layout Inspector: tìm mọi composable recompose thừa khi cuộn/đổi state → áp `derivedStateOf`, tách state, ổn định tham số để giảm.
- Đo **cold start** (dòng "Displayed" trong Logcat) trước và sau tối ưu, ghi lại con số.
- Kiểm tra không có thao tác nặng (parse, sort danh sách lớn) chạy trên main thread.

**Bài tập drill**
1. Tạo một leak có chủ đích (giữ reference tới Context/Activity) → phát hiện bằng Memory Profiler → sửa.
2. Dùng `derivedStateOf` cho một giá trị dẫn xuất (vd tiêu đề lọc theo search) và xác nhận giảm recomposition.
3. Cuộn danh sách dài, kiểm tra không bị jank; nếu có, tìm và xử lý nguyên nhân.
4. So sánh cold start trước/sau, viết 2–3 dòng nhận xét.

**DoD**
- [ ] Không còn leak rõ ràng khi vào/ra màn Detail nhiều lần.
- [ ] Giảm được recomposition thừa bằng kỹ thuật nâng cao (không chỉ `key`).
- [ ] Có số đo cold start trước/sau.
- [ ] Commit: `perf: profiling pass — leaks, recomposition, startup`

---

## Day 15 — Polish + Test + Build + Tổng kết

**Mục tiêu:** hoàn thiện, kiểm thử, build APK chạy độc lập.

**Học (~2h)**
- Unit test cơ bản (JUnit) cho mapper/logic.
- Tổng quan release build (signing, minify).

**Làm trên app**
- Viết 2–3 unit test: mapper `MovieDto -> Movie`, logic lọc search, toggle favorite.
- Rà soát UI: empty state, error state, dark mode, xoay màn hình, đổi ngôn ngữ — đảm bảo không crash.
- Build APK release (`./gradlew assembleRelease` hoặc menu Build → Generate App Bundle/APK), cài thử lên máy.
- Viết `README.md`: mô tả app + ảnh chụp màn hình.

**Bài tập tổng kết**
- Vẽ lại (bằng lời hoặc sơ đồ) kiến trúc app: `UI → ViewModel → Repository → (API / Room)`.
- Liệt kê 3 thứ muốn học tiếp (vd: Paging 3, testing nâng cao, CI/CD, Compose animation).

**DoD**
- [ ] Test pass.
- [ ] APK cài và chạy độc lập trên máy.
- [ ] Commit + tag: `git tag v1.0` — message `release: Movie Explorer v1.0`
- [ ] Viết **retro tổng** 2 tuần.

---

# Mẫu báo cáo hằng ngày

Điền nhanh mỗi cuối ngày (~5 phút). Mẹo: commit code mỗi ngày làm "bằng chứng" cho báo cáo.

```
Ngày X — [Chủ đề]
✅ Đã làm được:
   - ...
📚 Khái niệm mới nắm (giải thích lại bằng lời của mình 1–2 câu —
   nếu giải thích không nổi nghĩa là chưa hiểu thật):
   - ...
🧩 Output/commit hôm nay: [link commit hoặc mô tả]
❌ Còn vướng / chưa hiểu:
   - ...
⏭️ Cần ôn lại trước ngày mai:
   - ...
⏱️ Thời gian thực học: ___ giờ
```

---

# Bảng theo dõi tiến độ

| Ngày | Chủ đề | Done | Commit |
|------|--------|:----:|--------|
| 1 | Setup + Kotlin cơ bản | ☐ | |
| 2 | Kotlin OOP + Collections | ☐ | |
| 3 | Compose cơ bản + State | ☐ | |
| 4 | LazyColumn + Material 3 | ☐ | |
| 5 | Navigation | ☐ | |
| 6 | MVVM (ViewModel/StateFlow) | ☐ | |
| 7 | Milestone tuần 1 | ☐ | |
| 8 | App Lifecycle | ☐ | |
| 9 | Retrofit + API thật | ☐ | |
| 10 | Repository + Hilt + Error | ☐ | |
| 11 | Room + DataStore | ☐ | |
| 12 | Localization VI/EN | ☐ | |
| 13 | Push Notification (FCM) | ☐ | |
| 14 | Performance deep-dive + tổng kiểm tra | ☐ | |
| 15 | Polish + Build + Tổng kết | ☐ | |

---

# Nguồn học chính
- **Android Basics with Compose** (Google, miễn phí) — bám song song.
- **developer.android.com** — tài liệu chính chủ cho Compose, lifecycle, Room, navigation.
- **Material 3 Design Kit (Figma)** — đối chiếu component design ↔ code.
- **TMDB API docs** — endpoint dữ liệu phim.
- **Firebase docs** — phần Cloud Messaging cho Day 13.
