package helper;

import model.*;

public class OrderSenderTestHelper {

    public static ColdOrder createColdOrder(){
        return  new ColdOrder();
    }
    public static HotOrder createHotOrder(OrderType type, SugarQuantity sugarQuantity, boolean extraHot){
        return  new HotOrder(type,sugarQuantity,extraHot);
    }

}
