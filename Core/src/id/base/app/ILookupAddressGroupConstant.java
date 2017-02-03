package id.base.app;

import java.util.HashMap;
import java.util.Map;

public class ILookupAddressGroupConstant {

	public static final int PROVINSI_INT	= 1;
	public static final int KAB_KOTA_INT 	= 2;
	public static final int KECAMATAN_INT 	= 3;
	public static final int KELURAHAN_INT 	= 4;
	
	public static final String PROVINSI = "PROVINSI";
	public static final String KAB_KOTA = "KAB_KOTA";
	public static final String KECAMATAN = "KECAMATAN";
	public static final String KELURAHAN =  "KELURAHAN";
	
	public static Map<Integer, String> ADDRESS_GROUP_MAP = new HashMap<>();
	static{
		ADDRESS_GROUP_MAP.put(PROVINSI_INT, PROVINSI);
		ADDRESS_GROUP_MAP.put(KAB_KOTA_INT, KAB_KOTA);
		ADDRESS_GROUP_MAP.put(KECAMATAN_INT, KECAMATAN);
		ADDRESS_GROUP_MAP.put(KELURAHAN_INT, KELURAHAN);
	};
	
}