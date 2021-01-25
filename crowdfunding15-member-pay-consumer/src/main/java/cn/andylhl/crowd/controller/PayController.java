package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.api.MySQLRemoteService;
import cn.andylhl.crowd.config.PayProperties;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.utils.UUIDUtil;
import cn.andylhl.crowd.vo.OrderProjectVO;
import cn.andylhl.crowd.vo.OrderVO;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/***
 * @Title: PayController
 * @Description:
 * @author: lhl
 * @date: 2021/1/24 22:39
 */

@Controller
public class PayController {

    private Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private PayProperties payProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 调用支付宝接口，完成支付结算。
     * @param orderVO
     * @param session
     * @return 支付宝接口返回我们一个页面内容（用于去登录）
     * @throws AlipayApiException
     */
    @RequestMapping("/generate/order")
    public @ResponseBody String generateOrder(OrderVO orderVO, HttpSession session, @RequestParam("memberId") String memberId) throws AlipayApiException {
        logger.info("crowd-pay服务，完成支付结算");
        // 1. 从session中取出orderProjectVO
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        // 将会员id设置到orderProjectVO中
        orderProjectVO.setMemberId(memberId);
        // 2. 将orderProjectVO组装到orderV中
        orderVO.setOrderProjectVO(orderProjectVO);
        // 3. 完善orderVO中的信息
        // 订单号
        String currentTime = DateUtil.format(new Date(), Constant.ORDER_DATE_Format_ALL);
        String uuid = UUIDUtil.getUUID();
        String orderNum = currentTime + uuid;
        orderVO.setOrderNum(orderNum);
        // 计算支付总金额
        double orderAmount = (double)(orderProjectVO.getSupportPrice() * orderProjectVO.getReturnCount() + orderProjectVO.getFreight());
        orderVO.setOrderAmount(orderAmount);
        logger.info("订单对象内容" + orderVO.toString());
        // 4. 将orderVO放到session中
        session.setAttribute("orderVO", orderVO);

        // 5. 调用封装好的方法给支付宝接口发送请求
        return sendRequestToAliPay(orderNum, orderAmount, orderVO.getOrderProjectVO().getProjectName(), orderVO.getOrderProjectVO().getReturnContent());
    }


    /**
     * 装好的方法给支付宝接口发送请求（这里是我们去调用支付宝的接口）
     * @param orderNum 商户订单号, 我们自己生成的
     * @param orderAmount 付款金额
     * @param subject 订单名称
     * @param body 商品描述
     * @return 返回到页面上显示的支付宝登录界面
     * @throws AlipayApiException
     */
    private String sendRequestToAliPay(String orderNum, Double orderAmount, String subject, String body) throws AlipayApiException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                payProperties.getGatewayUrl(),
                payProperties.getAppId(),
                payProperties.getMerchantPrivateKey(),
                "json",
                payProperties.getCharset(),
                payProperties.getAlipayPublicKey(),
                payProperties.getSignType());

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(payProperties.getReturnUrl());
        alipayRequest.setNotifyUrl(payProperties.getNotifyUrl());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ orderNum +"\","
                + "\"total_amount\":\""+ orderAmount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        // 将请求结果返回
        return result;
    }

    @RequestMapping("/return")
    public @ResponseBody String returnUrlMethod(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                payProperties.getAlipayPublicKey(),
                payProperties.getCharset(),
                payProperties.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 将订单数据保存到数据库
            logger.info("将订单数据保存到数据库");
            // 调用远程服务保存订单数据
            OrderVO orderVO = (OrderVO) session.getAttribute("orderVO");
            // 设置支付宝交易号
            orderVO.setPayOrderNum(payOrderNum);
            ResultEntity<String> resultEntity = mySQLRemoteService.saveOrderVORemote(orderVO);
            if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
                logger.info("订单数据保存成功");
            }
            else {
                logger.info("订单数据保存失败");
                logger.info(resultEntity.getMessage());
            }

            return "商户订单号: "+orderNum+"<br/>支付宝交易号:"+payOrderNum+"<br/>付款金额:"+orderAmount;
        }else {
            return "验签失败";
        }
    }

    @RequestMapping("/notify")
    public @ResponseBody void notifyUrlMethod(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, payProperties.getAlipayPublicKey(), payProperties.getCharset(), payProperties.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            logger.info("商户订单号: " + out_trade_no);
            logger.info("支付宝交易号: " + trade_no);
            logger.info("交易状态: " + trade_status);
            logger.info("交易success");

        }else {//验证失败
            logger.info("交易fail");

        }
    }
}
/*
订单对象内容OrderVO{id='null', orderNum='20210125184226e0fb8b785a7c4197b975507e22734b19', payOrderNum='null', orderAmount=339.0, invoice=1, invoiceTitle='公司', orderRemark='备注1', addressId='1c3ee19842544b879cebd0ba8d21643e', orderProjectVO=OrderProjectVO{id='null', projectName='iphone12', launchName='苹果公司', returnContent='MagSafe 充电器', returnCount=1, count=3, remainCount=1, supportPrice=329, freight=10, orderId=null, signalPurchase=1, purchase=3}}
 */