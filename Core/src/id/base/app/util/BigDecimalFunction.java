package id.base.app.util;

import id.base.app.SystemConstant;

import java.math.BigDecimal;

public class BigDecimalFunction {

	public static BigDecimal divide(BigDecimal var1, BigDecimal var2) {
		return var1.divide(var2, SystemConstant.BIGDECIMAL_SCALE, SystemConstant.BIGDECIMAL_ROUNDING);
	}
	
}
