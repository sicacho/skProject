<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="include/header.jsp" %>
<title>Lazy English-Học tiếng anh dành cho người bận rộn</title>

    <div class="row">
        <div class="col-xs-12 col-sm-8 col-md-5 col-sm-offset-2 col-md-offset-4">
            <form role="form" action="<c:url value="/register"/>" method="post">
                <h2>Please Sign Up
                    <small>It's free and always will be.</small>
                </h2>
                <hr class="colorgraph">
                <c:if test="${error != null}">
                    <div class="form-group alert alert-error">
                        <a class="close" data-dismiss="alert" href="#">×</a>${error}
                    </div>
                </c:if>
                <div class="form-group">
                    <input type="text" name="username" id="username" class="form-control input-lg"
                           placeholder="Username" required="true" pattern="[a-zA-Z0-9]{4,16}" title="alpha-numeric only, at least is 4 character and max is 16 character"
                           tabindex="3">
                </div>
                <div class="form-group">
                    <input type="email" name="email" id="email" class="form-control input-lg"
                           placeholder="Email Address" tabindex="4">
                </div>
                <div class="form-group">
                    <input type="password" name="password" id="password" class="form-control input-lg" pattern=".{4,32}" title="at least is 4 character and max is 32 character"
                           placeholder="Password" tabindex="5">
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-3 col-md-3">
					<span class="button-checkbox">
						<button type="button" class="btn" data-color="info" tabindex="7">I Agree</button>
                        <input type="checkbox" name="t_and_c" id="t_and_c" class="hidden" value="1">
					</span>
                    </div>
                    <div class="col-xs-8 col-sm-9 col-md-9">
                        By clicking <strong class="label label-primary">Register</strong>, you agree to the <a href="#"
                                                                                                               data-toggle="modal"
                                                                                                               data-target="#t_and_c_m">Terms
                        and Conditions</a> set out by this site, including our Cookie Use.
                    </div>
                </div>

                <hr class="colorgraph">
                <div class="row">
                    <div style="text-align: center;margin: auto;width: 50%">
                        <input type="submit" value="Register" class="btn btn-primary btn-block btn-lg" tabindex="7">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="t_and_c_m" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">Terms & Conditions</h4>
                </div>
                <div class="modal-body">
                    <p>Lazyeng.com là một cộng đồng trực tuyến được xây dựng nhằm mục tiêu nâng cao khả năng tiếng Anh của mỗi thành viên. Tham gia tiếnganh123.com, bạn có thể học các bài học tiếng Anh trên website, nhận xét các bài học đó, thảo luận với những bạn bè khác trong cộng đồng để cùng nhau nâng cao kỹ năng sử dụng tiếng Anh của mình và giúp đỡ những người khác làm điều đó. Bạn cũng có thể tạo cho mình một không gian riêng để lưu trữ những thông tin cá nhân để chia xẻ với những người khác.
                    </p>

                    <p>Khi bạn tham gia Lazyeng.com, xin vui lòng đọc kỹ những điều khoản trong bản nội quy. Việc sử dụng các tiện ích của chúng tôi đồng nghĩa với việc bạn đồng ý với bản nội quy này. Nhằm giúp cho cộng đồng vững mạnh và phát triển hơn, Ban quản trị có quyền thay đổi nội dung của bản nội quy này trên cơ sở đảm bảo tối đa quyền lợi và nghĩa vụ của các thành viên mà không cần phải báo trước. Ban quản trị sẽ có thông báo sau mỗi lần thay đổi nội quy.</p>

                    <p>I. Quy định đăng ký thành viên và các thông tin cá nhân
                    </p>

                    <p>1. Thành viên mới đăng ký không được sử dụng tên đăng nhập (username) gây trùng lặp với username đã có, cố tình gây ra sự nhầm lẫn. Không được đặt username theo tên của danh nhân, tên của các vị lãnh đạo của Đảng và Nhà nước, tên của kẻ xấu (được hiểu như: trùm khủng bố, phát xít, tội phạm,...). Không được đặt username có ý nghĩa không lành mạnh, không trong sáng, dễ gây hiểu lầm. Tên username đã đăng ký rồi sẽ không được thay đổi, do đó các bạn cần chọn lựa thật kỹ trước khi đăng ký làm thành viên của Lazyeng.com. Không được sử dụng email của người khác (hoặc email giả) để đăng ký.
                    </p>

                    <p>2. Thành viên không được sử dụng biểu tượng (avatar) là các hình ảnh có nội dung không lành mạnh, ghê rợn, bạo lực, sexy, vi phạm thuần phong mỹ tục của Việt Nam. Không được sử dụng ảnh cá nhân của người khác làm avatar của mình.
                    </p>

                    <p>II. Quy định về nội dung được đăng tải</p>

                    <p>1. Bài viết nên sử dụng tiếng Việt có dấu. Tránh gửi các bài viết sai chính tả hoặc dùng ngôn ngữ chat, những bài viết này sẽ được nhắc nhở và biên tập lại. Hãy giữ cho tiếng Việt của chúng ta được trong sáng.
                    </p>

                    <p>2. Đối với những bài viết bằng tiếng nước ngoài, thành viên phải tự chịu trách nhiệm toàn bộ về nội dung bài viết.</p>

                    <p>3. Bài viết phải có nội dung liên quan đến chủ đề, không đưa thông tin thiếu tính xác thực, gây hiểu lẩm.
                    </p>

                    <p>4. Bài viết không được sử dụng từ ngữ thô tục, mất văn hoá, xúc phạm, vu khống thành viên khác.
                    </p>

                    <p>6. Không được đưa các hình ảnh bạo lực, đồi trụy, ghê rợn, có tính chất không lành mạnh, không phù hợp với truyền thống văn hóa Việt Nam.
                    </p>

                    <p>7. Nghiêm cấm việc đưa những thông tin bất hợp pháp sau đây:
                    </p>

                    <p>- Các thông tin có nội dung ảnh hưởng đến an ninh quốc gia: xuyên tạc và tuyên truyền chống đối các chủ trương chính sách của Ðảng và nhà nước, phá hoại khối đoàn kết toàn dân, các thông tin về chính trị nhạy cảm, quốc phòng, phản gián,...
                    </p>

                    <p>- Các thông tin có nội dung kích động bạo lực, tuyên truyền chiến tranh xâm lược, gây hận thù các dân tộc và nhân dân các nước, truyền bá tư tưởng phản động,...</p>

                    <p>- Các thông tin hoặc từ ngữ trái với thuần phong mỹ tục như khiêu dâm, đồi trụy, tệ nạn xã hội nghiện hút...
                    </p>

                    <p>- Các thông tin nhằm bôi nhọ, gây ảnh hưởng đến uy tín, danh dự của các tổ chức và cá nhân.
                    </p>

                    <p>- Các thông tin có nội dung ảnh hưởng đến an ninh kinh tế: lừa đảo, bí mật kinh tế,...
                    </p>

                    <p>- Các thông tin ảnh hưởng đến an toàn thông tin như virus, ăn cắp, phá hoại cơ sở dữ liệu,...
                    </p>

                    <p>- Các thông tin có ảnh hưởng xấu đối với văn hoá xã hội như xuyên tạc lịch sử, phủ nhận thành quả cách mạng, xúc phạm vĩ nhân và anh hùng dân tộc, xúc phạm uy tín tổ chức, trái với bản sắc văn hoá dân tộc, phao tin đồn nhảm,...</p>

                    <p>- Các thông tin ảnh hưởng đến tự do tín ngưỡng, tôn giáo của nhân dân.
                    </p>

                    <p>Khi gặp các nội dung này, Ban quản trị Lazyeng.com có toàn quyền và ngay lập tức mà không cần báo trước thực hiện việc xoá bỏ nội dung và có thể có các biện pháp đi kèm như cảnh cáo, khoá tài khoản tạm thời hoặc vĩnh viễn hoặc chuyển hồ sơ vi phạm đến các cơ quan chức năng xử lý theo pháp luật trong trường hợp cần thiết.

                    </p>

                    <p>8. Các thành viên phải tự chịu toàn bộ trách nhiệm về những nội dung do chính mình đưa lên Lazyeng.com. Bạn được phép gửi các bài viết, tin tức sưu tầm từ các nơi khác nhưng phải ghi rõ xuất xứ, nguồn gốc. Bạn đồng ý trả mọi bồi thường cũng như chi phí khác cho người có liên quan trong trường hợp có tranh chấp theo quy định của pháp luật hiện hành.
                    </p>

                    <p>9. Chúng tôi không chịu trách nhiệm về bất cứ nội dung nào do thành viên đăng tải trên Lazyeng.com. Thành viên khi gửi nội dung lên hệ thống đồng nghĩa là chấp nhận cho Ban quản trị toàn quyền sử dụng, chỉnh sửa, tái xử lý, phân phối nội dung đó trong nội bộ mạng Lazyeng.com.
                    </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">I Agree</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->

        <script>
            $(function () {
                $('.button-checkbox').each(function () {

                    // Settings
                    var $widget = $(this),
                            $button = $widget.find('button'),
                            $checkbox = $widget.find('input:checkbox'),
                            color = $button.data('color'),
                            settings = {
                                on: {
                                    icon: 'glyphicon glyphicon-check'
                                },
                                off: {
                                    icon: 'glyphicon glyphicon-unchecked'
                                }
                            };

                    // Event Handlers
                    $button.on('click', function () {
                        $checkbox.prop('checked', !$checkbox.is(':checked'));
                        $checkbox.triggerHandler('change');
                        updateDisplay();
                    });
                    $checkbox.on('change', function () {
                        updateDisplay();
                    });

                    // Actions
                    function updateDisplay() {
                        var isChecked = $checkbox.is(':checked');

                        // Set the button's state
                        $button.data('state', (isChecked) ? "on" : "off");

                        // Set the button's icon
                        $button.find('.state-icon')
                                .removeClass()
                                .addClass('state-icon ' + settings[$button.data('state')].icon);

                        // Update the button's color
                        if (isChecked) {
                            $button
                                    .removeClass('btn-default')
                                    .addClass('btn-' + color + ' active');
                        }
                        else {
                            $button
                                    .removeClass('btn-' + color + ' active')
                                    .addClass('btn-default');
                        }
                    }

                    // Initialization
                    function init() {

                        updateDisplay();

                        // Inject the icon if applicable
                        if ($button.find('.state-icon').length == 0) {
                            $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i>');
                        }
                    }

                    init();
                });
            });
        </script>
    </div>
</div>
<%@include file="include/footer.jsp" %>