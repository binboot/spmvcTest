 var globalVar={
 }


$(document).ready(function(){
	//先将string类型转换成Boolean类型
	var is30DayConsumerVar=new Boolean($("#is30DayConsumer").val());
	chenshuiyonghuFun(is30DayConsumerVar,"","");
});



function chenshuiyonghuFun(isBaiTiaoDeadUser, baiTiaoCouponCode,
		baiTiaoActivityCode) {
	// 当无优惠活动时
	// 为沉睡用户且无优惠券且不参加活动（即活动编号为空），显示“打白条 返5元（移动端京券，30日内到账）”
	if ((true == isBaiTiaoDeadUser) && ("" == baiTiaoCouponCode)
			&& ("" == baiTiaoActivityCode)) {
		$("#shengshuiUserTips")
				.html(
						" <span style='font-weight:900;color:#FF5D5B;font-size:18px;font-family: Microsoft Yahei;'>打白条 返<span style=';font-size:24px'>5</span>元</span>（移动端京券,30日内到账）");
	} else {
		// 当无优惠活动，也不为沉睡用户或有优惠券时
		$("#shengshuiUserTips").html("");
	}
}