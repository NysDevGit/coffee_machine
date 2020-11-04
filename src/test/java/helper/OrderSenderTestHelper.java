package helper;

import model.order.ColdOrder;
import model.order.HotOrder;
import model.order.SugarQuantity;
import model.order.type.HotOrderType;

public class OrderSenderTestHelper {

    public static ColdOrder createColdOrder(){
        return  new ColdOrder();
    }
    public static HotOrder createHotOrder(HotOrderType type, SugarQuantity sugarQuantity, boolean extraHot){
        return  new HotOrder(type,sugarQuantity,extraHot);
    }

}
