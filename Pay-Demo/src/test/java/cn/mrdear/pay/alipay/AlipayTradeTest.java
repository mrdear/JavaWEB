package cn.mrdear.pay.alipay;

import com.alipay.api.AlipayApiException;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
public class AlipayTradeTest {

    @Test
    public void testTradeWapPayRequest(){
        AlipayTrade alipayTrade = new AlipayTrade();
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put("out_trade_no",System.currentTimeMillis()+"");
        paraMap.put("total_amount","0.01");
        paraMap.put("subject","测试下单");
        paraMap.put("product_code","QUICK_WAP_PAY");
        alipayTrade.TradeWapPayRequest(paraMap);
    }
    @Test
    public void testRefund() throws AlipayApiException {
        AlipayTrade alipayTrade = new AlipayTrade();
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("trade_no","2016103121001004620234152469");
        paraMap.put("refund_amount","0.01");
        paraMap.put("refund_reason","测试退款");
        paraMap.put("out_request_no","HZ01RF001");
        paraMap.put("operator_id","OP001");
        paraMap.put("store_id","NJ_S_001");
        paraMap.put("terminal_id","NJ_T_001");
        alipayTrade.tradeRefundRequest(paraMap);
    }
}
