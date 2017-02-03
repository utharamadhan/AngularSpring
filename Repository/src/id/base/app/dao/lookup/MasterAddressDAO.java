package id.base.app.dao.lookup;

import id.base.app.AbstractHibernateDAO;
import id.base.app.ILookupAddressGroupConstant;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.LookupAddress;
import id.base.app.valueobject.MasterAddress;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class MasterAddressDAO extends AbstractHibernateDAO<MasterAddress,Long> implements IMasterAddressDAO {

	@Override
	public MasterAddress findById(Long id) throws SystemException {
		return super.findByPK(id);
	}

	@Override
	public void saveOrUpdate(MasterAddress anObject) throws SystemException {
		if(anObject.getPkMasterAddress()==null)
			super.create(anObject);
		else
		    super.update(anObject);
	}

	@Override
	public void delete(Long[] ids) throws SystemException {
		List<MasterAddress> objectList = new ArrayList<MasterAddress>();
		for(int i=0;i<ids.length;i++){
			MasterAddress object = new MasterAddress();
			object = findById(ids[i]);
			objectList.add(object);
		}
		super.delete(objectList);
	}

	@Override
	public List<MasterAddress> findObjects(Long[] objectPKs)
			throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<MasterAddress> findAllByFilter(int startNo,
			int offset, List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return null;
	}
	
	@Override
	public List<LookupAddress> findAddressByParent(String addressGroupSource, Long fkLookupAddressParent) throws SystemException {
		Criteria crit = getSession().createCriteria(MasterAddress.class);
		
		ProjectionList projectionList = Projections.projectionList();
		if(ILookupAddressGroupConstant.PROVINSI.equalsIgnoreCase(addressGroupSource)){
			crit.createAlias(MasterAddress.PROVINSI, MasterAddress.PROVINSI);
			crit.createAlias(MasterAddress.KAB_KOTA, MasterAddress.KAB_KOTA);
			crit.add(Restrictions.eq(MasterAddress.PROVINSI_ID, fkLookupAddressParent));
			projectionList.add(Projections.groupProperty(MasterAddress.KAB_KOTA_ID));
			projectionList.add(Projections.property(MasterAddress.KAB_KOTA_NAME));
		}else if(ILookupAddressGroupConstant.KAB_KOTA.equalsIgnoreCase(addressGroupSource)){
			crit.createAlias(MasterAddress.KAB_KOTA, MasterAddress.KAB_KOTA);
			crit.createAlias(MasterAddress.KECAMATAN, MasterAddress.KECAMATAN);
			crit.add(Restrictions.eq(MasterAddress.KAB_KOTA_ID, fkLookupAddressParent));
			projectionList.add(Projections.groupProperty(MasterAddress.KECAMATAN_ID));
			projectionList.add(Projections.property(MasterAddress.KECAMATAN_NAME));
		}else if(ILookupAddressGroupConstant.KECAMATAN.equalsIgnoreCase(addressGroupSource)){
			crit.createAlias(MasterAddress.KECAMATAN, MasterAddress.KECAMATAN);
			crit.createAlias(MasterAddress.KELURAHAN, MasterAddress.KELURAHAN);
			crit.add(Restrictions.eq(MasterAddress.KECAMATAN_ID, fkLookupAddressParent));
			projectionList.add(Projections.groupProperty(MasterAddress.KELURAHAN_ID));
			projectionList.add(Projections.property(MasterAddress.KELURAHAN_NAME));
		}
		crit.setProjection(projectionList);
		
		crit.setResultTransformer(new ResultTransformer() {
			
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				LookupAddress la = new LookupAddress();
				try{
					la.setPkLookupAddress(Long.valueOf(tuple[0].toString()));
					la.setName(tuple[1].toString());
				}catch(Exception e){
					LOGGER.error(e.getMessage(), e);
				}
				return la;
			}
			
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		
		List<LookupAddress> maList = null;
		
		try{
			maList = crit.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return maList;
	}

	@Override
	public List<Integer> searchPostalCode(String keyword) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.sqlRestriction("KODEPOS::VARCHAR(255) LIKE '%" + keyword + "%'"));
			crit.addOrder(Order.asc(MasterAddress.KODEPOS));
			crit.setProjection(Projections.projectionList().add(Projections.property(MasterAddress.KODEPOS)));
			crit.setResultTransformer(new ResultTransformer() {
				@Override
				public Object transformTuple(Object[] tuple, String[] aliases) {
					Integer result = null;
					try{
						result = Integer.parseInt(tuple[0].toString());
					}catch(Exception e){
						LOGGER.error(e.getMessage(), e);
					}
					return result;
				}
				
				@Override
				public List transformList(List collection) {
					return collection;
				}
			});
		return crit.list();
	}
}